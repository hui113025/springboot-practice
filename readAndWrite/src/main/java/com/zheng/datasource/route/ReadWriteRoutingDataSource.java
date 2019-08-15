package com.zheng.datasource.route;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {
	/**
	 * SHOULD BE SAME AS THE CONFIG FOR dataSource BEAN
	 */
	private Object dataSourceWrite;

    private Object dataSourceRead;
    
    public static final String WRITE = "WRITE";
    
    public static final String READ = "READ";
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceType = DatabaseContextHolder.getDataSourceType();
		if(null == dataSourceType || WRITE.equals(dataSourceType)){//
			return dataSourceWrite;
		}
		return dataSourceRead;
	}
	/**
	@Override
	public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("WRITE", writeDataSource);
        if(readDataSource != null) {
            targetDataSources.put("READ", readDataSource);
        }
        //调用父类的方法把数据源注入
        setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}
	 */
}
