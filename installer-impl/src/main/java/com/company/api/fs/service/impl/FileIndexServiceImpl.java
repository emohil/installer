package com.company.api.fs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fs.service.FileIndexService;
import com.company.api.fw.service.impl.BaseServiceImpl;
import com.company.po.fs.FileIndex;
import com.company.repo.fs.dao.FileIndexDao;
import com.company.repo.fw.dao.SqlDao;

@Service(FileIndexService.BEAN_NAME)
public class FileIndexServiceImpl extends BaseServiceImpl<FileIndex, String> //
        implements FileIndexService {
    
    @Autowired
    private SqlDao sqlDao;

    @Autowired
    public void setBaseDao(FileIndexDao dao) {
        super.setBaseDao(dao);
    }
    
    
    @Override
    public List<FileIndex> findByBelongTo(String belongTo) {
        String sql = "select * from ZL_FILE_INDEX where BELONG_TO=?";
        return sqlDao.listAll(sql, FileIndex.class, new Object[] {belongTo});
    }
    
    @Override
    public void delfile(String id) {
        sqlDao.execUpdate("delete from ZL_FILE_INDEX where ID=?", id);
    }


    @Override
    public List<FileIndex> findByBelongToAndExts(String belongTo, String ext1, String ext2) {
        String sql = "select * from ZL_FILE_INDEX where BELONG_TO=? and EXT1=? and EXT2=?";
        return sqlDao.listAll(sql, FileIndex.class, new Object[] {belongTo, ext1, ext2});
    }
}