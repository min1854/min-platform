package com.old.common.mq;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.old.common.mq.*.config")
@Configuration(proxyBeanMethods = false)
public class MqScan {
}
