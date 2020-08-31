package com.refactor.spring.boot.filters.stack;

import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liuxun
 * @apiNote 精准匹配路径
 */
public abstract class BaseFilter implements Filter {

    private final Set<String> urlMappings = new HashSet<>();
    private AntPathMatcher matcher = new AntPathMatcher();

    public BaseFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String contextPath = httpRequest.getContextPath();
        final String requestURI = httpRequest.getRequestURI();
        String uri = requestURI.replaceFirst(contextPath,"");
        if (isMatch(uri)){
            filter(httpRequest, httpResponse, chain);
        }else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    private boolean isMatch(String uri){
       return urlMappings.stream().anyMatch(mapping-> matcher.match(mapping, uri));
    }

    public void addUrlMappings(Collection<String> urlMappings){
        this.urlMappings.addAll(urlMappings);
    }

    protected abstract void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
}
