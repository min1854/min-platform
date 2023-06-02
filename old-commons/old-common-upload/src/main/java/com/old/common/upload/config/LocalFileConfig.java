package com.old.common.upload.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("old.file.local")
@ConditionalOnProperty(value = "old.file.serverType", havingValue = "local")
public class LocalFileConfig {
    private String uploadPath;
    private String profile;
    private String fileServer;


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                /** 本地文件上传路径 */
                registry.addResourceHandler(profile + "/**")
                        .addResourceLocations("file:" + uploadPath + "/");

            }
        };
    }
}
