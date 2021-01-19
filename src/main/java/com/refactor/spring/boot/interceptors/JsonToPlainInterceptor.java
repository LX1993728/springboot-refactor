package com.refactor.spring.boot.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  修改控制器返回数据的类型: 将application/json类型的数据，转为text/plain
 */
@Slf4j
public class JsonToPlainInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String contentType = response.getContentType();
        log.info("---contentType={} \t", contentType);
        if (contentType != null && contentType.contains("json")){
            response.setContentType("text/plain;charset=UTF-8");
        }
    }
}
