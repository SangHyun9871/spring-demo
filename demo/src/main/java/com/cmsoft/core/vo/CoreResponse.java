package com.cmsoft.core.vo;

public abstract class CoreResponse {
    private String code;
    private String message;
    private long executionTimeSeconds;

    public CoreResponse() {
        this.code = "0000";
        this.message = "Success";
        this.executionTimeSeconds = 0;
    }

    public CoreResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.executionTimeSeconds = 0;
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

    public long getExecutionTimeSeconds() {
        return executionTimeSeconds;
    }

    public void setExecutionTimeSeconds(long executionTimeSeconds) {
        this.executionTimeSeconds = executionTimeSeconds;
    }
} 