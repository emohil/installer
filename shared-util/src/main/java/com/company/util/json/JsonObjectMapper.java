package com.company.util.json;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.company.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

public class JsonObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    public JsonObjectMapper() {
        super();
        
        this.addHandler(new DeserializationProblemHandler() {
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp,
                    JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName)
                            throws IOException, JsonProcessingException {
                // 忽略无法匹配的属性
                return true;
            }
        });
        
        // 允许单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段和值都加引号
        // this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        // this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        // this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS,
        // true);
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
                    throws IOException, JsonProcessingException {
                jg.writeString("");
            }
        });
        // 设置日期格式
        this.setDateFormat(DateUtil.DATETIME_DEFAULT_PATTERN);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        this.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public String writeValueAsString(boolean isFormat, Object obj) throws JsonProcessingException {
        if (isFormat) {
            return super.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } else {
            return super.writeValueAsString(obj);
        }
    }

    public void setDateFormat(String pattern) {
        super.setDateFormat(new SimpleDateFormat(pattern));
    }

}
