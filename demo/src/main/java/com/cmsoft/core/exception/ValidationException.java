package com.cmsoft.core.exception;

/**
 * 유효성 검사 예외
 */
public class ValidationException extends RuntimeException {
    private final String code;
    private final String detail;
    
    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
        this.detail = null;
    }
    
    public ValidationException(String code, String message, String detail) {
        super(message);
        this.code = code;
        this.detail = detail;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDetail() {
        return detail;
    }
} 