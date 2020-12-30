package com.refactor.spring.boot.tasks.service.serviceImpl;

import com.refactor.spring.boot.tasks.JobTask;
import com.refactor.spring.boot.tasks.service.ServiceForController;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ServiceForControllerImpl implements ServiceForController {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Override
    public void executeJobForQueryUsername(Long  userId) {
        JobTask jobTask = new JobTask(userId);
        executor.execute(jobTask);
    }
}
