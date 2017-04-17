package com.company.repo.wk.dao;

import com.company.po.wk.WkMgrSlave;
import com.company.repo.fw.dao.BaseDao;

public interface WkMgrSlaveDao extends BaseDao<WkMgrSlave, String> {
    
    String BEAN_NAME = "wkMgrSlaveDao";
}