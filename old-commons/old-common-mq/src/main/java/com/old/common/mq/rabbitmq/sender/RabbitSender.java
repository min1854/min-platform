package com.old.common.mq.rabbitmq.sender;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.old.common.enums.ResultEnum;
import com.old.common.enums.mq.rabbitmq.RabbitMqEnum;
import com.old.common.mq.rabbitmq.domain.SendReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.UUID;

@Slf4j
public final class RabbitSender {

    private static final int DEFAULT_MAX_BODY_LENGTH = 1000;
    /**
     * 获取spring管理的rabbitTemplate
     */
    private static RabbitTemplate rabbitTemplate = SpringUtil.getBean("rabbitTemplate");

    static {
        Message.setMaxBodyLength(DEFAULT_MAX_BODY_LENGTH);
    }

    public static void sendMessage(RabbitMqEnum mqEnum, Object message) {
        send(SendReq.builder().rabbitMqEnum(mqEnum).message(message).build());
    }

    public static void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    private static void send(SendReq req) {
        if (check(req)) {
            log.info("参数不符合发送条件，请检查：{}", req);
            return;
        }

        RabbitMqEnum rabbitMqEnum = req.getRabbitMqEnum();
        String exchange = rabbitMqEnum.getExchange();
        String routingKey = rabbitMqEnum.getRoutingKey();
        String messageId = genMessageId();

        CorrelationData correlationData = new CorrelationData(messageId);
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setMessageId(messageId);
                if (req.getMessagePostProcessor() != null) {
                    message = req.getMessagePostProcessor().postProcessMessage(message);
                }
                // log.debug("消息属性：{}", message.getMessageProperties());
                message.getMessageProperties().setTimestamp(new Date());
                message.getMessageProperties().setDeliveryTag(MessageDeliveryMode.toInt(MessageDeliveryMode.PERSISTENT));
                if (rabbitMqEnum.getMessageExpiration() != null) {
                    message.getMessageProperties().setExpiration(rabbitMqEnum.getMessageExpiration());
                }
                ReturnedMessage returned = new ReturnedMessage(message, ResultEnum.FAIL.getCode(), null,
                        req.getRabbitMqEnum().getExchange(),
                        req.getRabbitMqEnum().getRoutingKey());
                correlationData.setReturned(returned);
                return message;
            }
        };
        rabbitTemplate.convertAndSend(exchange, routingKey,
                req.getMessage(), messagePostProcessor, correlationData);
    }

    private static String genMessageId() {

        return "messageId-" + UUID.randomUUID();
    }


    private static boolean check(SendReq req) {
        if (req.getRabbitMqEnum() == null ||
                StrUtil.isBlank(req.getRabbitMqEnum().getExchange()) ||
                StrUtil.isBlank(req.getRabbitMqEnum().getRoutingKey()) ||
                req.getMessage() == null) {
            return true;
        }
        return false;
    }
}
