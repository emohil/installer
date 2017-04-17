package com.company.po.auth;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.base.OrderPo;

@Entity
@Table(name = "ZL_AUTH_GROUP")
public class AuthGroup extends OrderPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    @Transient
    private List<Auth> authList;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }
}