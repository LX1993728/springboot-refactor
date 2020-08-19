package com.refactor.spring.boot.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReqMethodTool implements ApplicationRunner {
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

        // log.info("---{}--{}--",urlInfos.size(), urlMethods.size());
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

    /**
     * 判断是否是数据请求 还是访问页面请求
     * @param httpRequest 判断指定的请求是否是ajax请求
     * @return
     */
    public static boolean isAjax(HttpServletRequest httpRequest) {
        HandlerMethod handlerMethod = getHandleMethodByURI(httpRequest.getRequestURI());
        RequestMappingInfo info = getMappingInfoByURI(httpRequest.getRequestURI());
        if (handlerMethod == null || info == null){
            return true;
        }
        final Method method = handlerMethod.getMethod();
        // 获取请求方法所在的控制器类
        final Class<?> cClazz = method.getDeclaringClass();
        // 如果控制器上带有@RestController注解，一定是ajax请求
        if (cClazz.isAnnotationPresent(RestController.class)){
            return true;
        }

        // 如果请求方法上具有@ResponseBody注解，一定是ajax请求
        if (method.isAnnotationPresent(ResponseBody.class)){
            return true;
        }
        return false;
    }

    /**
     * 判断当前请求是否是ajax请求
     * @return
     */
    public static boolean isAjax() {
        final HttpServletRequest request = ServletTool.getRequest();

        return isAjax(request);
    }
}
