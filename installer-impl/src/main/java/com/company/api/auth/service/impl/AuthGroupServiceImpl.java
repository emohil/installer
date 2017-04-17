package com.company.api.auth.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.auth.service.AuthGroupService;
import com.company.api.auth.service.AuthService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.po.auth.Auth;
import com.company.po.auth.AuthGroup;
import com.company.dto.Order;
import com.company.repo.auth.dao.AuthGroupDao;

@Service(AuthGroupService.BEAN_NAME)
public class AuthGroupServiceImpl extends StringIdBaseServiceImpl<AuthGroup> //
        implements AuthGroupService {
    
    @Autowired
    private AuthService authService;

    @Autowired
    public void setBaseDao(AuthGroupDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public List<AuthGroup> groupListWithAuths() {
        List<AuthGroup> ret = super.findList(Order.asc("orders"));
        
        Map<String, List<Auth>> group2AuthList = authService.groupAuth();
        
        for (AuthGroup group : ret) {
            String id = group.getId();
            
            group.setAuthList(group2AuthList.get(id));
        }
        return ret;
    }
}
