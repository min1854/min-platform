package com.old.common.mybatis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.old.common.mybatis.config")
@Configuration(proxyBeanMethods = false)
public class MybatisScan {
}
