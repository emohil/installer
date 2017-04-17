package com.company.api.fw.service.impl.query;

import java.util.List;

/**
 * 查询条件载体。在我们内部，一般一次查询都包括查询sql和countsql 
 * 
 */
public class QueryInfo {
    private final String sql, countSql;
    private final List<Object> pars;

    public String getCountSql() {
        return countSql;
    }

    public QueryInfo(String sql, String countSql, List<Object> pars) {
        super();
        this.sql = sql;
        this.countSql = countSql;
        this.pars = pars;
    }

    //    public List<Object> getPars() {
    //        return pars;
    //    }

    public String getSql() {
        return sql;
    }

    public Object[] getParArr() {
        return pars.toArray();
    }
}