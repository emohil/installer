package com.company.api.indent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.IndentFreightService;
import com.company.po.indent.IndentFreight;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentFreightDao;

@Service(IndentFreightService.BEAN_NAME)
public class IndentFreightServiceImpl extends StringIdBaseServiceImpl<IndentFreight> //
        implements IndentFreightService {

    @Autowired
    public void setBaseDao(IndentFreightDao dao) {
        super.setBaseDao(dao);
    }
    
    @Autowired
    private SqlDao sqlDao;
    
    @Override
    public void deleteByIndentId(String id) {
        String sql = "delete from ZL_INDENT_FREIGHT where INDENT_ID = ?";
        sqlDao.execUpdate(sql, id);
    }
}