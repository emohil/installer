package com.company.api.account.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.CHECK_STATUS)
public enum EnumCheckStatus implements DictEntry {
    
    //审核状态
    UNCHECK("未审核"),
    
    NOTPASS("未通过"),
    
    PASS("审核通过")
    
    ;

    private String text;

    private EnumCheckStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
