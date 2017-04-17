package com.company.repo.fw.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.company.repo.fw.dao.StringIdBaseDao;

public class StringIdBaseDaoImpl<T> extends BaseDaoImpl<T, String> implements StringIdBaseDao<T> {

    @Override
    public int execute(String jpql, Object... pars) {
        
        Query query = entityManager.createQuery(jpql);
        
        setParameters(query, pars);
        
        return query.executeUpdate();
    }
    
    
    @Override
    public <E> E queryForObject(String jpql, Class<E> resultClass, Object... pars) {
        
        TypedQuery<E> query = entityManager.createQuery(jpql, resultClass);
        
        setParameters(query, pars);
        
        return query.getSingleResult();
    }
    
    
    @Override
    public <E> List<E> queryForList(String jpql, Class<E> resultClass, Object... pars) {
        TypedQuery<E> query = entityManager.createQuery(jpql, resultClass);
        
        setParameters(query, pars);
        
        return query.getResultList();
    }
    
    
    
    protected void setParameters(Query query, Object... pars) {
        if (pars == null || pars.length == 0) {
            return;
        }
        for (int i = 0; i < pars.length; i++) {
            query.setParameter(i + 1, pars[i]);
        }
    }
    
    protected void setPager(Query query, int start, int limit) {
        if (start > 0) {
            query.setFirstResult(start - 1);
        }
        if (limit > 0) {
            query.setMaxResults(limit);
        }
    }
}
