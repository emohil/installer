package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.PAY_STATUS)
public enum EnumPayStatus implements DictEntry {
    
    //订单执行状态
    NONE("未付款"),
    
    PAID("已付款"),
    
    ;

    private String text;

    private EnumPayStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
