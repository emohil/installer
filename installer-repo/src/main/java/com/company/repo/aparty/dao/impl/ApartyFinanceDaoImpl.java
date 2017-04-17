package com.company.repo.aparty.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.aparty.ApartyFinance;
import com.company.repo.aparty.dao.ApartyFinanceDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApartyFinanceDao.BEAN_NAME)
public class ApartyFinanceDaoImpl extends StringIdBaseDaoImpl<ApartyFinance> implements ApartyFinanceDao {

}
