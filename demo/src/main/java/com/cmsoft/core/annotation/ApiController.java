package com.cmsoft.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import io.micrometer.common.lang.NonNull;

/**
 * API 메소드를 담을 클래스를 정의
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ApiController {
    @NonNull
    String value() default "/service"; // API 메소드의 기본 경로
}
