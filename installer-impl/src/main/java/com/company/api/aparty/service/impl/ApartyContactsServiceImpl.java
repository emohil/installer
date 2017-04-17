package com.company.api.aparty.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.aparty.service.ApartyContactsService;
import com.company.api.dict.service.EnumPartyStamp;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.po.aparty.ApartyContacts;
import com.company.repo.aparty.dao.ApartyContactsDao;
import com.company.repo.fw.dao.SqlDao;

@Service(ApartyContactsService.BEAN_NAME)
public class ApartyContactsServiceImpl extends StringIdBaseServiceImpl<ApartyContacts>implements ApartyContactsService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ApartyContactsDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<ApartyContacts> queryByApartyAndType(EnumPartyStamp stamp, String apartyId) {
        String sql = "select * from ZL_APARTY_CONTACTS where APARTY_ID=? and STAMP=?";

        Object[] pars = new Object[] { apartyId, stamp.name() };
        List<ApartyContacts> _list = sqlDao.listAll(sql, ApartyContacts.class, pars);

        return _list;
    }

    @Override
    public void saveContacts(List<ApartyContacts> contactsList, EnumPartyStamp stamp, String apartyId) {
        if (contactsList == null) {
            return;
        }
        sqlDao.execUpdate("delete from ZL_APARTY_CONTACTS where APARTY_ID=? and STAMP=?", apartyId, stamp.name());
        for (ApartyContacts contacts : contactsList) {
            contacts.setId(null); //IMPORTANT!
            contacts.setApartyId(apartyId);
            contacts.setStamp(stamp.name());

            this.save(contacts);
        }
    }

    @Override
    public void deleteByApartyId(String apartyId) {
        sqlDao.execUpdate("delete from ZL_APARTY_CONTACTS where APARTY_ID=?", apartyId);
    }
}