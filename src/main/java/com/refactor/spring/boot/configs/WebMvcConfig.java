package com.refactor.spring.boot.configs;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON); // 全局的默认返回类型
    }

    // Tomcat Cookie 处理配置 Bean
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

    // 处理异常 springboot Caused by:  Unable to process parts as no multi-part configuration has been provided
    // https://stackoverflow.com/questions/24265573/unable-to-process-parts-as-no-multi-part-configuration-has-been-provided
    @Bean
    public CommonsMultipartResolver filterMultipartResolver(){
        CommonsMultipartResolver resolver = new
                CommonsMultipartResolver();
        return resolver;
    }
}
