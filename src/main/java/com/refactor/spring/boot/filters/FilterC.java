package com.refactor.spring.boot.filters;

import com.refactor.spring.boot.tools.ServletTool;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class FilterC implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("FilterC start");
        String  forward = "forward";
        if (request.getParameter(forward) != null){
            String uri = request.getParameter(forward);
            // ServletTool.forward(uri);
            // request.getRequestDispatcher(uri).forward(request,response);
            ServletTool.forward(uri,request,response);
            return;
        }
        chain.doFilter(request,response);
        log.info("FilterC end");
    }
}
