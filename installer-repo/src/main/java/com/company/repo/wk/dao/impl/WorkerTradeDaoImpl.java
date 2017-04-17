package com.company.repo.wk.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.wk.WorkerTrade;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.wk.dao.WorkerTradeDao;

@Repository(WorkerTradeDao.BEAN_NAME)
public class WorkerTradeDaoImpl extends StringIdBaseDaoImpl<WorkerTrade>//
        implements WorkerTradeDao {

}
