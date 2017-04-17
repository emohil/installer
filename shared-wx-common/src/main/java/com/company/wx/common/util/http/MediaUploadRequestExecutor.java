package com.company.wx.common.util.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.company.util.json.JacksonHelper;
import com.company.wx.common.bean.result.WxError;
import com.company.wx.common.bean.result.WxMediaUploadResult;
import com.company.wx.common.exception.WxErrorException;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 * 
 */
public class MediaUploadRequestExecutor implements RequestExecutor<WxMediaUploadResult, File> {

    @Override
    public WxMediaUploadResult execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, File file)
            throws WxErrorException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(config);
        }
        if (file != null) {
            HttpEntity entity = MultipartEntityBuilder.create().addBinaryBody("media", file)
                    .setMode(HttpMultipartMode.RFC6532).build();
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
        }
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = JacksonHelper.toObject(responseContent, WxError.class);
            if (error.getErrcode() != 0) {
                throw new WxErrorException(error);
            }
            return JacksonHelper.toObject(responseContent, WxMediaUploadResult.class);
        }
    }
}