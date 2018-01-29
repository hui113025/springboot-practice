package com.zheng.configurer.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by zhenghui on 2018/1/29.
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
    public static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /*
    * @Before：在方法执行之前进行执行：
    * @annotation(targetDataSource)：拦截注解targetDataSource的方法
    */
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
        //获取当前的指定的数据源;
        String dsId = targetDataSource.value();
        //如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源。
        if (!DataSourceContextHolder.containsDataSource(dsId)) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}" + targetDataSource.value() + point.getSignature());
        } else {
            logger.info("UseDataSource : {} > {}" + targetDataSource.value() + point.getSignature());
            //设置到动态数据源上下文中。
            DataSourceContextHolder.setDataSource(targetDataSource.value());
        }
    }

    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        logger.info("RevertDataSource : {} > {}" + targetDataSource.value() + point.getSignature());
        //方法执行完毕之后，销毁当前数据源信息，进行垃圾回收。
        DataSourceContextHolder.clearDataSource();
    }
}
