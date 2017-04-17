package com.company.wx.mp.bean.result;

import java.io.Serializable;

import com.company.wx.common.api.JsonBean;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WxMpOAuth2AccessToken extends JsonBean<WxMpOAuth2AccessToken> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn = -1;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String openid;

    private String scope;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public static WxMpOAuth2AccessToken fromJson(String json) {
        return fromJson(json, WxMpOAuth2AccessToken.class);
    }
}