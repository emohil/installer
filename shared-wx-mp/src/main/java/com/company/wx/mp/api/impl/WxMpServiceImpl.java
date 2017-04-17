package com.company.wx.mp.api.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.util.Dto;
import com.company.util.FileUtil;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;
import com.company.util.json.JacksonHelper;
import com.company.wx.common.api.WxConsts;
import com.company.wx.common.bean.WxAccessToken;
import com.company.wx.common.bean.WxJsapiSignature;
import com.company.wx.common.bean.WxMenu;
import com.company.wx.common.bean.result.WxError;
import com.company.wx.common.bean.result.WxMediaUploadResult;
import com.company.wx.common.exception.WxErrorException;
import com.company.wx.common.session.StandardSessionManager;
import com.company.wx.common.session.WxSessionManager;
import com.company.wx.common.util.crypto.SHA1;
import com.company.wx.common.util.crypto.WxCryptUtil;
import com.company.wx.common.util.http.MediaDownloadRequestExecutor;
import com.company.wx.common.util.http.MediaUploadRequestExecutor;
import com.company.wx.common.util.http.RequestExecutor;
import com.company.wx.common.util.http.SimpleGetRequestExecutor;
import com.company.wx.common.util.http.SimplePostRequestExecutor;
import com.company.wx.common.util.http.URIUtil;
import com.company.wx.common.util.http.Utf8ResponseHandler;
import com.company.wx.common.util.xml.XStreamInitializer;
import com.company.wx.mp.api.WxMpConfigStorage;
import com.company.wx.mp.api.WxMpService;
import com.company.wx.mp.bean.WxMpCustomMessage;
import com.company.wx.mp.bean.WxMpGroup;
import com.company.wx.mp.bean.WxMpMassGroupMessage;
import com.company.wx.mp.bean.WxMpMassNews;
import com.company.wx.mp.bean.WxMpMassOpenIdsMessage;
import com.company.wx.mp.bean.WxMpMassVideo;
import com.company.wx.mp.bean.WxMpMaterial;
import com.company.wx.mp.bean.WxMpMaterialArticleUpdate;
import com.company.wx.mp.bean.WxMpMaterialNews;
import com.company.wx.mp.bean.WxMpSemanticQuery;
import com.company.wx.mp.bean.WxMpTemplateMessage;
import com.company.wx.mp.bean.result.WxMpMassSendResult;
import com.company.wx.mp.bean.result.WxMpMassUploadResult;
import com.company.wx.mp.bean.result.WxMpMaterialCountResult;
import com.company.wx.mp.bean.result.WxMpMaterialFileBatchGetResult;
import com.company.wx.mp.bean.result.WxMpMaterialNewsBatchGetResult;
import com.company.wx.mp.bean.result.WxMpMaterialUploadResult;
import com.company.wx.mp.bean.result.WxMpMaterialVideoInfoResult;
import com.company.wx.mp.bean.result.WxMpOAuth2AccessToken;
import com.company.wx.mp.bean.result.WxMpPayCallback;
import com.company.wx.mp.bean.result.WxMpPayResult;
import com.company.wx.mp.bean.result.WxMpPrepayIdResult;
import com.company.wx.mp.bean.result.WxMpQrCodeTicket;
import com.company.wx.mp.bean.result.WxMpSemanticQueryResult;
import com.company.wx.mp.bean.result.WxMpUser;
import com.company.wx.mp.bean.result.WxMpUserCumulate;
import com.company.wx.mp.bean.result.WxMpUserList;
import com.company.wx.mp.bean.result.WxMpUserSummary;
import com.company.wx.mp.util.http.MaterialDeleteRequestExecutor;
import com.company.wx.mp.util.http.MaterialNewsInfoRequestExecutor;
import com.company.wx.mp.util.http.MaterialUploadRequestExecutor;
import com.company.wx.mp.util.http.MaterialVideoInfoRequestExecutor;
import com.company.wx.mp.util.http.MaterialVoiceAndImageDownloadRequestExecutor;
import com.company.wx.mp.util.http.QrCodeRequestExecutor;
import com.thoughtworks.xstream.XStream;

public class WxMpServiceImpl implements WxMpService {

    private static Logger log = LoggerFactory.getLogger(WxMpServiceImpl.class);

    /**
     * 全局的是否正在刷新access token的锁
     */
    protected final Object globalAccessTokenRefreshLock = new Object();

    /**
     * 全局的是否正在刷新jsapi_ticket的锁
     */
    protected final Object globalJsapiTicketRefreshLock = new Object();

    protected WxMpConfigStorage wxMpConfigStorage;

    protected CloseableHttpClient httpClient;

    protected HttpHost httpProxy;

    private int retrySleepMillis = 1000;

    private int maxRetryTimes = 5;

    protected WxSessionManager sessionManager = new StandardSessionManager();

    public boolean checkSignature(String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(wxMpConfigStorage.getToken(), timestamp, nonce).equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    public String getAccessToken() throws WxErrorException {
        return getAccessToken(false);
    }

    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        if (forceRefresh) {
            wxMpConfigStorage.expireAccessToken();
        }
        if (wxMpConfigStorage.isAccessTokenExpired()) {
            synchronized (globalAccessTokenRefreshLock) {
                if (wxMpConfigStorage.isAccessTokenExpired()) {
                    String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&appid="
                            + wxMpConfigStorage.getAppId() + "&secret=" + wxMpConfigStorage.getSecret();
                    try {
                        HttpGet httpGet = new HttpGet(url);
                        if (httpProxy != null) {
                            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
                            httpGet.setConfig(config);
                        }
                        CloseableHttpResponse response = getHttpclient().execute(httpGet);
                        String resultContent = new BasicResponseHandler().handleResponse(response);
                        WxError error = WxError.fromJson(resultContent);
                        if (error.getErrcode() != 0) {
                            throw new WxErrorException(error);
                        }
                        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                        wxMpConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
                    } catch (ClientProtocolException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return wxMpConfigStorage.getAccessToken();
    }

    public String getJsapiTicket() throws WxErrorException {
        return getJsapiTicket(false);
    }

    public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
        if (forceRefresh) {
            wxMpConfigStorage.expireJsapiTicket();
        }
        if (wxMpConfigStorage.isJsapiTicketExpired()) {
            synchronized (globalJsapiTicketRefreshLock) {
                if (wxMpConfigStorage.isJsapiTicketExpired()) {
                    String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
                    String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
                    Dto dto = JacksonHelper.toDto(responseContent);

                    String jsapiTicket = dto.getAsString("ticket");
                    int expiresInSeconds = dto.getAsInt("expires_in");
                    wxMpConfigStorage.updateJsapiTicket(jsapiTicket, expiresInSeconds);
                }
            }
        }
        return wxMpConfigStorage.getJsapiTicket();
    }

    public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = RandomUtil.getRandomStr(16);
        String jsapiTicket = getJsapiTicket(false);
        try {
            String signature = SHA1.genWithAmple("jsapi_ticket=" + jsapiTicket, "noncestr=" + noncestr, "timestamp="
                    + timestamp, "url=" + url);
            WxJsapiSignature jsapiSignature = new WxJsapiSignature();
            jsapiSignature.setAppId(wxMpConfigStorage.getAppId());
            jsapiSignature.setTimestamp(timestamp);
            jsapiSignature.setNonceStr(noncestr);
            jsapiSignature.setUrl(url);
            jsapiSignature.setSignature(signature);
            return jsapiSignature;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void customMessageSend(WxMpCustomMessage message) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
        execute(new SimplePostRequestExecutor(), url, message.toJson());
    }

    public void menuCreate(WxMenu menu) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
        execute(new SimplePostRequestExecutor(), url, menu.toJson());
    }

    public void menuDelete() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete";
        execute(new SimpleGetRequestExecutor(), url, null);
    }

    public WxMenu menuGet() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
        try {
            String resultContent = execute(new SimpleGetRequestExecutor(), url, null);
            return WxMenu.fromJson(resultContent);
        } catch (WxErrorException e) {
            // 46003 不存在的菜单数据
            if (e.getError().getErrcode() == 46003) {
                return null;
            }
            throw e;
        }
    }

    public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream)
            throws WxErrorException, IOException {
        return mediaUpload(mediaType, FileUtil.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    }

    public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?type=" + mediaType;
        return execute(new MediaUploadRequestExecutor(), url, file);
    }

    public File mediaDownload(String media_id) throws WxErrorException {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get";
        return execute(new MediaDownloadRequestExecutor(wxMpConfigStorage.getTmpDirFile()), url, "media_id=" + media_id);
    }

    public WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial material) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?type=" + mediaType;
        return execute(new MaterialUploadRequestExecutor(), url, material);
    }

    public WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews news) throws WxErrorException {
        if (news == null || news.isEmpty()) {
            throw new IllegalArgumentException("news is empty!");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_news";
        String responseContent = post(url, news.toJson());
        return WxMpMaterialUploadResult.fromJson(responseContent);
    }

    public InputStream materialImageOrVoiceDownload(String media_id) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return execute(new MaterialVoiceAndImageDownloadRequestExecutor(wxMpConfigStorage.getTmpDirFile()), url,
                media_id);
    }

    public WxMpMaterialVideoInfoResult materialVideoInfo(String media_id) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return execute(new MaterialVideoInfoRequestExecutor(), url, media_id);
    }

    public WxMpMaterialNews materialNewsInfo(String media_id) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return execute(new MaterialNewsInfoRequestExecutor(), url, media_id);
    }

    public boolean materialNewsUpdate(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/update_news";
        String responseText = post(url, wxMpMaterialArticleUpdate.toJson());
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrcode() == 0) {
            return true;
        } else {
            throw new WxErrorException(wxError);
        }
    }

    public boolean materialDelete(String media_id) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/del_material";
        return execute(new MaterialDeleteRequestExecutor(), url, media_id);
    }

    public WxMpMaterialCountResult materialCount() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
        String responseText = get(url, null);
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrcode() == 0) {
            return WxMpMaterialCountResult.fromJson(responseText);
        } else {
            throw new WxErrorException(wxError);
        }
    }

    public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int offset, int count) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
        Map<String, Object> params = new HashMap<>();
        params.put("type", WxConsts.MATERIAL_NEWS);
        params.put("offset", offset);
        params.put("count", count);
        String responseText = post(url, JacksonHelper.toJson(params));
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrcode() == 0) {
            return WxMpMaterialNewsBatchGetResult.fromJson(responseText);
        } else {
            throw new WxErrorException(wxError);
        }
    }

    public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int offset, int count)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        String responseText = post(url, JacksonHelper.toJson(params));
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrcode() == 0) {
            return JacksonHelper.toObject(responseText, WxMpMaterialFileBatchGetResult.class);
        } else {
            throw new WxErrorException(wxError);
        }
    }

    public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
        String responseContent = execute(new SimplePostRequestExecutor(), url, news.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/uploadvideo";
        String responseContent = execute(new SimplePostRequestExecutor(), url, video.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    public WxMpMassSendResult massGroupMessageSend(WxMpMassGroupMessage message) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
        String responseContent = execute(new SimplePostRequestExecutor(), url, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
        String responseContent = execute(new SimplePostRequestExecutor(), url, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    public WxMpGroup groupCreate(String name) throws WxErrorException {
        // TODO
        throw new RuntimeException("TODO");
    }

    public List<WxMpGroup> groupGet() throws WxErrorException {
        // TODO
        throw new RuntimeException("TODO");
    }

    public long userGetGroup(String openid) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/getid";
        Dto o = new Dto();
        o.addProperty("openid", openid);
        String responseContent = execute(new SimplePostRequestExecutor(), url, o.toString());
        Dto dto = JacksonHelper.toDto(responseContent);
        return dto.getAsLong("groupid");
    }

    public void groupUpdate(WxMpGroup group) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/update";
        execute(new SimplePostRequestExecutor(), url, group.toJson());
    }

    public void userUpdateGroup(String openid, long to_groupid) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
        Dto json = new Dto();
        json.addProperty("openid", openid);
        json.addProperty("to_groupid", to_groupid);
        execute(new SimplePostRequestExecutor(), url, json.toString());
    }

    public void userUpdateRemark(String openid, String remark) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
        Dto json = new Dto();
        json.addProperty("openid", openid);
        json.addProperty("remark", remark);
        execute(new SimplePostRequestExecutor(), url, json.toString());
    }

    public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        lang = lang == null ? "zh_CN" : lang;
        String responseContent = execute(new SimpleGetRequestExecutor(), url, "openid=" + openid + "&lang=" + lang);
        return WxMpUser.fromJson(responseContent);
    }

    public WxMpUserList userList(String next_openid) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get";
        String responseContent = execute(new SimpleGetRequestExecutor(), url, next_openid == null ? null
                : "next_openid=" + next_openid);
        return WxMpUserList.fromJson(responseContent);
    }

    public WxMpQrCodeTicket qrCodeCreateTmpTicket(int scene_id, Integer expire_seconds) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        Dto json = new Dto();
        json.addProperty("action_name", "QR_SCENE");
        if (expire_seconds != null) {
            json.addProperty("expire_seconds", expire_seconds);
        }
        Dto actionInfo = new Dto();
        Dto scene = new Dto();
        scene.addProperty("scene_id", scene_id);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = execute(new SimplePostRequestExecutor(), url, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    public WxMpQrCodeTicket qrCodeCreateLastTicket(int scene_id) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        Dto json = new Dto();
        json.addProperty("action_name", "QR_LIMIT_SCENE");
        Dto actionInfo = new Dto();
        Dto scene = new Dto();
        scene.addProperty("scene_id", scene_id);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = execute(new SimplePostRequestExecutor(), url, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    public WxMpQrCodeTicket qrCodeCreateLastTicket(String scene_str) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        Dto json = new Dto();
        json.addProperty("action_name", "QR_LIMIT_STR_SCENE");
        Dto actionInfo = new Dto();
        Dto scene = new Dto();
        scene.addProperty("scene_str", scene_str);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = execute(new SimplePostRequestExecutor(), url, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    public File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException {
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
        return execute(new QrCodeRequestExecutor(), url, ticket);
    }

    public String shortUrl(String long_url) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl";
        Dto o = new Dto();
        o.addProperty("action", "long2short");
        o.addProperty("long_url", long_url);
        String responseContent = execute(new SimplePostRequestExecutor(), url, o.toString());
        Dto dto = JacksonHelper.toDto(responseContent);
        return dto.getAsString("short_url");
    }

    public String templateSend(WxMpTemplateMessage templateMessage) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
        String responseContent = execute(new SimplePostRequestExecutor(), url, templateMessage.toJson());
        Dto dto = JacksonHelper.toDto(responseContent);
        return dto.getAsString("msgid");
    }

    public WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException {
        String url = "https://api.weixin.qq.com/semantic/semproxy/search";
        String responseContent = execute(new SimplePostRequestExecutor(), url, semanticQuery.toJson());
        return WxMpSemanticQueryResult.fromJson(responseContent);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String scope, String state) {
        return this.oauth2buildAuthorizationUrl(wxMpConfigStorage.getOauth2redirectUri(), scope, state);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
        url += "appid=" + wxMpConfigStorage.getAppId();
        url += "&redirect_uri=" + URIUtil.encodeURIComponent(redirectURI);
        url += "&response_type=code";
        url += "&scope=" + scope;
        if (state != null) {
            url += "&state=" + state;
        }
        url += "#wechat_redirect";
        return url;
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        url += "appid=" + wxMpConfigStorage.getAppId();
        url += "&secret=" + wxMpConfigStorage.getSecret();
        url += "&code=" + code;
        url += "&grant_type=authorization_code";

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
        url += "appid=" + wxMpConfigStorage.getAppId();
        url += "&grant_type=refresh_token";
        url += "&refresh_token=" + refreshToken;

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException {
        String url = "https://api.weixin.qq.com/sns/userinfo?";
        url += "access_token=" + oAuth2AccessToken.getAccessToken();
        url += "&openid=" + oAuth2AccessToken.getOpenid();
        if (lang == null) {
            url += "&lang=zh_CN";
        } else {
            url += "&lang=" + lang;
        }

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
            return WxMpUser.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken) {
        String url = "https://api.weixin.qq.com/sns/auth?";
        url += "access_token=" + oAuth2AccessToken.getAccessToken();
        url += "&openid=" + oAuth2AccessToken.getOpenid();

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            executor.execute(getHttpclient(), httpProxy, url, null);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WxErrorException e) {
            return false;
        }
        return true;
    }

    @Override
    public String[] getCallbackIP() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
        String responseContent = get(url, null);
        Dto dto = JacksonHelper.toDto(responseContent);
        List<?> ipList = dto.getAsList("ip_list");
        String[] ipArray = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            ipArray[i] = (String) ipList.get(i);
        }
        return ipArray;
    }

    @Override
    public List<WxMpUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException {
        // TODO
        throw new RuntimeException("TODO");
    }

    @Override
    public List<WxMpUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
        // TODO
        throw new RuntimeException("TODO");
    }

    public String get(String url, String queryParam) throws WxErrorException {
        return execute(new SimpleGetRequestExecutor(), url, queryParam);
    }

    public String post(String url, String postData) throws WxErrorException {
        return execute(new SimplePostRequestExecutor(), url, postData);
    }

    /**
     * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
     *
     * @param executor
     * @param uri
     * @param data
     * @return
     * @throws WxErrorException
     */
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        int retryTimes = 0;
        do {
            try {
                return executeInternal(executor, uri, data);
            } catch (WxErrorException e) {
                WxError error = e.getError();
                /**
                 * -1 系统繁忙, 1000ms后重试
                 */
                if (error.getErrcode() == -1) {
                    int sleepMillis = retrySleepMillis * (1 << retryTimes);
                    try {
                        log.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                } else {
                    throw e;
                }
            }
        } while (++retryTimes < maxRetryTimes);

        throw new RuntimeException("微信服务端异常，超出重试次数");
    }

    protected synchronized <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data)
            throws WxErrorException {
        if (uri.indexOf("access_token=") != -1) {
            throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
        }
        String accessToken = getAccessToken(false);

        String uriWithAccessToken = uri;
        uriWithAccessToken += (uri.indexOf('?') == -1 ? "?" : "&") + "access_token=" + accessToken;

        try {
            return executor.execute(getHttpclient(), httpProxy, uriWithAccessToken, data);
        } catch (WxErrorException e) {
            WxError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token 40001
             * 获取access_token时AppSecret错误，或者access_token无效 42001 access_token超时
             */
            if (error.getErrcode() == 42001 || error.getErrcode() == 40001) {
                // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access
                // token
                wxMpConfigStorage.expireAccessToken();
                return execute(executor, uri, data);
            }
            if (error.getErrcode() != 0) {
                throw new WxErrorException(error);
            }
            return null;
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected CloseableHttpClient getHttpclient() {
        return httpClient;
    }

    public void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider) {
        this.wxMpConfigStorage = wxConfigProvider;

        String http_proxy_host = wxMpConfigStorage.getHttpProxyHost();
        int http_proxy_port = wxMpConfigStorage.getHttpProxyPort();
        String http_proxy_username = wxMpConfigStorage.getHttpProxyUsername();
        String http_proxy_password = wxMpConfigStorage.getHttpProxyPassword();

        if (StringUtil.isNotBlank(http_proxy_host)) {
            // 使用代理服务器
            if (StringUtil.isNotBlank(http_proxy_username)) {
                // 需要用户认证的代理服务器
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(http_proxy_host, http_proxy_port),
                        new UsernamePasswordCredentials(http_proxy_username, http_proxy_password));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
            } else {
                // 无需用户认证的代理服务器
                httpClient = HttpClients.createDefault();
            }
            httpProxy = new HttpHost(http_proxy_host, http_proxy_port);
        } else {
            httpClient = HttpClients.createDefault();
        }
    }

    @Override
    public void setRetrySleepMillis(int retrySleepMillis) {
        this.retrySleepMillis = retrySleepMillis;
    }

    @Override
    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public WxMpPrepayIdResult getPrepayId(String openId, String outTradeNo, double amt, String body, String tradeType,
            String ip, String callbackUrl) {
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", wxMpConfigStorage.getAppId());
        packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
        packageParams.put("body", body);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("total_fee", (int) (amt * 100) + "");
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", callbackUrl);
        packageParams.put("trade_type", tradeType);
        packageParams.put("openid", openId);

        return getPrepayId(packageParams);
    }

    public WxMpPrepayIdResult getPrepayId(final Map<String, String> parameters) {
        String nonce_str = System.currentTimeMillis() + "";

        final SortedMap<String, String> packageParams = new TreeMap<String, String>(parameters);
        packageParams.put("appid", wxMpConfigStorage.getAppId());
        packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
        packageParams.put("nonce_str", nonce_str);
        checkParameters(packageParams);

        String sign = WxCryptUtil.createSign(packageParams, wxMpConfigStorage.getPartnerKey());
        packageParams.put("sign", sign);

        StringBuilder request = new StringBuilder("<xml>");
        for (Entry<String, String> para : packageParams.entrySet()) {
            request.append(String.format("<%s>%s</%s>", para.getKey(), para.getValue(), para.getKey()));
        }
        request.append("</xml>");

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(config);
        }

        StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
        httpPost.setEntity(entity);
        try {
            CloseableHttpResponse response = getHttpclient().execute(httpPost);
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            XStream xstream = XStreamInitializer.getInstance();
            xstream.alias("xml", WxMpPrepayIdResult.class);
            WxMpPrepayIdResult wxMpPrepayIdResult = (WxMpPrepayIdResult) xstream.fromXML(responseContent);
            return wxMpPrepayIdResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new WxMpPrepayIdResult();
    }

    final String[] REQUIRED_ORDER_PARAMETERS = new String[] { "appid", "mch_id", "body", "out_trade_no", "total_fee",
            "spbill_create_ip", "notify_url", "trade_type", };

    private void checkParameters(Map<String, String> parameters) {
        for (String para : REQUIRED_ORDER_PARAMETERS) {
            if (!parameters.containsKey(para))
                throw new IllegalArgumentException("Reqiured argument '" + para + "' is missing.");
        }
        if ("JSAPI".equals(parameters.get("trade_type")) && !parameters.containsKey("openid"))
            throw new IllegalArgumentException("Reqiured argument 'openid' is missing when trade_type is 'JSAPI'.");
        if ("NATIVE".equals(parameters.get("trade_type")) && !parameters.containsKey("product_id"))
            throw new IllegalArgumentException("Reqiured argument 'product_id' is missing when trade_type is 'NATIVE'.");
    }

    @Override
    public Map<String, String> getJSSDKPayInfo(String openId, String outTradeNo, double amt, String body,
            String tradeType, String ip, String callbackUrl) {
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", wxMpConfigStorage.getAppId());
        packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
        packageParams.put("body", body);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("total_fee", (int) (amt * 100) + "");
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", callbackUrl);
        packageParams.put("trade_type", tradeType);
        packageParams.put("openid", openId);

        return getJSSDKPayInfo(packageParams);
    }

    @Override
    public Map<String, String> getJSSDKPayInfo(Map<String, String> parameters) {
        WxMpPrepayIdResult wxMpPrepayIdResult = getPrepayId(parameters);
        String prepayId = wxMpPrepayIdResult.getPrepay_id();
        if (prepayId == null || prepayId.equals("")) {
            throw new RuntimeException("get prepayid error");
        }

        Map<String, String> payInfo = new HashMap<String, String>();
        payInfo.put("appId", wxMpConfigStorage.getAppId());
        // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
        payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        payInfo.put("nonceStr", System.currentTimeMillis() + "");
        payInfo.put("package", "prepay_id=" + prepayId);
        payInfo.put("signType", "MD5");

        String finalSign = WxCryptUtil.createSign(payInfo, wxMpConfigStorage.getPartnerKey());
        payInfo.put("paySign", finalSign);
        return payInfo;
    }

    @Override
    public WxMpPayResult getJSSDKPayResult(String transactionId, String outTradeNo) {
        String nonce_str = System.currentTimeMillis() + "";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", wxMpConfigStorage.getAppId());
        packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
        packageParams.put("transaction_id", transactionId);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("nonce_str", nonce_str);

        String sign = WxCryptUtil.createSign(packageParams, wxMpConfigStorage.getPartnerKey());
        String xml = new StringBuilder() //
                .append("<xml>") //
                .append("<appid>").append(wxMpConfigStorage.getAppId() + "</appid>") //
                .append("<mch_id>").append(wxMpConfigStorage.getPartnerId()).append("</mch_id>") //
                .append("<transaction_id>").append(transactionId).append("</transaction_id>") //
                .append("<out_trade_no>").append(outTradeNo).append("</out_trade_no>")//
                .append("<nonce_str>").append(nonce_str).append("</nonce_str>") //
                .append("<sign>").append(sign).append("</sign>") //
                .append("</xml>") //
                .toString();

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/orderquery");
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(config);
        }

        StringEntity entity = new StringEntity(xml, Consts.UTF_8);
        httpPost.setEntity(entity);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            XStream xstream = XStreamInitializer.getInstance();
            xstream.alias("xml", WxMpPayResult.class);
            WxMpPayResult wxMpPayResult = (WxMpPayResult) xstream.fromXML(responseContent);
            return wxMpPayResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new WxMpPayResult();
    }

    @Override
    public WxMpPayCallback getJSSDKCallbackData(String xmlData) {
        try {
            XStream xstream = XStreamInitializer.getInstance();
            xstream.alias("xml", WxMpPayCallback.class);
            WxMpPayCallback wxMpCallback = (WxMpPayCallback) xstream.fromXML(xmlData);
            return wxMpCallback;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WxMpPayCallback();
    }
}