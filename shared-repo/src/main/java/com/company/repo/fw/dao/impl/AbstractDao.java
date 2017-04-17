package com.company.repo.fw.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractDao extends HibernateDaoSupport {

    protected boolean forcemap = false;

    @PersistenceContext
    protected EntityManager entityManager;

    protected void setPager(Query query, int start, int limit) {
        if (start > 0) {
            query.setFirstResult(start - 1);
        }
        if (limit > 0) {
            query.setMaxResults(limit);
        }
    }

    protected void setParameters(Query query, Object... pars) {
        if (pars == null || pars.length == 0) {
            return;
        }
        for (int i = 0; i < pars.length; i++) {
            query.setParameter(i, pars[i]);
        }
    }

    protected void setJpaParameters(javax.persistence.Query jpaQuery, Object... pars) {
        if (pars == null || pars.length == 0) {
            return;
        }
        for (int i = 0; i < pars.length; i++) {
            jpaQuery.setParameter(i + 1, pars[i]);
        }
    }
    
    
    protected <T> void setResultTransformer(Query query, Class<T> type) {
        if (type != null) {
            query.setResultTransformer(new BeanTransformerAdapter<T>(type));
        }
        
    }

    protected void setResultTransformer(Query query, ResultTransformer transformer) {
        if (transformer != null) {
            query.setResultTransformer(transformer);
        } else if (forcemap) {
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        }
    }

}
