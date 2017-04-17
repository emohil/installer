package com.company.api.admin.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.ADMIN_STATUS)
public enum EnumAdminStatus implements DictEntry {

    ENABLED("已启用"),

    DISABLED("已停用")

    ;

    private String text;

    private EnumAdminStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
