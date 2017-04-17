package com.company.wx.common.util.http;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 * A {@link org.apache.http.client.ResponseHandler} that returns the response
 * body as a String for successful (2xx) responses. If the response code was
 * &gt;= 300, the response body is consumed and an
 * {@link org.apache.http.client.HttpResponseException} is thrown.
 * <p>
 * If this is used with
 * {@link org.apache.http.client.HttpClient#execute(org.apache.http.client.methods.HttpUriRequest, org.apache.http.client.ResponseHandler)}
 * , HttpClient may handle redirects (3xx responses) internally.
 * </p>
 *
 */
public class Utf8ResponseHandler extends AbstractResponseHandler<String> {

    public static final ResponseHandler<String> INSTANCE = new Utf8ResponseHandler();

    /**
     * Returns the entity as a body as a UTF-8 String.
     */
    @Override
    public String handleEntity(final HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity, Consts.UTF_8);
    }
}