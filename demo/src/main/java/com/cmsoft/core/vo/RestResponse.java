package com.cmsoft.core.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Rest API 응답 공통 객체
 */
public class RestResponse<T> extends CoreResponse {
    private T data;            // 응답 데이터
    private Map<String, Object> metadata;  // 메타 데이터

    public RestResponse() {
        super();
        this.metadata = new HashMap<>();
    }

    public RestResponse(T data) {
        super();
        this.data = data;
        this.metadata = new HashMap<>();
    }

    public RestResponse(String code, String message) {
        super(code, message);
        this.metadata = new HashMap<>();
    }

    public RestResponse(String code, String message, T data) {
        super(code, message);
        this.data = data;
        this.metadata = new HashMap<>();
    }

    // Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }
} 