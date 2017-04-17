package com.company.repo.account.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.account.Account;
import com.company.repo.account.dao.AccountDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;


@Repository(AccountDao.BEAN_NAME)
public class AccountDaoImpl extends StringIdBaseDaoImpl<Account> implements AccountDao {

}
