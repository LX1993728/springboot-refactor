package com.refactor.spring.boot.controllers;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/")
public class TestController {
    // 测试访问jsp
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
        return "/WEB-INF/jsp/view/test";
    }

    // 测试访问jsp
    @RequestMapping(value="/app", method = RequestMethod.GET)
    public String testAPP(){
        return "/app/testApp";
    }

    // 测试访问jsp (自定义标签的测试)
    @RequestMapping(value="/tag", method = RequestMethod.GET)
    public String testTag(){
        return "/app/testTag";
    }

    @GetMapping(value = "/test2", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object test2(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", 20);
        return resultMap;
    }

    @GetMapping(value = "/test3/a_{aid}/{bid}_b")
    @ResponseBody
    public Object test3(@PathVariable("aid")String aid,@PathVariable("bid")String bid ){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("aid", aid);
        resultMap.put("bid", bid);
        return resultMap;
    }

    @RequestMapping(value = "/testError",method = RequestMethod.GET)
    @ResponseBody
    public Object testERROR(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", 20/0);
        return resultMap;
    }

    @RequestMapping(value = "/testBad",method = RequestMethod.GET)
    @ResponseBody
    public Object testBAD(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", 20);
        return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/testIllegal",method = RequestMethod.GET)
    @ResponseBody
    public Object testIllegal(@RequestParam("name")String name,
                              @RequestParam("age")Integer age){
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("名称不能为空！！！");
        }
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", name);
        resultMap.put("age", age);
        return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
    }



    // 测试控制器与注解servlet的优先级
    @RequestMapping(value = "/annotation",method = RequestMethod.GET)
    @ResponseBody
    public Object annotation(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "annotation  servlet");
        return resultMap;
    }

    // 测试控制器与注册Bean的servlet的优先级
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    @ResponseBody
    public Object register(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "ServletRegistrationBean  servlet");
        return resultMap;
    }

    // 测试控制器与通过注入ServletContext注册的servlet的优先级
    @RequestMapping(value = "/context",method = RequestMethod.GET)
    @ResponseBody
    public Object context(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "ServletContext  servlet");
        return resultMap;
    }

    // 测试返回xml类型数据
    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Object getXmlData(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", 20);
        return  resultMap;
    }

    // 测试兼容旧的struts接口， 直接返回字符串
    @GetMapping(value = "/str", produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String getStrData(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", 20);

        String resultJsonStr = JSON.toJSONString(resultMap);
        return resultJsonStr;
    }
}
