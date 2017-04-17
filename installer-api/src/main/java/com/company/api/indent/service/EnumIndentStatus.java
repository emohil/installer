package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

/**
 * 
 * 订单状态
 * 
 * @author lihome
 *
 */
@EnumDict(EnumCodes.INDENT_STATUS)
public enum EnumIndentStatus implements DictEntry {

    NORMAL("正常"),

    EXCEPTION("异常"),
    
    CANCEL("已取消"),
    
    OVER("已终止");

    private String text;

    private EnumIndentStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
