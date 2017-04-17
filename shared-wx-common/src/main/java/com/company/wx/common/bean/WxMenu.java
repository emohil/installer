package com.company.wx.common.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.company.util.json.JacksonHelper;
import com.company.wx.common.api.JsonBean;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 自定义菜单能够帮助公众号丰富界面，让用户更好更快地理解公众号的功能。
 * 
 * 创建格式：
 * 
 * <pre>
 * {
 *  "button":[{
 *      "type":"click",
 *      "name":"今日歌曲",
 *      "key":"V1001_TODAY_MUSIC"
 *  }, {
 *      "name":"菜单",
 *      "sub_button":[{
 *          "type":"view",
 *          "name":"搜索",
 *          "url":"http://www.soso.com/"
 *      }, {
 *          "type":"view",
 *          "name":"视频",
 *          "url":"http://v.qq.com/"
 *      }]
 *   }]
 * }
 * </pre>
 * 
 * 查询已创建的菜单，返回格式：
 * 
 * <pre>
 *    {
 *      "menu" : {"buttons": [...]}
 *    }
 * </pre>
 */
public class WxMenu extends JsonBean<WxMenu> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 一级菜单数组，个数应为1~3个 */
    @JsonProperty("button")
    private List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();

    public List<WxMenuButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<WxMenuButton> buttons) {
        this.buttons = buttons;
    }

    public static WxMenu fromJson(String json) {
        return fromJson(json, WxMenu.class);
    }

    public static WxMenu fromJson(InputStream is) {
        return JacksonHelper.toObject(is, WxMenu.class);
    }

    /**
     * 请注意：
     * 
     * 1、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
     * 2、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
     * 3、创建自定义菜单后，由于微信客户端缓存，需要24小时微信客户端才会展现出来。测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。
     * 
     */
    public static class WxMenuButton extends JsonBean<WxMenuButton> implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 菜单的响应动作类型 */
        private String type;
        /** 菜单标题，不超过16个字节，子菜单不超过40个字节 */
        private String name;
        /** [click等点击类型必须] 菜单KEY值，用于消息接口推送，不超过128字节 */
        private String key;
        /** [view类型必须] 网页链接，用户点击菜单可打开链接，不超过256字节 */
        private String url;
        /** [media_id类型和view_limited类型必须] 调用新增永久素材接口返回的合法media_id */
        @JsonProperty("media_id")
        private String mediaId;
        /** 二级菜单数组，个数应为1~5个 */
        @JsonProperty("sub_button")
        private List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public List<WxMenuButton> getSubButtons() {
            return subButtons;
        }

        public void setSubButtons(List<WxMenuButton> subButtons) {
            this.subButtons = subButtons;
        }
    }
}