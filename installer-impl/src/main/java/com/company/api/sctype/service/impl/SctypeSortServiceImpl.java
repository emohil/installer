package com.company.api.sctype.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.district.service.AreaService;
import com.company.api.district.service.CityService;
import com.company.api.fw.DictCodes;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.dto.sctype.SctypeTreeItem;
import com.company.po.district.Area;
import com.company.po.district.City;
import com.company.po.item.Item;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeContent;
import com.company.po.sctype.SctypeSort;
import com.company.dto.Filter;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.sctype.dao.SctypeSortDao;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(SctypeSortService.BEAN_NAME)
public class SctypeSortServiceImpl extends StringIdBaseServiceImpl<SctypeSort> //
        implements SctypeSortService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    public void setBaseDao(SctypeSortDao dao) {
        super.setBaseDao(dao);
    }

    @Autowired
    SctypeContentService sctypeContentService;

    @Autowired
    SctypeService sctypeService;

    @Override
    public void save(SctypeSort entity) {

        String sortCode = entity.getCode1();

        entity.setName1(sysDictService.text(DictCodes.SERVICE_SORT, sortCode));

        super.save(entity);
    }

    @Override
    public SctypeSort load(String id) {
        SctypeSort sort = find(id);

        // 加载服务类别信息
        if (sort != null && StringUtil.isNotEmpty(sort.getSctypeId())) {
            sort.setSctype(sctypeService.find(sort.getSctypeId()));
        }

        return sort;
    }

    @Override
    public List<SctypeTreeItem> findTreeItems() {
        String sql = "select ID, CODE1, NAME1, SCTYPE_ID as PID from ZL_SCTYPE_SORT order by CODE1";

        return sqlDao.listAll(sql, SctypeTreeItem.class);
    }

    @Autowired
    public Map<String, List<SctypeTreeItem>> type2TreeItems() {

        Map<String, List<SctypeTreeItem>> ret = New.hashMap();
        for (SctypeTreeItem item : findTreeItems()) {
            List<SctypeTreeItem> items = ret.get(item.getPid());
            if (items == null) {
                items = New.list();
                ret.put(item.getPid(), items);
            }
            items.add(item);
        }

        return ret;
    }

    @Override
    public List<Sctype> typeList(List<String> sortIds) {

        if (sortIds.size() == 0) {
            return null;
        }

        List<SctypeSort> serveSortList = this.findList(Filter.in("id", sortIds));
        Set<String> serveTypeIds = New.hashSet();
        for (SctypeSort sctypeSort : serveSortList) {
            List<SctypeContent> contentList = sctypeContentService
                    .findList(Filter.eq("sctypeSortId", sctypeSort.getId()));
            sctypeSort.setContentList(contentList);
            serveTypeIds.add(sctypeSort.getSctypeId());
        }
        List<Sctype> serveTypeList = sctypeService.findList(Filter.in("id", serveTypeIds));
        for (Sctype sctype : serveTypeList) {
            List<SctypeSort> serveSortortList = New.list();
            for (SctypeSort sctypeSort : serveSortList) {
                if (sctype.getId().equals(sctypeSort.getSctypeId())) {
                    serveSortortList.add(sctypeSort);
                }
            }
            sctype.setSortList(serveSortortList);
        }
        return serveTypeList;
    }

    @Override
    public List<Sctype> calculatePrice(List<Sctype> typeList, Item item) {
        
        BigDecimal hundred = BigDecimal.valueOf(100);

        City city = cityService.findCityByCode1(item.getAreaCity());
        BigDecimal cityAddRate = BigDecimal.ONE;
        if (city != null) {
            cityAddRate = cityAddRate.add(city.getAddRate().divide(hundred));
        }

        Area area = areaService.findAreaByCode1(item.getAreaDist());
        BigDecimal areaAddRate = BigDecimal.ONE;
        if (area != null) {
            areaAddRate = areaAddRate.add(area.getAddRate().divide(hundred));
        }

        for (Sctype sctype : typeList) {
            for (SctypeSort sort : sctype.getSortList()) {
                for (SctypeContent content : sort.getContentList()) {
                    BigDecimal basePrice = content.getBaseQuote();
                    if (basePrice == null) {
                        basePrice = BigDecimal.ZERO;
                    }
                    BigDecimal price = basePrice.multiply(cityAddRate).multiply(areaAddRate)//
                            .setScale(2, RoundingMode.HALF_UP);
                    
                    content.setNumerationPrice(price);
                    content.setActualPrice(price);
                }
            }
        }

        return typeList;
    }
    
    
    public static void main(String[] args) {
        BigDecimal b = BigDecimal.valueOf(101.25);
        
        System.out.println( b.divide(BigDecimal.valueOf(100)));
    }
}