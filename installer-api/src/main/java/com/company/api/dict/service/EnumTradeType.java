package com.company.api.dict.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.TRADE_TYPE)
public enum EnumTradeType implements DictEntry {

    EARNING("收入"),

    CASH("取现"),

    DEDUCT("扣款"),
    
    REFUND("退款");
    
    

    private String text;

    private EnumTradeType(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }

}
