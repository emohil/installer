package com.company.api.item.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.CLEARING_STATUS)
public enum EnumClearingStatus implements DictEntry {
    
    //结算状态
    NONE("未结算"),
    
    YES("已结算"),
    
    ;

    private String text;

    private EnumClearingStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
