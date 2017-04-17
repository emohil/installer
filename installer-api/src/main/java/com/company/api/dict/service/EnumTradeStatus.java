package com.company.api.dict.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.TRADE_STATUS)
public enum EnumTradeStatus implements DictEntry {

    TREATMENT("处理中"),
    
    ACCOMPLISH("转账成功"),
    
    FAILURE("转账失败");

    private String text;

    private EnumTradeStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }

}
