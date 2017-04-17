package com.company.api.fw.service.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.company.api.fw.service.BaseService;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.Page;
import com.company.dto.Pageable;
import com.company.po.base.BasePo;
import com.company.repo.fw.dao.BaseDao;

@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] { BasePo.ID_PROPERTY_NAME, BasePo.CREATE_DATE_PROPERTY_NAME, BasePo.MODIFY_DATE_PROPERTY_NAME };

    private BaseDao<T, ID> baseDao;

    public void setBaseDao(BaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Transactional(readOnly = true)
    public T find(ID id) {
        return baseDao.find(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return findList(null, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<T> findList(@SuppressWarnings("unchecked") ID... ids) {
        List<T> result = new ArrayList<T>();
        if (ids != null) {
            for (ID id : ids) {
                T entity = find(id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return findList(null, count, filters, orders);
    }

    @Transactional(readOnly = true)
    public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return baseDao.findList(first, count, filters, orders);
    }

    @Transactional(readOnly = true)
    public Page<T> findPage(Pageable pageable) {
        return baseDao.findPage(pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return count(new Filter[] {});
    }

    @Transactional(readOnly = true)
    public long count(Filter... filters) {
        return baseDao.count(filters);
    }

    @Transactional(readOnly = true)
    public boolean exists(ID id) {
        return baseDao.find(id) != null;
    }

    @Transactional(readOnly = true)
    public boolean exists(Filter... filters) {
        return baseDao.count(filters) > 0;
    }

    @Transactional
    public void save(T entity) {
        baseDao.persist(entity);
    }

    @Transactional
    public T update(T entity) {
        return update(entity, new String[0]);
    }
    
    @Transactional
    public T update(T entity, String... ignoreProperties) {
        Assert.notNull(entity);
        if (baseDao.isManaged(entity)) {
            throw new IllegalArgumentException("Entity must not be managed");
        }
        T persistant = baseDao.find(baseDao.getIdentifier(entity));
        if (persistant != null) {
            copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
            return merge(persistant);
        } else {
            return merge(entity);
        }
    }

    private T merge(T entity) {
        return baseDao.merge(entity);
    }
    
    @Transactional
    public void delete(ID id) {
        delete(baseDao.find(id));
    }

    @Transactional
    public void delete(@SuppressWarnings("unchecked") ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(baseDao.find(id));
            }
        }
    }

    @Transactional
    public void delete(T entity) {
        baseDao.remove(entity);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object sourceValue = readMethod.invoke(source);
                        Object targetValue = readMethod.invoke(target);
                        
                        //skip null value, in other words, don't persistant null.
                        if (sourceValue == null) {
                            continue;
                        }
                        if (targetValue != null && targetValue instanceof Collection) {
                            Collection collection = (Collection) targetValue;
                            collection.clear();
                            collection.addAll((Collection) sourceValue);
                        } else {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, sourceValue);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

}