package com.company.repo.fw.dao;

import java.util.List;
import java.util.Map;

public interface SqlDao extends CommonDao {

    /**
     * Perform a page list sql.
     * 
     * @param sql sql/hql 
     * @param start begin position of return rows, 1 as the first.
     * @param limit max value of return rows. 
     * @param type class type of data.
     * @param pars Parameters for sql.
     * @return
     */
    <T> List<T> list(String sql, int start, int limit, Class<T> type, Object... pars);

    /**
     * Perform a page list sql.
     * 
     * @param sql sql/hql 
     * @param start begin position of return rows, 1 as the first.
     * @param limit max value of return rows. 
     * @param type class type of data.
     * @param ignoreFields ignore fields.
     * @param pars Parameters for sql. 
     * @return
     */
    <T> List<T> list(String sql, int start, int limit, Class<T> type, List<String> ignoreFields, Object... pars);

    /**
     * Perform a page list sql all.
     * 
     * @param sql sql/hql 
     * @param type class type of data.
     * @param pars Parameters for sql. 
     * @return
     */
    <T> List<T> listAll(String sql, Class<T> type, Object... pars);
    
    
    /**
     * Perform a sql wrap as map
     * @param sql
     * @param pars
     * @return
     */
    List<Map<String, Object>> queryForList(String sql, Object... pars);

    /**
     * Get unique result
     * 
     * @param sql sql/hql 
     * @param type class type of data.
     * @param pars Parameters for sql. 
     * @return
     */
    <T> T unique(String sql, Class<T> type, Object... pars);
}
