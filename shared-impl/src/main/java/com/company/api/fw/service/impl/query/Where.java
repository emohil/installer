package com.company.api.fw.service.impl.query;

import java.util.List;

/**
 * Where片段 
 * 
 * 
 * @see AbstractWhere
 * @see Wheres
 */
public interface Where {

    /**
     * 写入sql/参数buffer
     * 
     * @param sqlBuf
     * @param parsBuf
     * @return 如果对sql产生了效果，返回true，否则false 
     * @see LogicalOp#deal(List, StringBuilder, List)
     * @see CompareOp#deal(StringBuilder, List, String, Object)
     */
    boolean deal(StringBuilder sqlBuf, List<Object> parsBuf);

    Where and(Where... others);

    Where not();

    Where or(Where... others);
}
