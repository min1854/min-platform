package com.old.common.web.config;

import com.old.common.base.BaseEnum;
import com.old.common.serializer.DefaultInputJsonToEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Component
public class SerializerToBaseEnumConverter implements ConverterFactory<Serializable, BaseEnum<?>> {

    private static final String ENUM_VALUES = "values";

    @Override
    public <T extends BaseEnum<?>> Converter<Serializable, T> getConverter(Class<T> targetType) {
        // values是默认的方法，必定存在

        return (Converter<Serializable, T>) (Converter<Serializable, BaseEnum<?>>) source -> {
            try {
                return DefaultInputJsonToEnum.findEnum(source, targetType);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
