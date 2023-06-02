package com.old.common.web.sercurity;

import cn.hutool.core.text.AntPathMatcher;
import com.old.common.domain.login.LoginUser;
import com.old.common.enums.ResultEnum;
import com.old.common.exception.ResultException;
import com.old.common.web.util.LoginUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * 校验是否存在请求路径
 * 如果不存在则判断是否可以通过路径匹配
 */
public class AuthFilter implements Filter {
    public static final String PASS = "*";

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public BiFunction<Set<String>, String, Boolean> hasElement = (patterns, element) -> {

        // 空集合直接返回false
        if (patterns == null || patterns.size() == 0) {
            return false;
        }

        // 先尝试一下简单匹配，如果可以匹配成功则无需继续模糊匹配
        if (patterns.contains(element)) {
            return true;
        }

        // 开始模糊匹配
        for (String patt : patterns) {
            // 两者均为 null 时，直接返回 true
            if (patt == null && element == null) {
                return true;
            }
            // 两者其一为 null 时，直接返回 false
            if (patt == null || element == null) {
                return false;
            }
            // 如果表达式不带有*号，则只需简单equals即可 (这样可以使速度提升200倍左右)
            if (!patt.contains("*")) {
                return patt.equals(element);
            }
            if (antPathMatcher.match(patt, element)) {
                return true;
            }
        }

        // 走出for循环说明没有一个元素可以匹配成功
        return false;
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletRequest resp = (HttpServletRequest) response;


        LoginUser loginUser = LoginUtil.getLoginUser();
        if (loginUser.getMenuPaths().contains(req.getRequestURI())) {
            chain.doFilter(request, response);
        } else if (loginUser.getMenuPaths().contains(PASS)) {
            chain.doFilter(request, response);
        } else if (hasElement.apply(loginUser.getMenuPaths(), req.getRequestURI())) {
            chain.doFilter(request, response);
        }


        throw new ResultException(ResultEnum.REQUESTED_RANGE_NOT_SATISFIABLE);

    }
}
