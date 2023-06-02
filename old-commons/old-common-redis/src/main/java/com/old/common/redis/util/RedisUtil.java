package com.old.common.redis.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.extra.spring.SpringUtil;
import com.old.common.enums.redis.RedisKey;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.Function;

public class RedisUtil {
    private static RedisTemplate<Object, Object> redisTemplate;

    static {
        redisTemplate = SpringUtil.getBean(new TypeReference<RedisTemplate<Object, Object>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }


    public static void set(RedisKey redisKey, Object value) {
        set(redisKey, Function.identity(), value);
    }

    public static void set(RedisKey redisKey, String suffix, Object value) {
        set(redisKey, key -> key + suffix, value);
    }

    public static void set(RedisKey redisKey, Function<String, String> keyGen, Object value) {
        redisTemplate.opsForValue().set(keyGen.apply(redisKey.getKey()), value, redisKey.getExpireTime(), redisKey.getExpireTimeUnit());
    }


    public static <T> T get(RedisKey redisKey) {
        return get(redisKey, Function.identity());
    }

    public static <T> T get(RedisKey redisKey, String suffix) {
        return get(redisKey, key -> key + suffix);
    }

    public static <T> T get(RedisKey redisKey, Function<String, String> keyGen) {
        return (T) redisTemplate.opsForValue().get(keyGen.apply(redisKey.getKey()));
    }


    public static void refresh(RedisKey redisKey) {
        refresh(redisKey, Function.identity());
    }

    public static void refresh(RedisKey redisKey, String suffix) {
        refresh(redisKey, key -> key + suffix);
    }

    public static void refresh(RedisKey redisKey, Function<String, String> keyGen) {
        if (redisKey.isItExpire()) {
            redisTemplate.expire(keyGen.apply(redisKey.getKey()), redisKey.getExpireTime(), redisKey.getExpireTimeUnit());
        }
    }


    public static boolean del(RedisKey redisKey) {
        return del(redisKey, Function.identity());
    }

    public static boolean del(RedisKey redisKey, String suffix) {
        return del(redisKey, key -> key + suffix);
    }

    public static boolean del(RedisKey redisKey, Function<String, String> keyGen) {
        Boolean delete = redisTemplate.delete(keyGen.apply(redisKey.getKey()));
        return delete != null && delete;
    }


    public static Long hDel(RedisKey redisKey, String... hashKey) {
        return redisTemplate.opsForHash().delete(redisKey.getKey(), hashKey);
    }


    public static boolean hasKey(RedisKey redisKey) {
        return hasKey(redisKey, Function.identity());
    }

    public static boolean hasKey(RedisKey redisKey, String suffix) {
        return hasKey(redisKey, key -> key + suffix);
    }

    public static boolean hasKey(RedisKey redisKey, Function<String, String> keyGen) {
        Boolean hasKey = redisTemplate.hasKey(keyGen.apply(redisKey.getKey()));
        return hasKey != null && hasKey;
    }

    public static void zadd(RedisKey redisKey, Object value, double score) {
        zadd(redisKey, Function.identity(), value, score);
    }

    public static void zadd(RedisKey redisKey, String suffix, Object value, double score) {
        zadd(redisKey, key -> key + suffix, value, score);
    }

    public static void zadd(RedisKey redisKey, Function<String, String> keyGen, Object value, double score) {
        BoundZSetOperations<Object, Object> ops = redisTemplate.boundZSetOps(keyGen.apply(redisKey.getKey()));
        ops.add(value, score);
        if (redisKey.isItExpire()) {
            refresh(redisKey, keyGen);
        }
    }

    public static void execute(Consumer<RedisTemplate<Object, Object>> process) {
        process.accept(redisTemplate);
    }

    public static <T> T execute(Function<RedisTemplate<Object, Object>, T> process) {
        return process.apply(redisTemplate);
    }

    public static void hmset(RedisKey redisKey, String field, Object value) {
        hmset(redisKey, Function.identity(), field, value);
    }

    public static void hmset(RedisKey redisKey, String suffix, String field, Object value) {
        hmset(redisKey, key -> key + suffix, field, value);
    }

    public static void hmset(RedisKey redisKey, Function<String, String> keyGen, String field, Object value) {
        BoundHashOperations<Object, Object, Object> ops = redisTemplate.boundHashOps(keyGen.apply(redisKey.getKey()));
        ops.put(field, value);
        if (redisKey.isItExpire()) {
            refresh(redisKey, keyGen);
        }
    }

    public static <T> T hget(RedisKey redisKey, String field) {
        return hget(redisKey, Function.identity(), field);
    }

    public static <T> T hget(RedisKey redisKey, String suffix, String field) {
        return hget(redisKey, key -> key + suffix, field);
    }

    public static <T> T hget(RedisKey redisKey, Function<String, String> keyGen, String field) {
        BoundHashOperations<Object, Object, Object> ops = redisTemplate.boundHashOps(keyGen.apply(redisKey.getKey()));
        return (T) ops.get(field);
    }


    public static Long incr(RedisKey redisKey) {
        return incr(redisKey, Function.identity());
    }


    public static Long incr(RedisKey redisKey, String suffix) {
        return incr(redisKey, key -> key + suffix);
    }


    public static Long incr(RedisKey redisKey, Function<String, String> keyGen) {
        Long increment = redisTemplate.boundValueOps(keyGen.apply(redisKey.getKey())).increment();
        refresh(redisKey, keyGen);
        return increment;
    }
}
