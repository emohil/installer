package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.INDENT_EXCEP_STATUS)
public enum EnumIndentExcepStatus implements DictEntry {

    // 订单异常状态
    PAUSE("暂停"),

    TIMEOUT("超时未响应"),

    DELETIONS("物料缺失")

    ;

    private String text;

    private EnumIndentExcepStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
