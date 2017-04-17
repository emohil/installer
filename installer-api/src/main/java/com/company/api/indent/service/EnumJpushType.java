package com.company.api.indent.service;

import com.company.api.fw.DictEntry;

public enum EnumJpushType implements DictEntry {
    
    //推送类型
    INDENT("订单推送"),
    
    INDENT_DETAIL("订单详情"),
    
    INDENT_PROGRESS("订单进程"),
    
    WORKER_APPLY("新工匠请求页面"),
    
    MANAGE_DETAIL("查看经理人详情"),
    
    BALANCE_DETAIL("余额明细列表"),
    
    WORKER_LIST("工匠列表页面"),
    
    HOME_PAGE("个人中心"),
    
    MANAGER_CHOOSE("重新选择经理人页面")
    
    ;

    private String text;

    private EnumJpushType(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
