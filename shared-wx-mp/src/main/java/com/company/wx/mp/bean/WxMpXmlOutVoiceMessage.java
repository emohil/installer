package com.company.wx.mp.bean;

import com.company.wx.common.api.WxConsts;
import com.company.wx.common.util.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class WxMpXmlOutVoiceMessage extends WxMpXmlOutMessage {

    private static final long serialVersionUID = 1L;
    
    @XStreamAlias("Voice")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public WxMpXmlOutVoiceMessage() {
        this.msgType = WxConsts.XML_MSG_VOICE;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
