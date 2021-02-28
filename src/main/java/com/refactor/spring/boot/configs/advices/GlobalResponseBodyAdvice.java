package com.refactor.spring.boot.configs.advices;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.refactor.spring.boot.tools.ServletTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @title 全局统一响应处理
 * @author Xingbz
 * @createDate 2019-8-2
 */
@Slf4j
@Profile({"tl_trunk"})
@ControllerAdvice("com.refactor.spring.boot.controllers")
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    /** 此处如果返回false , 则不执行当前Advice的业务 */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
//        return returnType.hasMethodAnnotation(ResponseBody.class);
        return true;
    }

    /** 处理响应的具体业务方法 */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 响应值转JSON串输出到日志系统
        if (log.isInfoEnabled()) {
            log.info("\nURI={}\tstatus={}\nrequestParam={}\nresponseBody={}\n",
                    ServletTool.getRequest().getRequestURI(),
                    ServletTool.getResponse().getStatus(),
                    JSON.toJSONString(ServletTool.getRequest().getParameterMap(), SerializerFeature.UseSingleQuotes),
                    JSON.toJSONString(body, SerializerFeature.UseSingleQuotes)
            );
        }

        return body;
    }
}
