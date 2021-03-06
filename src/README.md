# 核心技术要点说明(Springboot隐藏的深坑):
 - WebMvcConfigurer 接口
 - WebMvcConfigurerAdapter 类
 - WebMvcConfigurationSupport 类
 - 拦截器栈的配置

## 说明：
 后面两个类都实现了WebMvcConfigurer接口。<br/>
 ① WebMvcConfigurationSupport: 只能有一个继承它的配置类且配置类继承WebMvcConfigurationSupport后会使SpringBoot类的自动配置功能失效导致拦截器等配置无法生效. <br/>
 ② WebMvcConfigurerAdapter: WebMvcConfigurerAdapter在Springboot新版本中已经被淘汰. <br/>
 ③ WebMvcConfigurer: 可以创建多个实现此接口的配置类取代WebMvcConfigurationSupport。但对于重构旧项目时特别涉及到原生的SpringAOP时出现异常. <br/>
## 建议 
 如果既要兼容旧的Spring规范又不能使自动配置失效，最好的填坑方法是: <br/>
 @EnableWebMvc + 一个空的WebMvcConfigurationSupport配置子类 + 多个实现WebMvcConfigurer的配置类 <br/>
 原因如下:<br/>
 一个空的WebMvcConfigurationSupport配置子类为了兼容旧的SpringAOP规范; <br/>
 @EnableWebMvc 接管自动配置,弥补由于配置WebMvcConfigurationSupport导致的拦截器失效; <br/>
 WebMvcConfigurer用于真正配置EE方面的内容; <br>
 
 # 关于拦截器栈的配置
 因struts拦截器栈的配置和SpringMVC拦截器的配置方向正好是相反的而且拦截器是不存在多个栈的概念的; 为了重构方便,在这里添加了自定义的配置文件Interceptor.properties
 添加拦截器栈时需要配置两处，一个需要在配置文件中配置每个拦截器栈的名称以及它所包含的拦截器标识和对应的路径映射信息,第二处是需要在
 InterceptorConfig.java类中按照struts.xml的配置顺序依次添加对应的拦截器并根据对应的标识添加路径映射。<br/>
 对于新的重构项目front而言，我会配置改造好拦截器以及对应的拦截器栈信息，新入的重构人员只需在对应的拦截器栈中添加对应action方法的路径映射即可.
 
 ## 拦截器配置请求转发
 有两种实现请求拦截器的方法，一种是实现HandlerInterceptor接口，另外一种是继承HandlerInterceptorAdapter类<br/>
 建议使用第一种实现HandlerInterceptor接口而不要继承HandlerInterceptorAdapter类，如果继承HandlerInterceptorAdapter类在拦截器
 内实现请求转发 ``request.getRequestDispatcher("/xx").forward(request,response);`` 转发到控制器方法时会出现死循环且不停的报异常。
 
 # 使用Filter替代Struts拦截器栈方案 (丢弃spring拦截器方案)
 因为在spring的Interceptor中无法对控制器方法的执行进行try-catch操作，从而无法兼容旧struts拦截器中拦截捕获以及finally进行处理，比如以下代码:<br/>
```
   try {
        return arg0.invoke();
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
        request.setAttribute("_msg", "系统错误");
        return Action.ERROR;
    }finally{
       // do other somethings
    }
 ```
因此使用Filter过滤器栈机制替换struts2拦截器栈机制. 

## Filter替换Struts2拦截器注意事项
① Filter仅支持以/开头以*结尾的路径，并不支持PathMatcher比如/a_*/*等 <br/>
② Filter过滤器的执行全部是在spring拦截器之前的，后续开发如果需要在旧Struts 拦截器之前执行的，就不能选用Spring的拦截器了

## SpringBoot 自定义Bean注入 注意事项
springboot由于和spring加载机制的不同，传统的继承 `SpringBootBeanAutowiringSupport` 实现自定义对象成员变量的注入，在springboot中已经不行了，需要自定义。

## HandlerMethodReturnValueHandler 与 ResponseBodyAdvice 冲突
① 需要改变 将ResponseBodyAdvice业务代码 放在HandlerMethodReturnValueHandler中处理