package com.company.api.aparty.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.aparty.service.ApartyContactsService;
import com.company.api.aparty.service.ApartyFinanceService;
import com.company.api.aparty.service.ApartyService;
import com.company.api.aparty.service.ApartyTradeService;
import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.dict.service.EnumPartyStamp;
import com.company.api.fw.service.Typeahead;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.api.item.service.ItemService;
import com.company.po.aparty.Aparty;
import com.company.po.aparty.ApartyContacts;
import com.company.po.aparty.ApartyFinance;
import com.company.po.item.Item;
import com.company.sf.aparty.ApartySf;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.ValueTextPair;
import com.company.repo.aparty.dao.ApartyDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.StringUtil;

@Service(ApartyService.BEAN_NAME)
public class ApartyServiceImpl extends StringIdBaseServiceImpl<Aparty>//
        implements ApartyService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    public void setBaseDao(ApartyDao dao) {
        super.setBaseDao(dao);
    }

    @Autowired
    private ApartyTradeService profitService;
    
    @Autowired
    private ApartyFinanceService financeService;

    @Autowired
    private ApartyContactsService contactsService;

    @Override
    public List<Aparty> list(ApartySf sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Aparty> _list = sqlDao.list(queryInfo.getSql(), start, limit, Aparty.class, queryInfo.getParArr());

        if (_list == null || _list.size() == 0) {
            return _list;
        }

        List<String> ids = New.list();
        for (Aparty aparty : _list) {
            ids.add(aparty.getId());
        }
        QueryInfo queryContact = QueryInfoBuilder //
                .ins("select * from ZL_APARTY_CONTACTS WHERE 1=1").andIn("APARTY_ID", ids).build();
        List<ApartyContacts> contactsList = sqlDao.listAll(queryContact.getSql(), ApartyContacts.class,
                queryContact.getParArr());
        Map<String, ApartyContacts> contacts1Map = New.hashMap();
        Map<String, ApartyContacts> contacts2Map = New.hashMap();
        for (ApartyContacts apartyContacts : contactsList) {
            String apartyId = apartyContacts.getApartyId();
            String stamp = apartyContacts.getStamp();

            if (EnumPartyStamp.APARTY.name().equals(stamp)) {
                if (contacts1Map.get(apartyId) == null) {
                    contacts1Map.put(apartyId, apartyContacts);
                }
            } else {
                if (contacts2Map.get(apartyId) == null) {
                    contacts2Map.put(apartyId, apartyContacts);
                }
            }
        }
        for (Aparty aparty : _list) {
            String _id = aparty.getId();
            aparty.setContacts1(contacts1Map.get(_id));
            aparty.setContacts2(contacts2Map.get(_id));
        }
        return _list;

    }

    @Override
    public int count(ApartySf sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(ApartySf sf) {
        if (sf == null) {
            sf = new ApartySf();
        }
        StringBuilder sql = new StringBuilder()//
                .append("select * from ZL_APARTY")//
                .append(" WHERE 1=1");

        List<Object> sqlPars = New.list();

        ApartyContacts c1 = sf.getContacts1();
        ApartyContacts c2 = sf.getContacts2();
        if (c1 != null && (StringUtil.isNotEmpty(c1.getName1()) || StringUtil.isNotEmpty(c1.getMobile()))) {
            sql.append(" and ID in (select APARTY_ID from ZL_APARTY_CONTACTS where STAMP = ?");
            sqlPars.add(EnumPartyStamp.APARTY.name());

            if (StringUtil.isNotEmpty(c1.getName1())) {
                sql.append(" and NAME1 like ?");
                sqlPars.add("%" + c1.getName1() + "%");
            }
            if (StringUtil.isNotEmpty(c1.getMobile())) {
                sql.append(" and MOBILE like ?");
                sqlPars.add("%" + c1.getMobile() + "%");
            }
            sql.append(")");
        }
        if (c2 != null && StringUtil.isNotEmpty(c2.getName1())) {
            sql.append(" and ID in (select APARTY_ID from ZL_APARTY_CONTACTS where STAMP = ?");
            sql.append(" and NAME1 like ?");
            sqlPars.add(EnumPartyStamp.BPARTY.name());
            sqlPars.add("%" + c2.getName1() + "%");
            sql.append(")");
        }

        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql.toString()).sqlPars(sqlPars) //
                .andContains("NAME1", sf.getName1())//
                .andContains("REGION_PROV", sf.getRegionProv())//
                .andContains("REGION_CITY", sf.getRegionCity())//
                .andContains("REGION_DIST", sf.getRegionDist())//
                .andGe("CRT_DATE", sf.getCrtDateBegin())//
                .andLe("CRT_DATE", sf.getCrtDateEnd());

        return builder;
    }

    @Override
    public void save(Aparty entity) {
        
        String maxCode1 = this.findMaxCode1();
        String nextCode1 = this.getNextCode1(maxCode1);
        
        entity.setCode1(nextCode1);
        entity.setProfitAmt(new BigDecimal(0));
        entity.setBalance(new BigDecimal(0));
        entity.setCrtDate(new Date());
        entity.setApartyStatus(EnumEnableStatus.ENABLED.name());

        super.save(entity);

        this.dealRelated(entity);
    }

    @Override
    public Aparty update(Aparty entity) {

        this.dealRelated(entity);

        return super.update(entity);
    }

    /** 处理其他相关事宜 */
    private void dealRelated(Aparty entity) {

        String id = entity.getId();

        financeService.saveOrUpdate(entity.getFinance(), id);

        contactsService.saveContacts(entity.getContacts1List(), EnumPartyStamp.APARTY, id);
        contactsService.saveContacts(entity.getContacts2List(), EnumPartyStamp.BPARTY, id);
    }

    @Override
    public Aparty find(String id) {
        Aparty data = super.find(id);

        ApartyFinance finance = financeService.findByApartyId(data.getId());
        // fill ApartyFinance info
        data.setFinance(finance);

        return data;
    }

    @Override
    public List<ValueTextPair> doTypeahead(Dto params) {
        String sql = new StringBuilder()//
                .append("select ID as VALUE, NAME1 as TEXT from ZL_APARTY")//
                .append(" where CODE1 like ? or NAME1 like ?").toString();

        String q = "%" + params.getAsString(TAG_Q) + "%";

        return sqlDao.list(sql, 0, Typeahead.LIMIT, ValueTextPair.class, new Object[] { q, q });

    }
    
    @Override
    public Dto deleteAparty(String id) {
        Dto rt = new Dto();
        
        List<Item> itemList = itemService.findList(Filter.eq("apartyId", id));
        if (itemList.size() > 0) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "该甲方已发布项目，无法删除");
        } else {
            delete(id);
            profitService.deleteByApartyId(id);
            financeService.deleteByApartyId(id);
            contactsService.deleteByApartyId(id);
            rt.put(TAG_SUCCESS, true);
            rt.put(TAG_MSG, "已成功删除该甲方信息");
            
        }
        
        return rt;
    }
    
    @Override
    public String findMaxCode1() {
        
        String sql = "select max(CODE1) as MAXCODE1 from ZL_APARTY";
        Object maxCode1 = sqlDao.unique(sql, (ResultTransformer) null);
        
        if (maxCode1 == null) {
            return "0";
        }
        
        return maxCode1.toString();
    }
    
    @Override
    public String getNextCode1(String maxCode1) {
        
        String nextCode1 = "001";
        if (maxCode1.equals("0")) {
            return nextCode1;
        }
        Integer code1 = Integer.valueOf(maxCode1);
        code1 += 1;
        nextCode1 = StringUtil.leftPad(code1.toString(), 3, "0");
        return nextCode1;
    }

    @Override
    public Aparty findApartyByCode1(String code1) {
        String sql = "select * from ZL_APARTY where CODE1=?";
        return sqlDao.unique(sql, Aparty.class, code1);
    }

}