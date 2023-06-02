package com.old.common.upload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.old.common.upload.mapper.file")
@ComponentScan(value = {"com.old.common.upload", "com.old.common.file"})
@Configuration(proxyBeanMethods = false)
public class UploadScan {
}
