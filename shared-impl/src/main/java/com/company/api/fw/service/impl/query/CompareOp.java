package com.company.api.fw.service.impl.query;

import java.util.List;

/**
 * 比较运算符 
 * 
 * @see CompareOps
 */
public interface CompareOp {

    /**
     * 写入sql/参数buffer.
     * 
     * @param sqlBuf sql buffer
     * @param parsBuf 参数buffer
     * @param field field名称 
     * @param par 比较的值 
     * @return 如果对buffer产生了效果，返回true，否则false
     */
    boolean deal(StringBuilder sqlBuf, List<Object> parsBuf, String field, Object par);

}
