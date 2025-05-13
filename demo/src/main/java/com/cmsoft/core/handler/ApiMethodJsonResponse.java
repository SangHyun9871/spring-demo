package com.cmsoft.core.handler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cmsoft.core.annotation.ApiMethod;
import com.cmsoft.core.annotation.ApiResponseType;


/**
 * ApiMethod 어노테이션의 REST,JSON 타입에 대해 강제로 json return으로 처리하는 핸들러
 */
@SuppressWarnings("null")
public class ApiMethodJsonResponse implements HandlerMethodReturnValueHandler {
    public static final List<ApiResponseType> VIEW_TYPES = Arrays.asList(ApiResponseType.REST, ApiResponseType.JSON);

    private final HttpMessageConverter<?>[] messageConverters;

    public ApiMethodJsonResponse(HttpMessageConverter<?>[] messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Method method = returnType.getMethod();
        if (method == null) {
            return false;
        }
        
        ApiMethod apiMethod = method.getAnnotation(ApiMethod.class);
        return apiMethod != null && VIEW_TYPES.contains(apiMethod.type());
    }

    @Override
    public void handleReturnValue(Object returnValue,MethodParameter returnType,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        
        mavContainer.setRequestHandled(true);
        
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(request);
        ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);

        writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
    }

    @SuppressWarnings("unchecked")
	private void writeWithMessageConverters(Object returnValue, MethodParameter returnType,
            ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
            throws IOException, HttpMediaTypeNotAcceptableException {
        
        Class<?> returnValueClass = returnValue.getClass();
        MediaType contentType = outputMessage.getHeaders().getContentType();
        
        if (contentType == null) {
            contentType = MediaType.APPLICATION_JSON;
        }
        
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter.canWrite(returnValueClass, contentType)) {
                ((HttpMessageConverter<Object>) converter).write(returnValue, contentType, outputMessage);
                return;
            }
        }
        
        throw new HttpMediaTypeNotAcceptableException("No suitable message converter found");
    }
} 