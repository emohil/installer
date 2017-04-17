package com.company.api.bankcard.service;

import com.company.po.bankcard.BankCard;
import com.company.api.fw.service.StringIdBaseService;

public interface BankCardService extends StringIdBaseService<BankCard> {
    
    String BEAN_NAME = "workerBankCardService";
    
    BankCard findByOwnerId(String ownerId);
    
    void delByOwnerId(String ownerId);
    
}