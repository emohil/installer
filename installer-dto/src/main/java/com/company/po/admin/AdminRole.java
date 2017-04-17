package com.company.po.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_ADMIN_ROLE")
public class AdminRole extends StringIdPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "ADMIN_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String adminId;
    
    @Column(name = "ROLE_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String roleId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}