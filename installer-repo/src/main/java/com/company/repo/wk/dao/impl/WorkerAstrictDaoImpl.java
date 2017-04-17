package com.company.repo.wk.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.wk.WorkerAstrict;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.wk.dao.WorkerAstrictDao;


@Repository(WorkerAstrictDao.BEAN_NAME)
public class WorkerAstrictDaoImpl extends StringIdBaseDaoImpl<WorkerAstrict> implements WorkerAstrictDao {

}
