package com.company.api.dict.service;

import com.company.api.fw.DictCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(DictCodes.PART)
public enum EnumPart implements DictEntry {
    
    //甲乙方
    APARTY("甲方"),
    
    OWNER("业主"),
    
    COMPANY("公司"),
    
    WORKER("工匠")
    
    ;

    private String text;

    private EnumPart(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
