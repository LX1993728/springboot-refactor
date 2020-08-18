package com.refactor.spring.boot.error;


import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @apiNote 全局异常处理 针对Rest请求
 * Warn：这种方式会拦截全局异常 不管是页面异常还是xml或json异常请求异常都会 返回json数据，不是我们想要的
 */

/*
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理非法的请求参数异常
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> parameterErrorHandler(HttpServletRequest request, IllegalArgumentException e) {
        ErrorInfo info = new ErrorInfo() ;
        info.setCode(HttpStatus.BAD_REQUEST.value());     // 标记一个错误信息类型
        info.setMessage(e.getMessage());
        info.setUrl(request.getRequestURL().toString());
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) // 所有的异常都是Exception子类
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo otherErrorHandler(HttpServletRequest request, Exception e) {
        ErrorInfo info = new ErrorInfo() ;
        info.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());     // 标记一个错误信息类型
        info.setMessage(e.getMessage());
        info.setUrl(request.getRequestURL().toString());
        log.error(e.getMessage(),e);
        return info ;
    }
}
*/