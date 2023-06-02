package com.old.common.mq.rabbitmq.config;

import com.old.common.serializer.ObjectMapperProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.GenericMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class RabbitListenerCustomizeConfigurer implements RabbitListenerConfigurer {
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        log.debug("rabbitMq监听者自定义配置");
        DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();

        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(ObjectMapperProvider.defaultMapper());


        defaultMessageHandlerMethodFactory.setMessageConverter(messageConverter);
        defaultMessageHandlerMethodFactory.afterPropertiesSet();
        registrar.setMessageHandlerMethodFactory(defaultMessageHandlerMethodFactory);
    }
}
