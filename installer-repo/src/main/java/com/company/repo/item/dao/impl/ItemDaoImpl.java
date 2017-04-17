package com.company.repo.item.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.item.Item;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.item.dao.ItemDao;

@Repository(ItemDao.BEAN_NAME)
public class ItemDaoImpl extends StringIdBaseDaoImpl<Item> implements ItemDao {

}
