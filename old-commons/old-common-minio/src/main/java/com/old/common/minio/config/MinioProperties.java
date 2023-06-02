package com.old.common.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "spring.minio")
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private Integer port;
    private Boolean secure = false;
    private String bucket;
    private String server;
}
