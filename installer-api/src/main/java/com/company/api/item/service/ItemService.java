package com.company.api.item.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.company.po.fs.FileIndex;
import com.company.po.item.Item;
import com.company.sf.item.ItemSf;
import com.company.api.fw.service.PairTypehead;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;
import com.company.dto.ValueTextPair;
import com.company.util.Dto;

public interface ItemService extends StringIdBaseService<Item>, PairTypehead {

    String BEAN_NAME = "itemService";

    List<Item> list(ItemSf sf, int start, int limit, List<Order> orders);

    int count(ItemSf sf);

    List<ValueTextPair> doTypeahead(Dto params);

    Dto deleteItem(String id);

    /**
     * 保存合同文件
     * 
     * @param id
     * @param files
     */
    void saveContracts(String id, MultipartFile[] files);
    
    
    List<FileIndex> loadContractFiles(String id);

}
