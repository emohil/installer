package com.company.repo.wk.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.wk.Worker;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.wk.dao.WorkerDao;


@Repository(WorkerDao.BEAN_NAME)
public class WorkerDaoImpl extends StringIdBaseDaoImpl<Worker> implements WorkerDao {

}
