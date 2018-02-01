package com.zheng.web;

import com.zheng.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhenghui on 2018/2/1.
 */
@RestController
@RequestMapping("/dubbo")
public class DubboController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DemoService demoService;

    @RequestMapping("/add")
    public Integer print(Integer a, Integer b) {
        Integer result = demoService.add(a,b);
        return result;
    }
}
