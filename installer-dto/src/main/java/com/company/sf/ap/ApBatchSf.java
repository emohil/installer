package com.company.sf.ap;

import java.util.Date;

import com.company.po.ap.ApBatch;


public class ApBatchSf extends ApBatch {
    
    private static final long serialVersionUID = 1L;
    
    private Date batchDateBegin;
    
    private Date batchDateEnd;

    public Date getBatchDateBegin() {
        return batchDateBegin;
    }

    public void setBatchDateBegin(Date batchDateBegin) {
        this.batchDateBegin = batchDateBegin;
    }

    public Date getBatchDateEnd() {
        return batchDateEnd;
    }

    public void setBatchDateEnd(Date batchDateEnd) {
        this.batchDateEnd = batchDateEnd;
    }
}
