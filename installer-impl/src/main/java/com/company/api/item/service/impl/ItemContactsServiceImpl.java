package com.company.api.item.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.EnumPartyStamp;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.item.service.ItemContactsService;
import com.company.po.item.ItemContacts;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.item.dao.ItemContactsDao;

@Service(ItemContactsService.BEAN_NAME)
public class ItemContactsServiceImpl extends StringIdBaseServiceImpl<ItemContacts> //
        implements ItemContactsService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ItemContactsDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public void saveContacts(List<ItemContacts> contactsList, EnumPartyStamp stamp, String itemId) {
        if (contactsList == null) {
            return;
        }
        sqlDao.execUpdate("delete from ZL_ITEM_CONTACTS where ITEM_ID=? and STAMP=?", itemId, stamp.name());
        for (ItemContacts contacts : contactsList) {
            contacts.setId(null); // IMPORTANT!
            contacts.setItemId(itemId);
            contacts.setStamp(stamp.name());

            this.save(contacts);
        }

    }

    @Override
    public List<ItemContacts> queryByItemAndType(EnumPartyStamp stamp, String itemId) {
        String sql = "select * from ZL_ITEM_CONTACTS where ITEM_ID=? and STAMP=?";

        Object[] pars = new Object[] { itemId, stamp.name() };
        List<ItemContacts> _list = sqlDao.listAll(sql, ItemContacts.class, pars);

        return _list;
    }

    @Override
    public void deleteByItemId(String itemId) {
        sqlDao.execUpdate("delete from ZL_ITEM_CONTACTS where ITEM_ID=?", itemId);
    }

}