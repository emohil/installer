package com.company.wx.mp.bean.outxmlbuilder;

import com.company.wx.mp.bean.WxMpXmlOutVideoMessage;

/**
 * 视频消息builder
 * 
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxMpXmlOutVideoMessage> {

    private String mediaId;
    private String title;
    private String description;

    public VideoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public VideoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public VideoBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public WxMpXmlOutVideoMessage build() {
        WxMpXmlOutVideoMessage m = new WxMpXmlOutVideoMessage();
        setCommon(m);
        m.setTitle(title);
        m.setDescription(description);
        m.setMediaId(mediaId);
        return m;
    }
}