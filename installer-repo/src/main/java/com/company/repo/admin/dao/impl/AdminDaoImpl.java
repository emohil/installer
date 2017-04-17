package com.company.repo.admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.admin.Admin;
import com.company.repo.admin.dao.AdminDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(AdminDao.BEAN_NAME)
public class AdminDaoImpl extends StringIdBaseDaoImpl<Admin>implements AdminDao {

}
