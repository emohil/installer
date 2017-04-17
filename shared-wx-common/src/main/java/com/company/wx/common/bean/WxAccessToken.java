package com.company.wx.common.bean;

import java.io.Serializable;

import com.company.wx.common.api.JsonBean;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
 * access_token的存储至少要保留512个字符空间。
 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 * 
 * 公众平台的API调用所需的access_token的使用及生成方式说明：
 * 1、为了保密appsecrect，第三方需要一个access_token获取和刷新的中控服务器。
 * 而其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，否则会造成access_token覆盖而影响业务；
 * 2、目前access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。
 * 中控服务器需要根据这个有效时间提前去刷新新access_token。在刷新过程中，中控服务器对外输出的依然是老access_token，
 * 此时公众平台后台会保证在刷新短时间内，新老access_token都可用，
 * 这保证了第三方业务的平滑过渡；
 * 3、access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，还需要提供被动刷新access_token的接口，
 * 这样便于业务服务器在API调用获知access_token已超时的情况下，可以触发access_token的刷新流程。
 * 
 * @author lihome
 *
 */
public class WxAccessToken extends JsonBean<WxAccessToken> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 获取到的凭证 */
    @JsonProperty("access_token")
    private String accessToken;

    /** 凭证有效时间，单位：秒 */
    @JsonProperty("expires_in")
    private int expiresIn = -1;

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

    public static WxAccessToken fromJson(String json) {
        return fromJson(json, WxAccessToken.class);
    }
}