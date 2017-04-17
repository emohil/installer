package com.company.po.wk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_WORKER_ASTRICT")
public class WorkerAstrict extends StringIdPo {

    private static final long serialVersionUID = 1L;

    // 被限制工人ID
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;
    
    // 被限制工人账户ID
    @Column(name = "W_ACCOUNT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String wAccountId;
    
    // 被限制时间
    @Column(name = "ASTRICT_TIME", length = 11, columnDefinition = "INT DEFAULT 0")
    private Integer astrictTime;

    // 操作经理人ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;
    
    // 操作经理人账户ID
    @Column(name = "M_ACCOUNT_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String mAccountId;
    
    // 限制缘由
    @Column(name = "COMMENT", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String comment;

    public WorkerAstrict() {
        this.comment = DEF_STRING;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getwAccountId() {
        return wAccountId;
    }

    public Integer getAstrictTime() {
        return astrictTime;
    }

    public String getManagerId() {
        return managerId;
    }

    public String getmAccountId() {
        return mAccountId;
    }

    public String getComment() {
        return comment;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public void setwAccountId(String wAccountId) {
        this.wAccountId = wAccountId;
    }

    public void setAstrictTime(Integer astrictTime) {
        this.astrictTime = astrictTime;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public void setmAccountId(String mAccountId) {
        this.mAccountId = mAccountId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}