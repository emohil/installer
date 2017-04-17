package com.company.api.dict.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.PARTY_STAMP)
public enum EnumPartyStamp implements DictEntry {
    
    //甲乙方
    APARTY("甲方"),
    
    BPARTY("乙方"),
    
    ;

    private String text;

    private EnumPartyStamp(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
