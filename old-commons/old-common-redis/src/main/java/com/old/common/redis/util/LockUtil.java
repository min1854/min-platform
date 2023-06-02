package com.old.common.redis.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.extra.spring.SpringUtil;
import com.old.common.enums.redis.RedisKey;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.Function;

public class LockUtil {
    private static final Runnable EMPTY = new Runnable() {
        @Override
        public void run() {

        }
    };
    private static RedisTemplate<Object, Object> redisTemplate;

    static {
        redisTemplate = SpringUtil.getBean(new TypeReference<RedisTemplate<Object, Object>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }

    public static void hashLock(RedisKey keyEnum, Object hashKey, Object lockEntity, Runnable success) {
        hashLock(keyEnum, hashKey, lockEntity, success, EMPTY);
    }

    public static void hashLock(RedisKey keyEnum, Object hashKey, Object lockEntity, Runnable success, Runnable fail) {
        hashLock(keyEnum, hashKey, lockEntity, flag -> {
            if (flag) {
                success.run();
            } else {
                fail.run();
            }
        });
    }


    public static void hashLock(RedisKey keyEnum, Object hashKey, Object lockEntity, Consumer<Boolean> lock) {
        BoundHashOperations<Object, Object, Object> ops = redisTemplate.boundHashOps(keyEnum.getKey());
        try {
            lock.accept(ops.putIfAbsent(hashKey, lockEntity));
        } finally {
            ops.delete(hashKey);
        }
    }

    public static void lock(RedisKey keyEnum, Function<String, String> keyGen, Object lockEntity, Runnable success) {
        lock(keyEnum, keyGen, lockEntity, success, EMPTY);
    }

    public static void lock(RedisKey keyEnum, Object lockEntity, Runnable success) {
        lock(keyEnum, Function.identity(), lockEntity, success, EMPTY);
    }

    public static void lock(RedisKey keyEnum, Object lockEntity, Runnable success, Runnable fail) {
        lock(keyEnum, Function.identity(), lockEntity, success, fail);
    }

    public static void lock(RedisKey keyEnum, Function<String, String> keyGen, Object lockEntity, Runnable success, Runnable fail) {
        lock(keyEnum, keyGen, lockEntity, flag -> {
            if (flag) {
                success.run();
            } else {
                fail.run();
            }
        });
    }

    public static void lock(RedisKey keyEnum, Object lockEntity, Consumer<Boolean> lock) {
        lock(keyEnum, Function.identity(), lockEntity, lock);
    }


    public static void lock(RedisKey redisKey, Function<String, String> keyGen, Object lockEntity, Consumer<Boolean> lock) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        String key = keyGen.apply(redisKey.getKey());
        try {
            lock.accept(valueOperations.setIfAbsent(key, lockEntity, redisKey.getExpireTime(), redisKey.getExpireTimeUnit()));
        } finally {
            redisTemplate.delete(key);
        }
    }
}
