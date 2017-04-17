package com.company.api.fw.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.api.fw.service.PagerService;
import com.company.api.fw.service.impl.query.QueryInfo;
import com.company.api.fw.service.impl.query.QueryInfoBuilder;
import com.company.dto.Order;
import com.company.repo.fw.dao.SqlDao;

public abstract class PagerBaseServiceImpl<SF, T> extends StringIdBaseServiceImpl<T> //
        implements PagerService<SF, T> {
    
    @Autowired
    private SqlDao sqlDao;
    
    protected Class<T> clazz;
    
    @SuppressWarnings("unchecked")
    public PagerBaseServiceImpl() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types != null && types.length > 1){
                this.clazz = (Class<T>) types[1];
            }
        }
    }

    @Override
    public List<T> list(SF sf, int start, int limit, List<Order> orders) {
        QueryInfo queryInfo = prepareQuery(sf).order(orders).build();

        List<T> list = sqlDao.list(queryInfo.getSql(), start, limit, clazz, queryInfo.getParArr());
        
        this.transformList(list, sf);
        
        return list;
    }
    
    public void transformList(List<T> list, SF sf) {}

    @Override
    public int count(SF sf) {
        QueryInfo queryInfo = prepareQuery(sf).build();
        return sqlDao.count(queryInfo.getCountSql(), queryInfo.getParArr());
    }

    public abstract QueryInfoBuilder prepareQuery(SF sf);
}
