package com.cyou.tv.zebra.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author liuxun
 * @apiNote 注册Filter方式二，通过@WebFilter注解+配置类的@ServletComponentScan注解扫描来实现
 * 注意：
 *   ① @WebFilter指定的过滤器优先级都高于FilterRegistrationBean配置的过滤器
 *   ② 通过此注解设置的filter无法设置过滤顺序
 */
@Slf4j
//@Order(2)
//@WebFilter(urlPatterns = "/*", filterName = "logCostFilter-4")
public class LogCostFilter4 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("logFilter-4 [@WebFilter] Execute cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
