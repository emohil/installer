package com.company.api.scnode.service;

import java.util.List;
import java.util.Map;

import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.scnode.ScnodeStep;
import com.company.api.fw.service.StringIdBaseService;

public interface ScnodeStepService extends StringIdBaseService<ScnodeStep> {

    String BEAN_NAME = "scnodeStepService";

    /**
     * 根据ID加载数据
     * 
     * @param id
     * @return
     */
    ScnodeStep load(String id);

    /**
     * 根据节点ID查找所有步骤
     * 
     * @param scnodeId
     * @return
     */
    List<ScnodeStep> findByScnodeId(String scnodeId);

    /**
     * 根据节点ID上传旗下的子项
     * 
     * @param scnodeId
     */
    void deleteByNodeId(String scnodeId);

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
    Map<String, List<ScnodeTreeItem>> scnode2TreeItems();
}