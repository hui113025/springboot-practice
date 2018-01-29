package com.zheng.configurer.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenghui on 2018/1/29.
 */
public class DataSourceContextHolder {
    public static final Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();

    /*
    *管理所有的数据源id;
    *主要是为了判断数据源是否存在;
    */
    public static List<String> dataSourceIds = new ArrayList<String>();

    // 设置数据源名
    public static void setDataSource(String dataSource) {
        logger.debug("切换到{}数据源", dataSource);
        contextHolder.set(dataSource);
    }

    // 获取数据源名
    public static String getDataSource() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDataSource() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author
     * @create 2016年1月24日
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

    static {
        dataSourceIds.add("dbMaster");
        dataSourceIds.add("dbSlave");
    }
}
