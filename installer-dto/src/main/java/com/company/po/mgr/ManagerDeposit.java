package com.company.po.mgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.company.po.account.Deposit;

/**
 * 经理人保证金表
 * 
 * @author lihome
 *
 */
@Entity
@Table(name = "ZL_MANAGER_DEPOSIT")
public class ManagerDeposit extends Deposit {

    private static final long serialVersionUID = 1L;

    // 经理人ID
    @Column(name = "MANAGER_ID", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String managerId;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
