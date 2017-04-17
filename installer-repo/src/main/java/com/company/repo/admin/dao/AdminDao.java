package com.company.repo.admin.dao;

import com.company.po.admin.Admin;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface AdminDao extends StringIdBaseDao<Admin> {
    
    String BEAN_NAME = "adminDao";
}
