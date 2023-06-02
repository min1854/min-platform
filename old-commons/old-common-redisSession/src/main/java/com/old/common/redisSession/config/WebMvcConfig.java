package com.old.common.redisSession.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Enumeration;

@Slf4j
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                        HttpSession session = request.getSession();
                        log.debug("请求uri：{}，会话id：{}，会话类型：{}", request.getRequestURI(), session.getId(), session.getClass());
                        Enumeration<String> headerNames = request.getHeaderNames();
                        if (request.getCookies() != null) {
                            for (Cookie cookie : request.getCookies()) {
                                log.debug("请求uri：{}，会话id：{}，cookie 名：{}。值：{}", request.getRequestURI(), session.getId(), cookie.getName(), cookie.getValue());
                            }
                        }
                        while (headerNames.hasMoreElements()) {
                            String name = headerNames.nextElement();
                            log.debug("请求uri：{}，会话id：{}，请求头：{}。值：{}", request.getRequestURI(), session.getId(), name, request.getHeader(name));
                        }
                        return HandlerInterceptor.super.preHandle(request, response, handler);
                    }
                })
                .addPathPatterns("/**");
    }
}
