package com.company.api.indent.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.EXCEPTION_RESULT)
public enum EnumExceptionResultStatus implements DictEntry {
    
    //异常处理结果
    CONTINUE("继续执行"),
    
    SUSPEND("暂停服务"),
    
    OVER("终止服务")
    
    ;

    private String text;

    private EnumExceptionResultStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
