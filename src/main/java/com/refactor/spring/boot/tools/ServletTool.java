package com.refactor.spring.boot.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liuxun
 * @apiNote 用于获取request和response，替换基于struts 的ServletActionContext
 */
@Slf4j
public class ServletTool {
    public static ServletRequestAttributes getAttributes(){
        return  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     *
     * @return 请求对象
     */
    public static HttpServletRequest getRequest(){
      return   getAttributes().getRequest();
    }

    /**
     *
     * @return 响应对象
     */
    public static HttpServletResponse getResponse(){
      return   getAttributes().getResponse();
    }


    /**
     * 请求转发工具 注意: 此方法只能在spring的拦截器和控制器方法中使用，不能在filter中使用
     * @param uri
     */
    public static void forward(String  uri){
        try {
            HttpServletRequest request = getRequest();
            HttpServletResponse response = getResponse();
            request.getRequestDispatcher(uri).forward(request,response);
        }catch (Exception e){
            if (e instanceof IllegalStateException){
                // 如果是非法状态异常，其实是类似于警告，并不影响功能，可以忽略
                log.error("Exception to forward {}, message:{}", new Object[]{uri, e.getMessage()});
            }else {
                log.error("Exception to forward {}, message:{}", new Object[]{uri, e.getMessage()}, e);
            }
        }
    }

    /**
     * 请求重定向方法 注意: 此方法只能在spring的拦截器和控制器方法中使用，不能在filter中使用
     * @param uri
     * @param isSelf 是否是自身的地址 还是第三方地址
     */
    public static void  redirect(String uri, boolean isSelf){
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        try {
            final String contextPath = request.getContextPath();
            if (isSelf){
                uri = uri.startsWith("/") ? uri : "/" + uri;
                response.sendRedirect( contextPath + uri);
            }else {
                response.sendRedirect(uri);
            }
        } catch (IOException e) {
            log.error("Exception to redirect {}, message:{}", uri, e.getMessage(), e);
        }
    }

    /**
     * 通用的请求转发 适用于控制器方法、拦截器、过滤器
     * @param uri
     * @param request
     * @param response
     */
    public static void  forward(String uri, ServletRequest request, ServletResponse response){
        try {
            // 原生Servlet API是严格匹配前缀的
            uri = uri.startsWith("/") ? uri : "/" + uri;
            request.getRequestDispatcher(uri).forward(request,response);
        } catch (Exception e) {
            if (e instanceof IllegalStateException){
                // 如果是非法状态异常，其实是类似于警告，并不影响功能，可以忽略
                log.error("Exception to forward {}, message:{}", new Object[]{uri, e.getMessage()});
            }else {
                log.error("Exception to forward {}, message:{}", new Object[]{uri, e.getMessage()}, e);
            }
        }
    }


}
