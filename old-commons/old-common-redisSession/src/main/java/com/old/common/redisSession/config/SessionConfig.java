package com.old.common.redisSession.config;

import com.old.common.serializer.ObjectMapperProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@Configuration(proxyBeanMethods = false)
public class SessionConfig {
    /**
     * 名称不能修改，否则会 redisSession 无法获取
     *
     * @return
     * @see RedisHttpSessionConfiguration#setDefaultRedisSerializer(RedisSerializer)
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(ObjectMapperProvider.defaultMapper());
    }

}
