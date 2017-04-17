package com.company.api.auth.service;

import java.util.List;
import java.util.Map;

import com.company.po.auth.Auth;
import com.company.api.fw.service.StringIdBaseService;

public interface AuthService extends StringIdBaseService<Auth> {

    String BEAN_NAME = "authService";

    /**
     * 按权限组 分组权限项
     * 
     * @return
     */
    Map<String, List<Auth>> groupAuth();
    
    List<String> authCodeList();
}
