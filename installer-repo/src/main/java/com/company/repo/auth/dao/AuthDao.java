package com.company.repo.auth.dao;

import com.company.po.auth.Auth;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface AuthDao extends StringIdBaseDao<Auth> {

    String BEAN_NAME = "authDao";
}
