package com.refactor.spring.boot.filters;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class LogCostFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("logFilter-2 [FilterRegistrationBean] Execute cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
