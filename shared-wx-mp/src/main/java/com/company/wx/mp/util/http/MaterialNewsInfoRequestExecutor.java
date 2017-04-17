package com.company.wx.mp.util.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.company.util.json.JacksonHelper;
import com.company.wx.common.bean.result.WxError;
import com.company.wx.common.exception.WxErrorException;
import com.company.wx.common.util.http.RequestExecutor;
import com.company.wx.common.util.http.Utf8ResponseHandler;
import com.company.wx.mp.bean.WxMpMaterialNews;

public class MaterialNewsInfoRequestExecutor implements RequestExecutor<WxMpMaterialNews, String> {

    public MaterialNewsInfoRequestExecutor() {
        super();
    }

    public WxMpMaterialNews execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String materialId)
            throws WxErrorException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(config);
        }

        Map<String, String> params = new HashMap<>();
        params.put("media_id", materialId);
        httpPost.setEntity(new StringEntity(JacksonHelper.toJson(params)));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        WxError error = WxError.fromJson(responseContent);
        if (error.getErrcode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpMaterialNews.fromJson(responseContent);
        }
    }
}