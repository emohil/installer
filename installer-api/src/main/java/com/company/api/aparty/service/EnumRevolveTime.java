package com.company.api.aparty.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

/**
 * 账期
 * @author kouwl
 *
 * @Date 2016年3月9日
 */
@EnumDict(EnumCodes.REVOLVE_TIME)
public enum EnumRevolveTime implements DictEntry {

    QUARTER("季度"),

    MONTHLY("月度"),
    
    WEEK("每周"),
    
    SINGLE("每单");

    private String text;

    private EnumRevolveTime(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}