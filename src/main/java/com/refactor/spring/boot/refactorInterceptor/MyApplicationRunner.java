package com.refactor.spring.boot.refactorInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @apiNote  在项目启动完毕后执行自定义操作
 * @author liuxun
 */

@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("-----  程序执行完毕，开始执行初始化操作 --------");
        // 1. 适配拦截器URL映射配置
        setURLMappingsToInterceptors();
    }

    /**
     * @apiNote 将扫描出URL拦截配置适配到拦截器中
     */
    private void setURLMappingsToInterceptors(){

    }
}
