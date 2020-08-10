package com.refactor.spring.boot.configs;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuxun
 * @apiNote 为访问的地址配置相关的后缀访问规则
 * 注意：可以添加多个ServletRegistrationBean只要名称beanID不冲突就不会覆盖或冲突, 但是
 * WebMvcConfigurationSupport的子配置类只能有一个, 如果想拆解可以配置多个实现WebMvcConfigurer接口的配置类来实现
 */

@Configuration
public class URLSuffixConfig implements WebMvcConfigurer {
    /**
     * @apiNote 设置url后缀匹配规则
     * 该设置匹配所有的后缀, 使用.do或.action都可以
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true) // 设置是否开启后缀匹配模式, 即: /test.*
                .setUseTrailingSlashMatch(true);  // 设置是否自动后缀路径模式匹配, 即: /test/
    }

    /**
     * @apiNote 设置严格匹配*.action和 * (仅仅匹配*.action 和 /)
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean urlSuffixRegistrationBean(DispatcherServlet dispatcherServlet){
        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletServletRegistrationBean.addUrlMappings("*.action");
        servletServletRegistrationBean.addUrlMappings("/");
        return servletServletRegistrationBean;
    }


}
