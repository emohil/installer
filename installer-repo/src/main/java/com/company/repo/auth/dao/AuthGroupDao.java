package com.company.repo.auth.dao;

import com.company.po.auth.AuthGroup;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface AuthGroupDao extends StringIdBaseDao<AuthGroup> {

    String BEAN_NAME = "authGroupDao";
}
