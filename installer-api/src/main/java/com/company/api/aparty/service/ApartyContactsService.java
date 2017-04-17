package com.company.api.aparty.service;


import java.util.List;

import com.company.api.dict.service.EnumPartyStamp;
import com.company.po.aparty.ApartyContacts;
import com.company.api.fw.service.StringIdBaseService;

public interface ApartyContactsService extends StringIdBaseService<ApartyContacts> {
    
    String BEAN_NAME = "apartyContactsService";
    
    /**
     * 维护联系人信息
     * @param contactsList
     * @param stamp
     * @param apartyId
     */
    void saveContacts(List<ApartyContacts> contactsList, EnumPartyStamp stamp, String apartyId);

    /**
     * 根据甲方查询联系人信息
     * @param stamp
     * @param apartyId
     * @return
     */
    List<ApartyContacts> queryByApartyAndType(EnumPartyStamp stamp, String apartyId);
    
    void deleteByApartyId(String apartyId);
    
}