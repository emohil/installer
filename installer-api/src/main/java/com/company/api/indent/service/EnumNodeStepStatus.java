package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.INDENT_NODE_STEP_STATUS)
public enum EnumNodeStepStatus implements DictEntry {
    
    //订单执行状态
    UNFINISHED("未完成"),
    
    FINISH("已完成");

    private String text;

    private EnumNodeStepStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
