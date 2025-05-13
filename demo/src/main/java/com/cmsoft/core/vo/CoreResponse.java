package com.cmsoft.core.vo;

public abstract class CoreResponse {
    private String code;
    private String message;

    public CoreResponse() {
        this.code = "0000";
        this.message = "Success";
    }

    public CoreResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 