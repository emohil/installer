package com.company.api.sctype.service;

import java.util.List;
import java.util.Map;

import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.item.Item;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeSort;
import com.company.api.fw.service.StringIdBaseService;

public interface SctypeSortService extends StringIdBaseService<SctypeSort> {
    
    String BEAN_NAME = "serveSortService";
    
    
    /**
     * 根据ID加载数据
     * @param id
     * @return
     */
    SctypeSort load(String id);
    
    /**
     * 获取所有的服务类别项
     * @return
     */
    List<SctypeTreeItem> findTreeItems();
    
    /**
     * 按类型分组 服务类别项
     * @return
     */
    Map<String, List<SctypeTreeItem>> type2TreeItems();
    
    /**
     * 
     * 根据服务类别获取服务类型list(itemAdd/itemEdit)
     * @return
     */
    List<Sctype> typeList(List<String> sortIds);
    
    /**
     * 计算项目中每一项的报价
     * @param typeList
     * @param item
     * @return
     */
    List<Sctype> calculatePrice(List<Sctype> typeList, Item item);
}