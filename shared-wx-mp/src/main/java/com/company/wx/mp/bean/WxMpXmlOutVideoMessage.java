package com.company.wx.mp.bean;

import com.company.wx.common.api.WxConsts;
import com.company.wx.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class WxMpXmlOutVideoMessage extends WxMpXmlOutMessage {

    private static final long serialVersionUID = 1L;
    
    @XStreamAlias("Video")
    protected final Video video = new Video();

    public WxMpXmlOutVideoMessage() {
        this.msgType = WxConsts.XML_MSG_VIDEO;
    }

    public String getMediaId() {
        return video.getMediaId();
    }

    public void setMediaId(String mediaId) {
        video.setMediaId(mediaId);
    }

    public String getTitle() {
        return video.getTitle();
    }

    public void setTitle(String title) {
        video.setTitle(title);
    }

    public String getDescription() {
        return video.getDescription();
    }

    public void setDescription(String description) {
        video.setDescription(description);
    }

    @XStreamAlias("Video")
    public static class Video {

        @XStreamAlias("MediaId")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String mediaId;

        @XStreamAlias("Title")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String title;

        @XStreamAlias("Description")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String description;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

}
