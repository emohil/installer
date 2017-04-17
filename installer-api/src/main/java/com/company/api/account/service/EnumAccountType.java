package com.company.api.account.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.ACCOUNT_TYPE)
public enum EnumAccountType implements DictEntry {

    WORKER("工人"),

    MANAGER("经理人");

    private String text;

    private EnumAccountType(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
