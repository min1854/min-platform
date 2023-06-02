package com.old.common.web.handler;

import cn.hutool.core.collection.CollUtil;
import com.old.common.base.BaseException;
import com.old.common.enums.ResultEnum;
import com.old.common.exception.ApiFeignException;
import com.old.common.exception.ResultException;
import com.old.common.result.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebMvcExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleApiFeignException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("参数校验不通过：{}", request.getRequestURI(), e);
        return new R<>(ResultEnum.BAD_REQUEST.format(CollUtil.getFirst(e.getFieldErrors()).getDefaultMessage()));
    }


    @ExceptionHandler(BaseException.class)
    public R<?> handleApiFeignException(BaseException e, HttpServletRequest request) {
        log.error("请求出现异常：{}", request.getRequestURI(), e);
        return R.fail();
    }


    @ExceptionHandler(ApiFeignException.class)
    public R<?> handleApiFeignException(ApiFeignException e, HttpServletRequest request) {
        log.error("远程服务请求出现异常：{}", request.getRequestURI(), e);
        return R.feignError();
    }


    @ExceptionHandler(ResultException.class)
    public R<?> handleResultException(ResultException e, HttpServletRequest request) {
        log.error("业务异常结果：{}", request.getRequestURI(), e);
        return new R<>(e.getResultEnum());
    }

}
