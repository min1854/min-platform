package com.old.common.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于 web 配置中，默认的控制器，以辅助其他配置作为使用，或是单独提供其他功能
 */
@RestController
@RequestMapping("/old/web")
public class WebController {

    public static final String THROW_EXCEPTION = WebController.class.getName() + ".exception";
    public static final String THROW_E_PATH = "/old/web/throwE";


    @RequestMapping("throwE")
    public void throwE(HttpServletRequest request) throws Exception {
        throw (Exception) request.getAttribute(THROW_EXCEPTION);
    }

}