package com.cmsoft.core.exception;

/**
 * 비즈니스 예외
 */
public class BusinessException extends RuntimeException {
    private final String code;
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
} 