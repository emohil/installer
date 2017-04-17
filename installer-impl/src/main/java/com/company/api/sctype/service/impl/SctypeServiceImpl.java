package com.company.api.sctype.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.sctype.Sctype;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.sctype.dao.SctypeDao;

@Service(SctypeService.BEAN_NAME)
public class SctypeServiceImpl extends StringIdBaseServiceImpl<Sctype>//
        implements SctypeService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private SctypeSortService sortService;

    @Autowired
    private SctypeContentService contentService;

    @Autowired
    public void setBaseDao(SctypeDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<SctypeTreeItem> findTreeItems() {
        return sqlDao.listAll("select ID, CODE1, NAME1 from ZL_SCTYPE order by CODE1", SctypeTreeItem.class);
    }

    @Override
    public List<SctypeTreeItem> treeItems() {

        List<SctypeTreeItem> typeList = findTreeItems();

        Map<String, List<SctypeTreeItem>> type2Sorts = sortService.type2TreeItems();

        Map<String, List<SctypeTreeItem>> sort2Contents = contentService.sort2TreeItems();

        for (SctypeTreeItem typeItem : typeList) {

            List<SctypeTreeItem> sortList = type2Sorts.get(typeItem.getId());

            if (sortList == null) {
                continue;
            }
            typeItem.setChildren(sortList);

            for (SctypeTreeItem sortItem : sortList) {
                List<SctypeTreeItem> contentList = sort2Contents.get(sortItem.getId());
                sortItem.setChildren(contentList);
            }
        }
        return typeList;
    }
}