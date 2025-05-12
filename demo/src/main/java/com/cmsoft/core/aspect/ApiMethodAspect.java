package com.cmsoft.core.aspect;

import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmsoft.core.annotation.ApiMethod;
import com.cmsoft.core.handler.ApiMethodJsonResponse;
import com.cmsoft.core.vo.CoreResponse;
import com.cmsoft.core.vo.RestResponse;

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
        String methodId = apiMethod.value();
        String methodDesc = apiMethod.desc();
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            if(result instanceof CoreResponse) {
                ((CoreResponse) result).setExecutionTimeSeconds((System.currentTimeMillis() - startTime) / 1000);
            }else if(ApiMethodJsonResponse.VIEW_TYPES.contains(apiMethod.type())) {
                // ApiMethod.REST에 Object가 반환되는 경우 RestResponse로 래핑
                if(!(result instanceof RestResponse)) {
                    result = new RestResponse<>(result);
                    RestResponse<?> restResponse = (RestResponse<?>) result;
                    restResponse.setExecutionTimeSeconds((System.currentTimeMillis() - startTime) / 1000);
                    restResponse.addMetadata("timestamp", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
                    if(result instanceof List) {
                        restResponse.addMetadata("totalCount", ((List<?>) result).size());
                    }
                }
            }
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.debug("ApiMethod [{}] - {} 실행 시간: {}ms", methodId, methodDesc, executionTime);
        }
    }
} 