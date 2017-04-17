package com.company.repo.mgr.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.mgr.ManagerTrade;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.mgr.dao.ManagerTradeDao;

@Repository(ManagerTradeDao.BEAN_NAME)
public class ManagerTradeDaoImpl extends StringIdBaseDaoImpl<ManagerTrade>//
        implements ManagerTradeDao {

}
