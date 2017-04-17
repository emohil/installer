package com.company.repo.item.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.item.ItemContacts;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.item.dao.ItemContactsDao;

@Repository(ItemContactsDao.BEAN_NAME)
public class ItemContactsDaoImpl extends StringIdBaseDaoImpl<ItemContacts> implements ItemContactsDao {

}
