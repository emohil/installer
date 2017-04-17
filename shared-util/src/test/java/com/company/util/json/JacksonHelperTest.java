package com.company.util.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.company.util.json.JacksonHelper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JacksonHelperTest {

    @Test
    public void testToObjectStringClassOfT() {
        String json1 = "{\"access_token\" : \"abcd\", \"expireIn\": 7200}";

        Bean1 bean1 = JacksonHelper.toObject(json1, Bean1.class);
        
        assertEquals("abcd", bean1.getAccessToken());
    }

}

class Bean1 {
    @JsonProperty(value = "access_token")
    private String accessToken;
    private int expireIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    @Override
    public String toString() {
        return JacksonHelper.toJson(this);
    }

}