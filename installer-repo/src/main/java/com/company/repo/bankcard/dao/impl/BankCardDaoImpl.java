package com.company.repo.bankcard.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.bankcard.BankCard;
import com.company.repo.bankcard.dao.BankCardDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;



@Repository(BankCardDao.BEAN_NAME)
public class BankCardDaoImpl extends StringIdBaseDaoImpl<BankCard> implements BankCardDao {

}
