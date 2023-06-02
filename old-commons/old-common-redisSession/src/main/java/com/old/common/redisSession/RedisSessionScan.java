package com.old.common.redisSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.old.common.redisSession.config")
@Configuration(proxyBeanMethods = false)
public class RedisSessionScan {
}
