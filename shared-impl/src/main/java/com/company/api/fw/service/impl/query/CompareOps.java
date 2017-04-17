package com.company.api.fw.service.impl.query;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 比较运算符
 * 
 */
public enum CompareOps implements CompareOp {

    /**
     * Like %string% 
     */
    CONTAINS("like") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            if (nullEmpty(par))
                return false;
            else
                return super.deal(sqlBuf, parsBuf, field, "%" + par + "%");
        }
    },

    /**
     * like %string
     */
    ENDSWITH("like") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            if (nullEmpty(par))
                return false;
            else
                return super.deal(sqlBuf, parsBuf, field, "%" + par);
        }
    },

    /**
     * like string%
     */
    STARTSWITH("like") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            if (nullEmpty(par))
                return false;
            else
                return super.deal(sqlBuf, parsBuf, field, par + "%");
        }
    },

    /**
     * =
     */
    EQ("="),

    /**
     * >=
     */
    GE(">="),

    /**
     * >
     */
    GT(">"),

    /**
     * in (v1, v2, v3...).
     *  Use an iterable/iterator/Array Object as par.
     */
    IN("in") {
        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            if (par == null)
                return false;

            final Iterator<Object> itr;
            if (par instanceof Iterator) {
                @SuppressWarnings("unchecked")
                Iterator<Object> par2 = (Iterator<Object>) par;
                itr = par2;
            } else if (par instanceof Iterable) {
                @SuppressWarnings("unchecked")
                Iterable<Object> iterable = (Iterable<Object>) par;
                itr = iterable.iterator();
            } else if (par.getClass().isArray()) {
                Object[] arr;
                if (par instanceof Object[]) {
                    arr = (Object[]) par;
                } else {
                    arr = new Object[Array.getLength(par)];
                    for (int i = arr.length - 1; i >= 0; i--) {
                        arr[i] = Array.get(par, i);
                    }
                }

                itr = Arrays.asList(arr).iterator();
            } else
                throw new IllegalArgumentException("You must specify an iterable/iterator/Array object for IN operator.");

            if (!itr.hasNext())
                // no values passed in, nothing to do.
                return false;
            else {
                final int IN_LIMIT = 1000;
                final int pos = sqlBuf.length();

                int c = 0;
                while (itr.hasNext()) {
                    c++;
                    parsBuf.add(itr.next());

                    final int mode = c % IN_LIMIT;
                    // Ending of IN
                    if (mode == 0)
                        sqlBuf.append(", ?").append(")");
                    // Beginning of IN
                    else if (mode == 1) {
                        // More than one IN, join them with OR 
                        if (c > IN_LIMIT)
                            sqlBuf.append(" or ");

                        sqlBuf.append(field).append(" in (?");
                    } else {
                        sqlBuf.append(", ?");
                    }
                }

                // yes, we have a leading "(" there without a ")" for closing it.
                if (c % IN_LIMIT != 0)
                    sqlBuf.append(")");

                // more than one OR pieces are there, wrap it with ()
                if (c > IN_LIMIT) {
                    sqlBuf.insert(pos, "(").append(")");
                }

                return true;
            }
        }
    },

    /**
     * field = ''
     */
    ISEMPTY("is empty") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            sqlBuf.append(field).append(" = ''");
            return true;
        }
    },

    ISNOTEMPTY("is not empty") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            sqlBuf.append(field).append(" <> ''");
            return true;
        }
    },

    /**
     * is not null
     */
    ISNOTNULL("isnotnull") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            sqlBuf.append(field).append(" IS NOT NULL");
            return true;
        }
    },
    /**
     * isnull()
     */
    ISNULL("isnull") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            sqlBuf.append(field).append(" IS NULL");
            return true;
        }
    },

    /**
     * <=
     */
    LE("<="),

    /**
     * <
     */
    LT("<"),

    /**
     * <>
     */
    NE("<>"),

    /**
     * not in (v1, v2, v3...)
     */
    NOTIN("notin") {

        @Override
        public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
            return LogicalOps.NOT.deal(Arrays.asList(Wheres.simple(field, IN, par)), sqlBuf, parsBuf);
        }
    };

    /**
     * 判断一个参数是否为null/empty
     * 
     * @param par
     * @return
     */
    private static boolean nullEmpty(Object par) {
        if (par == null)
            return true;

        if (par instanceof String) {
            String spar = (String) par;
            if (spar.isEmpty())
                return true;
        }

        return false;
    }

    private String op;

    private CompareOps(String op) {
        this.op = op;
    }

    public boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par) {
        if (nullEmpty(par)) {
            return false;
        } else {
            sqlBuf.append(field).append(" ").append(op).append(" ? ");
            parsBuf.add(par);
            return true;
        }
    }

}
