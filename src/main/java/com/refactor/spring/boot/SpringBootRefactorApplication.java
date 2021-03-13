package com.refactor.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@ServletComponentScan // 注册servlet方式一：通过扫描@WebServlet注解实现
@SpringBootApplication
public class SpringBootRefactorApplication {

    /**
     * @apiNote 配置试图解析是为了解决添加WebMvcConfigurationSupport配置类后无法访问jsp的BUG
     * @return
     */
    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(0);
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRefactorApplication.class, args);
    }

}
