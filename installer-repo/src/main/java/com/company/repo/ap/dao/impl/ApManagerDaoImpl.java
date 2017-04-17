package com.company.repo.ap.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.ap.ApManager;
import com.company.repo.ap.dao.ApManagerDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApManagerDao.BEAN_NAME)
public class ApManagerDaoImpl extends StringIdBaseDaoImpl<ApManager>//
        implements ApManagerDao {

}
