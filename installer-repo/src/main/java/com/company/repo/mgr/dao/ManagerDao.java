package com.company.repo.mgr.dao;

import com.company.po.mgr.Manager;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ManagerDao extends StringIdBaseDao<Manager> {

    String BEAN_NAME = "managerDao";
}
