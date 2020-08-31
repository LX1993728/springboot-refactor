package com.refactor.spring.boot.filters.stack;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class FilterB extends BaseFilter {

    @Override
    protected void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("FilterB start");
        chain.doFilter(request,response);
        log.info("FilterB end");
    }
}
