package com.old.common.web;

import cn.hutool.core.annotation.AnnotationUtil;
import com.old.common.web.annotation.CheckRole;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class AnnotationTests {


    @Test
    public void test1() throws NoSuchMethodException {
        Method method1 = TestAnnotation.class.getMethod("method1");
        CheckRole annotation = AnnotationUtil.getAnnotation(method1,
                CheckRole.class);
        System.out.println(annotation);


        annotation = AnnotationUtils.getAnnotation(method1, CheckRole.class);

        System.out.println(annotation);
    }


    @Test
    public void test2() {

        BiFunction<Method, Class<CheckRole>, CheckRole> t = new BiFunction<Method, Class<CheckRole>, CheckRole>() {
            @Override
            public CheckRole apply(Method method, Class<CheckRole> checkRoleClass) {
                return null;
            }
        };

        Consumer<BiFunction<Method, Class<CheckRole>, CheckRole>> consumer = biFunction -> {

            Method method1 = null;
            try {
                method1 = TestAnnotation.class.getMethod("method1");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return;
            }
            CheckRole annotation = biFunction.apply(method1, CheckRole.class);
            System.out.println(annotation);
        };

        consumer.accept(new BiFunction<Method, Class<CheckRole>, CheckRole>() {
            @Override
            public CheckRole apply(Method method, Class<CheckRole> checkRoleClass) {
                return AnnotationUtil.getAnnotation(method,
                        CheckRole.class);
            }
        });

        consumer.accept(new BiFunction<Method, Class<CheckRole>, CheckRole>() {
            @Override
            public CheckRole apply(Method method, Class<CheckRole> checkRoleClass) {
                return AnnotationUtils.getAnnotation(method, CheckRole.class);
            }
        });

    }

    @CheckRole
    public static class TestAnnotation {

        public void method1() {

        }
    }
}
