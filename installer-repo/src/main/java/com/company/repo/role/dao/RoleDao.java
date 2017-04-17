package com.company.repo.role.dao;

import com.company.po.role.Role;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface RoleDao extends StringIdBaseDao<Role> {

    String BEAN_NAME = "roleDao";
}
