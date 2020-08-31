package com.refactor.spring.boot.configs;

import com.refactor.spring.boot.filters.*;
import com.refactor.spring.boot.filters.stack.BaseFilter;
import com.refactor.spring.boot.filters.stack.FilterA;
import com.refactor.spring.boot.filters.stack.FilterB;
import com.refactor.spring.boot.filters.stack.FilterC;
import com.refactor.spring.boot.property.FilterProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author liuxun
 * @apiNote 有关filter的相关配置
 */
@Configuration
@EnableConfigurationProperties({FilterProperty.class})
public class FilterConfig {

    @Autowired
    private FilterProperty filterProperty;

    /**
     * @apiNote filter配置方式一：通过声明Spring Bean FilterRegistrationBean对Filter进行包装
     * @return
     * 注意：对这种配置filter的方式，order值越小，越先进入，越后放行
     */
    @Bean
    public FilterRegistrationBean registerFilter1() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogCostFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logCostFilter-1");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean registerFilter2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogCostFilter2());
        registration.addUrlPatterns("/*");
        registration.setName("logCostFilter-2");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return registration;
    }

    /**
     *
     * @param name 设置filter的名称
     * @param filter 指定的自定义filter
     * @param weightOrder 保证顺序
     * @return
     */
    private FilterRegistrationBean wrapperFilter(String name, BaseFilter filter, int weightOrder){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        String filterName = name == null ? filter.getClass().getSimpleName() : name;
        filter.addUrlMappings(filterProperty.getPatternsByFilterName(filterName));
        registration.setFilter(filter);
        registration.setName(filterName);
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + weightOrder);
        return registration;
    }
    @Bean
    public FilterRegistrationBean filterA() {
        return wrapperFilter("filter-A", new FilterA(), 1);
    }
    @Bean
    public FilterRegistrationBean filterB() {
        return wrapperFilter("filter-B", new FilterB(), 2);
    }
    @Bean
    public FilterRegistrationBean filterC() {
        return wrapperFilter("filter-C", new FilterC(), 3);
    }

}
