package com.cyou.tv.zebra.listeners;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ConfigContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("config context 初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("java context 销毁");
    }
}
