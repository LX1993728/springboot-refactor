package com.refactor.spring.boot.error;

import com.refactor.spring.boot.tools.ReqMethodTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @apiNote 对所有的控制器进行增强 异常处理(Ajax[xml,json] 以及页面控制器异常)
 * & 解决引入xml依赖后导致默认的json类型失效
 * @author liuxun
 */

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Object parameterErrorHandler(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e) {
        if (!ReqMethodTool.isAjax()){
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
        if (!ReqMethodTool.isAjax()){
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

}
