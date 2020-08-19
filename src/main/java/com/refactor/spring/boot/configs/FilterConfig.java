package com.refactor.spring.boot.configs;

import com.refactor.spring.boot.filters.*;
import com.refactor.spring.boot.property.FilterProperty;
import com.refactor.spring.boot.property.InterProperty;
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
        registration.setOrder(Ordered.LOWEST_PRECEDENCE -1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean registerFilter2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogCostFilter2());
        registration.addUrlPatterns("/*");
        registration.setName("logCostFilter-2");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean filterA() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FilterA());
        registration.addUrlPatterns(filterProperty.getPatternsByFilterName("filter-A").toArray(new String[]{}));
        registration.setName("filter-A");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE-4);
        return registration;
    }
    @Bean
    public FilterRegistrationBean filterB() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FilterB());
        registration.addUrlPatterns(filterProperty.getPatternsByFilterName("filter-B").toArray(new String[]{}));
        registration.setName("filter-B");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE-3);
        return registration;
    }
    @Bean
    public FilterRegistrationBean filterC() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FilterC());
        registration.addUrlPatterns(filterProperty.getPatternsByFilterName("filter-C").toArray(new String[]{}));
        registration.setName("filter-C");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE-2);
        return registration;
    }

}
