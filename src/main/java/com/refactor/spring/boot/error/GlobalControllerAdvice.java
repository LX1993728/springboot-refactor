package com.refactor.spring.boot.error;

import com.refactor.spring.boot.tools.RequestMethodTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @apiNote 对所有的控制器进行增强 异常处理(Ajax[xml,json] 以及页面控制器异常)
 * & 解决引入xml依赖后导致默认的json类型失效
 * @author liuxun
 */

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Object parameterErrorHandler(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e) {
        if (!isAjax(request)){
            log.error(e.getMessage(),e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("_msg","Bad Request");
            modelAndView.setViewName("forward:/400");
            return modelAndView;
        }else {
            ErrorInfo info = new ErrorInfo() ;
            info.setCode(HttpStatus.BAD_REQUEST.value());     // 标记一个错误信息类型
            info.setMessage(e.getMessage());
            info.setUrl(request.getRequestURL().toString());
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class) // 所有的异常都是Exception子类
    public Object otherErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (!isAjax(request)){
            log.error(e.getMessage(),e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("_msg","系统错误");
            modelAndView.setViewName("forward:/500");
            return modelAndView;
        }else {
            ErrorInfo info = new ErrorInfo() ;
            info.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());     // 标记一个错误信息类型
            info.setMessage(e.getMessage());
            info.setUrl(request.getRequestURL().toString());
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // [填坑]springboot隐藏不合理的地方(引入xml依赖后，凡是不指定contentType的rest请求都默认为了xml)
    // 实现ResponseBodyAdvice接口的方法，修改ResponseBody默认的contentType
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String cTypeValue = selectedContentType.toString();
        if (cTypeValue.equalsIgnoreCase(MediaType.APPLICATION_XHTML_XML_VALUE)){
            response.getHeaders().setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        }
        return body;
    }

    /**
     * 判断是否是数据请求 还是访问页面请求
     * @param httpRequest
     * @return
     */
    public static boolean isAjax(HttpServletRequest httpRequest) {
        HandlerMethod handlerMethod = RequestMethodTool.getHandleMethodByURI(httpRequest.getRequestURI());
        RequestMappingInfo info = RequestMethodTool.getMappingInfoByURI(httpRequest.getRequestURI());
        if (handlerMethod == null || info == null){
            return true;
        }
        final Method method = handlerMethod.getMethod();
        // 获取请求方法所在的控制器类
        final Class<?> cClazz = method.getDeclaringClass();
        // 如果控制器上带有@RestController注解，一定是ajax请求
        if (cClazz.isAnnotationPresent(RestController.class)){
            return true;
        }

        // 如果请求方法上具有@ResponseBody注解，一定是ajax请求
        if (method.isAnnotationPresent(ResponseBody.class)){
            return true;
        }
        return false;
    }
}
