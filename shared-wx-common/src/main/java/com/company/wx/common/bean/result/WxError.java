package com.company.wx.common.bean.result;

import java.io.Serializable;

import com.company.wx.common.api.JsonBean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 公众号每次调用接口时，可能获得正确或错误的返回码，开发者可以根据返回码信息调试接口，排查错误。
 * 
 * 参考链接： http://mp.weixin.qq.com/wiki/17/fa4e1434e57290788bde25603fa2fcbd.html
 * 
 * @author lihome
 */
@JsonDeserialize(using = WxErrorDeserializer.class)
public class WxError extends JsonBean<WxError> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 返回码 */
    private int errcode;

    /** 说明 */
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static WxError fromJson(String json) {
        return fromJson(json, WxError.class);
    }
}