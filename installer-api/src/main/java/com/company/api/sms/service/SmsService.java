package com.company.api.sms.service;

import com.company.api.fw.service.Tag;
import com.company.util.Dto;

public interface SmsService extends Tag {

    String BEAN_NAME = "smsService";

    /**
     * 发送短信
     * 
     * @param mobile
     *            接收短信的手机号
     * @param template
     *            短信模板
     * @param placeholder
     *            要替换的文字
     * @return
     */
    Dto sendSms(String mobile, SmsTemplate template, Object... placeholders);

    /**
     * <pre>
     * 获取短信验证码. 
     * 为了防止恶意获取短信验证码， 这里根据客户机主机限制发送短信的频率
     * </pre>
     * 
     * @param mobile
     *            接收短信的手机号
     * @param template
     *            短信模板
     * @param remoteHost
     *            请求主机
     * @return
     */
    Dto getVcode(String mobile, SmsTemplate template, String remoteHost);

    /**
     * 检查验证码
     * 
     * @param mobile
     *            接收到验证码的手机号
     * @param vcode
     *            接收到的验证码
     * @return
     */
    Dto checkVcode(String mobile, String vcode);
}
