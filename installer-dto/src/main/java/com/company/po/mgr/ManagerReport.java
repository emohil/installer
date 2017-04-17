package com.company.po.mgr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_MANAGER_REPORT")
public class ManagerReport extends StringIdPo {
    
    private static final long serialVersionUID = 1L;
    
    // 经理人ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;

    // 提报Account账号ID
    @Column(name = "ACCOUNT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String accountId;

    // 提报手机号
    @Column(name = "MOBILE", length = 20, columnDefinition = "VARCHAR(20) DEFAULT ''")
    private String mobile;
    
    // 提报时间
    @Column(name = "REPORT_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
}