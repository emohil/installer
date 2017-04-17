package com.company.po.wk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.account.Deposit;

/**
 * 工人保证金表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_WORKER_DEPOSIT")
public class WorkerDeposit extends Deposit {

    private static final long serialVersionUID = 1L;

    // 工人ID
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
