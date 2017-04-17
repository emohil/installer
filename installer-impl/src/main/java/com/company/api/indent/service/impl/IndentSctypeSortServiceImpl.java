package com.company.api.indent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.IndentSctypeSortService;
import com.company.po.indent.IndentSort;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentSctypeSortDao;

@Service(IndentSctypeSortService.BEAN_NAME)
public class IndentSctypeSortServiceImpl extends StringIdBaseServiceImpl<IndentSort>//
        implements IndentSctypeSortService {
    
    @Autowired
    public void setBaseDao(IndentSctypeSortDao dao) {
        super.setBaseDao(dao);
    }
    @Autowired
    private SqlDao sqlDao;
    
    @Override
    public void deleteByIndentId(String id) {
        String sql = "delete from ZL_INDENT_SORT where INDENT_ID = ?";
        sqlDao.execUpdate(sql, id);
    }


}