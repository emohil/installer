package com.company.api.item.service;


import java.util.List;

import com.company.api.dict.service.EnumPartyStamp;
import com.company.po.item.ItemContacts;
import com.company.api.fw.service.StringIdBaseService;

public interface ItemContactsService extends StringIdBaseService<ItemContacts> {
    
    String BEAN_NAME = "itemContactsService";
    
    /**
     * 维护联系人信息
     * @param contactsList
     * @param stamp
     * @param apartyId
     */
    void saveContacts(List<ItemContacts> contactsList, EnumPartyStamp stamp, String itemId);

    /**
     * 根据甲方查询联系人信息
     * @param stamp
     * @param apartyId
     * @return
     */
    List<ItemContacts> queryByItemAndType(EnumPartyStamp stamp, String itemId);
    
    void deleteByItemId(String itemId);
}