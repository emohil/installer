package com.company.api.fw.service.impl.query;

import java.util.List;

/**
 * 逻辑关系运算符 
 * 
 */
public interface LogicalOp {

    /**
     * 使用当前逻辑运算符，将一组Where条件加入到sql/参数 buffer中。
     * 
     * @param wheres 
     * @param sqlBuf SQL buffer
     * @param parsBuf 参数数组buffer
     * @return
     */
    boolean deal(List<Where> wheres, StringBuilder sqlBuf, List<Object> parsBuf);

}
