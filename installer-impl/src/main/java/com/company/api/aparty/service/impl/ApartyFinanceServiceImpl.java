package com.company.api.aparty.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.aparty.service.ApartyFinanceService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.po.aparty.ApartyFinance;
import com.company.repo.aparty.dao.ApartyFinanceDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.StringUtil;

@Service(ApartyFinanceService.BEAN_NAME)
public class ApartyFinanceServiceImpl extends StringIdBaseServiceImpl<ApartyFinance>implements ApartyFinanceService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ApartyFinanceDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public void saveOrUpdate(ApartyFinance entity, String apartyId) {
        if (entity == null) {
            return;
        }

        entity.setApartyId(apartyId);
        if (StringUtil.isEmpty(entity.getId())) {
            this.save(entity);
        } else {
            this.update(entity);
        }
    }
    
    @Override
    public ApartyFinance findByApartyId(String apartyId) {
        String sql = "select * from ZL_APARTY_FINANCE where APARTY_ID=?";
        
        return sqlDao.unique(sql, ApartyFinance.class, new Object[] {apartyId});
    }

    @Override
    public void deleteByApartyId(String apartyId) {
        sqlDao.execUpdate("delete from ZL_APARTY_FINANCE where APARTY_ID=?", apartyId);
        
    }
}