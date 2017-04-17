package com.company.repo.aparty.dao;

import com.company.po.aparty.ApartyTrade;
import com.company.repo.fw.dao.BaseDao;

public interface ApartyTradeDao extends BaseDao<ApartyTrade, String> {

    String BEAN_NAME = "apartyTradeDao";
}
