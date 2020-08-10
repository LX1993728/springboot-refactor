package com.refactor.spring.boot.configs;

import com.refactor.spring.boot.filters.LogCostFilter;
import com.refactor.spring.boot.filters.LogCostFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author liuxun
 * @apiNote 有关filter的相关配置
 */
@Configuration
public class FilterConfig {

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
}
