package com.company.repo.aparty.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.aparty.ApartyContacts;
import com.company.repo.aparty.dao.ApartyContactsDao;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;

@Repository(ApartyContactsDao.BEAN_NAME)
public class ApartyContactsDaoImpl extends StringIdBaseDaoImpl<ApartyContacts> implements ApartyContactsDao {


}
