package com.zheng.datasource;

import java.util.HashMap;
import java.util.Map;

public class DatabaseContextHolder {
//	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	private static Map<String,String> contextHolder=new HashMap<String,String>();
	public static void setCustomerType(String customerType) {
		contextHolder.put("datasource",customerType);
	}
	public static String getCustomerType() {
		return contextHolder.get("datasource");
	}
	public static void clearCustomerType() {
		contextHolder.clear();
	}
}