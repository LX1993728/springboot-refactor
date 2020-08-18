package com.refactor.spring.boot.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RequestMethodTool implements ApplicationRunner {
    /**
     * key: 请求路径映射 value:对应的控制器方法
     */
    private static final Map<String, HandlerMethod>  urlMethods = new HashMap<>();
    private static final Map<String,RequestMappingInfo> urlInfos = new HashMap<>();
    private static final AntPathMatcher matcher = new AntPathMatcher();

    // =======  程序启动完毕后执行初始化操作 ================
    @Override
    public void run(ApplicationArguments args) throws Exception {
        fetchInfoAndMethods();
    }

    // 抽取 请求方法以及映射信息到集合中
    private static void fetchInfoAndMethods(){
        // 获取url与类和方法的对应信息
        final RequestMappingHandlerMapping requestMappingHandler = SpringContextTool.getContext()
                .getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandler.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Method method = handlerMethod.getMethod();

            // 获取URL
            PatternsRequestCondition p = info.getPatternsCondition();
            List<String> urlList = p.getPatterns().stream().collect(Collectors.toList());
            String url = urlList.size() > 0 ? urlList.get(0) : "";
            // 处理rest 修改扫描路径映射中的参数为通配符
            String patternUrl = url.replaceAll("\\{[^}]*\\}","*");

            urlMethods.put(patternUrl, handlerMethod);
            urlInfos.put(patternUrl, info);
        }

        log.info("---{}--{}--",urlInfos.size(), urlMethods.size());
    }



    /**
     * 根据请求路径 获取对应的请求方法
     * 方便反射，用于获取请求方法的注解等
     * @param uri 请求的路径
     * @return
     */
    public static HandlerMethod getHandleMethodByURI(String uri){
        if (urlMethods.isEmpty()){
            fetchInfoAndMethods();
        }
        List<String> matchKeys = urlMethods.keySet().stream().filter(p -> matcher.match(p, uri)).collect(Collectors.toList());
        if (matchKeys.size() > 0){
            return urlMethods.get(matchKeys.get(0));
        }
        return null;
    }

    /**
     * 根据请求路径获取对应请求方法的映射信息
     * @param uri
     * @return
     */
    public static RequestMappingInfo getMappingInfoByURI(String uri){
        if (urlInfos.isEmpty()){
            fetchInfoAndMethods();
        }
        List<String> matchKeys = urlInfos.keySet().stream().filter(p -> matcher.match(p, uri)).collect(Collectors.toList());
        if (matchKeys.size() > 0){
            return urlInfos.get(matchKeys.get(0));
        }
        return null;
    }
}
