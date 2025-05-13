package com.cmsoft.core.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import com.cmsoft.core.annotation.ApiMethod;
import com.cmsoft.core.annotation.ApiController;

/**
 * ApiMethod 어노테이션이 있는 메소드를 requestMapping 정보를 생성하여 등록하는 핸들러
 */
public class ApiRequestMappingHandler extends RequestMappingHandlerMapping {
    
    private final Map<String, Method> methodMap = new HashMap<>();
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        
        // ApiController 어노테이션을 찾아서 ApiMethod 어노테이션이 있는 메소드를 등록
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(ApiController.class);
        for (Object controller : controllers.values()) {
            // bean에서 ApiController 어노테이션을 찾음
            ApiController apiController = AnnotationUtils.findAnnotation(controller.getClass(), ApiController.class);
            String api_path = (apiController != null && apiController.value() != null) ? apiController.value() : "/api";

            for (Method method : controller.getClass().getMethods()) {
                // 메소드에서 ApiMethod 어노테이션을 찾음
                ApiMethod apiMethod = AnnotationUtils.findAnnotation(method, ApiMethod.class);
                if (apiMethod != null) {
                    String methodCode = method.getName();
                    methodMap.put(methodCode, method);
                    
                    // RequestMappingInfo 생성 및 등록
                    RequestMappingInfo mappingInfo = RequestMappingInfo
                        .paths(api_path + "/" + methodCode)
                        .build();
                    
                    registerHandlerMethod(controller, method, mappingInfo);
                }
            }
        }
    }
    
    public Method getMethodByCode(String methodCode) {
        return methodMap.get(methodCode);
    }
} 