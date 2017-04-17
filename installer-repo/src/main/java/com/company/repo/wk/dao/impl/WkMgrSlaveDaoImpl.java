package com.company.repo.wk.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.wk.WkMgrSlave;
import com.company.repo.fw.dao.impl.BaseDaoImpl;
import com.company.repo.wk.dao.WkMgrSlaveDao;

@Repository(WkMgrSlaveDao.BEAN_NAME)
public class WkMgrSlaveDaoImpl extends BaseDaoImpl<WkMgrSlave, String>//
        implements WkMgrSlaveDao {

}