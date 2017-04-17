package com.company.po.role;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.base.StringIdPo;
import com.company.util.New;

@Entity
@Table(name = "ZL_ROLE")
public class Role extends StringIdPo {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "CODE1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String code1;
    
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    @Column(name = "IS_SYSTEM", columnDefinition = "INT DEFAULT 0")
    private Integer isSystem;
    
    @Transient
    private Map<String, Boolean> auth = New.hashMap();

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public Map<String, Boolean> getAuth() {
        return auth;
    }

    public void setAuth(Map<String, Boolean> auth) {
        this.auth = auth;
    }
}