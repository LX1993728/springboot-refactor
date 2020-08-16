package com.refactor.spring.boot.interceptors;

import com.refactor.spring.boot.refactorInterceptor.InterceptorNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component(InterceptorNames.INTERCEPTOR_B)
public class InterceptorB extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("--- InterceptorB pre ---");
        return true;
    }
}
