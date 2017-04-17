package com.company.api.indent.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.Constants;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.indent.service.IndentContentService;
import com.company.api.indent.service.IndentSctypeSortService;
import com.company.api.indent.service.IndentService;
import com.company.api.item.service.ItemPriceService;
import com.company.api.item.service.ItemService;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContent;
import com.company.po.indent.IndentSort;
import com.company.po.item.ItemPrice;
import com.company.po.sctype.SctypeContent;
import com.company.po.sctype.SctypeSort;
import com.company.dto.Filter;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentContentDao;
import com.company.util.BooleanUtil;
import com.company.util.Dto;
import com.company.util.New;

@Service(IndentContentService.BEAN_NAME)
public class IndentContentServiceImpl extends StringIdBaseServiceImpl<IndentContent>implements IndentContentService {

    @Autowired
    public void setBaseDao(IndentContentDao dao) {
        super.setBaseDao(dao);
    }
    
    @Autowired
    ItemPriceService itemPriceService;
    
    @Autowired
    IndentService indentService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    IndentSctypeSortService indentSctypeSortService;
    
    @Autowired
    SctypeSortService sctypeSortService;
    
    @Autowired
    SctypeContentService sctypeContentService ;
    
    @Autowired
    private SqlDao sqlDao;
    
    @Override
    public void deleteByIndentId(String id) {
        String sql = "delete from ZL_INDENT_CONTENT where INDENT_ID = ?";
        sqlDao.execUpdate(sql, id);
    }

    @Override
    public Indent saveIndentContent(Indent indent) {
        //保存 订单报价
        Dto indentPriceDto = indent.getIndentPriceDto();

        if (indentPriceDto == null) {
            return null;
        }

        Iterator<String> iterator = indentPriceDto.keySet().iterator();
        List<String> keyList = New.list();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key.endsWith("_code1")) {
                keyList.add(key);
            }
        }

        BigDecimal sum = new BigDecimal(0);
        BigDecimal worker_fee = new BigDecimal(0);
        BigDecimal manager_fee = new BigDecimal(0);
        Dto sorts = new Dto();
        for (String key : keyList) {
            String prefix = key.substring(0, key.lastIndexOf("_"));
            IndentContent indentContent = new IndentContent();
            String code1 = (String) indentPriceDto.get(prefix + "_code1");
            indentContent.setCode1(code1);
            IndentContent existIndentPrice = super.findOne(Filter.eq("indentId", indent.getId()), Filter.eq("code1", code1));

            if (BooleanUtil.getBoolean(indentPriceDto.get(prefix + "_checked"))) {
                indentContent.setCounts(Double.parseDouble(indentPriceDto.get(prefix + "_counts").toString()));
                ItemPrice itemPrice = itemPriceService.findOne(Filter.eq("itemId", indent.getItemId()), Filter.eq("serveContentId", indentContent.getCode1()));
                //甲方支付价格
                BigDecimal price = itemPrice.getActualPrice() == null ? new BigDecimal(0):itemPrice.getActualPrice();
                BigDecimal counts = new BigDecimal(indentContent.getCounts());
                indentContent.setIndentId(indent.getId());
                indentContent.setName1(itemPrice.getName1());
                indentContent.setUnit(itemPrice.getUnit());
                //工人接单价格
                indentContent.setPrice((price.multiply(itemPrice.getWorkerRate().divide(new BigDecimal(100)))).setScale(Constants.SCALE, RoundingMode.HALF_UP));
                indentContent.setTotal((price.multiply(itemPrice.getWorkerRate().divide(new BigDecimal(100))).multiply(counts)).setScale(Constants.SCALE, RoundingMode.HALF_UP));
                SctypeContent sctypeContent = sctypeContentService.find(code1);
                SctypeSort sctypeSort = sctypeSortService.find(sctypeContent.getSctypeSortId());
                IndentSort indentSort = new IndentSort();
                indentSort.setCode1(sctypeSort.getCode1());
                indentSort.setIndentId(indent.getId());
                indentSort.setName1(itemPrice.getName1());
                indentSort.setSctypeSortId(sctypeContent.getSctypeSortId());
                indentSort.setServeType(sctypeSort.getSctypeId());
                indentSctypeSortService.save(indentSort);
                sorts.put(sctypeSort.getCode1(), sctypeSort.getCode1());
                indent.setServeType(sctypeSort.getSctypeId());

                sum = sum.add(price.multiply(counts));
                worker_fee = worker_fee.add(indentContent.getTotal());
                manager_fee = manager_fee.add((price.multiply(itemPrice.getManagerRate().divide(new BigDecimal(100))).multiply(counts)).setScale(Constants.SCALE, RoundingMode.HALF_UP));
                if (existIndentPrice != null) {
                    indentContent.setId(existIndentPrice.getId());
                    super.update(indentContent);
                } else {
                    super.save(indentContent);
                }
            } else {
                if (existIndentPrice != null) {
                    super.delete(existIndentPrice.getId());
                }
            }
        }
        //TODO 维护订单列表表
        indent.setIndentSortDto(sorts);
        indent.setSortCount(sorts.size());
        indent.setSum(sum.setScale(Constants.SCALE, RoundingMode.HALF_UP));
        indent.setWorkerFee(worker_fee.setScale(Constants.SCALE, RoundingMode.HALF_UP));
        indent.setManagerFee(manager_fee.setScale(Constants.SCALE, RoundingMode.HALF_UP));
        BigDecimal actualPay = worker_fee.add(manager_fee);
        indent.setActualPay(actualPay);
        indent.setProfit(sum.subtract(actualPay));
        return indent;
        
    }

}