package com.refactor.spring.boot.refactorInterceptor;

import java.lang.annotation.*;

/**
 * @apiNote 自定义拦截器栈注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interceptor {
    /**
     * @apiNote 指定拦截器栈
     * @return
     */
    String stack() default "";

    /**
     * @apiNote 用于指定附加的拦截器(除了拦截器栈之外的拦截器)
     * @return
     */
    String[] interceptors() default {};
}
