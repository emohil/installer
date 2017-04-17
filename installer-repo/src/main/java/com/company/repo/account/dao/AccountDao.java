package com.company.repo.account.dao;

import com.company.po.account.Account;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface AccountDao extends StringIdBaseDao<Account> {

    String BEAN_NAME = "accountDao";
}
