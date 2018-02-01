package com.zheng.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.zheng.service.DemoService;

/**
 * Created by zhenghui on 2018/1/31.
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public Integer add(Integer a,Integer b){
        System.err.printf("方法add被调用 %s+%s", a, b);
        if(a==null||b==null){
            return 0;
        }
        return a+b;
    }
}
