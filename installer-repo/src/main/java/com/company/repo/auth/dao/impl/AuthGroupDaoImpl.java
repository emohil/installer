package com.company.repo.auth.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.auth.AuthGroup;
import com.company.repo.auth.dao.AuthGroupDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(AuthGroupDao.BEAN_NAME)
public class AuthGroupDaoImpl extends StringIdBaseDaoImpl<AuthGroup> //
        implements AuthGroupDao {

}
