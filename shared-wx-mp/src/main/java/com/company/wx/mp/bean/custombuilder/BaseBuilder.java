package com.company.wx.mp.bean.custombuilder;

import com.company.wx.mp.bean.WxMpCustomMessage;

public class BaseBuilder<T> {

    protected String msgType;

    protected String toUser;

    public T toUser(String toUser) {
        this.toUser = toUser;

        @SuppressWarnings("unchecked")
        T t = (T) this;

        return t;
    }

    public WxMpCustomMessage build() {
        WxMpCustomMessage m = new WxMpCustomMessage();
        m.setMsgType(this.msgType);
        m.setToUser(this.toUser);
        return m;
    }
}