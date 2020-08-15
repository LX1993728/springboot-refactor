package com.refactor.spring.boot.refactorInterceptor;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author liuxun
 * @apiNote 用于添加和获取 拦截器和对应URL的匹配映射
 */
@Slf4j
public class InterURLMappings {
    /**
     * key: interceptorName  value: URL mappings
     */
    private static final Map<String, Set<String>> urlMappingsMap = new HashMap<>();

    /**
     *
     * @param urlMapping  url 路径映射
     * @param stackName  拦截器栈 标识
     * @param additionInters 额外的拦截器
     */
    private static void setURLToInterStack(String urlMapping, String stackName, String ... additionInters){
        if (urlMapping == null || stackName == null){
            return;
        }

        Map<String, Set<String>> stackMap = InterceptorStack.getStacksMap();
        if (!stackMap.containsKey(stackName)){
            log.error("not config interceptor stack for name {}", stackName);
        }
        if (stackMap.get(stackName) == null){
            log.error("not init interceptor stack for name {}", stackName);
        }
        // 获取拦截器栈对应包含的的所有拦截器标识
        Set<String> interceptorNames = stackMap.get(stackName);
        if (additionInters != null && additionInters.length > 0){
            interceptorNames.addAll(Arrays.asList(additionInters));
        }
        interceptorNames.forEach(interName ->{
            if (urlMappingsMap.containsKey(interName) && urlMappingsMap.get(interName) != null){
                urlMappingsMap.get(interName).add(urlMapping);
            }else {
                Set<String> newMappings = new HashSet<>();
                newMappings.add(urlMapping);
                urlMappingsMap.put(interName, newMappings);
            }
        });
    }

    public static void setURLToInterceptor(String urlMapping, Interceptor interceptor){
        setURLToInterStack(urlMapping, interceptor.stack(), interceptor.interceptors());
    }

    public static Map<String, Set<String>> getUrlMappingsMap() {
        return urlMappingsMap;
    }
}
