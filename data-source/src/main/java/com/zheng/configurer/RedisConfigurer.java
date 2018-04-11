package com.zheng.configurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by admin on 2017/3/11.
 * 两种设置redis的方式
 */
//@Configuration
//@EnableCaching
public class RedisConfigurer extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    @Value("${spring.redis.maxIdle}")
    private Integer maxIdle;
    @Value("${spring.redis.maxTotal}")
    private Integer maxTotal;
    @Value("${spring.redis.maxWaitMillis}")
    private Integer maxWaitMillis;
    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.session.redis.database}")
    private Integer sessionbase;

    @Bean
    public CacheManager cacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate<?, ?> redisTemplate) {
        // 创建缓存管理器
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setCachePrefix(new DefaultRedisCachePrefix(":"));
        // 设置缓存过期时间（秒）
        cacheManager.setDefaultExpiration(3600 * 24);
        return cacheManager;
    }
    @Primary
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        return getRedisTemplate(jedisConnectionFactory());
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return connectionFactory(database);
    }

    @Primary
    @Bean(name = "sessionJedisConnectionFactory")
    public JedisConnectionFactory sessionJedisConnectionFactory() {
        return connectionFactory(sessionbase);
    }

    private RedisTemplate getRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    private JedisConnectionFactory connectionFactory(Integer database) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(host);
        if (!password.isEmpty()) {
            jedisConnectionFactory.setPassword(password);
        }
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(database);
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

}
