package com.company.repo.admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.admin.AdminRole;
import com.company.repo.admin.dao.AdminRoleDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(AdminRoleDaoImpl.BEAN_NAME)
public class AdminRoleDaoImpl extends StringIdBaseDaoImpl<AdminRole> //
        implements AdminRoleDao {

}
