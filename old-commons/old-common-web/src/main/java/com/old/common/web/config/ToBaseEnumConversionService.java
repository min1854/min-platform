package com.old.common.web.config;

import com.old.common.base.BaseEnum;
import com.old.common.serializer.DefaultInputJsonToEnum;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Component
public class ToBaseEnumConversionService implements ConversionService, WebMvcConfigurer {
    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return targetType.isAssignableFrom(BaseEnum.class);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getObjectType().isAssignableFrom(BaseEnum.class);
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        try {
            return (T) DefaultInputJsonToEnum.findEnum((Serializable) source, targetType);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return source == null ? null : ((BaseEnum<?>) source).getValue();
    }
}
