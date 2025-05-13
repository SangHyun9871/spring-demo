package com.cmsoft.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.cmsoft.core.vo.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("Unexpected error occurred", e);
        Throwable cause = e.getCause();
        if(cause == null) {
            cause = e;
        }

        if(e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            return new ResponseEntity<>(new ErrorResponse("404","요청하신 페이지를 찾을 수 없습니다.", cause.getMessage()), HttpStatus.NOT_FOUND);
        } else if(e instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            return new ResponseEntity<>(new ErrorResponse("405","지원하지 않는 HTTP 메소드입니다.", cause.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
        } else if(e instanceof org.springframework.web.bind.MissingServletRequestParameterException) {
            return new ResponseEntity<>(new ErrorResponse("400","필수 파라미터가 누락되었습니다.", cause.getMessage()), HttpStatus.BAD_REQUEST);
        } else if(e instanceof org.springframework.web.bind.MissingPathVariableException) {
            return new ResponseEntity<>(new ErrorResponse("400","필수 PathVariable이 누락되었습니다.", cause.getMessage()), HttpStatus.BAD_REQUEST);
        } else if(e instanceof org.springframework.web.bind.MissingRequestHeaderException) {
            return new ResponseEntity<>(new ErrorResponse("400","필수 Header가 누락되었습니다.", cause.getMessage()), HttpStatus.BAD_REQUEST);
        } else if(e instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
            return new ResponseEntity<>(new ErrorResponse("400","잘못된 요청입니다.", cause.getMessage()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ErrorResponse("500","서버 오류가 발생했습니다.", cause.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 