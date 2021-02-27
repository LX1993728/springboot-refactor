package com.refactor.spring.boot.controllers;

import com.alibaba.fastjson.JSON;
import com.refactor.spring.boot.tasks.service.ServiceForController;
import com.refactor.spring.boot.tools.ServletTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(path = "/")
public class TestController {
    @Autowired
    private ServiceForController serviceForController;


    // 测试访问jsp
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
        log.info("-------/test");
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

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public Object testIllegal2(@RequestParam("name")String name,
                              @RequestParam("age")Integer age){
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("名称不能为空！！！");
        }
        ServletTool.getRequest().setAttribute("name", name);
        ServletTool.getRequest().setAttribute("age", age);
        return "/WEB-INF/jsp/view/pageInfo";
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

    // 测试兼容旧的struts接口， 直接返回字符串
    @GetMapping(value = "/str")
    @ResponseBody
    public String getStrData(){
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", 20);

        // String resultJsonStr = JSON.toJSONString(resultMap);
        writeObject(resultMap);
        return null;
    }

    @GetMapping(value = "/jsonOrPage")
    @ResponseBody
    public Object getStrData(@RequestParam(required = false,defaultValue = "false")boolean isPage){
        if (!isPage){
            Map<String,Object>  resultMap = new HashMap<>();
            resultMap.put("name", "张三");
            resultMap.put("age", 20);
            return resultMap;
        }
        // ServletTool.forward("/app/testApp.jsp");
        ServletTool.forward("/app.action");
        return null;
    }

    @GetMapping(value = "/redirect_test")
    @ResponseBody
    public Object redirectTest(@RequestParam(required = false,defaultValue = "false")boolean isSelf){
        if (!isSelf){
           ServletTool.redirect("/app.action", true);
           return null;
        }
        ServletTool.redirect("http://www.baidu.com", false);
        return null;
    }

    @GetMapping(value = "/r")
    public Object r(){
         /*
         spring 原生支持的重定向方式，带不带反斜杠均可跳转 /
          */
        // return "redirect:/app.action";
        return "redirect:app.action";
    }

    /**
     * 测试重定向并传递参数
     * @return
     */
    @GetMapping(value = "/r_p")
    public Object r_p(RedirectAttributes attributes){
        attributes.addAttribute("name", "王五"+ System.currentTimeMillis());
        attributes.addAttribute("age", "29");
        return "redirect:page.action";
    }

    @GetMapping(value = "/r_p2") // FIXME:注意此方法是存在问题的，经访问测试发现无法取到重定向后的请求参数
    public Object r_p2(RedirectAttributes attributes){
        attributes.addFlashAttribute("name", "王五"+ System.currentTimeMillis());
        attributes.addFlashAttribute("age", "29");
        return "redirect:page.action";
    }

    @GetMapping(value = "/test_match") // FIXME:注意此方法是存在问题的，经访问测试发现无法取到重定向后的请求参数
    @ResponseBody
    public Object testMatchNum(){
        AntPathMatcher matcher = new AntPathMatcher();
        final boolean match1 = matcher.match("/aaa/^[0-9]*$", "/aaa/12300");
        return match1;
    }


    private void writeObject(Object o){
        final HttpServletResponse response = ServletTool.getResponse();
        response.setContentType("text/plain;charset=UTF-8");
        response.setLocale(new Locale("zh","CN"));
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(o));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/test_job")
    @ResponseBody
    public Object testJob(@RequestParam(required = false,defaultValue = "1") Long userId){
        serviceForController.executeJobForQueryUsername(userId);
        return "success";
    }

    @RequestMapping(value = "/test_trim",method = RequestMethod.GET)
    @ResponseBody
    public Object testTrim(@RequestParam(value = "name", required = false)String name,
                              @RequestParam(value = "age", required = false)Long age){

        final Map<String, String[]> reqMap = ServletTool.trimReqNameMap();
        name = ServletTool.getParamFromMap(reqMap,String.class,"name");
        age = ServletTool.getParamFromMap(reqMap,Long.class,"age");

        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("名称不能为空！！！");
        }
        Map<String,Object>  resultMap = new HashMap<>();
        resultMap.put("name", name);
        resultMap.put("age", age);
        return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
    }

}
