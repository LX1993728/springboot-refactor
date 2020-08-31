package com.refactor.spring.boot.filters.stack;

import com.refactor.spring.boot.tools.ServletTool;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class FilterC extends BaseFilter {

    @Override
    protected void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
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
