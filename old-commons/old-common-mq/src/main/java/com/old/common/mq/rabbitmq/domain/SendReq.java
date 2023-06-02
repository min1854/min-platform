package com.old.common.mq.rabbitmq.domain;

import com.old.common.enums.mq.rabbitmq.RabbitMqEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.core.MessagePostProcessor;

@Data
@Builder
public class SendReq {

    private RabbitMqEnum rabbitMqEnum;
    private Object message;
    private MessagePostProcessor messagePostProcessor;
}
