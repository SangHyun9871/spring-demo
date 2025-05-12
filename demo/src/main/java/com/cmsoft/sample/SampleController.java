package com.cmsoft.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SampleController {

    @Value("${test.value}")
    private String testValue;
    
    @RequestMapping("/hello")
    @ResponseBody
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

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
