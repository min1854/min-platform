package com.old.common.enums.mq.rabbitmq;

import com.old.common.enums.mq.MqEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RabbitMqEnum implements MqEnum {

    SMS_NOTICE(new SmsNotice(), "短信消息"),
    PAY_LOAD(new Payload(), "payLoad 注解消息"),

    ;
    private MqEnum value;

    private String desc;


    @Override
    public String getExchange() {
        return value.getExchange();
    }

    @Override
    public String getQueue() {
        return value.getQueue();
    }

    @Override
    public String getRoutingKey() {
        return value.getRoutingKey();
    }

    @Override
    public String getMessageExpiration() {
        return value.getMessageExpiration();
    }


    public static class SmsNotice implements MqEnum {
        public static final String EXCHANGE = "old.sms.notice.exchange";

        public static final String QUEUE = "old.sms.notice.queue";

        public static final String ROUTING_KEY = "smsNotice";

        public static final String MESSAGE_EXPIRATION = "3000";

        @Override
        public String getExchange() {
            return EXCHANGE;
        }

        @Override
        public String getQueue() {
            return QUEUE;
        }

        @Override
        public String getRoutingKey() {
            return ROUTING_KEY;
        }

        @Override
        public String getMessageExpiration() {
            return MESSAGE_EXPIRATION;
        }
    }


    public static class Payload implements MqEnum {
        public static final String EXCHANGE = "old.sms.notice.exchange";

        public static final String QUEUE = "payloadQueue";

        public static final String ROUTING_KEY = "payload";

        public static final String MESSAGE_EXPIRATION = "3000";

        @Override
        public String getExchange() {
            return EXCHANGE;
        }

        @Override
        public String getQueue() {
            return QUEUE;
        }

        @Override
        public String getRoutingKey() {
            return ROUTING_KEY;
        }

        @Override
        public String getMessageExpiration() {
            return MESSAGE_EXPIRATION;
        }
    }
}
