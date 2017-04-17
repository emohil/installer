package com.company.repo.fs.dao;

import com.company.po.fs.FileIndex;
import com.company.repo.fw.dao.BaseDao;

public interface FileIndexDao extends BaseDao<FileIndex, String> {
    
    String BEAN_NAME = "fileIndexDao";
}