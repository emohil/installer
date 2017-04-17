package com.company.repo.admin.dao;

import com.company.po.admin.AdminRole;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface AdminRoleDao extends StringIdBaseDao<AdminRole> {

    String BEAN_NAME = "adminRoleDao";
}
