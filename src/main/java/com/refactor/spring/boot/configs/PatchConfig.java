package com.refactor.spring.boot.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author liuxun
 * @apiNote 此配置不建议使用，但对于一些老项目(使用了很多原生SpringAOP)如果缺少此类的配置会不兼容。
 * 注意：只要配置了WebMvcConfigurationSupport，自动配置就会失效，必须使用@EnableWebMvc来接管自动配置
 */
@Configuration
@EnableWebMvc
public class PatchConfig extends WebMvcConfigurationSupport {

}
