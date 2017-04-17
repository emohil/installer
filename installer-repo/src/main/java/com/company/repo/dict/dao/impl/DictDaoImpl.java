package com.company.repo.dict.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.company.po.dict.Dict;
import com.company.repo.dict.dao.DictDao;
import com.company.repo.fw.dao.impl.BaseDaoImpl;

@Repository(DictDao.BEAN_NAME)
public class DictDaoImpl extends BaseDaoImpl<Dict, String> implements DictDao {

    public void deleteByGroupCode(String groupCode) {
        String jpql = "delete from Dict d where d.groupCode = ?";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter(1, groupCode).executeUpdate();
    }

    public List<Dict> findByGroupCode(String groupCode) {
        String jpql = "FROM Dict WHERE groupCode = ?" ;
        TypedQuery<Dict> query = entityManager.createQuery(jpql, Dict.class).setParameter(1, groupCode).setFlushMode(FlushModeType.COMMIT);
        return query.getResultList();
    }

    
}