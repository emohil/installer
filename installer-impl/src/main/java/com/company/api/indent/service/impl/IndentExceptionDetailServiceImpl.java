package com.company.api.indent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.IndentExceptionDetailService;
import com.company.po.indent.IndentExceptionDetail;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentExceptionDetailDao;

@Service(IndentExceptionDetailService.BEAN_NAME)
public class IndentExceptionDetailServiceImpl extends StringIdBaseServiceImpl<IndentExceptionDetail>implements IndentExceptionDetailService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    public void setBaseDao(IndentExceptionDetailDao dao) {
        super.setBaseDao(dao);
    }

}