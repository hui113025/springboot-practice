package com.zheng.configurer.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by chenqi on 2017/10/16.
 */
@ConfigurationProperties(prefix = "spring.redis")
@Component
public class CommonRedis extends RedisConfigBean {
}
