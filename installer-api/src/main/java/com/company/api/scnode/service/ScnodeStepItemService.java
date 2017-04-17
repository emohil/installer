package com.company.api.scnode.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.fs.FileIndex;
import com.company.po.scnode.ScnodeStepItem;
import com.company.api.fw.service.StringIdBaseService;

public interface ScnodeStepItemService extends StringIdBaseService<ScnodeStepItem> {

    String BEAN_NAME = "scnodeStepItemService";

    /**
     * 根据ID加载数据
     * 
     * @param id
     * @return
     */
    ScnodeStepItem load(String id);

    /**
     * 根据步骤ID删除 旗下的所有子项
     * 
     * @param stepId
     */
    void deleteByStepId(String stepId);

    /**
     * 获取所有的服务节点步骤
     * 
     * @return
     */
    List<ScnodeTreeItem> findTreeItems();

    /**
     * 按服务节点分组 服务步骤项
     * 
     * @return
     */
    Map<String, List<ScnodeTreeItem>> step2TreeItems();

    /**
     * 保存图片
     * @param id
     * @param sketchFiles
     */
    void saveSketchFiles(String id, MultipartFile[] sketchFiles);
    
    /**
     * 加载图片
     * @param id
     * @return
     */
    List<FileIndex> loadSketchFiles(String id);
}