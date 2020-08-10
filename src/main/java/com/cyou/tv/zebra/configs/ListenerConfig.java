package com.cyou.tv.zebra.configs;

import com.cyou.tv.zebra.listeners.ConfigContextListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuxun
 * @apiNote 有关listener的配置
 */
@Configuration
public class ListenerConfig {

    @Bean
    public ServletListenerRegistrationBean configContextListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean<>();
        bean.setListener(new ConfigContextListener());
        return bean;
    }
}
