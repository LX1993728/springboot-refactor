package com.refactor.spring.boot.tools;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

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

    /**
     * @apiNote 为请求参数名去除空格
     */
    public static Map<String, String[]> trimReqNameMap(){
        final HttpServletRequest request = getRequest();
        final Map<String, String[]> trimMap = new HashMap<>();
        final Map<String, String[]> reqMap = request.getParameterMap();
        for (String key : reqMap.keySet()) {
            String[] value = reqMap.get(key);
            trimMap.put(key.trim(), value);
        }

        return trimMap;
    }

    /**
     * 注意：目前只处理简单类型 Long String Integer Boolean Double Float
     * @param map 已经去空格转化后的请求Map
     * @param c  要获取请求参数的类型
     * @param paramName 要获取请求参数的名称
     * @param <T> 返回的请求参数
     * @return
     */
    public static <T> T getParamFromMap(Map<String, String[]> map,Class<T> c,String paramName ){
        if (map == null || map.isEmpty() || StringUtils.isEmpty(paramName) || !map.containsKey(paramName)){
            return null;
        }

        final String[] values = map.get(paramName);
        if (values == null || values.length == 0){
            return null;
        }

        String value = values[0];

        return getVal(value, c);
    }

    public static <T> T getVal(String val, Class<T> type) {
        if (type.isAssignableFrom(String.class)){
            return (T) val.trim();
        }
        // 把val转换成type类型返回 比如说getVal("123",Integer.class) 返回一个123
        T value = null;
        String className = type.getSimpleName();
        if (type == Integer.class) {
            className = "Int";
        }
        String convertMethodName = "parse" + className;
        try {
            Method m = type.getMethod(convertMethodName, String.class);
            value = (T) m.invoke(null, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     *  一版在servlet中使用
     * @param response 原生的response对象
     * @param o  写回前端的对象
     * @throws IOException
     */
    public static void writeJsonStrForObject(HttpServletResponse response, Object o) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setLocale(new Locale("zh","CN"));
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(o));
    }

}
