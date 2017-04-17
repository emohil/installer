package com.company.api.indent.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.company.dto.indentnode.IndentNodeTreeItem;
import com.company.po.indent.IndentNodeStepItem;
import com.company.api.fw.service.StringIdBaseService;
import com.company.util.Dto;

public interface IndentNodeStepItemService extends StringIdBaseService<IndentNodeStepItem> {

    String BEAN_NAME = "indentNodeStepItemService";


    /**
     * 获取所有的服务节点步骤
     * @return
     */
    List<IndentNodeTreeItem> findTreeItems();

    /**
     * 按服务节点分组 服务步骤项
     * @return
     */
    Map<String, List<IndentNodeTreeItem>> step2TreeItems();

    int finishedItemCounts(String indentNodeStepId);

    void finishTipText(String id);
    
    /**
     * 查找测量类型订单的节点步骤子项
     * @param indentNodeStepId
     * @return
     */
    IndentNodeStepItem findByNodeStepId(String indentNodeStepId);

    Dto dealItem(Map<String, MultipartFile> files, String indentNodeStepItemId);

    void itemFinished(String indentNodeStepItemId);
}