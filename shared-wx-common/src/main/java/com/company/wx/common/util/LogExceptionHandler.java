package com.company.wx.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.wx.common.api.WxErrorExceptionHandler;
import com.company.wx.common.exception.WxErrorException;

public class LogExceptionHandler implements WxErrorExceptionHandler {

    private Logger log = LoggerFactory.getLogger(WxErrorExceptionHandler.class);

    @Override
    public void handle(WxErrorException e) {

        log.error("Error happens", e);

    }
}
