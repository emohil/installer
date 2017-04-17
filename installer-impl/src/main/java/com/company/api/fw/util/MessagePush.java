package com.company.api.fw.util;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class MessagePush {

    private static final String appKey = "33fc6d9b5c31c5391a097f5a";
    private static final String masterSecret = "81dc028201f7f5414d6300c2";
    private JPushClient jpushClient;
    private String title;
    private String message;
    private Map<String, String> extras;

    public MessagePush(String message) {
        this.message = message;
        jpushClient = new JPushClient(masterSecret, appKey, 3);
    }

    public MessagePush(String message, String title) {
        this(message);
        this.title = title;
    }
    
    public MessagePush(String message, String title, Map<String, String> extras) {
        this(message);
        this.title = title;
        this.extras = extras;
    }

    /**
     * 向所有Android发送消息
     * 
     * @return 消息id
     */
    public long sendPushAllAndroid() {
        PushPayload payload = buildPushObject_android_all_alert();
        long msgId = 0;
        try {
            PushResult result = jpushClient.sendPush(payload);
            msgId = result.msg_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgId;
    }
    
    /**
     * 向所有人发送消息
     * 
     * @return 消息id
     */
    public long sendPushAll() {
        PushPayload payload = buildPushObject_all_all_alert();
        long msgId = 0;
        try {
            PushResult result = jpushClient.sendPush(payload);
            msgId = result.msg_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgId;
    }

    /**
     * 向指定别名的Android客户端发送消息
     * 
     * @param alias所有别名信息集合
     * @return 消息id
     */
    public long sendPushAlias(Set<String> alias) {
        PushPayload payloadAlias = buildPushObject_android_alias_alertWithTitle(alias);
        long msgId = 0;
        try {
            PushResult result = jpushClient.sendPush(payloadAlias);
            msgId = result.msg_id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgId;
    }

    /**
     * 向指定组的Android客户端发送消息
     * 
     * @param tag组名称
     * @return 消息id
     */
    public long sendPushTag(String tag) {
        PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle(tag);
        long msgId = 0;
        try {
            PushResult result = jpushClient.sendPush(payloadtag);
            msgId = result.msg_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgId;
    }
    
    /**
     * 向registrationId 客户端发送消息
     * 
     * @param registrationIds设备的标识 
     * @return 消息id
     */
    public long pushMsgToRegistrationIds(List<String> registrationIds) {  
        PushResult result = null;  
        try {  
            Platform platform = Platform.all();  
            // 获取发送设备的标识  
            Audience audience = Audience.registrationId(registrationIds);  
            Notification notifcation = Notification.android(message, title, extras);  
            PushPayload pushPayload=PushPayload.newBuilder().setPlatform(platform).setAudience(audience).setNotification(notifcation).build();  
            result = jpushClient.sendPush(pushPayload);  
        } catch (APIConnectionException e) {
            e.printStackTrace();  
        } catch (APIRequestException e) {
            e.printStackTrace();  
        }
        return result.msg_id;
    }  

    /**
     * 下列封装了三种获得消息推送对象（PushPayload）的方法
     * buildPushObject_android_alias_alertWithTitle、
     * buildPushObject_android_tag_alertWithTitle、 buildPushObject_all_all_alert
     */
    public PushPayload buildPushObject_android_alias_alertWithTitle(Set<String> alias) {
        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias)).setNotification(Notification.android(message, title, extras)).build();
    }

    public PushPayload buildPushObject_android_tag_alertWithTitle(String tag) {
        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag(tag)).setNotification(Notification.android(message, title, extras)).build();
    }

    public PushPayload buildPushObject_android_all_alert() {
        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.all()).setNotification(Notification.android(message, title, extras)).build();
    }
    
    public PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(message);
    }
}
