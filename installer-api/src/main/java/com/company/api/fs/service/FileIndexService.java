package com.company.api.fs.service;

import java.util.List;

import com.company.po.fs.FileIndex;
import com.company.api.fw.service.BaseService;

public interface FileIndexService extends BaseService<FileIndex, String> {
    
    String BEAN_NAME = "fileIndexService";
    
    List<FileIndex> findByBelongTo(String belongTo);
    
    List<FileIndex> findByBelongToAndExts(String belongTo, String ext1, String ext2);
    
    void delfile(String id);
}