package com.old.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.old.common.serializer.ObjectMapperProvider;
import com.old.common.util.function.OldSupplier;

import java.io.Reader;
import java.lang.reflect.Type;

public class TypeJsonUtil {

    private static final ObjectMapper TYPE_OM = ObjectMapperProvider.defaultTypeMapper();


    public static <T> T toObj(String json, Class<T> tClass) {
        return execute(() -> TYPE_OM.readValue(json, tClass));
    }


    public static String toJson(Object obj) {
        return execute(() -> TYPE_OM.writeValueAsString(obj));
    }

    public static <T> T toObj(String json, TypeReference<T> tTypeReference) {
        return execute(() -> TYPE_OM.readValue(json, tTypeReference));
    }

    public static <T> T toObj(Reader reader, Type type) {
        return execute(() -> TYPE_OM.readValue(reader, TYPE_OM.constructType(type)));
    }

    private static <T> T execute(OldSupplier<T> oldSupplier) {
        try {
            return oldSupplier.get();
        } catch (Throwable e) {
            logException(e);
        }
        return null;
    }


    private static void logException(Throwable t) {
        // t.printStackTrace();
        t.printStackTrace(System.out);
    }

}
