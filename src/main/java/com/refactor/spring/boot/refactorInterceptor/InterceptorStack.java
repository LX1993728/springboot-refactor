package com.refactor.spring.boot.refactorInterceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @apiNote  配置所有拦截器栈相关的信息
 * @author liuxun
 */
public class InterceptorStack {
    // 配置所有拦截器栈的名称标识 注意: 此名称是需要配置在注解里的
    public static final String STACK_ONE = "stackOne";
    public static final String STACK_TWO = "stackTwo";


    /**
     * 配置对应的所有拦截器栈集合
     * key: 拦截器栈的名称标识  value: 拦截器栈包含的拦截器标识
     */
    private static final Map<String, Set<String>> stacksMap = new HashMap<>();

    static {
        // 初始化每个拦截器栈对应的set集合
        stacksMap.put(STACK_ONE, new HashSet<>());
        stacksMap.put(STACK_TWO, new HashSet<>());

        // 然后添加拦截器
        stacksMap.get(STACK_ONE).add(InterceptorNames.INTERCEPTOR_A);
        stacksMap.get(STACK_ONE).add(InterceptorNames.INTERCEPTOR_B);
        stacksMap.get(STACK_TWO).add(InterceptorNames.INTERCEPTOR_B);
        stacksMap.get(STACK_TWO).add(InterceptorNames.INTERCEPTOR_C);
    }

    public static Map<String, Set<String>> getStacksMap() {
        return stacksMap;
    }
}
