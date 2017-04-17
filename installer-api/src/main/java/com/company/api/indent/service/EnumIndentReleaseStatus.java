package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.INDENT_RELEASE_STATUS)
public enum EnumIndentReleaseStatus implements DictEntry {
    
    //订单发布状态
    RELEASED("已发布"),
    
    UNRELEASED("未发布")
    
    ;

    private String text;

    private EnumIndentReleaseStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
