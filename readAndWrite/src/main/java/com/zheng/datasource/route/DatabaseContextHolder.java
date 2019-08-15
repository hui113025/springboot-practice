package com.zheng.datasource.route;

public class DatabaseContextHolder {
	
	private static final ThreadLocal<String> contextHolderTL = new ThreadLocal<String>();
	
	/**
	 * 
	 * @param datasourceType
	 * 	READ,WRITE
	 */
	public static void setDataSourceType(String datasourceType) {
		contextHolderTL.set(datasourceType);
	}

	public static String getDataSourceType() {
		return contextHolderTL.get();
	}

	public static void removeDataSourceType() {
		contextHolderTL.remove();
	}
}