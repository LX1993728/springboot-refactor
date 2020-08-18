package com.refactor.spring.boot.configs;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuxun
 * @apiNote 配置全局的错误页面以及主页面
 */
@Configuration
public class ErrorPageConfig implements WebMvcConfigurer, ErrorPageRegistrar {
    /**
     * 配置主页面  相当于一个普通的控制器 等同于web.xml中的配置<welcome-file-list> 以及<error-page/>
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 配置欢迎页
        registry.addViewController("/").setViewName("/WEB-INF/home");
        // 配置错误页
        registry.addViewController("/404").setViewName("/WEB-INF/404");
        registry.addViewController("/400").setViewName("/WEB-INF/400");
        registry.addViewController("/401").setViewName("/WEB-INF/401");
        registry.addViewController("/500").setViewName("/WEB-INF/500");
        // 配置请求转发 , 配置请求转发时需要在路径前添加"forward:"前缀
         registry.addViewController("/test2_r").setViewName("forward:/test2");
    }

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        registry.addErrorPages(error400Page,error401Page,error404Page,error500Page);

    }
}
