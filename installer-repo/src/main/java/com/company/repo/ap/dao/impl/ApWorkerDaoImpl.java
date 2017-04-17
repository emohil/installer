package com.company.repo.ap.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.ap.ApWorker;
import com.company.repo.ap.dao.ApWorkerDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApWorkerDao.BEAN_NAME)
public class ApWorkerDaoImpl extends StringIdBaseDaoImpl<ApWorker>//
        implements ApWorkerDao {

}
