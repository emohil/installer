package com.company.repo.role.dao;

import com.company.po.role.RoleAuth;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface RoleAuthDao extends StringIdBaseDao<RoleAuth> {

    String BEAN_NAME = "roleAuthDao";
}
