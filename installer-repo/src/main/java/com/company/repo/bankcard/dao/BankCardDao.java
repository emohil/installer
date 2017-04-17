package com.company.repo.bankcard.dao;

import com.company.po.bankcard.BankCard;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface BankCardDao extends StringIdBaseDao<BankCard> {

    String BEAN_NAME = "BankCardDao";
}
