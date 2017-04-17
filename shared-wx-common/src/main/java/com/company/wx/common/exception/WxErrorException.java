package com.company.wx.common.exception;

import com.company.wx.common.bean.result.WxError;

/**
 * WxError Exception
 * @author lihome
 */
public class WxErrorException extends Exception {

    private static final long serialVersionUID = 1;

    private WxError error;

    public WxErrorException(WxError error) {
        super(error.toString());
        this.error = error;
    }

    public WxError getError() {
        return error;
    }

}
