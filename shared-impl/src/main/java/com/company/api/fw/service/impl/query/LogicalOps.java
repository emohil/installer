package com.company.api.fw.service.impl.query;

import java.util.List;

/**
 * 逻辑运算符 
 * 
 */
public enum LogicalOps implements LogicalOp {

    AND {

        @Override
        public boolean deal(List<Where> wheres, StringBuilder sqlBuf, List<Object> parsBuf) {
            return dealMultiples0(" and ", wheres, sqlBuf, parsBuf);
        }
    },

    OR {

        @Override
        public boolean deal(List<Where> wheres, StringBuilder sqlBuf, List<Object> parsBuf) {
            return dealMultiples0(" or ", wheres, sqlBuf, parsBuf);
        }
    },

    NOT {

        @Override
        public boolean deal(List<Where> wheres, StringBuilder sql, List<Object> pars) {
            final String TAG_START = "not(", TAG_END = ")";

            if (wheres == null || wheres.isEmpty())
                return false;

            sql.append(TAG_START);

            Where where = wheres.get(0);
            if (where.deal(sql, pars)) {
                sql.append(TAG_END);
                return true;
            } else {
                sql.setLength(sql.length() - TAG_START.length());
                return false;
            }
        }
    };

    /**
     * 处理多个。
     * 
     * @param op 运算符
     * @param wheres 多个where片段
     * @param sqlBuf sql buffer
     * @param parsBuf pars buffer
     * @return
     */
    final boolean dealMultiples0(String op, List<Where> wheres, StringBuilder sqlBuf, List<Object> parsBuf) {
        final String TAG_START = "(", TAG_END = ")";

        int c = 0;
        StringBuilder sql2 = new StringBuilder();
        for (Where where : wheres) {
            if (where.deal(sql2, parsBuf)) {
                c++;
                sql2.append(op);
            }
        }

        if (c == 0) {
            return false;
        } else {
            // trim the ending LogicalOperator
            sql2.setLength(sql2.length() - op.length());

            // only wrap with () when more than one conditions are there.
            if (c > 1) {
                sqlBuf.append(TAG_START).append(sql2).append(TAG_END);
            } else {
                sqlBuf.append(sql2);
            }

            return true;
        }
    }
}
