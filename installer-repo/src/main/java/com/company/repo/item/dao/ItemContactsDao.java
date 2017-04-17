package com.company.repo.item.dao;

import com.company.po.item.ItemContacts;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ItemContactsDao extends StringIdBaseDao<ItemContacts> {

    String BEAN_NAME = "itemContactsDao";
}
