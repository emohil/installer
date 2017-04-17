package com.company.api.auth.service;

import java.util.List;

import com.company.po.auth.AuthGroup;
import com.company.api.fw.service.StringIdBaseService;

public interface AuthGroupService extends StringIdBaseService<AuthGroup> {

    String BEAN_NAME = "authGroupService";

    /**
     * 查询全部权限组，且每个权限组中包含权限项列表
     * 
     * @return
     */
    List<AuthGroup> groupListWithAuths();
}
