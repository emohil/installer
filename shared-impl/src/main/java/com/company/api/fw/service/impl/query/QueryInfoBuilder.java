package com.company.api.fw.service.impl.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.company.dto.Order;

/**
 * 
 * @see WhereBuilder
 */
public class QueryInfoBuilder {

    /**
     * 创建一个builder
     * 
     * @param sql 原始sql
     * @return 返回builder
     */
    public static QueryInfoBuilder ins(String sql) {
        QueryInfoBuilder ret = new QueryInfoBuilder();
        ret.sql = sql;
        return ret;
    }

    private static String countSql(String sql) {
        final String tag = "FROM ";

        int pos = sql.toUpperCase().indexOf(tag);
        if (pos < 0)
            throw new IllegalArgumentException("Sql must does not match [select ... from ...] or [from ...]: " + sql);

        return "select count(*) " + sql.substring(pos);
    }

    private static String appendGroup(String sql, List<String> groups) {
        if (groups == null || groups.isEmpty())
            return sql;

        StringBuilder ret = new StringBuilder(sql);
        for (int i = 0, imax = groups.size(); i < imax; i++) {
            String field = groups.get(i);
            if (i > 0) {
                ret.append(", ");
            } else {
                ret.append(" group by ");
            }
            ret.append(field);
        }
        ret.append(" ");

        return ret.toString();
    }

    private static String appendCountGroup(String sql, List<String> groups) {
        if (groups == null || groups.isEmpty())
            return sql;

        StringBuilder sbf = new StringBuilder(appendGroup(sql, groups));
        sbf.insert(0, " from (").append(") ");
        sbf.append(" alias").append(System.currentTimeMillis()).append(" ");

        return sbf.toString();
    }

    private WhereBuilder whereBuilder = new WhereBuilder();
    private List<Order> orders = new ArrayList<Order>();
    private List<String> groups = new ArrayList<String>();
    private String sql;
    private List<Object> sqlPars;

    //------------------------ CompareOp.AND  begin -----------------------
    public QueryInfoBuilder and(String field, CompareOp compare, Object par) {
        return and(Wheres.simple(field, compare, par));
    }

    public QueryInfoBuilder and(Where... wheres) {
        whereBuilder.and(wheres);
        return this;
    }

    public QueryInfoBuilder andEq(String field, Object par) {
        return and(field, CompareOps.EQ, par);
    }

    public QueryInfoBuilder andGe(String field, Object par) {
        return and(field, CompareOps.GE, par);
    }

    public QueryInfoBuilder andGt(String field, Object par) {
        return and(field, CompareOps.GT, par);
    }

    public QueryInfoBuilder andLe(String field, Object par) {
        return and(field, CompareOps.LE, par);
    }

    public QueryInfoBuilder andLt(String field, Object par) {
        return and(field, CompareOps.LT, par);
    }

    public QueryInfoBuilder andNe(String field, Object par) {
        return and(field, CompareOps.NE, par);
    }

    /**
     * SQL/HQL: and in (?,?,?)
     * 
     * @param field
     * @param par
     *            interable/iterator/Array/String
     * @return
     */
    public QueryInfoBuilder andIn(String field, Object par) {
        return and(field, CompareOps.IN, par);
    }

    public QueryInfoBuilder andIsEmpty(String field) {
        return and(field, CompareOps.ISEMPTY, null);
    }

    public QueryInfoBuilder andIsNotNull(String field) {
        return and(field, CompareOps.ISNOTNULL, null);
    }

    public QueryInfoBuilder andIsNull(String field) {
        return and(field, CompareOps.ISNULL, null);
    }

    /**
     * SQL/HQL: and like '%string%'
     * 
     * @param field
     * @param par
     * @return
     */
    public QueryInfoBuilder andContains(String field, Object par) {
        return and(field, CompareOps.CONTAINS, par);
    }

    /**
     * SQL/HQL: and like '%string'
     * 
     * @param field
     * @param par
     * @return
     */
    public QueryInfoBuilder andEndsWith(String field, Object par) {
        return and(field, CompareOps.ENDSWITH, par);
    }

    /**
     * SQL/HQL: and like 'string%'
     * 
     * @param field
     * @param par
     * @return
     */
    public QueryInfoBuilder andStartsWith(String field, Object par) {
        return and(field, CompareOps.STARTSWITH, par);
    }

    /**
     * SQL/HQL: and not in (?,?,?)
     * 
     * @param field
     * @param par
     *            interable/iterator/Array/String
     * @return
     */
    public QueryInfoBuilder andNotIn(String field, Object par) {
        return and(field, CompareOps.NOTIN, par);
    }

    /**
     * and exists 查询
     * 
     * @param sql 子查询 select 语句块 例如:select * from a where 1=1
     * @param where 子查询中的where 语句块 格式为: Wheres.simple() 多个用unite() 连接
     * @return
     */
    public QueryInfoBuilder andExists(String sql, Where where) {
        whereBuilder.andExists(sql, where);
        return this;
    }

    /**
     * and not exists 查询
     * 
     * @param sql 子查询 select 语句块 例如:select * from a where 1=1
     * @param where 子查询中的where 语句块 格式为: Wheres.simple() 多个用unite() 连接
     * @return
     */
    public QueryInfoBuilder andNotExists(String sql, Where where) {
        whereBuilder.andNotExists(sql, where);
        return this;
    }

    //------------------------ CompareOp.AND  end -----------------------

    //------------------------ CompareOp.OR  begin -----------------------
    public QueryInfoBuilder or(String field, CompareOp compare, Object par) {
        return or(Wheres.simple(field, compare, par));
    }

    public QueryInfoBuilder or(Where... wheres) {
        whereBuilder.or(wheres);
        return this;
    }

    public QueryInfoBuilder orEq(String field, Object par) {
        return or(field, CompareOps.EQ, par);
    }

    public QueryInfoBuilder orGe(String field, Object par) {
        return or(field, CompareOps.GE, par);
    }

    public QueryInfoBuilder orGt(String field, Object par) {
        return or(field, CompareOps.GT, par);
    }

    public QueryInfoBuilder orLe(String field, Object par) {
        return or(field, CompareOps.LE, par);
    }

    public QueryInfoBuilder orLt(String field, Object par) {
        return or(field, CompareOps.LT, par);
    }

    public QueryInfoBuilder orNe(String field, Object par) {
        return or(field, CompareOps.NE, par);
    }

    //------------------------ CompareOp.OR  end -----------------------

    public QueryInfoBuilder sqlPars(List<Object> sqlPars) {
        this.sqlPars = sqlPars;
        return this;
    }
    
    public QueryInfo build() {
        Where where = whereBuilder.build();
        StringBuilder sqlBuf = new StringBuilder();
        List<Object> parsBuf = new ArrayList<Object>();
        if (sqlPars != null) {
            parsBuf.addAll(sqlPars);
        }
        boolean hasWhere = where.deal(sqlBuf, parsBuf);

        String fullSql = hasWhere ? (sql + " and " + sqlBuf.toString()) : sql;
        String countSql = countSql(appendCountGroup(fullSql, groups));

        fullSql = Order.appendOrder(appendGroup(fullSql, groups), orders);
        return new QueryInfo(fullSql, countSql, parsBuf);
    }

    /**
     * 增加排序 
     * 
     * @param orders
     * @return
     */
    public QueryInfoBuilder order(Collection<Order> orders) {
        if (orders == null) {
            orders = new ArrayList<Order>();
        }
        this.orders.addAll(orders);
        return this;
    }

    /**
     * 增加一个ASC排序 
     * 
     * @param field
     * @return
     */
    public QueryInfoBuilder orderAsc(String field) {
        orders.add(Order.asc(field));
        return this;
    }

    /**
     * 增加一个DESC排序 
     * 
     * @param field
     * @return
     */
    public QueryInfoBuilder orderDesc(String field) {
        orders.add(Order.desc(field));
        return this;
    }

    /**
     * 增加一个group分组
     * 
     * @param field
     * @return
     */
    public QueryInfoBuilder group(String field) {
        groups.add(field);
        return this;
    }

}
