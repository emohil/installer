package com.company.repo.item.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.item.ItemPrice;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.item.dao.ItemPriceDao;

@Repository(ItemPriceDao.BEAN_NAME)
public class ItemPriceDaoImpl extends StringIdBaseDaoImpl<ItemPrice> implements ItemPriceDao {

}
