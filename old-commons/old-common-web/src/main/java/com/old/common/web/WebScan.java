package com.old.common.web;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.old.common.web.config", "com.old.common.web.handler", "com.old.common.web.controller"})
public class WebScan {
}
