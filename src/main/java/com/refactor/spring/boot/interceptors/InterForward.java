package com.refactor.spring.boot.interceptors;

import com.refactor.spring.boot.tools.ServletTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @apiNote 拦截器内进行请求转发
 */
@Slf4j
public class InterForward implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("--- inter forward pre---");
         //request.getRequestDispatcher("/app").forward(request,response);
         ServletTool.forward("/app");
        return false;
    }
}
