package com.company.repo.role.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.role.RoleAuth;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.role.dao.RoleAuthDao;

@Repository(RoleAuthDao.BEAN_NAME)
public class RoleAuthDaoImpl extends StringIdBaseDaoImpl<RoleAuth> //
        implements RoleAuthDao {

}
