package com.cmsoft.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.cmsoft.core.annotation.ApiMethod;
import com.cmsoft.core.annotation.ApiMode;
import com.cmsoft.core.annotation.ApiService;
import com.cmsoft.core.annotation.ApiType;

@ApiService
public class SampleService {
    
    @Value("${test.value}")
    private String testValue;

    @ApiMethod(value = "A001", desc = "Hello API", type = ApiType.REST, mode = ApiMode.SELECT)
    public Object hello() {
        List<Map<String, Object>> list = new ArrayList<>();
        
        Map<String, Object> map1 = new HashMap<>();
        map1.put("hello", "hello");
        list.add(map1);
        
        Map<String, Object> map2 = new HashMap<>();
        map2.put("profile", testValue);
        list.add(map2);
        
        return list;
    }
}
