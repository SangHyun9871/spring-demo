package com.cmsoft.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cmsoft.core.vo.ErrorResponse;
import com.cmsoft.core.vo.RestResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestResponse<ErrorResponse> handleException(Exception e) {
        logger.error("Unexpected error occurred", e);
        ErrorResponse error = new ErrorResponse(
            "E001", 
            "서버 오류가 발생했습니다.",
            e.getMessage()
        );
        return new RestResponse<>(error);
    }
    
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<ErrorResponse> handleBusinessException(BusinessException e) {
        logger.warn("Business error occurred: {}", e.getMessage());
        ErrorResponse error = new ErrorResponse(
            e.getCode(),
            e.getMessage()
        );
        return new RestResponse<>(error);
    }
    
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<ErrorResponse> handleValidationException(ValidationException e) {
        logger.warn("Validation error occurred: {}", e.getMessage());
        ErrorResponse error = new ErrorResponse(
            e.getCode(),
            e.getMessage(),
            e.getDetail()
        );
        return new RestResponse<>(error);
    }
} 