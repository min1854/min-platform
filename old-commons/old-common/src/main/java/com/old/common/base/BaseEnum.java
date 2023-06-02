package com.old.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.old.common.serializer.BaseEnumDeserializer;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@BaseEnum.BaseFiled
@JsonDeserialize(using = BaseEnumDeserializer.class)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum<T extends Enum<T>> extends Serializable {

    String VALUE_FIELD = "value";
    String DESC_FIELD = "desc";

    Serializable getValue();

    String getDesc();

    /**
     * 这是方法还是有必要的，可以通过 Enum.getDeclaringClass() 类获取实际的类型
     *
     * @return
     */
    Enum<T> self();

    @Retention(RetentionPolicy.RUNTIME)
    @interface BaseFiled {
        String valueField() default VALUE_FIELD;

        String descField() default DESC_FIELD;
    }


    /**
     * 本来想增加 nullValue 和判断是否为空字段的方法，但想到如果这样就一定会有一个默认值代表 null，而这样在 请求返回 json 的序列化时，就会
     * 出现一个 nullValue 感觉也不好，所以不增加了
     * @return
     */
    // T nullValue();
    // boolean isNullValue();

}
