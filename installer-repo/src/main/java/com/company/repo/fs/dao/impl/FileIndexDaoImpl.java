package com.company.repo.fs.dao.impl;

import org.springframework.stereotype.Repository;

import com.company.po.fs.FileIndex;
import com.company.repo.fs.dao.FileIndexDao;
import com.company.repo.fw.dao.impl.BaseDaoImpl;

@Repository(FileIndexDao.BEAN_NAME)
public class FileIndexDaoImpl extends BaseDaoImpl<FileIndex, String>//
        implements FileIndexDao {

}