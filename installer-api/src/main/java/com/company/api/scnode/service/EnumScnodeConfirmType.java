package com.company.api.scnode.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

/**
 * 确认类型
 * 
 * @author lihome
 *
 */
@EnumDict(EnumCodes.SCNODE_CONFIRM_TYPE)
public enum EnumScnodeConfirmType implements DictEntry {

    /**
     * 确认基本信息，如收货人姓名，电话等
     */
    BASIC("确认基本信息"),

    /**
     * 确认物品数量
     */
    QUANTITY("确认数量")

    ;

    private String text;

    private EnumScnodeConfirmType(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
