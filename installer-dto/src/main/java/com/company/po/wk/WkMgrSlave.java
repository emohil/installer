package com.company.po.wk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.company.po.base.StringIdPo;

/**
 * 工人、经理人从属记录表。
 * 每次工人申请加入经理人、经理人同意或者忽略的时候维护该表（同一条记录）
 * @author kouwl
 *
 */
@Entity
@Table(name = "ZL_WK_MGR_SLAVE")
public class WkMgrSlave extends StringIdPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;

    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;

    @Column(name = "STATUS", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String status;

    @Column(name = "DESC1", length = 200, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String desc1;
    
    // 申请
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPLY_DATE", columnDefinition = "DATETIME DEFAULT 19000101")
    private Date applyDate;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    
}
