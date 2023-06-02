package com.old.common.upload.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("old.file.database")
@ConditionalOnProperty(value = "old.file.serverType", havingValue = "database")
public class DataBaseFileConfig {
    private String fileServer;
    private String profile;
}
