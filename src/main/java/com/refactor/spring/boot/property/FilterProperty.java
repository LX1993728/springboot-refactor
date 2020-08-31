package com.refactor.spring.boot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @apiNote  配置所有拦截器栈相关的信息
 * @author liuxun
 */
@Component
@Data
@PropertySource("classpath:filter.properties")
@ConfigurationProperties(prefix = "filter-stack")
public class FilterProperty {
    private List<FilterStack> list = new ArrayList<>();

    public List<String> getPatternsByFilterName(String name){
        Set<String> patterns = new HashSet<>();
        list.stream().filter(f->f.getFilters().contains(name)).map(FilterStack::getPatterns).forEach(patterns::addAll);

        Set<String> actionPatterns = new HashSet<>();
        patterns.forEach(p->{
            if (!p.endsWith(".action")){
                actionPatterns.add(p + ".action");
            }
        });
        patterns.addAll(actionPatterns);
        /**
         * 注意事项:
         * 所以如果没有在filter.properties中没有配置对应拦截器的话，需要给予默认值为空字符串
         */
        if (patterns.isEmpty()){
            patterns.add("");
        }
        return new ArrayList<>(patterns);
    }

}

@Data
class FilterStack{
    // 路径映射
    private List<String> patterns =  new ArrayList<>();
    // 包含拦截器的名称ID
    private List<String> filters = new ArrayList<>() ;
    //拦截器栈的名称
    private String name;
}
