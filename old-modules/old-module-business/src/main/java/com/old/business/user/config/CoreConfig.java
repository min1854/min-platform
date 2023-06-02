package com.old.business.user.config;

import com.old.common.web.enums.CheckLoginHandler;
import com.old.common.web.sercurity.CheckLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CoreConfig {

    @Bean
    public FilterRegistrationBean<CheckLoginFilter> checkLoginFilter(@Autowired IgnoresUrls ignoreUrls) {
        FilterRegistrationBean<CheckLoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CheckLoginFilter(ignoreUrls.getIgnoreUrls(), CheckLoginHandler.WRITE.getHandler()));
        registrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return registrationBean;
    }

    // 过滤器跨域配置
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        // 允许跨域的头部信息
        config.addAllowedHeader("*");
        // 允许跨域的方法
        config.addAllowedMethod("*");
        // 可访问的外部域
        config.addAllowedOrigin("*");
        // 需要跨域用户凭证（cookie、HTTP认证及客户端SSL证明等）
        // config.setAllowCredentials(true);
        // config.addAllowedOriginPattern("*");

        // 跨域路径配置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
