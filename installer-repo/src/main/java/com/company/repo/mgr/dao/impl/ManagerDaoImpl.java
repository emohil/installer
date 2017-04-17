package com.company.repo.mgr.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.mgr.Manager;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.mgr.dao.ManagerDao;


@Repository(ManagerDao.BEAN_NAME)
public class ManagerDaoImpl extends StringIdBaseDaoImpl<Manager> implements ManagerDao {

}
