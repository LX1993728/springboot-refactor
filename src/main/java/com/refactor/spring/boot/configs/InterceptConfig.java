package com.refactor.spring.boot.configs;

import com.refactor.spring.boot.interceptors.*;
import com.refactor.spring.boot.refactorInterceptor.InterProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuxun
 * @apiNote 在这里只负责配置拦截器
 */
@Configuration
@EnableConfigurationProperties({InterProperty.class})
public class InterceptConfig implements WebMvcConfigurer {

    private static final String[] excludePathPatterns = new String[]{
            "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
    };

    @Autowired
    private InterProperty interProperty;

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

        // 注意: 默认情况下拦截器配置的顺序即是拦截器的拦截顺序
        registry.addInterceptor(new InterA()).addPathPatterns(interProperty.getPatternsByInterName("inter-A")).excludePathPatterns(excludePathPatterns);
        registry.addInterceptor(new InterB()).addPathPatterns(interProperty.getPatternsByInterName("inter-B")).excludePathPatterns(excludePathPatterns);
        registry.addInterceptor(new InterC()).addPathPatterns(interProperty.getPatternsByInterName("inter-C")).excludePathPatterns(excludePathPatterns);
        registry.addInterceptor(new InterD()).addPathPatterns(interProperty.getPatternsByInterName("inter-D")).excludePathPatterns(excludePathPatterns);
    }



}
