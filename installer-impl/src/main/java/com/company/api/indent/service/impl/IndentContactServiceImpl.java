package com.company.api.indent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.IndentContactService;
import com.company.po.indent.IndentContact;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentContactDao;

@Service(IndentContactService.BEAN_NAME)
public class IndentContactServiceImpl extends StringIdBaseServiceImpl<IndentContact>//
        implements IndentContactService {

    @Autowired
    public void setBaseDao(IndentContactDao dao) {
        super.setBaseDao(dao);
    }

    @Autowired
    private SqlDao sqlDao;
    
    @Override
    public void deleteByIndentId(String id) {
        String sql = "delete from ZL_INDENT_CONTACT where INDENT_ID = ?";
        sqlDao.execUpdate(sql, id);
    }
    
    @Override
    public IndentContact findByIndentId(String indentId) {
        String sql = "select * from ZL_INDENT_CONTACT where INDENT_ID=?";
        return sqlDao.unique(sql, IndentContact.class, indentId);
    }

}