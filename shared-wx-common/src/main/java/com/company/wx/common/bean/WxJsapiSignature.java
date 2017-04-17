package com.company.wx.common.bean;

import java.io.Serializable;

import com.company.wx.common.api.JsonBean;

/**
 * jspai signature
 */
public class WxJsapiSignature extends JsonBean<WxJsapiSignature> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appId;

    private String nonceStr;

    private long timestamp;

    private String url;

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public static WxJsapiSignature fromJson(String json) {
        return fromJson(json, WxJsapiSignature.class);
    }
}