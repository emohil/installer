package com.company.api.invite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.invite.service.InvitePartnerService;
import com.company.po.invite.InvitePartner;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.invite.dao.InvitePartnerDao;

@Service(InvitePartnerService.BEAN_NAME)
public class InvitePartnerServiceImpl extends StringIdBaseServiceImpl<InvitePartner> implements InvitePartnerService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(InvitePartnerDao dao) {
        super.setBaseDao(dao);
    }


}