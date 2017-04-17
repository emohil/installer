package com.company.api.sms.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.api.fw.Constants;
import com.company.api.fw.util.EnvConfig;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.util.BooleanUtil;
import com.company.util.Dto;
import com.company.util.ExpireMap;
import com.company.util.New;
import com.company.util.ObjectUtil;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;
import com.company.util.Validate;
import com.company.util.json.JacksonHelper;

@Service(SmsService.BEAN_NAME)
public class SmsServiceImpl implements SmsService {

    private static String SERVICE_URL = EnvConfig.getProperty(EnvConfig.SMS_SERVICE_URL);

    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private static final int EXPIRDTIME = 900000;
    private static final int INTERVAL = 60;// 60s
    private static Map<String, String> vcodeMap = New.hashMap();
    private static Map<String, Long> ipMap = new ExpireMap<>(INTERVAL);

    @Override
    public Dto sendSms(String mobile, SmsTemplate template, Object... placeholders) {
        
        String content = String.format(template.getTpl(), placeholders);
        
        return this._doSendSms(mobile, content);
    }

    /**
     * 获取验证码
     */
    @Override
    public Dto getVcode(String mobile, SmsTemplate template, String remoteHost) {

        Dto ret = new Dto();
//TODO
//        if (checkMaliceRequest(remoteHost)) {
//            log.error("手机号: " + mobile + "　请不要频繁获取短信验证码!");
//            ret.put(TAG_MSG, "请不要频繁获取短信验证码!");
//
//            ret.put(TAG_SUCCESS, false);
//            return ret;
//        }

        // 生成随机码
        String vcode = generateVcode(6);
        // 保存随机码
        vcodeMap.put(mobile, vcode + Constants.MULTI_VALUE_SEP + System.currentTimeMillis());

        return sendSms(mobile, template, vcode);
    }

    /**
     * 调用接口，发送短信
     */
    private Dto _doSendSms(String mobile, String content) {

        log.info("手机号：{}, 短信内容：{}", mobile, content);
        
        Dto ret = new Dto();
        
        if (BooleanUtil.getBoolean(EnvConfig.getProperty(EnvConfig.DEBUGMODE))) {
            ret.put(TAG_SUCCESS, true);
            return ret;
        }
        
        Dto req = new Dto();
        
        req.put("id", 1);
        req.put("jsonrpc", "2.0");
        req.put("method", "save");

        Dto sms = new Dto();
        sms.put("businessType", "短信发送"); // 业务类型，用于统计
        sms.put("sendText", content); // 短信内容
        sms.put("sendTo", mobile); // 接收人姓名，可以多个，用半角逗号分隔
        sms.put("toDetail", mobile);
        sms.put("sendFrom", "api");

        req.put("params", Arrays.asList(sms));

        Dto auth = new Dto();
        auth.put("loginToken", getToken());

        req.put("auth", auth);

        return send(req);
    }

    /**
     * 校验短信验证码
     * 
     * @param mobile
     * @param vcode
     * @return
     */
    public Dto checkVcode(String mobile, String vcode) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        Validate.notBlank(mobile, "手机号码不能为空", new Object[0]);
        Validate.notBlank(vcode, "验证码不能为空", new Object[0]);

        String vcodeData = (String) vcodeMap.get(mobile);
        if (StringUtil.isBlank(vcodeData)) {
            ret.put(TAG_MSG, "无此验证码信息");
            return ret;
        }
        String[] vcodeInfos = StringUtil.split(vcodeData, (Constants.MULTI_VALUE_SEP));
        String oldVcode = vcodeInfos[0];
        if (!vcode.trim().equals(oldVcode.trim())) {
            ret.put(TAG_MSG, "验证码不正确");
            return ret;
        }
        String oldDate = vcodeInfos[1];

        if (System.currentTimeMillis() - Long.parseLong(oldDate) > EXPIRDTIME) {
            ret.put(TAG_MSG, "验证码超时");
            return ret;
        }

        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_MSG, "验证码验证成功");
        return ret;
    }

    /**
     * 检查恶意请求
     * 
     * @param params
     * @return
     */
    private boolean checkMaliceRequest(String remoteHost) {

        if (StringUtil.isEmpty(remoteHost)) {
            // 出现此情形,一般为调用的参数不全致,如IP地址.
            return true;
        }
        // if (remoteInfo.startsWith("127.0.0.1")) {
        // // 本地测试,无需校验
        // return false;
        // }

        Long ipValue = ipMap.get(remoteHost);
        long now = System.currentTimeMillis();
        if (ipValue == null || now - ipValue >= INTERVAL * 1000) {
            ipMap.put(remoteHost, now);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取发送短信的Token
     * 
     * @return
     */
    private String getToken() {
        String account = EnvConfig.getProperty(EnvConfig.SMS_ACCOUNT);
        String password = EnvConfig.getProperty(EnvConfig.SMS_PASSWORD);

        Dto req = new Dto();
        req.put("id", 1);
        req.put("jsonrpc", "2.0");
        req.put("method", "genLoginToken");
        req.put("params", Arrays.asList(account, password));

        try {
            String resStr = postJson(SERVICE_URL + "power/authService", JacksonHelper.toJson(req));
            Dto resp = JacksonHelper.toDto(resStr);

            Object result = resp.get("result");

            if (result == null || !(result instanceof Map)) {
                return null;
            }
            Map<?, ?> mResult = (Map<?, ?>) result;

            return ObjectUtil.toString(mResult.get("others"));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 调用发送短信API
     * 
     * @param req
     * @return
     */
    private Dto send(Dto req) {
        Dto ret = new Dto();
        try {
            String resStr = postJson(SERVICE_URL + "smsSave", JacksonHelper.toJson(req));

            Dto resp = JacksonHelper.toDto(resStr);

            Object result = resp.get("result");

            if (result == null || !(result instanceof Map)) {
                ret.put(TAG_SUCCESS, false);
                ret.put(TAG_MSG, "请求失败");
                return ret;
            }
            Map<?, ?> mResult = (Map<?, ?>) result;

            boolean success = BooleanUtil.getBoolean(mResult.get("result"));

            ret.put(TAG_SUCCESS, success);
            if (!success) {
                ret.put(TAG_MSG, resp.getAsString("msg"));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 发送POST请求
     * 
     * @param url
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static String postJson(String url, String jsonStr) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonStr, "utf-8");
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity httpEntity = response.getEntity();
        // if (httpEntity != null) {
        // post.abort();
        // }
        if (statusCode == 200) {
            ByteArrayOutputStream resHolder = new ByteArrayOutputStream();
            httpEntity.writeTo(resHolder);
            resHolder.flush();
            return resHolder.toString("utf-8");
        } else {
            log.error("服务异常");
            throw new Exception("服务异常");
        }
    }

    /**
     * 生成验证码
     * 
     * @param len
     * @return
     */
    private String generateVcode(int len) {
        return String.valueOf(RandomUtil.random(len));
    }
}