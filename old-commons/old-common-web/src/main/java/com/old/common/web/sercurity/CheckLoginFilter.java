package com.old.common.web.sercurity;

import cn.hutool.core.text.AntPathMatcher;
import com.old.common.domain.login.LoginUser;
import com.old.common.exception.ResultException;
import com.old.common.web.enums.CheckLoginHandler;
import com.old.common.web.util.LoginUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;

/**
 * 判断是否登录
 * <p>
 * CheckLoginHandler 用于处理如果出现未登录的情况如果处理，默认提供了写出响应和转发抛出异常的处理
 */
@Slf4j
public class CheckLoginFilter implements Filter {

    public Set<String> ignoreUrls;

    public AntPathMatcher antPathMatcher;

    private CheckLoginHandler.Handler handler;

    public CheckLoginFilter(Set<String> ignoreUrls, CheckLoginHandler.Handler handler) {
        this.ignoreUrls = ignoreUrls;
        this.handler = handler;
        antPathMatcher = new AntPathMatcher();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        for (String pattern : ignoreUrls) {
            if (pattern.equals(uri) || antPathMatcher.match(pattern, uri)) {
                chain.doFilter(request, response);
                return;
            }
        }


        try {
            LoginUser loginUser = LoginUtil.getLoginUser();
            LoginUtil.refresh(loginUser);
            chain.doFilter(request, response);
        } catch (ResultException e) {
            log.error("用户未登录，请求地址：{}", httpServletRequest.getRequestURI());
            handler.accept(e, (HttpServletRequest) request, (HttpServletResponse) response);
        }

    }

}
