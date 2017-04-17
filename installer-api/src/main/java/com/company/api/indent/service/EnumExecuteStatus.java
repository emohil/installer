package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.INDENT_EXECUTE_STATUS)
public enum EnumExecuteStatus implements DictEntry {
    
    //订单执行状态
    CENTRE("执行中"),
    
    AFTER("执行完毕"),
    
    BEFORE("未执行")
    
    ;

    private String text;

    private EnumExecuteStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
