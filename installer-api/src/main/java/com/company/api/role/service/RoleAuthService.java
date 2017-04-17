package com.company.api.role.service;

import java.util.List;
import java.util.Map;

import com.company.po.role.Role;
import com.company.po.role.RoleAuth;
import com.company.api.fw.service.StringIdBaseService;

public interface RoleAuthService extends StringIdBaseService<RoleAuth> {
    
    String BEAN_NAME = "roleAuthService";
    
    void saveRoleAuths(Role role, Map<String, Boolean> auths);
    
    List<RoleAuth> findAuthByRoleId(String roleId);
    
    List<RoleAuth> findAuthByAdmin(String adminId);
}
