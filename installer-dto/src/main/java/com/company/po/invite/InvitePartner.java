package com.company.po.invite;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_INVITE_PARTNER")
public class InvitePartner extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 邀请人ID
    @Column(name = "ACCOUNT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String accountId;
    
    // 被邀请手机号
    @Column(name = "MOBILE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String mobile;
    
    // 邀请时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INVITE_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date inviteDate;

    public String getAccountId() {
        return accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public Date getInviteDate() {
        return inviteDate;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setInviteDate(Date inviteDate) {
        this.inviteDate = inviteDate;
    }
}