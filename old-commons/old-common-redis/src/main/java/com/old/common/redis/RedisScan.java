package com.old.common.redis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.old.common.redis.config")
public class RedisScan {
}
