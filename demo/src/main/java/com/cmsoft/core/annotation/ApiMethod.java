package com.cmsoft.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.lang.NonNull;

/**
 * API 메소드 정의
 * 
 * @value API 메소드 ID
 * @desc API 메소드 설명
 * @type API 메소드 타입 (REST/VIEW/DATA)
 * @mode API 메소드 모드 (NORMAL/SELECT/INSERT/UPDATE/DELETE)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiMethod {
    @NonNull 
    String value();                       // API 메소드 ID
    
    @NonNull
    String desc();                        // API 메소드 설명

    @NonNull
    ApiType type() default ApiType.VIEW;  // API 메소드 타입

    @NonNull
    ApiMode mode() default ApiMode.NORMAL; // API 메소드 모드
} 