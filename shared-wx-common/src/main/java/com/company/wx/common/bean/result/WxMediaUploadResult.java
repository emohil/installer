package com.company.wx.common.bean.result;

import java.io.Serializable;

import com.company.wx.common.api.JsonBean;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 公众号可调用本接口来上传图片、语音、视频等文件到微信服务器，上传后服务器会返回对应的media_id，公众号此后可根据该media_id来获取多媒体。
 * 请注意，media_id是可复用的，调用该接口需http协议。
 * 
 * 参考链接 http://mp.weixin.qq.com/wiki/10/78b15308b053286e2a66b33f0f0f5fb6.html
 * 
 * @author lihome
 *
 */
public class WxMediaUploadResult extends JsonBean<WxMediaUploadResult> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图） */
    private String type;

    /** 媒体文件上传后，获取时的唯一标识 */
    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("thumb_media_id")
    private String thumbMediaId;

    /** 媒体文件上传时间戳 */
    @JsonProperty("created_at")
    private long createdAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public static WxMediaUploadResult fromJson(String json) {
        return fromJson(json, WxMediaUploadResult.class);
    }
}
