package com.refactor.spring.boot.refactorInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * @author liuxun
 * @apiNote 用于在Bean的初始化过程中扫描自定义的@Interceptor注解，将URI以及对应的Interceptor信息统一抽取
 */
@Slf4j
@Component
public class BeanPostResolver implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 进行拦截映射的配置抽取
        fetchMappingsConfig(bean,beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void fetchMappingsConfig(Object bean, String beanName){
        Class<?> clzz = bean.getClass();
        // 首先判断此bean是否是控制器
        boolean isController = clzz.isAnnotationPresent(Controller.class);
        boolean isRestController = clzz.isAnnotationPresent(RestController.class);

        // 不是控制器类型，不做任何处理
        if (!isController && !isRestController){
            return;
        }
        String requestPrefix = "";
        // 获取控制器的前缀路径
        if (clzz.isAnnotationPresent(RequestMapping.class)){
            RequestMapping annotation = clzz.getAnnotation(RequestMapping.class);
            String[] prefixs = annotation.path().length > 0 ? annotation.path() : annotation.value();
            requestPrefix =  prefixs.length > 0 ? prefixs[0] : "";
        }
        log.info("------------- request prefix= {} ------------", requestPrefix);


        for (Method method : clzz.getMethods()){
            if (method.isAnnotationPresent(Interceptor.class)){

            }
        }

    }

    // 冲注解中获取
    private String getPathFromAnnotation(Method method){
        // 获取所有@XXMappings的注解
        return null;
    }
}
