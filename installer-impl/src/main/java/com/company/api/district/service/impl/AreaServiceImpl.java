package com.company.api.district.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.district.service.AreaService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.dto.city.CityData;
import com.company.po.district.Area;
import com.company.repo.district.dao.AreaDao;
import com.company.repo.fw.dao.SqlDao;
import com.company.util.New;

@Service(AreaService.BEAN_NAME)
public class AreaServiceImpl extends StringIdBaseServiceImpl<Area> implements AreaService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(AreaDao dao) {
        super.setBaseDao(dao);
    }

    
    @Override
    public void save(Area entity) {
        List<Area> areaList = entity.getAreaList();
        String cityId = entity.getCityId();
    
        if (areaList == null) {
            return;
        }
        sqlDao.execUpdate("delete from ZL_AREA where CITY_ID=?", cityId);
        for (Area area : areaList) {
            area.setId(null); //IMPORTANT!
            area.setCityId(cityId);
            super.save(area);
        }
    }
    
    @Override
    public List<Area> queryByCityId(String apartyId) {
        String sql = "select * from ZL_AREA where CITY_ID=?";

        Object[] pars = new Object[] { apartyId};
        List<Area> _list = sqlDao.listAll(sql, Area.class, pars);

        return _list;
    }
    
    
    @Override
    public Map<String, List<CityData>> city2Areas() {
        String sql = new StringBuilder() //
                .append("select CODE1 as V, NAME1 as N, CITY_ID as P") //
                .append(" from ZL_AREA") //
                .append(" order by CODE1 ASC") //
                .toString();

        List<CityData> _list = sqlDao.listAll(sql, CityData.class);
        
        Map<String, List<CityData>> city2Areas = New.hashMap();
        
        for (CityData data : _list) {
            String cityId = data.getP();
            List<CityData> t = city2Areas.get(cityId);
            if (t == null) {
                t = New.list();
                city2Areas.put(cityId, t);
            }
            t.add(data);
        }

        return city2Areas;
    }
    
    @Override
    public Area findAreaByCode1(String code1) {
        String sql = "select * from ZL_AREA where CODE1=?";
        return sqlDao.unique(sql, Area.class, code1);
    }

}