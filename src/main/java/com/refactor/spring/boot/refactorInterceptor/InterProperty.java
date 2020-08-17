package com.refactor.spring.boot.refactorInterceptor;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @apiNote  配置所有拦截器栈相关的信息
 * @author liuxun
 */
@Component
@Data
@PropertySource("classpath:interceptor.properties")
@ConfigurationProperties(prefix = "stack")
public class InterProperty {
    private List<InterceptStack> list;

    public List<String> getPatternsByInterName(String name){
        Set<String> patterns = new HashSet<>();
        list.stream().filter(s->s.getInters().contains(name)).map(InterceptStack::getPattens).forEach(patterns::addAll);
        /**
         * 注意事项: 如果addPathPatterns为空数组的话在springboot中默认会匹配所有的请求即/**
         * 所以如果没有在interceptor.properties中没有配置对应拦截器的话，需要给予默认值为空字符串
         */
        if (patterns.isEmpty()){
            patterns.add("");
        }
        return new ArrayList<>(patterns);
    }

}
@Data
class InterceptStack{
    // 路径映射
    private List<String> pattens ;
    // 包含拦截器的名称ID
    private List<String> inters ;
    //拦截器栈的名称
    private String name;
}
