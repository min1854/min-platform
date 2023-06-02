package com.old.common.serializer;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.TimeZone;

public class ObjectMapperProvider {

    private static void logException(Throwable t) {
        t.printStackTrace();
        // t.printStackTrace(System.out);
    }

    public static ObjectMapper defaultMapper() {
        return Inner.OBJECT_MAPPER;
    }

    public static ObjectMapper defaultTypeMapper() {
        return Inner.TYPE_OBJECT_MAPPER;
    }

    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, false);
        // 如果一个类没有 get、set 方法，或是没有可输出的属性，就会抛出异常。false 不抛出异常
        // mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略不存在的字段
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        mapper.setTimeZone(TimeZone.getDefault());
        mapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        mapper.registerModule(localDateTimeModule());

        return mapper;
    }

    public static ObjectMapper typeObjectMapper() {
        ObjectMapper mapper = objectMapper();

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 这个会将 json 保存一个类型的字段
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return mapper;
    }

    private static Module localDateTimeModule() {
        SimpleModule module = new SimpleModule();

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(" ")
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .toFormatter();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        module.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        module.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        module.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        return module;
    }

    private static class Inner {
        public static final ObjectMapper OBJECT_MAPPER = ObjectMapperProvider.objectMapper();
        public static final ObjectMapper TYPE_OBJECT_MAPPER = ObjectMapperProvider.typeObjectMapper();
    }
}
