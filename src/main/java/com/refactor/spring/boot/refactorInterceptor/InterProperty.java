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
