package com.old.common.base;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.old.common.serializer.ObjectMapperProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

@Slf4j
public class BaseTest {


    List<LogMessage> messages = new ArrayList<>(3);

    public BaseTest debug(String msg, Object... objects) {
        log.debug(msg, objects);
        return this;
    }

    public BaseTest info(String msg, Object... objects) {
        log.info(msg, objects);
        return this;
    }

    public BaseTest warn(String msg, Object... objects) {
        log.warn(msg, objects);
        return this;
    }

    public BaseTest error(String msg, Object... objects) {
        log.error(msg, objects);
        return this;
    }

    public BaseTest atLastLog(String msg, Object result) {
        messages.add(new LogMessage(msg, result));
        return this;
    }

    public void ignoreThrowableRun(Run runnable) {
        System.out.println();
        try {
            runnable.run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println();
    }

    public <T> T ignoreThrowableCall(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @AfterEach
    public void echoResult() throws JsonProcessingException {
        if (messages.isEmpty()) {
            return;
        }
        abstractEcho("结果：\r\n", (sb, obj) -> sb.append(((LogMessage) obj).getMsg() + "： "), (sb, obj) -> sb.append("\r\n"),
                messages.toArray());
    }

    public BaseTest echoDefaultJsonResult(Object... objects) {
        abstractEcho("结果：", objects);
        return this;
    }

    public BaseTest echoJsonResult(String msg, Object... objects) {
        abstractEcho(msg, objects);
        return this;
    }

    private void abstractEcho(String msg, Object... objects) {
        abstractEcho(msg, (sb, obj) -> {
        }, (sb, obj) -> {
        }, objects);
    }

    private void abstractEcho(String msg, BiConsumer<StringBuilder, Object> before, BiConsumer<StringBuilder, Object> after,
                              Object... objects) {
        StringBuilder stringBuilder = new StringBuilder(msg);
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            before.accept(stringBuilder, object);
            stringBuilder.append(" {} ");
            after.accept(stringBuilder, object);
            objects[i] = objectToJsonString(object instanceof LogMessage ? ((LogMessage) object).getResult() : object);
        }
        log.info(stringBuilder.toString(), objects);
    }

    private String objectToJsonString(Object object) {
        if (object instanceof String) {
            return (String) object;
        } else if (object instanceof JSONObject) {
            return ((JSONObject) object).toString();
        } else if (object instanceof JSONArray) {
            return ((JSONArray) object).toString();
        } else if (object == null) {
            return null;
        } else if (!(object instanceof Serializable)) {
            return object.toString();
        } else if (object instanceof Number) {
            return object.toString();
        } else {
            try {
                return ObjectMapperProvider.defaultMapper().writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.error("序列化为 json 失败，结果：{}", object, e);
            }
            return null;
        }
    }

    public static interface Run {
        public abstract void run() throws Throwable;
    }

    @Data
    @AllArgsConstructor
    public static class LogMessage {
        String msg;
        Object result;
    }


}
