package com.cyou.tv.zebra.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author liuxun
 * @apiNote MVC相关配置类：包括spring.xml的加载以及静态资源的放行 以及web相关的跨域配置
 */

@Configuration
@ImportResource({"classpath*:applicationContext*.xml"})
public class WebMvcConfig extends WebMvcConfigurationSupport {

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
     * @apiNote 配置拦截器并对swagger的路径进行方向
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localInterceptor())
//                .registry.addPathPatterns("/**")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");


        super.addInterceptors(registry);
    }

    /**
     * @apiNote 添加静态资源
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加swagger相关的静态资源
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

    /**
     * 配置主页面  相当于一个普通的控制器 等同于web.xml中的配置<welcome-file-list>
     * @param registry
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("/WEB-INF/home");
    }
}
