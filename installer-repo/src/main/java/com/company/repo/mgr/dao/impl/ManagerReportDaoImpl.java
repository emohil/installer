package com.company.repo.mgr.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.mgr.ManagerReport;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.mgr.dao.ManagerReportDao;


@Repository(ManagerReportDao.BEAN_NAME)
public class ManagerReportDaoImpl extends StringIdBaseDaoImpl<ManagerReport> implements ManagerReportDao {

}
