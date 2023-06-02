package com.old.common.web.config;

import com.old.common.base.BaseEnum;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.GenericConverter;

import java.io.Serializable;
import java.util.Set;

/**
 * 还没想好要怎么弄
 */
public class StringToEnumConversion implements GenericConverter, ConditionalConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(new ConvertiblePair(Serializable.class, BaseEnum.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.getObjectType().isAssignableFrom(BaseEnum.class);
    }
}
