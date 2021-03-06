package com.refactor.spring.boot.controllers;

import com.refactor.spring.boot.domains.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class TestController2 {

    @Autowired
    private Environment environment;

    @Value("${spring.profiles.active}")
    private String profile;

    // 测试
    @GetMapping("/testGetProfile")
    @ResponseBody
    public Object getProfile(){
        for (String profile : environment.getActiveProfiles()){
            log.info("-------- " + profile);
        }
        return  profile;
    }

    @GetMapping("/testHttpLog")
    @ResponseBody
    public Object getHttpLog(HttpServletRequest request){
        // 测试logbook的下游代码在格式是body的情况下是否可以调用getParameter
        final String aaa = request.getParameter("aaa");
        log.info(aaa);
        return   new JSONResult("00000","成功");
    }
}
