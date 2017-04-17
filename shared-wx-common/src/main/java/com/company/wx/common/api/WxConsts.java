package com.company.wx.common.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信相关常量
 * 
 * @author lihome
 *
 */
public abstract class WxConsts {

    // /////////////////////
    // 微信推送过来的消息的类型，和发送给微信xml格式消息的消息类型
    // /////////////////////
    public static final String XML_MSG_TEXT = "text";
    public static final String XML_MSG_IMAGE = "image";
    public static final String XML_MSG_VOICE = "voice";
    public static final String XML_MSG_VIDEO = "video";
    public static final String XML_MSG_NEWS = "news";
    public static final String XML_MSG_MUSIC = "music";
    public static final String XML_MSG_LOCATION = "location";
    public static final String XML_MSG_LINK = "link";
    public static final String XML_MSG_EVENT = "event";
    public static final String XML_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";

    // /////////////////////
    // 主动发送消息的消息类型
    // /////////////////////
    public static final String CUSTOM_MSG_TEXT = "text";
    public static final String CUSTOM_MSG_IMAGE = "image";
    public static final String CUSTOM_MSG_VOICE = "voice";
    public static final String CUSTOM_MSG_VIDEO = "video";
    public static final String CUSTOM_MSG_MUSIC = "music";
    public static final String CUSTOM_MSG_NEWS = "news";
    public static final String CUSTOM_MSG_FILE = "file";
    public static final String CUSTOM_MSG_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";

    // /////////////////////
    // 群发消息的消息类型
    // http://mp.weixin.qq.com/wiki/14/0c53fac3bdec3906aaa36987b91d64ea.html
    // /////////////////////
    public static final String MASS_MSG_NEWS = "mpnews";
    public static final String MASS_MSG_TEXT = "text";
    public static final String MASS_MSG_VOICE = "voice";
    public static final String MASS_MSG_IMAGE = "image";
    public static final String MASS_MSG_VIDEO = "mpvideo";
    public static final String MASS_MSG_WXCARD = "wxcard";

    // /////////////////////
    // 群发消息后微信端推送给服务器的反馈消息
    // /////////////////////
    /** 发送成功 */
    public static final String MASS_ST_SUCCESS = "send success";
    /** 发送失败 */
    public static final String MASS_ST_FAIL = "send fail";
    /** 涉嫌广告 */
    public static final String MASS_ST_ERR10001 = "err(10001)";
    /** 涉嫌政治 */
    public static final String MASS_ST_ERR20001 = "err(20001)";
    /** 涉嫌社会 */
    public static final String MASS_ST_ERR20004 = "err(20004)";
    /** 涉嫌色情 */
    public static final String MASS_ST_ERR20002 = "err(20002)";
    /** 涉嫌违法犯罪 */
    public static final String MASS_ST_ERR20006 = "err(20006)";
    /** 涉嫌欺诈 */
    public static final String MASS_ST_ERR20008 = "err(20008)";
    /** 涉嫌版权 */
    public static final String MASS_ST_ERR20013 = "err(20013)";
    /** 涉嫌互推_互相宣传 */
    public static final String MASS_ST_ERR22000 = "err(22000)";
    /** 涉嫌其他 */
    public static final String MASS_ST_ERR21000 = "err(21000)";

    /**
     * 群发反馈消息代码所对应的文字描述
     */
    public static final Map<String, String> MASS_ST_2_DESC = new HashMap<String, String>();
    static {
        MASS_ST_2_DESC.put(MASS_ST_SUCCESS, "发送成功");
        MASS_ST_2_DESC.put(MASS_ST_FAIL, "发送失败");
        MASS_ST_2_DESC.put(MASS_ST_ERR10001, "涉嫌广告");
        MASS_ST_2_DESC.put(MASS_ST_ERR20001, "涉嫌政治");
        MASS_ST_2_DESC.put(MASS_ST_ERR20004, "涉嫌社会");
        MASS_ST_2_DESC.put(MASS_ST_ERR20002, "涉嫌色情");
        MASS_ST_2_DESC.put(MASS_ST_ERR20006, "涉嫌违法犯罪");
        MASS_ST_2_DESC.put(MASS_ST_ERR20008, "涉嫌欺诈");
        MASS_ST_2_DESC.put(MASS_ST_ERR20013, "涉嫌版权");
        MASS_ST_2_DESC.put(MASS_ST_ERR22000, "涉嫌互推_互相宣传");
        MASS_ST_2_DESC.put(MASS_ST_ERR21000, "涉嫌其他");
    }

    // /////////////////////
    // 微信端推送过来的事件类型
    // /////////////////////
    public static final String EVT_SUBSCRIBE = "subscribe";
    public static final String EVT_UNSUBSCRIBE = "unsubscribe";
    public static final String EVT_SCAN = "SCAN";
    public static final String EVT_LOCATION = "LOCATION";
    public static final String EVT_CLICK = "CLICK";
    public static final String EVT_VIEW = "VIEW";
    public static final String EVT_MASS_SEND_JOB_FINISH = "MASSSENDJOBFINISH";
    public static final String EVT_SCANCODE_PUSH = "scancode_push";
    public static final String EVT_SCANCODE_WAITMSG = "scancode_waitmsg";
    public static final String EVT_PIC_SYSPHOTO = "pic_sysphoto";
    public static final String EVT_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
    public static final String EVT_PIC_WEIXIN = "pic_weixin";
    public static final String EVT_LOCATION_SELECT = "location_select";
    public static final String EVT_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
    public static final String EVT_ENTER_AGENT = "enter_agent";

    // /////////////////////
    // 上传多媒体文件的类型
    // /////////////////////
    public static final String MEDIA_IMAGE = "image";
    public static final String MEDIA_VOICE = "voice";
    public static final String MEDIA_VIDEO = "video";
    public static final String MEDIA_THUMB = "thumb";
    public static final String MEDIA_FILE = "file";

    // /////////////////////
    // 文件类型
    // /////////////////////
    public static final String FILE_JPG = "jpeg";
    public static final String FILE_MP3 = "mp3";
    public static final String FILE_AMR = "amr";
    public static final String FILE_MP4 = "mp4";

    // /////////////////////
    // 自定义菜单的按钮类型
    // 参考链接：http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html
    // /////////////////////
    /** 点击推事件 */
    public static final String BTN_CLICK = "click";
    /** 跳转URL */
    public static final String BTN_VIEW = "view";
    /** 扫码推事件 */
    public static final String BTN_SCANCODE_PUSH = "scancode_push";
    /** 扫码推事件且弹出“消息接收中”提示框 */
    public static final String BTN_SCANCODE_WAITMSG = "scancode_waitmsg";
    /** 弹出系统拍照发图 */
    public static final String BTN_PIC_SYSPHOTO = "pic_sysphoto";
    /** 弹出拍照或者相册发图 */
    public static final String BTN_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
    /** 弹出微信相册发图器 */
    public static final String BTN_PIC_WEIXIN = "pic_weixin";
    /** 弹出地理位置选择器 */
    public static final String BTN_LOCATION_SELECT = "location_select";
    /** 下发消息（除文本消息） */
    public static final String BTN_MEDIA_ID = "media_id";
    /** 跳转图文消息URL */
    public static final String BTN_VIEW_LIMITED = "view_limited";

    // /////////////////////
    // oauth2网页授权的scope
    // ！！！注意：测试公众账号必须在用户关注的情况下才能调用此接口， 正式账号无此限制！！！
    // /////////////////////
    /** 不弹出授权页面，直接跳转，只能获取用户openid */
    public static final String OAUTH2_SCOPE_BASE = "snsapi_base";
    /** 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息 */
    public static final String OAUTH2_SCOPE_USERINFO = "snsapi_userinfo";

    // /////////////////////
    // 永久素材类型
    // /////////////////////
    public static final String MATERIAL_NEWS = "news";
    public static final String MATERIAL_VOICE = "voice";
    public static final String MATERIAL_IMAGE = "image";
    public static final String MATERIAL_VIDEO = "video";
}