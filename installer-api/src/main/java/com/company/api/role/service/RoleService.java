package com.company.api.role.service;

import java.util.List;

import com.company.po.role.Role;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;

public interface RoleService extends StringIdBaseService<Role> {

    String BEAN_NAME = "roleService";
    
    List<Role> list(Role sf, int start, int limit, List<Order> orders);

    int count(Role sf);
    
    Role load(String id);
}
