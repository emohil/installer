package com.company.repo.item.dao;

import com.company.po.item.ItemPrice;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ItemPriceDao extends StringIdBaseDao<ItemPrice> {

    String BEAN_NAME = "itemPriceDao";
}
