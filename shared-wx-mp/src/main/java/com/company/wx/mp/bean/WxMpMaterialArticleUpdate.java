package com.company.wx.mp.bean;

import java.io.Serializable;

import com.company.util.json.JacksonHelper;

public class WxMpMaterialArticleUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mediaId;
    private int index;
    private WxMpMaterialNews.WxMpMaterialNewsArticle articles;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public WxMpMaterialNews.WxMpMaterialNewsArticle getArticles() {
        return articles;
    }

    public void setArticles(WxMpMaterialNews.WxMpMaterialNewsArticle articles) {
        this.articles = articles;
    }

    public String toJson() {
        return JacksonHelper.toJson(this);
    }

    @Override
    public String toString() {
        return "WxMpMaterialArticleUpdate [" + "mediaId=" + mediaId + ", index=" + index + ", articles=" + articles
                + "]";
    }
}