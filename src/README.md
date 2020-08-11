# 核心技术要点说明(Springboot隐藏的深坑):
 - WebMvcConfigurer 接口
 - WebMvcConfigurerAdapter 类
 - WebMvcConfigurationSupport 类

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