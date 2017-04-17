package com.company.repo.auth.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.auth.Auth;
import com.company.repo.auth.dao.AuthDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(AuthDao.BEAN_NAME)
public class AuthDaoImpl extends StringIdBaseDaoImpl<Auth> //
        implements AuthDao {

}
