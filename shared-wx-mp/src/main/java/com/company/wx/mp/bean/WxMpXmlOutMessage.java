package com.company.wx.mp.bean;

import java.io.Serializable;

import com.company.wx.common.util.xml.XStreamCDataConverter;
import com.company.wx.mp.api.WxMpConfigStorage;
import com.company.wx.mp.bean.outxmlbuilder.ImageBuilder;
import com.company.wx.mp.bean.outxmlbuilder.MusicBuilder;
import com.company.wx.mp.bean.outxmlbuilder.NewsBuilder;
import com.company.wx.mp.bean.outxmlbuilder.TextBuilder;
import com.company.wx.mp.bean.outxmlbuilder.TransferCustomerServiceBuilder;
import com.company.wx.mp.bean.outxmlbuilder.VideoBuilder;
import com.company.wx.mp.bean.outxmlbuilder.VoiceBuilder;
import com.company.wx.mp.util.crypto.WxMpCryptUtil;
import com.company.wx.mp.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public abstract class WxMpXmlOutMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String fromUserName;

    @XStreamAlias("CreateTime")
    protected Long createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String toXml() {
        @SuppressWarnings("unchecked")
        Class<WxMpXmlOutMessage> clazz = (Class<WxMpXmlOutMessage>) this.getClass();
        
        return XStreamTransformer.toXml(clazz, this);
    }

    /**
     * 转换成加密的xml格式
     * 
     * @return
     */
    public String toEncryptedXml(WxMpConfigStorage wxMpConfigStorage) {
        String plainXml = toXml();
        WxMpCryptUtil pc = new WxMpCryptUtil(wxMpConfigStorage);
        return pc.encrypt(plainXml);
    }

    /**
     * 获得文本消息builder
     * 
     * @return
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder
     * 
     * @return
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder
     * 
     * @return
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder
     * 
     * @return
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得音乐消息builder
     * 
     * @return
     */
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    /**
     * 获得图文消息builder
     * 
     * @return
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    /**
     * 获得客服消息builder
     *
     * @return
     */
    public static TransferCustomerServiceBuilder TRANSFER_CUSTOMER_SERVICE() {
        return new TransferCustomerServiceBuilder();
    }
}
