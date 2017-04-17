package com.company.repo.mgr.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.mgr.ManagerDeposit;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.mgr.dao.ManagerDepositDao;

@Repository(ManagerDepositDao.BEAN_NAME)
public class ManagerDepositDaoImpl extends StringIdBaseDaoImpl<ManagerDeposit>//
        implements ManagerDepositDao {

}
