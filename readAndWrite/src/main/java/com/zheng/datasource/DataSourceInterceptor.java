package com.zheng.datasource;

import org.aspectj.lang.JoinPoint;

public class DataSourceInterceptor {
	public void setDataSourceR(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("dataSourceR");
	}
	public void setDataSourceW(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("dataSourceW");
	}
}