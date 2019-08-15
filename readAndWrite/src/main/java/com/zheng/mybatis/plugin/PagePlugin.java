package com.zheng.mybatis.plugin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.PropertyException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class }) })
public class PagePlugin implements Interceptor {
	private static final Logger log = LoggerFactory.getLogger(PagePlugin.class);
    private static String dialect = "";
    private static String pageSqlId = "";

    private static boolean sql_inj(String str)
    {
	    String inj_str = "exec|insert|drop|chr|mid|master|truncate|char|declare|;|-|+";
	    String[] inj_stra=inj_str.split("\\|");
	    for (int i=0 ; i < inj_stra.length ; i++ )
	    {
		    if (str.indexOf(inj_stra[i])>=0)
		    {
		    	return true;
		    }
	    }
	    return false;
    }
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation ivk) throws Throwable {

        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
                    .getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
                    .getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper
                    .getValueByFieldName(delegate, "mappedStatement");

            if (mappedStatement.getId().matches(pageSqlId)) {
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject error");
                } else {
                    Connection connection = (Connection) ivk.getArgs()[0];
                    String sql = boundSql.getSql();
                   
                   /* if(sql_inj(sql)){
                    	log.error("injection sql: "+sql);
                    	throw new IllegalArgumentException("contains illegal symbol: "+sql);
                    }*/
                    StringBuilder sqlBuild = new StringBuilder("select count(0) from ( ");
                    sqlBuild.append(sql).append(" ) myCount");
                    String countSql = sqlBuild.toString();
                    log.info("总数sql 语句:"+countSql);
                    PreparedStatement countStmt = connection
                            .prepareStatement(countSql);
                    BoundSql countBS = new BoundSql(
                            mappedStatement.getConfiguration(), countSql,
                            boundSql.getParameterMappings(), parameterObject);
                    setParameters(countStmt, mappedStatement, countBS,
                            parameterObject);
                    ResultSet rs = countStmt.executeQuery();
                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    rs.close();
                    countStmt.close();

                    PageInfo page = null;
                    if (parameterObject instanceof PageInfo) {
                        page = (PageInfo) parameterObject;
                        page.setTotalResult(count);
                    } else if(parameterObject instanceof Map){
                        Map<String, Object> map = (Map<String, Object>)parameterObject;
                        page = new PageInfo();
                        BeanUtils.populate(page, (Map)map.get("page"));
                        if(page == null)
                            page = new PageInfo();
                        page.setTotalResult(count);
                        map.put("page", page);
                    }else {
                        Field pageField = ReflectHelper.getFieldByFieldName(
                                parameterObject, "page");
                        if (pageField != null) {
                            page = (PageInfo) ReflectHelper.getValueByFieldName(
                                    parameterObject, "page");
                            if (page == null)
                                page = new PageInfo();
                            page.setTotalResult(count);
                            ReflectHelper.setValueByFieldName(parameterObject,
                                    "page", page);
                        } else {
                            throw new NoSuchFieldException(parameterObject
                                    .getClass().getName());
                        }
                    }
                    String pageSql = generatePageSql(sql, page);
                    log.info("分页   sql:"+pageSql);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
                }
            }
        }
        return ivk.proceed();
    }

    private void setParameters(PreparedStatement ps,
            MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters")
                .object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql
                .getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null
                    : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry
                            .hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName
                            .startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(
                                            propertyName.substring(prop
                                                    .getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject
                                .getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException(
                                "There was no TypeHandler found for parameter "
                                        + propertyName + " of statement "
                                        + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value,
                            parameterMapping.getJdbcType());
                }
            }
        }
    }


    private String generatePageSql(String sql, PageInfo page) {
        if (page != null && (dialect !=null || !dialect.equals(""))) {
        	String sqlt = sql.toLowerCase();
            sqlt = sqlt.replaceAll("  ", "");
            String orderBy = null;
            if(sqlt.lastIndexOf("order by") > 0){
            	orderBy = sqlt.substring(sqlt.lastIndexOf("order by"));
            }
            StringBuffer pageSql = new StringBuffer();
            if ("mysql".equals(dialect)) {
                pageSql.append(sql);
                pageSql.append(" limit " + page.getCurrentResult() + ","
                        + page.getShowCount());
            } else if ("oracle".equals(dialect)) {
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
                pageSql.append(sql);
                pageSql.append(")  tmp_tb where ROWNUM<=");
                pageSql.append(page.getCurrentResult() + page.getShowCount());
                pageSql.append(") where row_id>");
                pageSql.append(page.getCurrentResult());
            }else if ("mysql".equals(dialect)) {
                pageSql.append(" SELECT T.* FROM(  ");
                pageSql.append(" SELECT row_number() OVER (ORDER BY ").append(page.getSortField())
                .append(" ").append(page.getOrder());
                pageSql.append(" ) as rownumber,so.*  from (");
                pageSql.append(sql.replaceAll(";", ""));
                pageSql.append(") AS so ");
                pageSql.append(" ) AS T  ");
                pageSql.append(" WHERE	 T.rownumber <=").append(page.getCurrentResult() + page.getShowCount());
                pageSql.append("  AND T.rownumber >=").append(page.getCurrentResult()+1);
                if(null != orderBy && !"".equals(orderBy)){
                	pageSql.append(" order by ");
                	orderBy = orderBy.replace("order by", "");
                	String[] orderBys = orderBy.split(",");
                	for(int i = 0;i<orderBys.length;i++){
                		String ob =orderBys[i];
                		if(ob.indexOf(".") > 0){
                			ob = ob.substring(ob.indexOf(".")+1);
                		}
                		pageSql.append(ob);
                		if(i < orderBys.length - 1)
                			pageSql.append(",");
                	}
                }
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (dialect ==null || dialect.equals("")) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (dialect ==null || dialect.equals("")) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }

}