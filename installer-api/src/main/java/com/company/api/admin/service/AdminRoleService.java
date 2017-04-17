package com.company.api.admin.service;

import java.util.List;
import java.util.Map;

import com.company.po.admin.Admin;
import com.company.po.admin.AdminRole;
import com.company.api.fw.service.StringIdBaseService;

public interface AdminRoleService extends StringIdBaseService<AdminRole> {

    String BEAN_NAME = "adminRoleService";
    
    void saveAdminRoles(Admin admin, Map<String, Boolean> roles);
    
    List<AdminRole> findRoleByAdminId(String adminId);
    
}
