package com.company.wx.common.util.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.company.util.FileUtil;
import com.company.wx.common.exception.WxErrorException;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 
 */
public class FileDownloadRequestExecutor implements RequestExecutor<File, String> {

    private File downloadFile;

    public FileDownloadRequestExecutor() {
        super();
    }

    public FileDownloadRequestExecutor(File downloadFile) {
        super();
        this.downloadFile = downloadFile;
        //ensure folder not null
        if (!downloadFile.getParentFile().exists()) {
            downloadFile.getParentFile().mkdirs();
        }
    }

    @Override
    public File execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String queryParam)
            throws WxErrorException, ClientProtocolException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        HttpGet httpGet = new HttpGet(uri);
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpGet.setConfig(config);
        }

        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);

            FileUtil.saveFile(inputStream, downloadFile);

            return downloadFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}