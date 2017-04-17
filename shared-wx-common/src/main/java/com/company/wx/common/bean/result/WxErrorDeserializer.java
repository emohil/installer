package com.company.wx.common.bean.result;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * <pre>
 * {@link WxError} 采用自定义的 {@link JsonDeserializer} 
 * 主要由于所有的Json串先强制反序列化为 {@link WxError}
 * </pre>
 * @author lihome
 *
 */
public class WxErrorDeserializer extends JsonDeserializer<WxError> {

    @Override
    public WxError deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        WxError bean = new WxError();

        JsonNode node = jp.getCodec().readTree(jp);

        if (node.get("errcode") != null) {
            bean.setErrcode(node.get("errcode").asInt());
        }
        if (node.get("errmsg") != null) {
            bean.setErrmsg(node.get("errmsg").asText());
        }

        return bean;
    }
}