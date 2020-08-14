package com.refactor.spring.boot.configs;

import com.refactor.spring.boot.interceptors.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuxun
 * @apiNote 在这里只负责配置拦截器
 */
@Configuration
public class InterceptConfig implements WebMvcConfigurer {

    private static final String[] excludePathPatterns = new String[]{
            "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
    };

    /**
     * @apiNote 配置拦截器并对swagger的路径进行方向
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         1.加入的顺序就是拦截器执行的顺序，
         2.按顺序执行所有拦截器的preHandle
         3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle
        */
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);

    }


}
