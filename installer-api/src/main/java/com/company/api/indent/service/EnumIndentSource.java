package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.INDENT_SOURCE)
public enum EnumIndentSource implements DictEntry {
    
    //订单来源
    NORMAL("正单"),
    
    AFTERSALE("售后单"),
    
    FREE("免费单")
    
    ;

    private String text;

    private EnumIndentSource(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
