package com.old.business.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/getHandler")
    public String getHandler(@Autowired HandlerMethod handlerMethod) {
        log.debug("处理器：{}", handlerMethod);
        return "成功请求";
    }
}
