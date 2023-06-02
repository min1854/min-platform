package com.old.common.web.enums;


import com.old.common.enums.ResultEnum;
import com.old.common.exception.ResultException;
import com.old.common.result.R;
import com.old.common.util.JsonUtil;
import com.old.common.web.controller.WebController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum CheckLoginHandler {

    WRITE((e, request, response) -> {
        //
        /**
         * 这样请求结束快，但可能会出现缺少了响应头的问题
         * 测试发现少了这个请求头
         * Transfer-Encoding: chunked
         */
        log.error("用户未登录请求：{}", request.getRequestURI());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().write(JsonUtil.toJson(R.r(e.getResultEnum())));
        } catch (IOException ex) {
            throw new ResultException(ResultEnum.WRITE_ERROR, ex);
        }

    }),

    FORWARD((e, request, response) -> {

        try {
            request.setAttribute(WebController.THROW_EXCEPTION, e);
            request.getRequestDispatcher(WebController.THROW_E_PATH).forward(request, response);
        } catch (ServletException | IOException ex) {
            throw new ResultException(ResultEnum.FORWARD_ERROR, ex);
        }
    }),

    ;
    private final Handler handler;


    @FunctionalInterface
    public static interface Handler {
        void accept(ResultException e, HttpServletRequest request, HttpServletResponse response);
    }


}
