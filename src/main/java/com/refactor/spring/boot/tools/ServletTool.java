package com.refactor.spring.boot.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 请求转发工具
     * @param uri
     */
    public static void forward(String  uri){
        try {
            HttpServletRequest request = getRequest();
            HttpServletResponse response = getResponse();
            request.getRequestDispatcher(uri).forward(request,response);
        }catch (Exception e){
            log.error("Exception to forward {}, message:{}", uri, e.getMessage(), e);
        }
    }
}
