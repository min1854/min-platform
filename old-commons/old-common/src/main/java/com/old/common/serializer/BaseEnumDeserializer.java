package com.old.common.serializer;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.old.common.base.BaseEnum;
import com.old.common.base.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Function;

/**
 * 面向接口的枚举反序列化器
 *
 * @author eric
 * @date 2020/9/2
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {
    @Override
    public BaseEnum<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        // 前端输入的值
        String inputParameter = jp.getText();
        if (inputParameter == null || inputParameter.length() == 0) {
            return null;
        }


        JsonStreamContext parsingContext = jp.getParsingContext();

        Object currentValue = parsingContext.getCurrentValue();
        // 字段名
        String currentName = parsingContext.getCurrentName();

        JsonStreamContext parent = parsingContext.getParent();

        if (currentValue == null) {
            // 声明的参数类型（@RequestBody 对应的参数），
            currentValue = parent.getCurrentValue();
        }
        if (currentName == null) {
            currentName = parent.getCurrentName();
            if (currentName == null) {
                log.error("字段名字为空 {}", currentName);
            }
        }
        if (currentValue == null) {
            throw new BaseException("自定义枚举反序列化错误:json的这个字段[" + parsingContext.getParent() + "]没有值。枚举反序列化失败,注意该属性不可以使用lombok的注解，如@NonNull等");
        }


        JsonToken currentToken = jp.getCurrentToken();
        Serializable value = null;
        String desc = null;
        // 通过对象和属性名获取属性的类型
        Class<?> enumClass = ReflectUtil.getField(currentValue.getClass(), currentName).getType();
        if (currentToken == JsonToken.START_OBJECT) {
            // 这里专门解决自定义序列化的结果，再次原样反序列化的处理。适用场景，mq，redis，MongoDB等存储。或者okhttps的restful调用。
            // 这里已经是 枚举的对象格式的 json 了
            TreeNode treeNode = jp.readValueAsTree();
            if (treeNode != null) {
                value = findValue(treeNode, enumClass);
                desc = findDesc(treeNode, enumClass);
            } else {
                return null;
            }
        }
//        还是不要这样，既然是 json 成了对象的格式，反序列化时也是对象的格式吧
//        else if (currentToken == JsonToken.VALUE_STRING) {
//            value = jp.getValueAsString();
//        } else if (currentToken == JsonToken.VALUE_NUMBER_INT) {
//            value = jp.getValueAsInt();
//        }


        return DefaultInputJsonToEnum.getEnum(value, desc, enumClass);


    }

    public Serializable findValue(TreeNode enumNode, Class<?> enumClass) {
        if (enumNode != null) {
            TreeNode valueNode = enumNode.get(getBaseField(enumClass, BaseEnum.BaseFiled::valueField));
            if (valueNode instanceof TextNode) {
                return ((TextNode) valueNode).asText();
            } else if (valueNode instanceof ShortNode) {
                return ((ShortNode) valueNode).shortValue();
            } else if (valueNode instanceof NumericNode) {
                NumericNode numericNode = (NumericNode) valueNode;
                switch (numericNode.numberType()) {
                    case DOUBLE:
                        return numericNode.doubleValue();
                    case FLOAT:
                        return numericNode.floatValue();
                    case LONG:
                        return numericNode.longValue();
                    case INT:
                        return numericNode.intValue();
                    case BIG_DECIMAL:
                        return numericNode.decimalValue();
                    case BIG_INTEGER:
                        return numericNode.bigIntegerValue();
                    default:
                        return null;
                }
            }
        }
        return null;
    }

    public String findDesc(TreeNode enumNode, Class<?> enumClass) {
        if (enumNode != null) {
            TreeNode valueNode = enumNode.get(getBaseField(enumClass, BaseEnum.BaseFiled::descField));
            if (valueNode instanceof TextNode) {
                return ((TextNode) valueNode).asText();
            }
        }
        return null;
    }


    public String getBaseField(Class<?> enumClass, Function<BaseEnum.BaseFiled, String> function) {
        BaseEnum.BaseFiled baseFiled = AnnotationUtil.getAnnotation(enumClass, BaseEnum.BaseFiled.class);
        if (baseFiled == null) {
            baseFiled = AnnotationUtil.getAnnotation(BaseEnum.class, BaseEnum.BaseFiled.class);
        }
        if (baseFiled != null) {
            return function.apply(baseFiled);
        }
        return null;
    }
}
