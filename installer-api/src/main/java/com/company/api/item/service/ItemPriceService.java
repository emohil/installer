package com.company.api.item.service;



import java.util.List;

import com.company.po.item.ItemPrice;
import com.company.api.fw.service.PairTypehead;
import com.company.api.fw.service.StringIdBaseService;


public interface ItemPriceService extends StringIdBaseService<ItemPrice>, PairTypehead {
    
    String BEAN_NAME = "itemPriceService";
    
    void saveItemPrice(List<ItemPrice> priceList, String id);
    
    void deleteByItemId(String itemId);
    
}
