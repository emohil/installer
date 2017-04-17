package com.company.po.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PoListener<ID extends Serializable> {
    
    
    @PrePersist
    public void prePersist(BasePo<ID> po) {
        po.setCreateDate(new Date());
        po.setModifyDate(new Date());
    }

    @PreUpdate
    public void preUpdate(BasePo<ID> po) {
        po.setModifyDate(new Date());
    }
}
