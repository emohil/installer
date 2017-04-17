package com.company.wx.common.util.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.AbstractResponseHandler;

public class InputStreamResponseHandler extends AbstractResponseHandler<InputStream> {

    public static final ResponseHandler<InputStream> INSTANCE = new InputStreamResponseHandler();

    @Override
    public InputStream handleEntity(final HttpEntity entity) throws IOException {
        return entity.getContent();
    }
}