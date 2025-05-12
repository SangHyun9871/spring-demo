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
import com.cmsoft.core.annotation.ApiService;

/**
 * ApiMethod 어노테이션이 있는 메소드를 requestMapping 정보를 생성하여 등록하는 핸들러
 */
public class ApiMethodHandlerMapping extends RequestMappingHandlerMapping {
    private final String API_METHOD_PATH = "/service";
    
    private final Map<String, Method> methodMap = new HashMap<>();
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        
        // 모든 Controller 빈을 찾아서 ApiMethod 어노테이션이 있는 메소드를 등록
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(ApiService.class);
        for (Object controller : controllers.values()) {
            for (Method method : controller.getClass().getMethods()) {
                ApiMethod apiMethod = AnnotationUtils.findAnnotation(method, ApiMethod.class);
                if (apiMethod != null) {
                    String methodCode = apiMethod.value();
                    methodMap.put(methodCode, method);
                    
                    // RequestMappingInfo 생성 및 등록
                    RequestMappingInfo mappingInfo = RequestMappingInfo
                        .paths(API_METHOD_PATH)
                        //.methods(RequestMethod.GET)
                        .params("method=" + methodCode)
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