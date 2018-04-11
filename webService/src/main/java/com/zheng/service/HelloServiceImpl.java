package com.zheng.service;

import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.Date;

/**
 * Created by zhenghui on 2018/4/11.
 */
@Component
@WebService(endpointInterface = "com.zheng.service.HelloService", serviceName = "HelloWorldWs")
//指定webservice所实现的接口以及服务名称
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return name + "，您好！现在时间是：" + new Date();
    }
}
