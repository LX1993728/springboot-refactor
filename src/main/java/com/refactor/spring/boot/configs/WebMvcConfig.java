package com.refactor.spring.boot.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author liuxun
 * @apiNote MVC相关配置类：包括spring.xml的加载以及静态资源的放行 以及web相关的跨域配置
 * 不建议使用继承WebMvcConfigurationSupport的方式，会关闭自动配置(挖坑)，建议配置多个WebMvcConfigurer
 */

@Configuration
@ImportResource({"classpath*:applicationContext*.xml"})
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * @apiNote 设置跨域相关的配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 是否允许证书 不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }

    /**
     * @apiNote 添加静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加swagger相关的静态资源
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

}
