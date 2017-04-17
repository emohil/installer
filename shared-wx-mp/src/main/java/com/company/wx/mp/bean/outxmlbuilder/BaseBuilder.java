package com.company.wx.mp.bean.outxmlbuilder;

import com.company.wx.mp.bean.WxMpXmlOutMessage;

public abstract class BaseBuilder<BuilderType, ValueType> {

    protected String toUserName;

    protected String fromUserName;

    public BuilderType toUser(String touser) {
        this.toUserName = touser;

        @SuppressWarnings("unchecked")
        BuilderType builderType = (BuilderType) this;

        return builderType;
    }

    public BuilderType fromUser(String fromusername) {
        this.fromUserName = fromusername;

        @SuppressWarnings("unchecked")
        BuilderType builderType = (BuilderType) this;

        return builderType;
    }

    public abstract ValueType build();

    public void setCommon(WxMpXmlOutMessage m) {
        m.setToUserName(this.toUserName);
        m.setFromUserName(this.fromUserName);
        m.setCreateTime(System.currentTimeMillis() / 1000l);
    }
}