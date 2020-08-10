package com.cyou.tv.zebra.configs;

import com.cyou.tv.zebra.servlets.ContextServlet;
import com.cyou.tv.zebra.servlets.RegisterBeanServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author liuxun
 * @apiNote 有关Servlet另外的两种配置方式
 *
 */

@Configuration
public class ServletConfig implements ServletContextInitializer {

    /**
     * @apiNote 注册servlet方式二：通过定义spring bean ServletRegistrationBean 方式
     * @return
     */
    @Bean
    public ServletRegistrationBean servletBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/register");
        registrationBean.setServlet(new RegisterBeanServlet());
        return registrationBean;
    }

    /**
     * @apiNote 方式三：初始化ServletContext的时候注册Servlet
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration initServlet = servletContext.addServlet("contextServlet", ContextServlet.class);
        initServlet.addMapping("/context");

        // 可用于替换web.xml中的 <context-param>
        // servletContext.setInitParameter("","");
    }

}
