package com.old.common.web.sercurity;

import cn.hutool.core.text.AntPathMatcher;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.RoleBo;
import com.old.common.enums.ResultEnum;
import com.old.common.exception.ResultException;
import com.old.common.web.annotation.CheckLogin;
import com.old.common.web.annotation.CheckPermission;
import com.old.common.web.annotation.CheckRole;
import com.old.common.web.annotation.IgnoreCheck;
import com.old.common.web.enums.CheckMode;
import com.old.common.web.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * 校验 角色
 * 校验 是否登陆
 * <p>
 * 这里的代码有些简陋，看看后续是否抽取
 */
public class AuthInterceptor implements HandlerInterceptor {

    private ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        IgnoreCheck ignoreCheck = getAnnotation(method.getMethod(), IgnoreCheck.class);
        if (ignoreCheck != null) {
            return true;
        }

        CheckLogin checkLogin = getAnnotation(method.getMethod(), CheckLogin.class);
        if (checkLogin != null) {
            LoginUtil.getLoginUser();
        }

        CheckPermission checkPermission = getAnnotation(method.getMethod(), CheckPermission.class);
        if (checkPermission != null) {
            LoginUser loginUser = LoginUtil.getLoginUser();
            checkMenu(checkPermission, loginUser.getMenuPaths(), loginUser.getRoleBos());
        }

        CheckRole checkRole = getAnnotation(method.getMethod(), CheckRole.class);
        if (checkRole != null) {
            checkRolePower(checkRole, LoginUtil.getLoginUser().getRoleBos());
        }


        return true;
    }

    private void checkRolePower(CheckRole checkRole, List<RoleBo> roleBos) {
        apiAssert.isEmpty(roleBos, ResultEnum.USER_ROLE_DEFICIENCY);


        CheckMode checkMode = checkRole.mode();


        if (CheckMode.AND.equals(checkMode)) {
            hasRoleAnd(checkRole.value(), roleBos);
        } else if (CheckMode.OR.equals(checkMode)) {
            for (String role : checkRole.value()) {
                for (RoleBo bo : roleBos) {
                    if (bo.getRoleName().equals(role) || antPathMatcher.match(role, bo.getRoleName())) {
                        return;
                    }
                }
            }
            apiAssert.handler(ResultEnum.USER_ROLE_DEFICIENCY);

        }

    }

    private void checkMenu(CheckPermission checkPermission, Set<String> menus, List<RoleBo> roleBos) {
        apiAssert.isEmpty(menus, ResultEnum.NO_REQUEST_PERMISSION);


        try {
            if (CheckMode.AND.equals(checkPermission.mode())) {
                for (String permission : checkPermission.value()) {
                    for (String menu : menus) {
                        apiAssert.isTrue(!menu.equals(permission) || antPathMatcher.match(permission, menu), ResultEnum.NO_REQUEST_PERMISSION);
                    }
                }
            } else if (CheckMode.OR.equals(checkPermission.mode())) {
                for (String permission : checkPermission.value()) {
                    for (String menu : menus) {
                        if (menu.equals(permission) || antPathMatcher.match(permission, menu)) {
                            return;
                        }
                    }
                }
                apiAssert.handler(ResultEnum.NO_REQUEST_PERMISSION);

            }
        } catch (ResultException e) {
            // 权限认证未通过，再开始角色认证
            if (checkPermission.orRole().length <= 0) {
                throw e;
            }
            apiAssert.isEmpty(roleBos, ResultEnum.USER_ROLE_DEFICIENCY);
            for (String role : checkPermission.orRole()) {
                String[] rArr = role.split(",");
                // 某一项role认证通过，则可以提前退出了，代表通过
                if (hasRoleAnd(rArr, roleBos)) {
                    return;
                }
            }
        }

    }

    private boolean hasRoleAnd(String[] rArr, List<RoleBo> roleBos) {
        for (String role : rArr) {
            for (RoleBo bo : roleBos) {
                if (!bo.getRoleName().equals(role) || antPathMatcher.match(role, bo.getRoleName())) {
                    apiAssert.handler(ResultEnum.USER_ROLE_DEFICIENCY);
                }
            }
        }

        return false;
    }


    public <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        T annotation = method.getAnnotation(annotationClass);
        if (annotation == null) {
            annotation = method.getDeclaredAnnotation(annotationClass);
        }
        return annotation;
    }

}
