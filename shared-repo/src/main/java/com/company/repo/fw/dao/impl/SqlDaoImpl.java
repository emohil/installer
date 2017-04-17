package com.company.repo.fw.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.company.repo.fw.dao.SqlDao;

public class SqlDaoImpl extends AbstractDao implements SqlDao {

    @Override
    public <T> List<T> list(final String sql, final int start, final int limit, final Class<T> type,
            final Object... pars) {
        return list(sql, start, limit, type, null, pars);
    }

    @Override
    public <T> List<T> list(final String sql, final int start, final int limit, final Class<T> type,
            final List<String> ignoreFields, final Object... pars) {

        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);
                setPager(query, start, limit);

                setResultTransformer(query, type);

                return query.list();
            }
        });
    }

    @Override
    public <T> List<T> list(final String sql, final int start, final int limit, final ResultTransformer transformer,
            final Object... pars) {

        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);
                setPager(query, start, limit);
                setResultTransformer(query, transformer);

                return query.list();
            }
        });
    }

    @Override
    public <T> List<T> listAll(final String sql, final Class<T> type, final Object... pars) {
        return list(sql, 0, 0, type, pars);
    }

    
    @Override
    public List<Map<String, Object>> queryForList(final String sql, final Object... pars) {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);
                setResultTransformer(query, Criteria.ALIAS_TO_ENTITY_MAP);

                return query.list();
            }
        });
    }
    
    
    @Override
    public int count(final String sql, final Object... pars) {

        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);

                Number rs = (Number) query.uniqueResult();
                return rs.intValue();
            }
        });
    }

    @Override
    public <T> T unique(final String sql, final Class<T> type, final Object... pars) {

        return getHibernateTemplate().execute(new HibernateCallback<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);
                setPager(query, 1, 1);

                setResultTransformer(query, type);

                return (T) query.uniqueResult();
            }
        });
    }

    @Override
    public <T> T unique(final String sql, final ResultTransformer transformer, final Object... pars) {

        return getHibernateTemplate().execute(new HibernateCallback<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);
                setPager(query, 1, 1);
                setResultTransformer(query, transformer);

                return (T) query.uniqueResult();
            }
        });
    }

    @Override
    public int execUpdate(final String sql, final Object... pars) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                setParameters(query, pars);

                return query.executeUpdate();
            }
        });
    }
}
