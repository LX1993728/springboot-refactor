package com.refactor.spring.boot.tasks.service.serviceImpl;

import com.refactor.spring.boot.tasks.service.ServiceForJob;
import org.springframework.stereotype.Service;

@Service
public class ServiceForJobImpl implements ServiceForJob {

    @Override
    public String getUserNameById(Long userId) {
        return "zhangsan_"+userId;
    }
}
