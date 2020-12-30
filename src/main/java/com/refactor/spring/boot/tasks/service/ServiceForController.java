package com.refactor.spring.boot.tasks.service;

import com.refactor.spring.boot.tasks.JobTask;

/**
 * @apiNote 供控制器调用的service
 */
public interface ServiceForController {
    void executeJobForQueryUsername(Long  userId);
}
