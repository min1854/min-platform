package com.old.common.mq.rabbitmq.config;

import com.old.common.serializer.ObjectMapperProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration(proxyBeanMethods = false)
public class RabbitMqConfig {

    /**
     *
     * @param rabbitTemplate
     * @param rabbitConfirmCallback
     * @param rabbitReturnsCallback
     */
    @Autowired
    public void customer(@Autowired RabbitTemplate rabbitTemplate,
                         @Autowired RabbitConfirmCallback rabbitConfirmCallback,
                         @Autowired RabbitReturnsCallback rabbitReturnsCallback) {
        log.debug("rabbitTemplate自定义配置");
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(ObjectMapperProvider.typeObjectMapper()));
        rabbitTemplate.setConfirmCallback(rabbitConfirmCallback);
        rabbitTemplate.setReturnsCallback(rabbitReturnsCallback);
    }
}
