package com.company.repo.ap.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.ap.ApIndent;
import com.company.repo.ap.dao.ApIndentDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApIndentDao.BEAN_NAME)
public class ApIntdentDaoImpl extends StringIdBaseDaoImpl<ApIndent>//
        implements ApIndentDao {

}
