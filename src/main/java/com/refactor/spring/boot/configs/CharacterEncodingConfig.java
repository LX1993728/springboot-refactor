package com.refactor.spring.boot.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

/**
 * @author liuxun
 * @apiNote 处理springboot乱码
 */
@Configuration
public class CharacterEncodingConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        //用于注册过滤器
        FilterRegistrationBean frb = new FilterRegistrationBean();
        // 使用spring 提供的字符编码过滤器，不用自己写过滤器
        CharacterEncodingFilter cef = new CharacterEncodingFilter(StandardCharsets.UTF_8.toString(),true);
        frb.setFilter(cef);
        return frb;
    }

}


