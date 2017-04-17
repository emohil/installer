package com.company.wx.mp.bean;

import java.io.Serializable;

import com.company.util.json.JacksonHelper;

/**
 * 群发时用到的视频素材
 * 
 */
public class WxMpMassVideo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String mediaId;
    private String title;
    private String description;

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

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String toJson() {
        return JacksonHelper.toJson(this);
    }
}