package com.zheng.service.impl;

import com.zheng.configurer.datasource.TargetDataSource;
import com.zheng.dao.UserMapper;
import com.zheng.model.User;
import com.zheng.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zhenghui on 2018/1/29.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    public UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    @TargetDataSource("dbSlave")
    public User findById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
