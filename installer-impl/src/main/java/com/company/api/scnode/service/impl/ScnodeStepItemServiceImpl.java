package com.company.api.scnode.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.EnumCodes;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.dto.scnode.ScnodeTreeItem;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.po.scnode.ScnodeStepItem;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.scnode.dao.ScnodeStepItemDao;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(ScnodeStepItemService.BEAN_NAME)
public class ScnodeStepItemServiceImpl extends StringIdBaseServiceImpl<ScnodeStepItem>//
        implements ScnodeStepItemService {

    @Autowired
    private SqlDao sqlDao;

    @Resource(name = ScnodeStepItemService.BEAN_NAME)
    private ScnodeStepItemService self;

    @Autowired
    private ScnodeStepService scnodeStepService;

    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    public void setBaseDao(ScnodeStepItemDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public ScnodeStepItem load(String id) {
        ScnodeStepItem item = find(id);

        // 加载服务类别信息
        if (item != null && StringUtil.isNotEmpty(item.getScnodeStepId())) {
            item.setScnodeStep(scnodeStepService.find(item.getScnodeStepId()));
        }
        return item;
    }

    @Override
    public void deleteByStepId(String stepId) {
        String delSql = "delete from ZL_SCNODE_STEP_ITEM where SCNODE_STEP_ID=?";
        sqlDao.execUpdate(delSql, new Object[] { stepId });
    }

    @Override
    public List<ScnodeTreeItem> findTreeItems() {

        String sql = new StringBuilder()//
                .append("select ID") //
                .append(", NAME1 as CODE1") //
                .append(", DESC1 as NAME1") //
                .append(", ITEM_TYPE as TYPE")//
                .append(", ORDERS, SCNODE_STEP_ID as PID") //
                .append(" from ZL_SCNODE_STEP_ITEM") //
                .append(" order by ORDERS") //
                .toString();

        List<ScnodeTreeItem> items = sqlDao.listAll(sql, ScnodeTreeItem.class);
        for (ScnodeTreeItem item : items) {
            String typeDisp = sysDictService.text(EnumCodes.SCNODE_CONFIRM_TYPE, item.getType());
            item.setTypeDisp(typeDisp);
        }
        return items;
    }

    @Autowired
    public Map<String, List<ScnodeTreeItem>> step2TreeItems() {

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

    @Override
    public void saveSketchFiles(String id, MultipartFile[] sketchFiles) {
        if (sketchFiles == null || sketchFiles.length == 0) {
            return;
        }

        for (int i = 0; i < sketchFiles.length; i++) {
            UnFileIndex ufi = new UnFileIndex(sketchFiles[i], id);

            fileManagerService.save(ufi);
        }
    }
    
    
    @Override
    public List<FileIndex> loadSketchFiles(String id) {
        
        List<FileIndex> fiList = fileManagerService.findByBelongTo(id);
        for (FileIndex fi : fiList) {
            fi.setFileUrl(fileManagerService.getFileUrlByFilepath(fi.getFilePath()));
        }
        return fiList;
    }
}