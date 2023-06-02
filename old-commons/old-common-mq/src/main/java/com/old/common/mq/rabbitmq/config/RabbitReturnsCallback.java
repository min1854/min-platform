package com.old.common.mq.rabbitmq.config;

import com.old.common.enums.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class RabbitReturnsCallback implements RabbitTemplate.ReturnsCallback {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 这个是 mq 返回的结果消息，
     * 如果投递到了队列，但是没有消费者，消息过期了也不会回调这个接口
     *
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        Message message = returned.getMessage();
        log.debug("消息没有绑定队列，消息id：{}，交换机：{}，路由key：{}，状态码：{}，原因：{}，消息属性：{}",
                message.getMessageProperties().getMessageId(),
                returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), message.getMessageProperties());

        if (message.getMessageProperties().getMessageId() == null) {
            log.warn("未绑定队列的消息并不存在消息id，交换机：{}，路由 key：{}，状态码：{}，原因：{}，消息内容：{}",
                    returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(),
                    new String(returned.getMessage().getBody()));
            return;
        }

        message.getMessageProperties().setHeader("returnedMessageTime", new Date());
        redisTemplate.opsForHash().put(RedisKey.RABBIT_FAIL_MESSAGE.getKey(), message.getMessageProperties().getMessageId(), returned);
    }
}
