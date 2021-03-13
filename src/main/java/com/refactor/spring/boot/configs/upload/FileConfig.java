package com.refactor.spring.boot.configs.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    /**
     * 指定自定义解析器
     * 将 multipartResolver 指向我们刚刚创建好的继承 CustomMultipartResolver 类的 自定义文件上传处理类
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        return new CustomMultipartResolver();
    }


    /**
     * 图片保存路径，自动从yml文件中获取数据
     *   示例： D:/upload/
     */
    @Value("${file.path}")
    private String fileSavePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/images/”开头的，
         * 就给我映射到本机的“E:/images/”这个文件夹内，去找你要的资源
         * 注意：E:/images/ 后面的 “/”一定要带上
         */
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+fileSavePath);
    }
}

