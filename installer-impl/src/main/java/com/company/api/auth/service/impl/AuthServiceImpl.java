package com.company.api.auth.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.auth.service.AuthService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.po.auth.Auth;
import com.company.dto.Order;
import com.company.repo.auth.dao.AuthDao;
import com.company.util.New;

@Service(AuthService.BEAN_NAME)
public class AuthServiceImpl extends StringIdBaseServiceImpl<Auth> //
        implements AuthService {

    
    @Autowired
    public void setBaseDao(AuthDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public Map<String, List<Auth>> groupAuth() {

        Map<String, List<Auth>> ret = New.hashMap();

        List<Auth> authList = super.findList(Order.asc("orders"));

        for (Auth auth : authList) {
            String authGroupId = auth.getAuthGroupId();

            List<Auth> temp = ret.get(authGroupId);

            if (temp == null) {
                temp = New.list();
                ret.put(authGroupId, temp);
            }
            temp.add(auth);
        }
        return ret;
    }
    
    @Override
    public List<String> authCodeList() {
        
        List<String> authCodeList = New.list();
        
        List<Auth> list = findAll();
        for (Auth auth : list) {
            authCodeList.add(auth.getCode1());
        }
        return authCodeList; 
    }
}
