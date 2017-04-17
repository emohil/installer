package com.company.api.mgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.mgr.service.ManagerReportService;
import com.company.po.mgr.ManagerReport;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.mgr.dao.ManagerReportDao;

@Service(ManagerReportService.BEAN_NAME)
public class ManagerReportServiceImpl extends StringIdBaseServiceImpl<ManagerReport> implements ManagerReportService{

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    public void setBaseDao(ManagerReportDao dao) {
        super.setBaseDao(dao);
    }
    
}