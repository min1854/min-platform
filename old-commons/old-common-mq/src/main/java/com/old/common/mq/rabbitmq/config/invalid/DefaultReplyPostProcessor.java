package com.old.common.mq.rabbitmq.config.invalid;

import com.old.common.enums.redis.RedisKey;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.amqp.rabbit.listener.adapter.InvocationResult;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.ReplyPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @see MessagingMessageListenerAdapter 调用监听注解的类
 * <p>
 * 这个要有消费消息之后有返回结果才会生效
 * @see AbstractAdaptableMessageListener#handleResult(InvocationResult, Message, Channel, Object)
 * @see AbstractAdaptableMessageListener#doHandleResult(InvocationResult, Message, Channel, Object)
 */
@Slf4j
@Component(DefaultReplyPostProcessor.BEAN_NAME)
public class DefaultReplyPostProcessor implements ReplyPostProcessor {

    public static final String BEAN_NAME = "defaultReplyPostProcessor";

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Message apply(Message req, Message resp) {
        String messageId = req.getMessageProperties().getMessageId();
        log.debug("删除消息锁标识：{}", messageId);
        redisTemplate.opsForHash().delete(RedisKey.RABBIT_PROCESS_MESSAGE.getKey(), messageId);
        return resp;
    }

}
