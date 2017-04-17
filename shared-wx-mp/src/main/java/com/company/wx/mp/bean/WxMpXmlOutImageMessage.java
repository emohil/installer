package com.company.wx.mp.bean;

import com.company.wx.common.api.WxConsts;
import com.company.wx.common.util.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class WxMpXmlOutImageMessage extends WxMpXmlOutMessage {

    private static final long serialVersionUID = 1L;

    @XStreamAlias("Image")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public WxMpXmlOutImageMessage() {
        this.msgType = WxConsts.XML_MSG_IMAGE;
    }
}