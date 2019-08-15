package com.zheng.mybatis.plugin;

import com.zheng.datasource.route.ReadWriteRoutingDataSource;
import com.zheng.datasource.route.DatabaseContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts({ 
    @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
    @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) 
})
public class DataSourceTypePlugin implements Interceptor {
	
	private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    public Object intercept(Invocation invocation) throws Throwable {
    	Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        String dataSourceType = null;
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase().replaceAll("[\\t\\n\\r]", " ");
        if (SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
        	//!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
            if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                dataSourceType = ReadWriteRoutingDataSource.WRITE;
            } else {
                if(sql.matches(REGEX)) {
                    dataSourceType = ReadWriteRoutingDataSource.WRITE;
                } else {
                    dataSourceType = ReadWriteRoutingDataSource.READ;
                }
            }
        }else{
        	dataSourceType = ReadWriteRoutingDataSource.WRITE;
        }
        DatabaseContextHolder.setDataSourceType(dataSourceType);
        return invocation.proceed();
    }


    public Object plugin(Object target) {
    	if(target instanceof Executor)
    		return Plugin.wrap(target, this);
    	else return target;
    }

    public void setProperties(Properties p) {
    }
}