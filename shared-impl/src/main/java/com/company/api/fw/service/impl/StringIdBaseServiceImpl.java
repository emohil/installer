package com.company.api.fw.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Filter;
import com.company.dto.Order;

@Transactional
public class StringIdBaseServiceImpl<T> extends BaseServiceImpl<T, String> implements StringIdBaseService<T> {

    
    @Override
    public T findOne(Filter... filter) {
        List<T> list = findList(Arrays.asList(filter));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    
    @Override
    public List<T> findList(Filter filter) {
        return findList(Arrays.asList(filter));
    }
    
    
    @Override
    public List<T> findList(Order order) {
        return findList(null, Arrays.asList(order));
    }
    
    
    @Override
    public List<T> findList(Filter filter, Order order) {
        return findList(Arrays.asList(filter), Arrays.asList(order));
    }
    
    
    @Override
    public List<T> findList(List<Filter> filters) {
        return findList(filters, null);
    }

    @Override
    public List<T> findList(List<Filter> filters, List<Order> orders) {
        return findList(null, filters, orders);
    }
}
