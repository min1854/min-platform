package com.old.common.minio;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.old.common.minio.config")
public class MinioScan {
}
