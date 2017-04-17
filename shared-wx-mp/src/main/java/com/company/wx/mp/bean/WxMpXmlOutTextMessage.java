package com.company.wx.mp.bean;

import com.company.wx.common.api.WxConsts;
import com.company.wx.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class WxMpXmlOutTextMessage extends WxMpXmlOutMessage {

    private static final long serialVersionUID = 1L;
    
    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    public WxMpXmlOutTextMessage() {
        this.msgType = WxConsts.XML_MSG_TEXT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
