package com.company.api.admin.service;

import com.company.api.fw.DictEntry;

public enum EnumAuth implements DictEntry {

    apartyList("全部甲方"), apartyAdd("新增甲方"), //

    itemList("全部项目"), itemAdd("新增项目"), //

    indentList("全部订单"), indentAdd("新增订单"), //

    managerList("经理人管理"), workerList("工匠管理"), //

    provList("城市管理"), //
    
    scnodeTree("服务节点"), sctypeTree("服务类别"), //

    adminList("全部账号"), adminAdd("新增账号"), roleList("角色管理");

    ;

    private String text;

    private EnumAuth(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
