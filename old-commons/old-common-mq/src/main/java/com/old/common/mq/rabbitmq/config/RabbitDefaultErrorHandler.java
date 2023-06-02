package com.old.common.mq.rabbitmq.config;

import com.old.common.enums.ResultEnum;
import com.old.common.enums.redis.RedisKey;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.old.common.mq.rabbitmq.config.RabbitDefaultErrorHandler.BEAN_NAME;

@Slf4j
@Component(BEAN_NAME)
public class RabbitDefaultErrorHandler implements RabbitListenerErrorHandler {

    public static final String BEAN_NAME = "rabbitDefaultErrorHandler";

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${spring.rabbitmq.listener.direct.retry.max-attempts:3}")
    private Integer directMaxAttempts;

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts:3}")
    private Integer simpleMaxAttempts;


    /**
     * 也是同样，如果有返回值，需要配置返回发送的 mq 地址
     *
     * @param amqpMessage the raw message received.
     * @param message     the converted spring-messaging message (if available).
     * @param e           the exception the listener threw, wrapped in a
     *                    {@link ListenerExecutionFailedException}.
     * @return
     * @throws Exception
     */
    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
                              ListenerExecutionFailedException e) throws Exception {
        String messageId = amqpMessage.getMessageProperties().getMessageId();
        log.error("消息消费出现异常，消息id：{}", messageId, e);

        if (redisTemplate.boundHashOps(RedisKey.RABBIT_MESSAGE_COUNT.getKey()).increment(messageId, 1)
                < directMaxAttempts) {
            throw e;
        }
        log.error("消息：{}，超过最大重试次数，{}， {}", messageId, directMaxAttempts, simpleMaxAttempts);

        Message saveMessage = buildSaveMessage(amqpMessage);


        ReturnedMessage returnedMessage = new ReturnedMessage(saveMessage, ResultEnum.FAIL.getCode(), "消息消费异常",
                saveMessage.getMessageProperties().getReceivedExchange(),
                saveMessage.getMessageProperties().getReceivedRoutingKey());

        redisTemplate.opsForHash().put(RedisKey.RABBIT_FAIL_MESSAGE.getKey(),
                messageId, returnedMessage);
        return null;
    }

    public Message buildSaveMessage(Message amqpMessage) {
        // 这两个属性不去除，redis 序列化会出现栈溢出的异常
        amqpMessage.getMessageProperties().setTargetMethod(null);
        amqpMessage.getMessageProperties().setTargetBean(null);
        return amqpMessage;
    }

    public Message buildMessageAndAddErrorTime(Message amqpMessage) {
        amqpMessage.getMessageProperties().setHeader("errorTime", new Date());
        return buildSaveMessage(amqpMessage);
    }
}
