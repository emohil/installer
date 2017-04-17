package com.company.api.aparty.service;

import com.company.po.aparty.ApartyFinance;
import com.company.api.fw.service.StringIdBaseService;

public interface ApartyFinanceService extends StringIdBaseService<ApartyFinance> {
    
    String BEAN_NAME = "apartyFinanceService";
    
    void saveOrUpdate(ApartyFinance entity, String apartyId);
    
    ApartyFinance findByApartyId(String apartyId);
    
    void deleteByApartyId(String apartyId);
}