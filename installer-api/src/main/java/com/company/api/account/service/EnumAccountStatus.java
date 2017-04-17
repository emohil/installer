package com.company.api.account.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.ACCOUNT_STATUS)
public enum EnumAccountStatus implements DictEntry {
    
    ENABLED("启用"),
    
    DISABLED("停用")
    
    ;

    private String text;

    private EnumAccountStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
