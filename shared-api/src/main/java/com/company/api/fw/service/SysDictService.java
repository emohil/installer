package com.company.api.fw.service;

import java.util.List;

import com.company.dto.SysDict;

/**
 * 系统级别的数据字典服务接口.
 * 
 * @author lihome
 *
 */
public interface SysDictService {
    
    String BEAN_NAME = "sysDictService";
    
    /**
     * Refresh dict
     */
    void refreshDict();

    /**
     * List 
     */
    List<SysDict> listGroupCopy(String groupCode, SysDict top, String... excludeKeys);
    
    List<SysDict> listGroupCopy(String groupCode, String... excludeKeys);
    
    List<SysDict> listGroupTransfer(String groupCode, SysDict top, String... includeKeys);
    
    List<SysDict> listGroupTransfer(String groupCode, String... includeKeys);
    
    String text(String groupCode, String value);
    
    String texts(String groupCode, String... values);
    
    Integer order(String groupCode, String value);
}
