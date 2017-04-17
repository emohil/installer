package com.company.api.fw.service.impl.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Utility tool(static functions) for building where clauses.
 */
public abstract class Wheres {

    /**
     * 多个where的联合体 
     * 
     */
    private static class CompositeWhere extends AbstractWhere {
        private LogicalOp logical;
        private List<Where> members;

        public CompositeWhere(LogicalOp logical, Where... members) {
            if (members == null || members.length == 0)
                throw new IllegalArgumentException("Parameter 'members' cannot be null/empty.");

            this.logical = logical;
            this.members = Arrays.asList(members);
        }

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf) {
            return logical.deal(members, sqlBuf, parsBuf);
        }

    }

    /**
     * select * from t1 where
     * exists (select * from t2 where t2.f2 = t1.f1 and t2..... )
     * 
     * @author chinafzy@gmail.com
     *
     */
    private static class ExistsWhere extends AbstractWhere {
        private String innerSql;
        private Where innerWhere;

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf) {

            StringBuilder sql2 = new StringBuilder(innerSql);
            List<Object> pars2 = new ArrayList<Object>();

            boolean ret = innerWhere.deal(sql2, pars2);
            if (ret) {

                sqlBuf.append(" exists(").append(sql2).append(")");
                parsBuf.addAll(pars2);
                return true;
            } else
                return false;
        }
    }

    /**
     * 单个where 
     * 
     * @author chinafzy@gmail.com
     *
     */
    private static class SimpleWhere extends AbstractWhere {
        private CompareOp compare;
        private String field;
        private Object par;

        public SimpleWhere(String field, CompareOp compare, Object par) {
            super();
            this.field = field;
            this.compare = compare;
            if (par instanceof Enum<?>) {
                par = ((Enum<?>) par).name();
            }
            this.par = par;
        }

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf) {
            return compare.deal(sqlBuf, parsBuf, field, par);
        }

    }

    private static class SqlWhere extends AbstractWhere {

        private String sql;
        private Collection<?> pars;

        public SqlWhere(String sql, Collection<?> pars) {
            super();
            this.sql = sql;
            this.pars = pars;
        }

        public SqlWhere(String sql, Object... pars) {
            this(sql, Arrays.asList(pars));
        }

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf) {
            sqlBuf.append(sql);
            parsBuf.addAll(pars);
            return true;
        }

    }

    public static Where exists(String sql, Where where) {
        ExistsWhere ret = new ExistsWhere();
        ret.innerSql = sql;
        ret.innerWhere = where;

        return ret;
    }

    /**
     * 创建一个简单where
     * 
     * @param field
     * @param compare
     * @param par
     * @return
     */
    public static Where simple(String field, CompareOp compare, Object par) {
        return new SimpleWhere(field, compare, par);
    }

    public static Where sql(String sql, List<Object> pars) {
        return new SqlWhere(sql, pars);
    }

    public static Where sql(String sql, Object... pars) {
        return new SqlWhere(sql, pars);
    }

    public static Where not(Where where) {
        return new CompositeWhere(LogicalOps.NOT, where);
    }

    /**
     * 与另外的where联合，构建一个新的where 
     * 
     * @param logical
     * @param another
     * @return
     */
    public static Where unite(LogicalOp logical, Where... wheres) {
        return new CompositeWhere(logical, wheres);
    }

    /**
     * 一个默认的空where
     */
    public static final Where EMPTY = new AbstractWhere() {

        @Override
        public boolean deal(StringBuilder sql, List<Object> pars) {
            return false;
        }
    };

}
