package com.refactor.spring.boot.configs;

import com.refactor.spring.boot.interceptors.InterceptorA;
import com.refactor.spring.boot.interceptors.InterceptorB;
import com.refactor.spring.boot.interceptors.InterceptorC;
import com.refactor.spring.boot.interceptors.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxun
 * @apiNote 在这里只负责配置拦截器
 */
@Configuration
public class InterceptConfig implements WebMvcConfigurer {
    @Autowired
    private InterceptorA interceptorA;

    @Autowired
    private InterceptorB interceptorB;

    @Autowired
    private InterceptorC interceptorC;

    private static final String[] excludePathPatterns = new String[]{
            "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
    };

    private static final Map<String, InterceptorRegistration> interRegistrationMap = new HashMap<>();

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
        addInterceptor(registry,interceptorA).addPathPatterns("").excludePathPatterns(excludePathPatterns);
        addInterceptor(registry,interceptorB).addPathPatterns("").excludePathPatterns(excludePathPatterns);
        addInterceptor(registry,interceptorC).addPathPatterns("").excludePathPatterns(excludePathPatterns);

    }

    private InterceptorRegistration addInterceptor(InterceptorRegistry registry,HandlerInterceptor interceptor){
       InterceptorRegistration registration = registry.addInterceptor(interceptor);
        Class interClzz =  interceptor.getClass();
        if (interClzz.isAnnotationPresent(Component.class)) {
            Component component = (Component) interClzz.getAnnotation(Component.class);
            interRegistrationMap.put(component.value(), registration);
        }

        return registration;
    }

    public static Map<String, InterceptorRegistration> getInterRegistrationMap() {
        return interRegistrationMap;
    }
}
