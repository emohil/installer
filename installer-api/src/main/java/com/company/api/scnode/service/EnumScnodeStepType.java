package com.company.api.scnode.service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;

@EnumDict(EnumCodes.SCNODE_STEP_TYPE)
public enum EnumScnodeStepType implements DictEntry {

    TIPTEXT("提示文字"),

    SIGN("签到"),

    CONFIRM("确认"),

    UPLOAD("上传"),

    INVITEEVAL("邀请评价");

    private String text;

    private EnumScnodeStepType(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
