package com.company.repo.aparty.dao;

import com.company.po.aparty.ApartyContacts;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface ApartyContactsDao extends StringIdBaseDao<ApartyContacts> {

    String BEAN_NAME = "apartyContactsDao";
    
}
