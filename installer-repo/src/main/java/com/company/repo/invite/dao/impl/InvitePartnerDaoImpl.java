package com.company.repo.invite.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.invite.InvitePartner;
import com.company.repo.fw.dao.impl.StringIdBaseDaoImpl;
import com.company.repo.invite.dao.InvitePartnerDao;


@Repository(InvitePartnerDao.BEAN_NAME)
public class InvitePartnerDaoImpl extends StringIdBaseDaoImpl<InvitePartner> implements InvitePartnerDao {

}
