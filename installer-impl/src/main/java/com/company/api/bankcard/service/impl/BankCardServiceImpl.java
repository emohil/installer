package com.company.api.bankcard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.bankcard.service.BankCardService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.po.bankcard.BankCard;
import com.company.repo.bankcard.dao.BankCardDao;
import com.company.repo.fw.dao.SqlDao;

@Service(BankCardService.BEAN_NAME)
public class BankCardServiceImpl extends StringIdBaseServiceImpl<BankCard>implements BankCardService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    public void setBaseDao(BankCardDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public BankCard findByOwnerId(String OwnerId) {
        String sql = "select * from ZL_BANK_CARD where OWNER_ID=?";
        return sqlDao.unique(sql, BankCard.class, OwnerId);
    }
    
    @Override
    public void delByOwnerId(String ownerId) {
        String sql = "delete from ZL_BANK_CARD where OWNER_ID=?";
        sqlDao.execUpdate(sql, ownerId);
    }
}