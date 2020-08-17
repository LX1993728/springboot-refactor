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
 
 ## 关于拦截器栈的配置
 因struts拦截器栈的配置和SpringMVC拦截器的配置方向正好是相反的而且拦截器是不存在多个栈的概念的; 为了重构方便,在这里添加了自定义的配置文件Interceptor.properties
 添加拦截器栈时需要配置两处，一个需要在配置文件中配置每个拦截器栈的名称以及它所包含的拦截器标识和对应的路径映射信息,第二处是需要在
 InterceptorConfig.java类中按照struts.xml的配置顺序依次添加对应的拦截器并根据对应的标识添加路径映射。<br/>
 对于新的重构项目front而言，我会配置改造好拦截器以及对应的拦截器栈信息，新入的重构人员只需在对应的拦截器栈中添加对应action方法的路径映射即可.
 
 ## 拦截器配置请求转发
 有两种实现请求拦截器的方法，一种是实现HandlerInterceptor接口，另外一种是继承HandlerInterceptorAdapter类<br/>
 建议使用第一种实现HandlerInterceptor接口而不要继承HandlerInterceptorAdapter类，如果继承HandlerInterceptorAdapter类在拦截器
 内实现请求转发 ``request.getRequestDispatcher("/xx").forward(request,response);`` 转发到控制器方法时会出现死循环且不停的报异常。