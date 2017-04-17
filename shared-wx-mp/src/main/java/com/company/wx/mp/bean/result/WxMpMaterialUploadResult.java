package com.company.wx.mp.bean.result;

import java.io.Serializable;

import com.company.util.json.JacksonHelper;

public class WxMpMaterialUploadResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mediaId;
    private String url;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static WxMpMaterialUploadResult fromJson(String json) {
        return JacksonHelper.toObject(json, WxMpMaterialUploadResult.class);
    }

    @Override
    public String toString() {
        return "WxMpMaterialUploadResult [media_id=" + mediaId + ", url=" + url + "]";
    }

}
