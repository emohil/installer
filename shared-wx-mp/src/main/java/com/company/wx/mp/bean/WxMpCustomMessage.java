package com.company.wx.mp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.company.util.json.JacksonHelper;
import com.company.wx.mp.bean.custombuilder.ImageBuilder;
import com.company.wx.mp.bean.custombuilder.MusicBuilder;
import com.company.wx.mp.bean.custombuilder.NewsBuilder;
import com.company.wx.mp.bean.custombuilder.TextBuilder;
import com.company.wx.mp.bean.custombuilder.VideoBuilder;
import com.company.wx.mp.bean.custombuilder.VoiceBuilder;

/**
 * 客服消息
 * 
 */
public class WxMpCustomMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String toUser;
    private String msgType;
    private String content;
    private String mediaId;
    private String thumbMediaId;
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;
    private List<WxArticle> articles = new ArrayList<WxArticle>();

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgType() {
        return msgType;
    }

    /**
     * <pre>
     * 请使用
     * {@link com.company.wx.common.api.WxConsts#CUSTOM_MSG_TEXT}
     * {@link com.company.wx.common.api.WxConsts#CUSTOM_MSG_IMAGE}
     * {@link com.company.wx.common.api.WxConsts#CUSTOM_MSG_VOICE}
     * {@link com.company.wx.common.api.WxConsts#CUSTOM_MSG_MUSIC}
     * {@link com.company.wx.common.api.WxConsts#CUSTOM_MSG_VIDEO}
     * {@link com.company.wx.common.api.WxConsts#CUSTOM_MSG_NEWS}
     * </pre>
     * 
     * @param msgType
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
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

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    public List<WxArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<WxArticle> articles) {
        this.articles = articles;
    }

    public String toJson() {
        return JacksonHelper.toJson(this);
    }

    public static class WxArticle {

        private String title;
        private String description;
        private String url;
        private String picUrl;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

    }

    /**
     * 获得文本消息builder
     * 
     * @return
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder
     * 
     * @return
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder
     * 
     * @return
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder
     * 
     * @return
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得音乐消息builder
     * 
     * @return
     */
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    /**
     * 获得图文消息builder
     * 
     * @return
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }
}