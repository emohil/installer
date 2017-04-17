package com.company.repo.role.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.role.Role;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.role.dao.RoleDao;

@Repository(RoleDao.BEAN_NAME)
public class RoleDaoImpl extends StringIdBaseDaoImpl<Role> //
        implements RoleDao {

}
