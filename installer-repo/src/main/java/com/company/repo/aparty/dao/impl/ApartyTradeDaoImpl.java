package com.company.repo.aparty.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.aparty.ApartyTrade;
import com.company.repo.aparty.dao.ApartyTradeDao;
import com.company.repo.fw.dao.impl.BaseDaoImpl;

@Repository(ApartyTradeDao.BEAN_NAME)
public class ApartyTradeDaoImpl extends BaseDaoImpl<ApartyTrade, String> implements ApartyTradeDao {

}
