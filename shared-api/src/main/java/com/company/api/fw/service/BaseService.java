package com.company.api.fw.service;

import java.io.Serializable;
import java.util.List;

import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.Page;
import com.company.dto.Pageable;


public interface BaseService<T, ID extends Serializable> extends Tag {

    T find(ID id);

    List<T> findAll();

    List<T> findList(@SuppressWarnings("unchecked") ID... ids);

    List<T> findList(Integer count, List<Filter> filters, List<Order> orders);

    List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders);

    Page<T> findPage(Pageable pageable);

    long count();

    long count(Filter... filters);

    boolean exists(ID id);

    boolean exists(Filter... filters);

    void save(T entity);

    T update(T entity);

    T update(T entity, String... ignoreProperties);

    void delete(ID id);

    void delete(@SuppressWarnings("unchecked") ID... ids);

    void delete(T entity);

}