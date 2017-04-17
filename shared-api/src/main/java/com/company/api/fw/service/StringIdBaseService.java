package com.company.api.fw.service;

import java.util.List;

import com.company.dto.Filter;
import com.company.dto.Order;

public interface StringIdBaseService<T> extends BaseService<T, String> {
    
    /**
     * 根据单个 {@link Order} 查找数据, 并获取单条记录.
     * @param filter
     * @return
     */
    T findOne(Filter... filter);
    
    /**
     * 根据单个 {@link Filter} 检索数据.
     * 
     * @param filter 过滤条件. 不再判断此参数的合法性
     * @return
     */
    List<T> findList(Filter filter);
    
    /**
     * 根据单个 {@link Order} 排序列检索数据.
     * 
     * @param order 排序规则. 不再判断此参数的合法性
     * @return
     */
    List<T> findList(Order order);
    
    
    /**
     * 根据单个 {@link Filter} 与 {@link Order} 检索数据.
     * 
     * @param filter 过滤条件. 不再判断此参数的合法性
     * @param order 排序规则. 不再判断此参数的合法性
     * @return
     */
    List<T> findList(Filter filter, Order order);
    
    
    /**
     * 根据 多个{@link Filter} 检索数据.
     * 
     * @param filters 过滤条件.
     * @return
     */
    List<T> findList(List<Filter> filters);

    
    /**
     * 根据 多个{@link Filter} 与 多个{@link Order} 检索数据.
     * 
     * @param filters 过滤条件.
     * @param orders 排序条件.
     * @return
     */
    List<T> findList(List<Filter> filters, List<Order> orders);
}