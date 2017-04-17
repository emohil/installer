package com.company.api.dict.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.YESNO)
public enum EnumYesno implements DictEntry {
    
    YES("是"), NO("否");

    private String text;

    private EnumYesno(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
