package com.refactor.spring.boot.tasks;

import com.refactor.spring.boot.configs.beanAutowire.SpringBootBeanAutowiringSupport;
import com.refactor.spring.boot.tasks.service.ServiceForJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JobTask extends SpringBootBeanAutowiringSupport implements Runnable{
    private Long userId;

    @Autowired
    private ServiceForJob serviceForJob;

    public JobTask(Long userId) {
        this.userId = userId;
    }


    @Override
    public void run() {
        String userName = serviceForJob.getUserNameById(userId);
        log.info("======getUserName= {} =======", userName);
    }
}
