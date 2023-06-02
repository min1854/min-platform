package com.old.common.serializer;

import com.old.common.base.BaseEnum;
import com.old.common.base.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * 传入的参数中,去相应的枚举code中获取
 */
@Slf4j
public class DefaultInputJsonToEnum {

    private static final String ENUM_VALUES = "values";


    public static BaseEnum<?> getEnum(Serializable value, String desc, Class<?> enumClass) {
        try {
            BaseEnum<?> anEnum = findEnum(value, desc, enumClass);
            if (anEnum == null) {
                // 如果都拿不到,那就直接抛出异常了
                log.error("枚举反序列化错误，需要的枚举为：{} 输入参数 value = {}， desc = {} 找不到对应的枚举值", enumClass, value, desc);
                throw new BaseException("枚举反序列化失败");
            }
            return anEnum;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BaseException("json 反序列化为枚举失败", e);
        }
    }

    public static BaseEnum<?> findEnum(Serializable value, String desc, Class<?> enumClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return findEnum(enumClass, baseEnum ->
                baseEnum.getValue().equals(value) && baseEnum.getDesc().equals(desc)
        );
    }


    public static BaseEnum<?> findEnum(Serializable value, Class<?> enumClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return findEnum(enumClass, baseEnum -> baseEnum.getValue().equals(value)
        );
    }

    public static BaseEnum<?> findEnum(Class<?> enumClass, Predicate<BaseEnum<?>> predicate)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // values是默认的方法，必定存在
        Method valuesMethod = enumClass.getDeclaredMethod(ENUM_VALUES);
        // 通过反射获取该枚举类的所有枚举值
        for (BaseEnum<?> baseEnum : (BaseEnum<?>[]) valuesMethod.invoke(null)) {
            if (predicate.test(baseEnum)) {
                return baseEnum;
            }
        }
        return null;
    }

}