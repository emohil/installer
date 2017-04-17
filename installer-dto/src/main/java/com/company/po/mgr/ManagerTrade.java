package com.company.po.mgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.account.Trade;

/**
 * 经理人交易流水表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_MANAGER_TRADE")
public class ManagerTrade extends Trade {

    private static final long serialVersionUID = 1L;

    // 经理人ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;
    
    @Transient
    private String managerName;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

}