package com.zheng.mybatis.plugin;

import com.zheng.vo.Page;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor {
	private String dialect = "mysql";

	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		String originalSql = boundSql.getSql().trim();
		Object parameterObject = boundSql.getParameterObject();

		Page page = searchPageWithXpath(boundSql.getParameterObject(), ".", "page", "*/page");
		if (page != null) {
			String countSql = getCountSql(originalSql, dialect);
			Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			PreparedStatement countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
			parameterHandler.setParameters(countStmt);
			ResultSet rs = countStmt.executeQuery();
			String totalCount = "0";
			if (rs.next()) {
				totalCount = rs.getString(1);
			}
			rs.close();
			countStmt.close();
			connection.close();
			page.setTotalCount(totalCount);

			String page_sql = getPageSql(originalSql, dialect, page.startRecord, Integer.parseInt(page.getPageSize()));
			BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, page_sql);
			MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	
	private Page searchPageWithXpath(Object o, String... xpaths) {
		JXPathContext context = JXPathContext.newContext(o);
		Object result;
		for (String xpath : xpaths) {
			try {
				result = context.selectSingleNode(xpath);
			} catch (JXPathNotFoundException e) {
				continue;
			}
			if (result instanceof Page) {
				return (Page) result;
			}
		}
		return null;
	}

	@Override
	public void setProperties(Properties properties) {
		Properties p = properties;
		if (p.getProperty("dialect") != null && !p.getProperty("dialect").trim().equals("")) {
			dialect = p.getProperty("dialect");
		}
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		// builder.keyProperty(ms.getKeyProperty());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	
	private String getCountSql(String sql, String dialect) {
		StringBuffer pageSql = new StringBuffer();
	
		if (dialect.equalsIgnoreCase("mysql")) {
			pageSql.append("SELECT COUNT(*) FROM (");
			pageSql.append(sql);
			pageSql.append(") aliasForPage");
		}else if(dialect.equalsIgnoreCase("postgresql")){
			pageSql.append("SELECT COUNT(*) FROM (");
			pageSql.append(sql);
			pageSql.append(") aliasForPage");
		}
		return pageSql.toString();
	}

	private String getPageSql(String sql, String dialect, int startRecord, int pageSize) {
		StringBuffer pageSql = new StringBuffer();
		
		if (dialect.equalsIgnoreCase("mysql")) {
			pageSql.append("select * from (");
			pageSql.append(sql);
			pageSql.append(" ) a limit");
			pageSql.append((startRecord - 1));
			pageSql.append(",");
			pageSql.append(pageSize);
			//ret = " select * from ( " + sql + " ) a limit " + (startRecord - 1) + ", " + pageSize;
		}else if(dialect.equalsIgnoreCase("postgresql")){
			pageSql.append("select * from (");
			pageSql.append(sql);
			pageSql.append(" ) a limit");
			pageSql.append(pageSize);
			pageSql.append(" offset ");
			pageSql.append((startRecord - 1));
			//ret = " select * from ( " + sql + " ) a limit " + pageSize + " offset " + (startRecord - 1);
		}
		return pageSql.toString();
	}
}
