package com.company.api.fw.service.impl.query;

/**
 * Shortcut functions for building where. Not necessary but easier to use.
 * 
 */
public class WhereBuilder {

    private Where inner = Wheres.EMPTY;

    public Where build() {
        return inner;
    }

    public WhereBuilder and(Where... wheres) {
        inner = inner.and(wheres);
        return this;
    }

    public WhereBuilder or(Where... wheres) {
        inner = inner.or(wheres);
        return this;
    }

    public WhereBuilder not() {
        inner = inner.not();
        return this;
    }

    public WhereBuilder andStartsWith(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.STARTSWITH, value));
    }

    public WhereBuilder andEndsWith(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.ENDSWITH, value));
    }

    public WhereBuilder andContains(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.CONTAINS, value));
    }

    public WhereBuilder andEq(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.EQ, value));
    }

    public WhereBuilder andNe(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.NE, value));
    }

    public WhereBuilder andLt(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.LT, value));
    }

    public WhereBuilder andLe(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.LE, value));
    }

    public WhereBuilder andGt(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.GT, value));
    }

    public WhereBuilder andGe(String field, Object value) {
        return and(Wheres.simple(field, CompareOps.GE, value));
    }

    public WhereBuilder andNull(String field) {
        return and(Wheres.simple(field, CompareOps.ISNULL, null));
    }

    public WhereBuilder andNotNull(String field) {
        return and(Wheres.simple(field, CompareOps.ISNOTNULL, null));
    }

    /**
     * and exists 查询
     * 
     * @param sql 子查询 select 语句块 例如:select * from a where 1=1
     * @param where 子查询中的where 语句块 格式为: Wheres.simple() 多个用unite() 连接
     * @return
     */
    public WhereBuilder andExists(String sql, Where where) {
        inner = inner.and(Wheres.exists(sql, where));
        return this;
    }

    /**
     * and not exists 查询
     * 
     * @param sql 子查询 select 语句块 例如:select * from a where 1=1
     * @param where 子查询中的where 语句块 格式为: Wheres.simple() 多个用unite() 连接
     * @return
     */
    public WhereBuilder andNotExists(String sql, Where where) {
        inner = inner.and(Wheres.not(Wheres.exists(sql, where)));
        return this;
    }

}
