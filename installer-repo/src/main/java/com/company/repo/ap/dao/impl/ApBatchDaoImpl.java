package com.company.repo.ap.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.ap.ApBatch;
import com.company.repo.ap.dao.ApBatchDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApBatchDao.BEAN_NAME)
public class ApBatchDaoImpl extends StringIdBaseDaoImpl<ApBatch>//
        implements ApBatchDao {

}
