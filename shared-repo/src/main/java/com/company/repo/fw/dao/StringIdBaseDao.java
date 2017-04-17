package com.company.repo.fw.dao;

import java.util.List;


public interface StringIdBaseDao<T> extends BaseDao<T, String> {

    /**
     * Execute a query given JPQL.
     * @param jpql JPQL query to execute
     * @param pars arguments to bind to the query
     * @return the number of rows affected
     */
    int execute(String jpql, Object... args);
    
    
    /**
     * Query given JPQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, expecting a result object.
     * @param jpql JPQL query to execute
     * @param resultClass 
     * @param pars arguments to bind to the query
     * @return the result object of the return type, or {@code null} in case of JPQL NULL
     */
    <E> E queryForObject(String jpql, Class<E> resultClass, Object... pars);
    
    /**
     * Query given JPQL to create a prepared statement from SQL and a
     * list of arguments to bind to the query, expecting a result list.
     * @param jpql JPQL query to execute
     * @param resultClass 
     * @param pars arguments to bind to the query
     * @return the result list of the return type
     */
    <E> List<E> queryForList(String jpql, Class<E> resultClass, Object... pars);
}