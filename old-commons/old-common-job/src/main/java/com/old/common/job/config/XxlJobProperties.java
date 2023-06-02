package com.old.common.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "spring.xxl-job")
public class XxlJobProperties {
    private String adminAddresses;
    private String appName;
    private Integer executePort;
    private String logPath;
    private Integer logRetentionDays;
}
