package com.cmsoft.core.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmsoft.core.annotation.ApiMethod;

/**
 * ApiMethod 어노테이션이 적용된 메소드의 실행 시간을 측정하는 Aspect
 */
@Aspect
@Component
public class ApiMethodAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiMethodAspect.class);
    
    /**
     * ApiMethod 어노테이션이 적용된 메소드의 실행 시간을 측정
     * @param joinPoint 실행되는 메소드의 정보
     * @param apiMethod ApiMethod 어노테이션 정보
     * @return 메소드의 실행 결과
     * @throws Throwable 메소드 실행 중 발생한 예외
     */
    @Around("@annotation(apiMethod)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint, ApiMethod apiMethod) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodId = className + "." + joinPoint.getSignature().getName();
        String methodDesc = apiMethod.desc();
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.debug("ApiMethod [{}] - {} 실행 시간: {}ms", methodId, methodDesc, executionTime);
        }
    }
} 