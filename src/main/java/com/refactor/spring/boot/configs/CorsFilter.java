package com.refactor.spring.boot.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 通用的(跨域请求过滤器)
 * @apiNote 使用方式如下：
 * ① 在项目中配置此过滤器即可实现跨域请求的限制
 * ② 配置二级域名，在设置Cookie的时候调用setDomain(cookie);即可跨域时自动携带cookie
 * @author liuxun
 *
 */
@Slf4j
public class CorsFilter implements Filter {
    private static String COOKIE_DOMAIN = ".changyou.com";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String origin=request.getHeader("Origin");
        if(origin!=null){
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        String headers=request.getHeader("Access-Control-Request-Headers");
        if(headers!=null){
            response.setHeader("Access-Control-Allow-Headers",headers );
            response.setHeader("Access-Control-Expose-Headers",headers );
        }
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        // 销毁处理
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("----------init corsFilter ------{}", new Date());
    }

    // 在设置Cookie时调用此方法
    public static void setDomain(Cookie cookie){
        if (StringUtils.isEmpty(COOKIE_DOMAIN)){
            cookie.setDomain(COOKIE_DOMAIN);
        }
    }
}

