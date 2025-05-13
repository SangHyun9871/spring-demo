package com.cmsoft.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmsoft.core.annotation.ApiMethod;
import com.cmsoft.core.annotation.ApiMode;
import com.cmsoft.core.annotation.ApiResponseType;
import com.cmsoft.core.annotation.ApiController;

@ApiController("/sample") // url : ~/sample/{method name}
public class SampleApiController {
    
    @Value("${test.value}")
    private String testValue;

    @ApiMethod(desc = "sample view", type = ApiResponseType.VIEW, mode = ApiMode.SELECT)
    // url : ~/sample/A003
    public String A003() {
        return "test";  // views/test.jsp
    }

    @ApiMethod(desc = "기본값은 type=view mode=select 이므로 view 의 설명만 입력해도됨.")
    // url : ~/sample/A004
    public String A004(@RequestParam(name = "test", defaultValue = "") String test) {
        return "test";  // views/test.jsp
    }

    @ApiMethod(desc = "rest sample", type = ApiResponseType.REST, mode = ApiMode.SELECT)
    // url : ~/sample/A001
    public Object A001() {
        List<Map<String, Object>> list = new ArrayList<>();
        
        Map<String, Object> map1 = new HashMap<>();
        map1.put("hello", "hello");
        list.add(map1);
        
        Map<String, Object> map2 = new HashMap<>();
        map2.put("profile", testValue);
        list.add(map2);
        
        return list; // json
    }

    @ApiMethod(desc = "json sample", type = ApiResponseType.JSON, mode = ApiMode.SELECT)
    // url : ~/sample/A002
    public Object A002() {
        List<Map<String, Object>> list = new ArrayList<>();
        
        Map<String, Object> map1 = new HashMap<>();
        map1.put("hello", "hello");
        list.add(map1);
        
        Map<String, Object> map2 = new HashMap<>();
        map2.put("profile", testValue);
        list.add(map2);
        
        return list; // json
    }
}
