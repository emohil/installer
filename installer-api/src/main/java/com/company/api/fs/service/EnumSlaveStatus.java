package com.company.api.fs.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;
/**
 * 工人经理人从属关系枚举
 * 
 * @author kouwl
 *
 */
@EnumDict(EnumCodes.SLAVE_STATUS)
public enum EnumSlaveStatus implements DictEntry {
    
    
    APPLY("申请中"),
    
    NEGLECT("忽略"),
    
    AGREE("同意"),
    
    ABOLISH("离职");

    private String text;

    private EnumSlaveStatus(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
