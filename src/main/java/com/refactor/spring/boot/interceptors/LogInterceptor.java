package com.refactor.spring.boot.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("--- LogInterception.preHandle ---");
        long startTime =  System.currentTimeMillis();
        log.info("Request URL: {}", request.getRequestURL());
        log.info("Start Time: {}", startTime);

        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("--- LogInterception.postHandle ---");
        log.info("Request URL: {}", request.getRequestURL());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        log.info("--- LogInterception.afterCompletion ---");
        log.info("Request URL: {}", request.getRequestURL());
        log.info("endTime Time: {}", endTime);

        log.info("Time Taken: {}", endTime-startTime);
        // 注意: 此接口方法控制器执行完毕 渲染视图之前 调用的，是不能捕获到控制器方法的执行异常的，代码如下
        if (ex != null){
            log.error("--- controller error message: {} ---",ex.getMessage());
        }
    }
}
