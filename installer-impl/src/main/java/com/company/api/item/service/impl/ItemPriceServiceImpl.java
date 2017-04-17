package com.company.api.item.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.item.service.ItemPriceService;
import com.company.po.item.ItemPrice;
import com.company.dto.ValueTextPair;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.item.dao.ItemPriceDao;
import com.company.util.Dto;

@Service(ItemPriceService.BEAN_NAME)
public class ItemPriceServiceImpl extends StringIdBaseServiceImpl<ItemPrice> //
        implements ItemPriceService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ItemPriceDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<ValueTextPair> doTypeahead(Dto params) {
        return null;
    }

    @Override
    public void saveItemPrice(List<ItemPrice> priceList, String itemId) {
        if (priceList == null) {
            return;
        }

        sqlDao.execUpdate("delete from ZL_ITEM_PRICE where ITEM_ID=?", new Object[] {itemId});
        for (ItemPrice itemPrice : priceList) {

            BigDecimal workerRate = itemPrice.getWorkerRate();
            BigDecimal managerRate = itemPrice.getManagerRate();

            BigDecimal profitRate = new BigDecimal(100).subtract(workerRate).subtract(managerRate);
            itemPrice.setItemId(itemId);
            itemPrice.setWorkerRate(workerRate);
            itemPrice.setManagerRate(managerRate);
            itemPrice.setProfitRate(profitRate);
            this.save(itemPrice);

        }
    }

    @Override
    public void deleteByItemId(String itemId) {
        sqlDao.execUpdate("delete from ZL_ITEM_PRICE where ITEM_ID=?", itemId);
    }

}