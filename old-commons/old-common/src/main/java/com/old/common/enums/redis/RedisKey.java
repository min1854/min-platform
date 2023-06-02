package com.old.common.enums.redis;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public enum RedisKey {


    RABBIT_FAIL_MESSAGE("old:mq:failMessage", -1),

    RABBIT_MESSAGE_COUNT("old:mq:messageCount", -1),

    RABBIT_PROCESS_MESSAGE("old:mq:processMessage", -1),


    LOGIN_USER("old:login_user:", 1000 * 60 * 60),


    MOBILE_LOGIN_USER("old:mobile_login_user:", 1000 * 60 * 60),


    MOBILE_REGISTRY_CAPTCHA("old:mobile_registry_captcha:", 1000 * 60 * 3),

    WE_CHAT_ACCESS_TOKEN("old:we_chat_access_token", 1000 * 60 * 3),
    SOCIALIZE_LAST_CHAT("old:socialize:last_chat", -1),
    LOGIN_OUT_OF_LIMIT("old:login_of_limit:", 15 * 60 * 1000),


    ;

    private final String key;
    /**
     * 默认毫秒，-1 不过期
     */
    private final Integer expireTime;

    private final TimeUnit expireTimeUnit;

    RedisKey(String key, Integer expireTime) {
        this.key = key;
        this.expireTime = expireTime;
        this.expireTimeUnit = TimeUnit.MILLISECONDS;
    }

    public boolean isItExpire() {
        return this.expireTime != -1;
    }

    public String append(String suffix) {
        return this.key + suffix;
    }

    public String format(Object... param) {
        return StrUtil.format(this.key, param);
    }
}
