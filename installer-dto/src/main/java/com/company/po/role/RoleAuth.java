package com.company.po.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_ROLE_AUTH")
public class RoleAuth extends StringIdPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "ROLE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String roleId;
    
    @Column(name = "AUTH_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String authId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }
}