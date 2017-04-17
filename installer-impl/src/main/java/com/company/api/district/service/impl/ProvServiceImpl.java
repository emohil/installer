package com.company.api.district.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.district.service.ProvService;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.dto.city.CityData;
import com.company.po.district.Prov;
import com.company.dto.Order;
import com.company.repo.district.dao.ProvDao;
import com.company.repo.fw.dao.SqlDao;

@Service(ProvService.BEAN_NAME)
public class ProvServiceImpl extends StringIdBaseServiceImpl<Prov>implements ProvService {

    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(ProvDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<Prov> list(Prov sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<Prov> _list = sqlDao.list(queryInfo.getSql(), start, limit, Prov.class, queryInfo.getParArr());

        return _list;
    }

    @Override
    public int count(Prov sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    private QueryInfoBuilder prepareQuery(Prov sf) {
        if (sf == null) {
            sf = new Prov();
        }
        String sql = "select * from ZL_PROV where 1=1";
        QueryInfoBuilder builder = QueryInfoBuilder.ins(sql) //
                .andContains("NAME1", sf.getName1()) //
                ;
        return builder;
    }

    @Override
    public List<Prov> queryProvList() {
        String sql = "select * from ZL_PROV order by STATUS DESC, CODE1 ASC";

        List<Prov> _list = sqlDao.listAll(sql, Prov.class);

        return _list;
    }

    @Override
    public List<CityData> queryEnabledProvs() {
        String sql = new StringBuilder() //
                .append("select ID as V, NAME1 as N") //
                .append(" from ZL_PROV") //
                .append(" where STATUS='").append(EnumEnableStatus.ENABLED.name()).append("'") //
                .append(" order by CODE1 ASC") //
                .toString();

        List<CityData> _list = sqlDao.listAll(sql, CityData.class);

        return _list;
    }

}