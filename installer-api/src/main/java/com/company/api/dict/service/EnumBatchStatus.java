package com.company.api.dict.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.BATCH_STATUS)
public enum EnumBatchStatus implements DictEntry {

    UNTREATED("未处理"),

    DELETED("已删除"),

    POSTED("已过账");

    private String text;

    private EnumBatchStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }

}
