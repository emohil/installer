package com.company.repo.item.dao;

import com.company.po.item.Item;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ItemDao extends StringIdBaseDao<Item> {

    String BEAN_NAME = "itemDao";
}
