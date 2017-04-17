package com.company.repo.fw.dao;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

public interface CommonDao {

    /**
     * Count.
     * 
     * @param query
     *            sql/hql
     * @param pars
     *            Parameters for query count.
     * @return
     */
    int count(String query, Object... pars);

    /**
     * Perform a page list query.
     * 
     * @param query
     *            sql/hql
     * @param start
     *            begin position of return rows, 1 as the first.
     * @param limit
     *            max value of return rows.
     * @param transformer
     *            Data transformer.
     * @param pars
     *            Parameters for query.
     * @return
     */
    <T> List<T> list(String query, int start, int limit, ResultTransformer transformer, Object... pars);

    /**
     * Get unique result
     * 
     * @param query
     *            sql/hql
     * @param transformer
     *            Data transformer.
     * @param pars
     *            Parameters for query.
     * @return
     */
    <T> T unique(String query, ResultTransformer transformer, Object... pars);

    /**
     * Execute update
     * 
     * @param query
     *            sql/hql
     * @param pars
     *            Parameters for update
     * @return
     */
    int execUpdate(String query, Object... pars);
}
