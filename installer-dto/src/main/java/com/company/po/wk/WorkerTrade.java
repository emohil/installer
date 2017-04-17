package com.company.po.wk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.account.Trade;

/**
 * 工人交易流水表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_WORKER_TRADE")
public class WorkerTrade extends Trade {

    private static final long serialVersionUID = 1L;

    // 工人ID
    @Column(name = "WORKER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String workerId;
    
    @Transient
    private String workerName;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

}