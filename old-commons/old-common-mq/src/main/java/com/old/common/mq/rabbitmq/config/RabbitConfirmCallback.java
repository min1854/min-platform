package com.old.common.mq.rabbitmq.config;

import com.old.common.enums.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String messageId = Optional.ofNullable(correlationData).map(CorrelationData::getId).orElse(null);
        log.debug("消息确认结果，消息id：{}，ack 状态：{}，原因：{}", messageId, ack, cause);

        if (ack) {
            return;
        }
        if (correlationData == null) {
            log.warn("不存在相关数据，消息id：{}", messageId);
            return;
        }
        ReturnedMessage returned = correlationData.getReturned();
        if (returned == null) {
            log.warn("无退回消息，消息id：{}", messageId);
            return;
        }
        ReturnedMessage returnedMessage = new ReturnedMessage(returned.getMessage(), returned.getReplyCode(), cause,
                returned.getExchange(), returned.getRoutingKey());
        returned.getMessage().getMessageProperties().setHeader("ackFailTime", new Date());

        redisTemplate.opsForHash().put(RedisKey.RABBIT_FAIL_MESSAGE.getKey(), messageId, returnedMessage);
        // 虽然是 object，但也会反序列化成真正的类型
        // Object obj = ObjectMapperProvider.defaultMapper().readValue(body, Object.class);

    }
}
