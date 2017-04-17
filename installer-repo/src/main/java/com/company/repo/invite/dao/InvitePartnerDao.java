package com.company.repo.invite.dao;

import com.company.po.invite.InvitePartner;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface InvitePartnerDao extends StringIdBaseDao<InvitePartner> {

    String BEAN_NAME = "invitePartnerDao";
}
