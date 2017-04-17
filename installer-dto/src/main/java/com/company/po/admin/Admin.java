package com.company.po.admin;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.TransformField;
import com.company.context.ContextBean;
import com.company.po.base.StringIdPo;
import com.company.util.New;

@Entity
@Table(name = "ZL_ADMIN", uniqueConstraints = @UniqueConstraint(columnNames = "USER") )
public class Admin extends StringIdPo implements ContextBean<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "USER", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''", nullable = false, updatable = false, unique = true)
    private String user;

    @Column(name = "PWD", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''", nullable = false)
    private String pwd;

    @Column(name = "EMAIL", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''", nullable = false)
    private String email;

    @Column(name = "NAME1", length = 255, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String name1;
    
    @TransformField(groupCode = EnumCodes.ADMIN_STATUS)
    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;
    
    @Transient
    private String statusDisp;
    
    @Column(name = "IS_SYSTEM", columnDefinition = "INT DEFAULT 0")
    private Integer isSystem;
    
    @Transient
    private Map<String, Boolean> roles = New.hashMap();
    
    @Transient
    private List<String> authIdList = New.list();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDisp() {
        return statusDisp;
    }

    public void setStatusDisp(String statusDisp) {
        this.statusDisp = statusDisp;
    }
   
    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public Map<String, Boolean> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Boolean> roles) {
        this.roles = roles;
    }
    
    public List<String> getAuthIdList() {
        return authIdList;
    }

    public void setAuthIdList(List<String> authIdList) {
        this.authIdList = authIdList;
    }

    @Override
    public String getKeyId() {

        return getId();
    }
}