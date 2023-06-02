package com.old.common.web.annotation;

import com.old.common.web.enums.CheckMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CheckRole {


    /**
     * 需要校验的角色标识
     *
     * @return 需要校验的角色标识
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     *
     * @return 验证模式
     */
    CheckMode mode() default CheckMode.AND;
}
