package com.refactor.spring.boot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Scope("prototype")
@Controller
public class TestScopeController {

    {
        log.info("=======初始化创建控制器================");
    }

    @GetMapping("/scope")
    public Object scope(){
        return new HttpEntity<String>("scope");
    }
}
