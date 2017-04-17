package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.INDENT_VISIT_SOURCE)
public enum EnumIndentVisitSource implements DictEntry {
    
    //订单上门来源
    FIRST("首次上门"),
    
    SECOND("二次上门")
    
    ;

    private String text;

    private EnumIndentVisitSource(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
