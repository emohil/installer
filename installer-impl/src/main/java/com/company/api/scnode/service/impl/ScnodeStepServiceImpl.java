package com.company.api.scnode.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.EnumCodes;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.scnode.ScnodeStep;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.scnode.dao.ScnodeStepDao;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(ScnodeStepService.BEAN_NAME)
public class ScnodeStepServiceImpl extends StringIdBaseServiceImpl<ScnodeStep>//
        implements ScnodeStepService {
    
    @Autowired
    private SqlDao sqlDao;
    
    @Resource(name = ScnodeStepService.BEAN_NAME)
    private ScnodeStepService self;
    
    @Autowired
    private ScnodeService scnodeService;
    
    @Autowired
    private ScnodeStepItemService scnodeStepItemService;
    
    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public void setBaseDao(ScnodeStepDao dao) {
        super.setBaseDao(dao);
    }

    
    @Override
    public void delete(String id) {
        super.delete(id);
        
        scnodeStepItemService.deleteByStepId(id);
    }


    @Override
    public ScnodeStep load(String id) {
        ScnodeStep step = self.find(id);

        // 加载服务类别信息
        if (step != null && StringUtil.isNotEmpty(step.getScnodeId())) {
            step.setScnode(scnodeService.find(step.getScnodeId()));
        }

        return step;
    }
    
    @Override
    public List<ScnodeStep> findByScnodeId(String scnodeId) {
        String sql = "select * from ZL_SCNODE_STEP where SCNODE_ID=? order by ORDERS";
        return sqlDao.listAll(sql, ScnodeStep.class, new Object[] {scnodeId});
    }
    
    @Override 
    public void deleteByNodeId(String scnodeId) {
        
        List<ScnodeStep> stepList = findByScnodeId(scnodeId);
        for (ScnodeStep step : stepList) {
            scnodeStepItemService.deleteByStepId(step.getId());
        }
        
        String delSql = "delete from ZL_SCNODE_STEP where SCNODE_ID=?";
        sqlDao.execUpdate(delSql, new Object[] {scnodeId});
    }
    
    
    @Override
    public List<ScnodeTreeItem> findTreeItems() {
        
        String sql = new StringBuilder()//
                .append("select ID") //
                .append(", NAME1 as CODE1") //
                .append(", DESC1 as NAME1") //
                .append(", SCNODE_ID as PID") //
                .append(", STEP_TYPE as TYPE") //
                .append(", SORT_ID") //
                .append(", ORDERS") //
                .append(" from ZL_SCNODE_STEP") //
                .append(" order by ORDERS") //
                .toString();

        List<ScnodeTreeItem> items = sqlDao.listAll(sql, ScnodeTreeItem.class);
        for (ScnodeTreeItem item : items) {
            String typeDisp = sysDictService.text(EnumCodes.SCNODE_STEP_TYPE, item.getType());
            item.setTypeDisp(typeDisp);
        }
        
        return items;
    }

    @Autowired
    public Map<String, List<ScnodeTreeItem>> scnode2TreeItems() {

        Map<String, List<ScnodeTreeItem>> ret = New.hashMap();
        for (ScnodeTreeItem item : self.findTreeItems()) {
            List<ScnodeTreeItem> items = ret.get(item.getPid());
            if (items == null) {
                items = New.list();
                ret.put(item.getPid(), items);
            }
            items.add(item);
        }

        return ret;
    }
}