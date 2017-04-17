package com.company.repo.wk.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.wk.WorkerDeposit;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.wk.dao.WorkerDepositDao;

@Repository(WorkerDepositDao.BEAN_NAME)
public class WorkerDepositDaoImpl extends StringIdBaseDaoImpl<WorkerDeposit>//
        implements WorkerDepositDao {

}
