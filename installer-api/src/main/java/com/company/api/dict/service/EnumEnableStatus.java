package com.company.api.dict.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

/**
 * 通用的启用状态枚举，适用于一般的 启用/停用 功能.
 * 
 * @author lihome
 *
 */
@EnumDict(EnumCodes.ENABLE_STATUS)
public enum EnumEnableStatus implements DictEntry {

    ENABLED("已启用"),

    DISABLED("已停用");

    private String text;

    private EnumEnableStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}