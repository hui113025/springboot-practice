package com.zheng.service;

import javax.jws.WebService;

/**
 * 描述: 定义服务接口
 *
 * @author yanpenglei
 * @create 2017-10-27 13:20
 **/
@WebService
public interface HelloService {
    String sayHi(String name);
}

