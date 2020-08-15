package com.refactor.spring.boot.refactorInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @apiNote  在项目启动完毕后执行自定义操作
 * @author liuxun
 */

@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandler;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("-----  程序执行完毕，开始执行初始化操作 --------");
        // 1. 获取所有配置了指定注解的拦截器
        getAllInterceptorURIMappingsToMap();
        // 2. 适配拦截器URL映射配置
        setURLMappingsToInterceptors();
    }

    /**
     * @apiNote 将扫描出URL拦截配置适配到拦截器中
     */
    private void setURLMappingsToInterceptors(){

    }

    private void getAllInterceptorURIMappingsToMap(){
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandler.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(Interceptor.class)){
                Interceptor interceptor = method.getAnnotation(Interceptor.class);

                PatternsRequestCondition p = info.getPatternsCondition();
                List<String> urlList = p.getPatterns().stream().collect(Collectors.toList());
                String url = urlList.size() > 0 ? urlList.get(0) : "";
                // 处理rest
                String patternUrl = url.replaceAll("\\{[^}]*\\}","*");
                log.info("--------{}-----------",patternUrl);
                InterURLMappings.setURLToInterceptor(patternUrl, interceptor);
            }
        }
        log.info("--- mapInfo={}------", InterURLMappings.getUrlMappingsMap());
    }
}