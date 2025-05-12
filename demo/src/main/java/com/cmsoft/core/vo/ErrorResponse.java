package com.cmsoft.core.vo;

public class ErrorResponse extends CoreResponse {
    private String detail;

    public ErrorResponse(String code, String message) {
        super(code, message);
    }

    public ErrorResponse(String code, String message, String detail) {
        super(code, message);
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
} 