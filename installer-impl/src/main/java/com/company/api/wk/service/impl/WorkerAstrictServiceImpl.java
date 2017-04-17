package com.company.api.wk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.wk.service.WorkerAstrictService;
import com.company.po.wk.WorkerAstrict;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.wk.dao.WorkerAstrictDao;

@Service(WorkerAstrictService.BEAN_NAME)
public class WorkerAstrictServiceImpl extends StringIdBaseServiceImpl<WorkerAstrict> implements WorkerAstrictService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(WorkerAstrictDao dao) {
        super.setBaseDao(dao);
    }


}