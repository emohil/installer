package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.EVALUATE_STATUS)
public enum EnumEvaluateStatus implements DictEntry {
    
    //评价状态
    EVALUATED("已评价"),
    
    UNEVALUATED("未评价"),
    
    ;

    private String text;

    private EnumEvaluateStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
