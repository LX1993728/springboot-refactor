package com.refactor.spring.boot.configs.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.refactor.spring.boot.tools.ServletTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@Component
public class JsonToPlainHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponseBody.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // .setRequestHandled(true)表示此函数可以处理请求，不必交给别的代码处理
        mavContainer.setRequestHandled(true);
        if (returnValue != null && !(returnValue instanceof String)){
            HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
            response.setContentType("text/plain;charset=UTF-8");
            response.setLocale(new Locale("zh","CN"));
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(returnValue));
        }
        printBody(returnValue);
    }

    private void printBody(Object body){
        // 响应值转JSON串输出到日志系统
        if (log.isInfoEnabled()) {
            log.info("\nURI={}\tstatus={}\nrequestParam={}\nresponseBody={}\n",
                    ServletTool.getRequest().getRequestURI(),
                    ServletTool.getResponse().getStatus(),
                    JSON.toJSONString(ServletTool.getRequest().getParameterMap(), SerializerFeature.UseSingleQuotes),
                    JSON.toJSONString(body, SerializerFeature.UseSingleQuotes)
            );
        }
    }
}
