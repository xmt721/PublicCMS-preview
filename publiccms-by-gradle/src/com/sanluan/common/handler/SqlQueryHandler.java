package com.sanluan.common.handler;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sanluan.common.base.Base;

public class SqlQueryHandler extends Base   {
	public static final String COUNT_SQL = "select count(*) ";
    public static final String KEYWORD_FROM = " from ";
    public static final String KEYWORD_ORDER = " order by ";
    public static final String KEYWORD_GROUP = " group by ";
    boolean whereFlag = true;
    boolean orderFlag = true;
    boolean groupFlag = true;
    private StringBuilder sqlBuilder;
    private Map<String, Object> map;
    private Map<String, Object[]> arrayMap;
    private Integer firstResult;
    private Integer maxResults;
    private Boolean cacheable;

    public SqlQueryHandler(String sql) {
        this.sqlBuilder = new StringBuilder(" ");
        sqlBuilder.append(sql);
    }

    public SqlQueryHandler() {
        this.sqlBuilder = new StringBuilder();
    }

    public SQLQuery getQuery(Session session) {
        return getQuery(session, getSql());
    }

    public SQLQuery getCountQuery(Session session) {
        return getQuery(session, getCountSql());
    }


    public SqlQueryHandler condition(String condition) {
        if (whereFlag) {
            whereFlag = false;
            sqlBuilder.append(" where ");
        } else {
            sqlBuilder.append(" and ");
        }
        sqlBuilder.append(condition);
        return this;
    }

    public SqlQueryHandler order(String sqlString) {
        if (orderFlag) {
            orderFlag = false;
            append(KEYWORD_ORDER);
        } else {
            sqlBuilder.append(',');
        }
        sqlBuilder.append(sqlString);
        return this;
    }

    public SqlQueryHandler group(String sqlString) {
        if (groupFlag) {
            groupFlag = false;
            sqlBuilder.append(KEYWORD_GROUP);
        } else {
            sqlBuilder.append(',');
        }
        sqlBuilder.append(sqlString);
        return this;
    }

    public SqlQueryHandler append(String sqlString) {
        sqlBuilder.append(" ");
        sqlBuilder.append(sqlString);
        return this;
    }

    public SqlQueryHandler setFirstResult(int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public SqlQueryHandler setMaxResults(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public SqlQueryHandler setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
        return this;
    }

    public SqlQueryHandler setParameter(String key, Object value) {
        if (empty(map)) {
            map = new HashMap<String, Object>();
        }
        map.put(key, value);
        return this;
    }

    public SqlQueryHandler setParameter(String key, Object[] value) {
        if (empty(arrayMap)) {
            arrayMap = new HashMap<String, Object[]>();
        }
        arrayMap.put(key, value);
        return this;
    }

    private SQLQuery getQuery(Session session, String sql) {
    	SQLQuery query = session.createSQLQuery(sql);
        if (notEmpty(map)) {
            for (String key : map.keySet()) {
                query.setParameter(key, map.get(key));
            }
        }
        if (notEmpty(arrayMap)) {
            for (String key : arrayMap.keySet()) {
                query.setParameterList(key, arrayMap.get(key));
            }
        }
        if (notEmpty(firstResult)) {
            query.setFirstResult(firstResult);
        }
        if (notEmpty(maxResults)) {
            query.setMaxResults(maxResults);
        }
        if (notEmpty(cacheable)) {
            query.setCacheable(cacheable);
        }
        return query;
    }
    

    private String getSql() {
        return sqlBuilder.toString();
    }

    private String getCountSql() {
        String sql = getSql();
        sql = sql.substring(sql.toLowerCase().indexOf(KEYWORD_FROM));
        int orderIndex = sql.toLowerCase().indexOf(KEYWORD_ORDER);
        if (-1 != orderIndex) {
            sql = sql.substring(0, orderIndex);
        }
        return COUNT_SQL + sql;
    }
}
