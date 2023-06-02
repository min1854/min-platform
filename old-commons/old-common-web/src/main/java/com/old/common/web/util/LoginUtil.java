package com.old.common.web.util;

import cn.hutool.core.util.IdUtil;
import com.old.common.apiAssert.ResultAssertGenerator;
import com.old.common.domain.login.LoginUser;
import com.old.common.enums.ResultEnum;
import com.old.common.enums.redis.RedisKey;
import com.old.common.redis.util.RedisUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class LoginUtil {

    public static final String TOKEN_PREFIX = LoginUser.TOKEN_PREFIX;
    public static final Integer DEFAULT_USER_ID = LoginUser.DEFAULT_USER_ID;
    public static final String DEFAULT_USER_NAME = LoginUser.DEFAULT_USER_NAME;

    private static final RedisKey LOGIN_USER_KEY = RedisKey.LOGIN_USER;


    public static Integer loginUserId() {
        return getLoginUser().getUserBo().getId();
    }

    public static String loginUserName() {
        return getLoginUser().getUserBo().getUserName();
    }

    public static LoginUser getLoginUser() {
        return ResultAssertGenerator.create(getToken())
                .then(token -> (LoginUser) RedisUtil.get(LOGIN_USER_KEY, token))
                .isNull(ResultEnum.FORBIDDEN)
                .getObj();
    }

    public static String getToken() {
        return ResultAssertGenerator.create(RequestContextHolder.getRequestAttributes())
                .isNull(ResultEnum.NOT_WEB)
                .then(attributes -> {
                    return ((ServletRequestAttributes) attributes).getRequest();
                })
                .then(request -> request.getHeader(HttpHeaders.AUTHORIZATION))
                .isEmpty(ResultEnum.UNAUTHORIZED)
                .then(token -> {
                    if (token.startsWith(TOKEN_PREFIX)) {
                        return token.substring(TOKEN_PREFIX.length());
                    }
                    return token;
                })
                .getObj();
    }


    public static void refresh(LoginUser loginUser) {
        refresh(getToken(), loginUser);
    }

    public static void refresh(String token, LoginUser loginUser) {
        RedisUtil.set(LOGIN_USER_KEY, token, loginUser);
    }

    public static String login(LoginUser loginUser) {
        String token = IdUtil.simpleUUID();
        login(token, loginUser);
        return token;
    }

    public static void login(String token, LoginUser loginUser) {
        RedisUtil.set(LOGIN_USER_KEY, token, loginUser);
    }


    public static void logout() {
        RedisUtil.del(LOGIN_USER_KEY, getToken());
    }

    public static boolean isDefaultUser() {
        return getLoginUser().isDefaultUser();
    }
}
