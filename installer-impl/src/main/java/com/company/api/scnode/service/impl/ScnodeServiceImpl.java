package com.company.api.scnode.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.scnode.Scnode;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.scnode.dao.ScnodeDao;

@Service(ScnodeService.BEAN_NAME)
public class ScnodeServiceImpl extends StringIdBaseServiceImpl<Scnode>//
        implements ScnodeService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private ScnodeStepService scnodeStepService;
    
    @Autowired
    private ScnodeStepItemService scnodeStepItemService;
    
    @Autowired
    public void setBaseDao(ScnodeDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public void delete(String id) {
        super.delete(id);
        
        scnodeStepService.deleteByNodeId(id);
    }
    
    @Override
    public List<ScnodeTreeItem> findTreeItems() {
        return sqlDao.listAll("select ID, CODE1, NAME1, ORDERS from ZL_SCNODE order by ORDERS", ScnodeTreeItem.class);
    }
    
    @Override
    public List<ScnodeTreeItem> treeItems() {
        
        List<ScnodeTreeItem> nodeList = findTreeItems();
        
        Map<String, List<ScnodeTreeItem>> scnode2Steps = scnodeStepService.scnode2TreeItems();

        Map<String, List<ScnodeTreeItem>> step2Items = scnodeStepItemService.step2TreeItems();

        for (ScnodeTreeItem node : nodeList) {

            List<ScnodeTreeItem> stepList = scnode2Steps.get(node.getId());

            if (stepList == null) {
                continue;
            }
            node.setChildren(stepList);

            for (ScnodeTreeItem step : stepList) {
                List<ScnodeTreeItem> itemList = step2Items.get(step.getId());
                step.setChildren(itemList);
            }
        }
        
        return nodeList;
    }
}