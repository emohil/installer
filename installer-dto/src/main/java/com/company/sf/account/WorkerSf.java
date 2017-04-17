package com.company.sf.account;

import java.util.Date;

import com.company.po.account.Account;
import com.company.po.wk.Worker;

public class WorkerSf extends Worker {

    private static final long serialVersionUID = 1L;

    private Account account;

    private Date comitDateBegin;

    private Date comitDateEnd;

    private Date gotDateBegin;

    private Date gotDateEnd;
    
    private String role;
    
    private String serviceCategory2;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getComitDateBegin() {
        return comitDateBegin;
    }

    public void setComitDateBegin(Date comitDateBegin) {
        this.comitDateBegin = comitDateBegin;
    }

    public Date getComitDateEnd() {
        return comitDateEnd;
    }

    public void setComitDateEnd(Date comitDateEnd) {
        this.comitDateEnd = comitDateEnd;
    }

    public Date getGotDateBegin() {
        return gotDateBegin;
    }

    public void setGotDateBegin(Date gotDateBegin) {
        this.gotDateBegin = gotDateBegin;
    }

    public Date getGotDateEnd() {
        return gotDateEnd;
    }

    public void setGotDateEnd(Date gotDateEnd) {
        this.gotDateEnd = gotDateEnd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getServiceCategory2() {
        return serviceCategory2;
    }

    public void setServiceCategory2(String serviceCategory2) {
        this.serviceCategory2 = serviceCategory2;
    }

}
