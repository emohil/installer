package com.company.api.district.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.district.service.AreaService;
import com.company.api.district.service.CityService;
import com.company.api.district.service.ProvService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.dto.city.CityData;
import com.company.po.district.City;
import com.company.dto.Order;
import com.company.repo.district.dao.CityDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.New;

@Service(CityService.BEAN_NAME)
public class CityServiceImpl extends StringIdBaseServiceImpl<City> //
        implements CityService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    private ProvService provService;

    @Autowired
    private AreaService areaService;

    @Autowired
    public void setBaseDao(CityDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public void save(City entity) {
        
        entity.setStatus(EnumEnableStatus.ENABLED.name());
        
        super.save(entity);
    }

    @Override
    public List<City> list(City sf, int start, int limit, List<Order> orders) {

        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<City> _list = sqlDao.list(queryInfo.getSql(), start, limit, City.class, queryInfo.getParArr());

        return _list;
    }

    @Override
    public int count(City sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(City sf) {
        if (sf == null) {
            sf = new City();
        }
        String sql = "select * from ZL_CITY where 1=1";
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andContains("NAME1", sf.getName1()) //
                .andEq("PROV_ID", sf.getProvId());
        return builder;
    }

    @Override
    public List<City> queryByProvId(City sf) {

        String sql = "select * from ZL_CITY where PROV_ID = ? order by STATUS DESC, CODE1 ASC";

        Object[] pars = new Object[] { sf.getProvId() };
        List<City> _list = sqlDao.listAll(sql, City.class, pars);

        return _list;
    }

    @Override
    public Map<String, List<CityData>> enabledProv2Cities() {
        String sql = new StringBuilder() //
                .append("select ID as V, NAME1 as N, PROV_ID as P") //
                .append(" from ZL_CITY") //
                .append(" where STATUS='").append(EnumEnableStatus.ENABLED.name()).append("'") //
                .append(" order by CODE1 ASC") //
                .toString();

        List<CityData> _list = sqlDao.listAll(sql, CityData.class);

        Map<String, List<CityData>> prov2Cities = New.hashMap();

        for (CityData data : _list) {
            String provId = data.getP();
            List<CityData> t = prov2Cities.get(provId);
            if (t == null) {
                t = New.list();
                prov2Cities.put(provId, t);
            }
            t.add(data);
        }

        return prov2Cities;
    }

    @Override
    public List<CityData> cityData() {

        List<CityData> provs = provService.queryEnabledProvs();

        Map<String, List<CityData>> prov2Cities = this.enabledProv2Cities();

        Map<String, List<CityData>> city2Areas = areaService.city2Areas();

        for (CityData prov : provs) {

            List<CityData> cityList = prov2Cities.get(prov.getV());

            if (cityList == null) {
                continue;
            }
            prov.setC(cityList);

            for (CityData city : cityList) {
                List<CityData> areaList = city2Areas.get(city.getV());
                city.setC(areaList);
            }
        }
        return provs;
    }
    
    @Override
    public City update(City entity) {
        entity.setStatus(EnumEnableStatus.ENABLED.name());
        return super.update(entity);
    }
    
    public void closeCity(City entity) {
        super.update(entity);
    }
    
    @Override
    public City findCityByCode1(String code1) {
        String sql = "select * from ZL_CITY where CODE1=?";
        return sqlDao.unique(sql, City.class, code1);
    }
}