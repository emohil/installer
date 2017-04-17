package com.company.api.item.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.aparty.service.ApartyService;
import com.company.api.dict.service.EnumPartyStamp;
import com.company.api.district.service.AreaService;
import com.company.api.district.service.CityService;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.service.Typeahead;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentService;
import com.company.api.item.service.ItemContactsService;
import com.company.api.item.service.ItemPriceService;
import com.company.api.item.service.ItemService;
import com.company.po.aparty.Aparty;
import com.company.po.district.Area;
import com.company.po.district.City;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.po.indent.Indent;
import com.company.po.item.Item;
import com.company.po.item.ItemPrice;
import com.company.sf.indent.IndentSf;
import com.company.sf.item.ItemSf;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.ValueTextPair;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.item.dao.ItemDao;
import com.company.util.DateUtil;
import com.company.util.Dto;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;

@Service(ItemService.BEAN_NAME)
public class ItemServiceImpl extends StringIdBaseServiceImpl<Item>implements ItemService {

    @Autowired
    private ItemContactsService contactsService;

    @Autowired
    private ApartyService apartyService;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private IndentService indentService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ItemDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<Item> list(ItemSf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Item> _list = sqlDao.list(queryInfo.getSql(), start, limit, Item.class, queryInfo.getParArr());

        for (Item item : _list) {
            List<ItemPrice> findList = itemPriceService.findList(Filter.eq("itemId", item.getId()));
            Set<String> types = new HashSet<String>();
            
            
            IndentSf indentSf = new IndentSf();
            indentSf.setItemId(item.getId());
            int indentCount = (int)indentService.count(indentSf);
            item.setActualAmt(indentCount);
            
            int receiveCount = indentService.countByItemIdAndStatus(item.getId(), EnumExecuteStatus.BEFORE.name());
            item.setReceiveAmt(receiveCount);
            
            for (ItemPrice itemPrice : findList) {
                types.add(itemPrice.getTypeName());
            }
            String serveType = "";
            for (String type : types) {
                serveType += type + "，";
            }
            if (serveType.length() > 0) {

                serveType = serveType.substring(0, serveType.length() - 1);
            }
            item.setServeType(serveType);
        }

        return _list;
    }

    @Override
    public int count(ItemSf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(ItemSf sf) {
        if (sf == null) {
            sf = new ItemSf();
        }
        String sql = new StringBuilder()//
                .append("select item.* from ZL_ITEM item")//
                .append(" left join ZL_APARTY aparty")//
                .append(" on item.APARTY_ID = aparty.ID")//
                .append(" where 1=1") //
                .toString();

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andContains("item.NAME1", sf.getName1())//
                .andGe("item.BEGIN_DATE", sf.getBeginDate())//
                .andLe("item.OVER_DATE", sf.getOverDate()) //
                .andGe("item.CREATE_DATE", sf.getCrtDateBegin())//
                .andLe("item.CREATE_DATE", sf.getCrtDateEnd()) //
                .andContains("item.COMPETE_STATUS", sf.getCompeteStatus())//
                .andContains("item.EXECUTE_STATUS", sf.getExecuteStatus())//
                .andContains("item.PAY_STATUS", sf.getPayStatus())//
                .andContains("item.STATUS", sf.getStatus()) //
                .andContains("aparty.NAME1", sf.getApartyIdDisp());
        return builder;
    }

    @Override
    public void save(Item item) {

        City city = cityService.findOne(Filter.eq("code1", item.getAreaCity()));
        BigDecimal cityAddRate = new BigDecimal(0);
        if (city != null) {
            cityAddRate = city.getAddRate();
        }

        Area area = areaService.find(item.getAreaDist());
        BigDecimal areaAddRate = new BigDecimal(0);;
        if (area != null) {
            areaAddRate = city.getAddRate();
        }

        List<ItemPrice> itemPriceList = item.getItemPriceList();
        for (ItemPrice itemPrice : itemPriceList) {
            itemPrice.setAreaRebate(areaAddRate);
            itemPrice.setCityRebate(cityAddRate);
        }
        
        
        String date = DateUtil.format(new Date(), "yyyyMMddHHmm");
        String code1 = date + RandomUtil.getRandomStr(4);
        item.setCode1(code1);
        item.setCompeteStatus("BEFORE");
        item.setExecuteStatus("BEFORE");
        item.setPayStatus("NONE");
        item.setStatus("NORMAL");
        super.save(item);

        this.dealRelated(item);
    }

    @Override
    public void saveContracts(String id, MultipartFile[] files) {

        if (files == null || files.length == 0) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            UnFileIndex ufi = new UnFileIndex(files[i], id);

            fileManagerService.save(ufi);
        }
    }

    @Override
    public Item update(Item entity) {

        this.dealRelated(entity);

        return super.update(entity);
    }

    /** 处理其他相关事宜 */
    private void dealRelated(Item entity) {

        String id = entity.getId();

        itemPriceService.saveItemPrice(entity.getItemPriceList(), id);

        contactsService.saveContacts(entity.getContacts1List(), EnumPartyStamp.APARTY, id);
        contactsService.saveContacts(entity.getContacts2List(), EnumPartyStamp.BPARTY, id);
    }

    @Override
    public Item find(String id) {
        Item data = super.find(id);
        String apartyId = data.getApartyId();
        if (StringUtil.isNotEmpty(apartyId)) {
            Aparty aparty = apartyService.find(apartyId);
            data.setAparty(aparty);
        }
        List<ItemPrice> itemPriceList = itemPriceService.findList(Filter.eq("itemId", id));
        data.setItemPriceList(itemPriceList);

        return data;
    }

    @Override
    public List<ValueTextPair> doTypeahead(Dto params) {

        String q = "%" + params.getAsString(TAG_Q) + "%";
        String id = params.getAsString("id");
        String sql = "";
        if (!StringUtil.isNotEmpty(id)) {
            sql = new StringBuilder()//
                    .append("select item.ID as VALUE, item.NAME1 as TEXT, aparty.NAME1 as VALUE2 from ZL_ITEM item, ZL_APARTY aparty")//
                    .append(" where item.APARTY_ID = aparty.ID and (item.CODE1 like ? or item.NAME1 like ?)").toString();
            return sqlDao.list(sql, 0, Typeahead.LIMIT, ValueTextPair.class, new Object[] { q, q });
        } else {
            sql = new StringBuilder()//
                    .append("select item.ID as VALUE, item.NAME1 as TEXT, aparty.NAME1 as VALUE2 from ZL_ITEM item, ZL_APARTY aparty")//
                    .append(" where item.APARTY_ID = aparty.ID and item.APARTY_ID = ? and (item.CODE1 like ? or item.NAME1 like ?)")
                    .toString();
            return sqlDao.list(sql, 0, Typeahead.LIMIT, ValueTextPair.class, new Object[] { id, q, q });
        }

    }

    @Override
    public Dto deleteItem(String id) {
        Dto rt = new Dto();
        List<Indent> indentList = indentService.findList(Filter.eq("itemId", id));
        if (indentList.size() > 0) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "该项目已发布订单，无法删除！");
        } else {
            delete(id);
            contactsService.deleteByItemId(id);
            itemPriceService.deleteByItemId(id);
            rt.put(TAG_SUCCESS, true);
            rt.put(TAG_MSG, "已成功删除该项目信息！");
        }

        return rt;
    }

    @Override
    public List<FileIndex> loadContractFiles(String id) {
        
        List<FileIndex> fiList = fileManagerService.findByBelongTo(id);
        for (FileIndex fi : fiList) {
            fi.setFileUrl(fileManagerService.getFileUrlByFilepath(fi.getFilePath()));
        }
        return fiList;
    }
}