package com.company.wx.common.api;

import com.company.wx.common.exception.WxErrorException;

/**
 * WxErrorException Handler
 */
public interface WxErrorExceptionHandler {

    /**
     * WxErrorException Handle
     * 
     * @param e
     */
    public void handle(WxErrorException e);

}
