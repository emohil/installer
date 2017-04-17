package com.company.wx.mp.util.xml;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import com.company.util.New;
import com.company.wx.common.util.xml.XStreamInitializer;
import com.company.wx.mp.bean.WxMpXmlMessage;
import com.company.wx.mp.bean.WxMpXmlOutImageMessage;
import com.company.wx.mp.bean.WxMpXmlOutMessage;
import com.company.wx.mp.bean.WxMpXmlOutMusicMessage;
import com.company.wx.mp.bean.WxMpXmlOutNewsMessage;
import com.company.wx.mp.bean.WxMpXmlOutTextMessage;
import com.company.wx.mp.bean.WxMpXmlOutVideoMessage;
import com.company.wx.mp.bean.WxMpXmlOutVoiceMessage;
import com.thoughtworks.xstream.XStream;

public class XStreamTransformer {

    protected static final Map<Class<? extends Serializable>, XStream> CLASS_2_XSTREAM_INSTANCE = configXStreamInstance();

    /**
     * xml -> pojo
     *
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T fromXml(Class<T> clazz, String xml) {
        @SuppressWarnings("unchecked")
        T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(xml);
        return object;
    }

    public static <T> T fromXml(Class<T> clazz, InputStream is) {
        @SuppressWarnings("unchecked")
        T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(is);
        return object;
    }

    /**
     * pojo -> xml
     *
     * @param clazz
     * @param object
     * @return
     */
    public static <T extends Serializable> String toXml(Class<T> clazz, T object) {
        return CLASS_2_XSTREAM_INSTANCE.get(clazz).toXML(object);
    }

    private static Map<Class<? extends Serializable>, XStream> configXStreamInstance() {
        Map<Class<? extends Serializable>, XStream> map = New.hashMap();
        map.put(WxMpXmlMessage.class, config_WxMpXmlMessage());
        map.put(WxMpXmlOutMusicMessage.class, config_WxMpXmlOutMusicMessage());
        map.put(WxMpXmlOutNewsMessage.class, config_WxMpXmlOutNewsMessage());
        map.put(WxMpXmlOutTextMessage.class, config_WxMpXmlOutTextMessage());
        map.put(WxMpXmlOutImageMessage.class, config_WxMpXmlOutImageMessage());
        map.put(WxMpXmlOutVideoMessage.class, config_WxMpXmlOutVideoMessage());
        map.put(WxMpXmlOutVoiceMessage.class, config_WxMpXmlOutVoiceMessage());
        return map;
    }

    private static XStream config_WxMpXmlMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlMessage.class);
        xstream.processAnnotations(WxMpXmlMessage.ScanCodeInfo.class);
        xstream.processAnnotations(WxMpXmlMessage.SendPicsInfo.class);
        xstream.processAnnotations(WxMpXmlMessage.SendPicsInfo.Item.class);
        xstream.processAnnotations(WxMpXmlMessage.SendLocationInfo.class);

        xstream.aliasField("MsgID", WxMpXmlMessage.class, "msgId");
        return xstream;
    }

    private static XStream config_WxMpXmlOutImageMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlOutMessage.class);
        xstream.processAnnotations(WxMpXmlOutImageMessage.class);
        return xstream;
    }

    private static XStream config_WxMpXmlOutNewsMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlOutMessage.class);
        xstream.processAnnotations(WxMpXmlOutNewsMessage.class);
        xstream.processAnnotations(WxMpXmlOutNewsMessage.Item.class);
        return xstream;
    }

    private static XStream config_WxMpXmlOutMusicMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlOutMessage.class);
        xstream.processAnnotations(WxMpXmlOutMusicMessage.class);
        xstream.processAnnotations(WxMpXmlOutMusicMessage.Music.class);
        return xstream;
    }

    private static XStream config_WxMpXmlOutTextMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlOutMessage.class);
        xstream.processAnnotations(WxMpXmlOutTextMessage.class);
        return xstream;
    }

    private static XStream config_WxMpXmlOutVideoMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlOutMessage.class);
        xstream.processAnnotations(WxMpXmlOutVideoMessage.class);
        xstream.processAnnotations(WxMpXmlOutVideoMessage.Video.class);
        return xstream;
    }

    private static XStream config_WxMpXmlOutVoiceMessage() {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(WxMpXmlOutMessage.class);
        xstream.processAnnotations(WxMpXmlOutVoiceMessage.class);
        return xstream;
    }

}
