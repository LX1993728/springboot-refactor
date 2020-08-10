package com.cyou.tv.zebra.configs;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.TaglibDescriptorImpl;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.descriptor.TaglibDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author liuxun
 * @apiNote 用于替换web.xml中<jsp-config>标签，配置一些有关jsp标签(例如jstl标签)
 *  注意：此配置类ConfigurableServletWebServerFactory 只有springboot2.x以上版本才有
 */
@Configuration
public class JspTagsConfig {

    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
        return new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                super.postProcessContext(context);
                List<TaglibDescriptor> taglibs = new ArrayList<>();
                // 为自定义的标签配置location以及uri,相当于web.xml中的
                /*
                   <jsp-config>
                        <taglib>
                            <taglib-uri>/custom/my</taglib-uri>
                            <taglib-location>/WEB-INF/tlds/myTags.tld</taglib-location>
                        </taglib>
                   </jsp-config>
                 */
                TaglibDescriptor customDescriptor = new TaglibDescriptorImpl("/WEB-INF/tlds/myTags.tld","/custom/my");
                taglibs.add(customDescriptor);

                 // jsp-property-group列表和taglib列表
                context.setJspConfigDescriptor(new JspConfigDescriptorImpl(Collections.emptyList(),taglibs));
            }
        };
    }

}
