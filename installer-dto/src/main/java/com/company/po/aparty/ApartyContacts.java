package com.company.po.aparty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_APARTY_CONTACTS")
public class ApartyContacts extends StringIdPo {

    /**
     * 甲/乙方联系人信息
     */
    private static final long serialVersionUID = 1L;
    
    @JsonProperty//甲方ID
    @Column(name = "APARTY_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''", updatable = false)
    private String apartyId;
    
    @JsonProperty//联系人姓名
    @Column(name = "NAME1", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String name1;
    
    @JsonProperty//职务
    @Column(name = "DUTY", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String duty;
    
    @JsonProperty//电话
    @Column(name = "MOBILE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String mobile;
    
    @JsonProperty//邮箱
    @Column(name = "EMAIL", length = 30, columnDefinition = "VARCHAR(30) DEFAULT ''")
    private String email;
    
    @JsonProperty//线条
    @Column(name = "LINELLAE", length = 20, columnDefinition = "VARCHAR(30) DEFAULT ''")
    private String linellae;
    
    @JsonProperty//QQ
    @Column(name = "QQ", length = 20, columnDefinition = "VARCHAR(30) DEFAULT ''")
    private String qq;
    
    @JsonProperty//座机
    @Column(name = "TELEPHONE", length = 20, columnDefinition = "VARCHAR(30) DEFAULT ''")
    private String telePhone;
    
    @JsonProperty//传真
    @Column(name = "FAX", length = 20, columnDefinition = "VARCHAR(30) DEFAULT ''")
    private String fax;
    
    @JsonProperty//甲/乙方
    @Column(name = "STAMP", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''", updatable = false)
    private String stamp;
    
    public String getApartyId() {
		return apartyId;
	}

	public void setApartyId(String apartyId) {
		this.apartyId = apartyId;
	}

	public String getName1() {
    	return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getLinellae() {
        return linellae;
    }

    public void setLinellae(String linellae) {
        this.linellae = linellae;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
}