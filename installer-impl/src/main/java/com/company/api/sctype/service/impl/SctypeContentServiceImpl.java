package com.company.api.sctype.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.sctype.SctypeContent;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.sctype.dao.SctypeContentDao;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(SctypeContentService.BEAN_NAME)
public class SctypeContentServiceImpl extends StringIdBaseServiceImpl<SctypeContent> //
        implements SctypeContentService {

    @Autowired
    private SqlDao sqlDao;

    /** 这里无法使用@Autowired */
    @Resource(name = SctypeContentService.BEAN_NAME)
    private SctypeContentService self;
    
    @Autowired
    private SctypeSortService sctypeSortService;

    @Autowired
    public void setBaseDao(SctypeContentDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public SctypeContent load(String id) {
        SctypeContent content = find(id);

        // 加载服务类别信息
        if (content != null && StringUtil.isNotEmpty(content.getSctypeSortId())) {
            content.setSctypeSort(sctypeSortService.find(content.getSctypeSortId()));
        }

        return content;
    }

    @Override
    public List<SctypeTreeItem> findTreeItems() {
        String sql = new StringBuilder()//
                .append("select ID, CODE1")//
                .append(", DESC1 as NAME1")//
                .append(", SCTYPE_SORT_ID as PID")//
                .append(", UNIT")//
                .append(", BASE_QUOTE")//
                .append(" from ZL_SCTYPE_CONTENT")//
                .append(" order by CODE1")//
                .toString();

        return sqlDao.listAll(sql, SctypeTreeItem.class);

    }

    @Autowired
    public Map<String, List<SctypeTreeItem>> sort2TreeItems() {

        Map<String, List<SctypeTreeItem>> ret = New.hashMap();
        for (SctypeTreeItem item : self.findTreeItems()) {
            List<SctypeTreeItem> items = ret.get(item.getPid());
            if (items == null) {
                items = New.list();
                ret.put(item.getPid(), items);
            }
            items.add(item);
        }

        return ret;
    }

}