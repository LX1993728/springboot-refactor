package com.refactor.spring.boot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController2 {

    @Autowired
    private Environment environment;

    @Value("${spring.profiles.active}")
    private String profile;

    // 测试
    @GetMapping("/testGetProfile")
    public Object getProfile(){
        for (String profile : environment.getActiveProfiles()){
            log.info("-------- " + profile);
        }
        return  profile;
    }
}
