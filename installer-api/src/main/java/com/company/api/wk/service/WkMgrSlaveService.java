package com.company.api.wk.service;

import java.util.Date;
import java.util.List;

import com.company.po.wk.WkMgrSlave;
import com.company.api.fw.service.BaseService;

public interface WkMgrSlaveService extends BaseService<WkMgrSlave, String> {
    
    String BEAN_NAME = "wkMgrSlaveService";
    
    WkMgrSlave findByMIdAndWId(String managerId, String workerId, String status);
    
    List<WkMgrSlave> findByWkId(String workerId);

    List<WkMgrSlave> findOverTimeApply(Date date);
}