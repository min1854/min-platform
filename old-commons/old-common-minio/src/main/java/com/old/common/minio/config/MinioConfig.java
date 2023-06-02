package com.old.common.minio.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class MinioConfig {

    @Bean
    public MinioClient minioClient(@Autowired MinioProperties properties) throws NoSuchAlgorithmException {
        log.debug("minio客户端创建：{}", properties);

        return MinioClient.builder()
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .endpoint(properties.getEndpoint(), properties.getPort(), properties.getSecure())
                .build();
    }
}
