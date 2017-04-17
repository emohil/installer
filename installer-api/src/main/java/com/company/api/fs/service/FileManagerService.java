package com.company.api.fs.service;

import java.io.File;
import java.util.List;

import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;

/**
 * 文件管理服务
 * 
 * @author lihome
 *
 */
public interface FileManagerService {

    String BEAN_NAME = "fileManagerService";

    /**
     * 保存附件
     * 
     * @param ufi
     * @return
     */
    FileIndex save(UnFileIndex ufi);

    /**
     * 根据fileid删除一个文件
     * 
     * @param fileid
     * @return
     */
    boolean delete(String fileid);

    /**
     * 根据fileid获取文件索引
     * 
     * @param fileid
     * @return
     */
    FileIndex get(String fileid);

    /**
     * 根据fileid获取文件
     * 
     * @param fileid
     * @return
     */
    File getFileById(String fileid);

    /**
     * 根据fileid获取缩略文件
     * 
     * @param fileid
     * @return
     */
    File getThumbById(String fileid);

    /**
     * 根据filepath获取文件
     * 
     * @param fileid
     * @return
     */
    File getFileByFilepath(String filepath);

    /**
     * 根据filepath获取缩略图
     * 
     * @param fileid
     * @return
     */
    File getThumbByFilepath(String filepath);

    /**
     * 根据fileid获取文件URL
     * 
     * @param fileid
     * @return
     */
    String getFileUrlById(String fileid);

    /**
     * 根据fileid获取缩略图URL
     * 
     * @param fileid
     * @return
     */
    String getThumbUrlById(String fileid);

    /**
     * 根据filepath获取文件URL
     * 
     * @param filepath
     * @return
     */
    String getFileUrlByFilepath(String filepath);

    /**
     * 根据filepath获取缩略图URL
     * 
     * @param filepath
     * @return
     */
    String getThumbUrlByFilepath(String filepath);

    /**
     * 根据所属ID查找文件索引
     * 
     * @param belongTo
     * @return
     */
    List<FileIndex> findByBelongTo(String belongTo);

    /**
     * 根据所属ID, EXT1, EXT2查找文件索引
     * 
     * @param belongTo
     * @return
     */
    List<FileIndex> findByBelongToAndExts(String belongTo, String ext1, String ext2);
}
