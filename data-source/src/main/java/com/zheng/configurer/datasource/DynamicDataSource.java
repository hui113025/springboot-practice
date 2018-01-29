package com.zheng.configurer.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by zhenghui on 2018/1/29.
 */
public class DynamicDataSource extends AbstractRoutingDataSource  {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        logger.debug("数据源为{}", DataSourceContextHolder.getDataSource());
        return DataSourceContextHolder.getDataSource();
    }
}
