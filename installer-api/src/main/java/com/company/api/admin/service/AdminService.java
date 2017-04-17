package com.company.api.admin.service;

import java.util.List;
import java.util.Map;

import com.company.po.admin.Admin;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface AdminService extends StringIdBaseService<Admin> {

    String BEAN_NAME = "adminService";
    
    List<Admin> list(Admin sf, int start, int limit, List<Order> orders);

    int count(Admin sf);
    
    Map<String, Object> validateUser(Admin data);
    
    Admin findByUser(String user);
    
    void disableAdmin(String id);
    
    void enableAdmin(String id);
    
    Admin load(String id);
}
