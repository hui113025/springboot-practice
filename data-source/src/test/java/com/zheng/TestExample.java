package com.zheng;

import com.zheng.model.User;
import com.zheng.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by zhenghui on 2018/1/29.
 */
public class TestExample extends Tester{
    @Resource
    private UserService userService;

    @Test
    public void testUser(){

    }

}
