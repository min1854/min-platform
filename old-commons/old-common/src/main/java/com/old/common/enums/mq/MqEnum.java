package com.old.common.enums.mq;

import java.util.Map;

public interface MqEnum {

    String getExchange();

    String getQueue();

    String getRoutingKey();


    /**
     * 消息过期时间，单位毫秒，为空不过期
     */
    String getMessageExpiration();

    default Map<String, Object> queueParam() {
        return null;
    }
}
