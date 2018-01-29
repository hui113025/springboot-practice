package com.zheng.configurer;

import com.zheng.configurer.datasource.DynamicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenghui on 2018/1/26.
 */
@Configuration
public class DataSourceConfigurer {
    @Bean
    @ConfigurationProperties(prefix = "master.spring.datasource")
    public DataSource dbMaster() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "slave.spring.datasource")
    public DataSource dbSlave() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dbMaster());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put("dbMaster", dbMaster());
        dsMap.put("dbSlave", dbSlave());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
}
