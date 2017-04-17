package com.company.api.fs.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.fs.service.FileGetter;
import com.company.api.fs.service.FileIndexService;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fs.service.FileSaver;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.util.ImageUtil;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;

@Service(FileManagerService.BEAN_NAME)
public class FileManagerServiceImpl implements FileManagerService {

    @Autowired
    private FileIndexService fileIndexService;

    @Autowired
    private FileSaver fileSaver;

    @Autowired
    private FileGetter fileGetter;

    private String datePart() {
        Calendar c = Calendar.getInstance();
        StringBuilder part = new StringBuilder().append(c.get(Calendar.YEAR));

        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        if (month < 10) {
            part.append("0");
        }
        part.append(month);
        if (day < 10) {
            part.append("0");
        }
        part.append(day);

        return part.toString();
    }

    @Override
    public FileIndex save(UnFileIndex ufi) {

        FileIndex fi = new FileIndex(ufi);

        // 文件名以随机字符的方式存放
        String saveFilePath = RandomUtil.uuid();

        // 解析后缀
        String fileName = ufi.getFileName();
        String suffix = "";
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) {
            suffix = fileName.substring(pos + 1);
        }
        if (!StringUtil.isEmpty(suffix)) {
            saveFilePath += "." + suffix;
        }

        // 确定文件路径
        String filePath = datePart() + "/" + saveFilePath;
        fi.setFilePath(filePath);

        // 获取文件
        File upFile = ufi.getUpFile();
        if (upFile == null || !upFile.exists()) {
            upFile = new File(saveFilePath);
            try {
                ufi.getmUpfile().transferTo(upFile);
            } catch (IllegalStateException ex) {
                throw new IllegalStateException(ex.getMessage());
            } catch (IOException ex) {
                throw new IllegalStateException(ex.getMessage());
            }
        }
        // 调用保存文件
        fileSaver.saveFile(upFile, filePath);

        // 删除临时文件
        upFile.delete();

        // 保存索引表
        fileIndexService.save(fi);

        return fi;
    }

    @Override
    public boolean delete(String fileid) {
        FileIndex fi = get(fileid);
        // 没有找到相关文件信息
        if (fi == null) {
            return false;
        }
        // 从文件存储路径删除
        fileSaver.deleteFile(fi.getFilePath());
        // 删除文件索引
        // TODO 直接传入对象报错
        fileIndexService.delete(fi.getId());

        return true;
    }

    @Override
    public FileIndex get(String fileid) {
        return fileIndexService.find(fileid);
    }

    @Override
    public File getFileById(String fileid) {
        FileIndex fi = get(fileid);
        // 没有找到相关文件信息
        if (fi == null) {
            return null;
        }
        return getFileByFilepath(fi.getFilePath());
    }

    @Override
    public File getThumbById(String fileid) {
        FileIndex fi = get(fileid);
        // 没有找到相关文件信息
        if (fi == null) {
            return null;
        }
        return getThumbByFilepath(fi.getFilePath());
    }

    @Override
    public File getFileByFilepath(String filepath) {
        return fileGetter.getFile(filepath);
    }

    @Override
    public File getThumbByFilepath(String filepath) {
        String thumbpath = ImageUtil.thumbnailFilename(filepath);
        File thumb = fileGetter.getFile(thumbpath);
        if (!thumb.exists()) {
            File file = fileGetter.getFile(filepath);
            try {
                ImageUtil.thumbnail(file, thumb, ImageUtil.THUMBNAIL_SIZE, ImageUtil.THUMBNAIL_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return thumb;
    }

    @Override
    public String getFileUrlById(String fileid) {
        FileIndex fi = get(fileid);
        // 没有找到相关文件信息
        if (fi == null) {
            return null;
        }
        return getFileUrlByFilepath(fi.getFilePath());
    }

    @Override
    public String getThumbUrlById(String fileid) {
        FileIndex fi = get(fileid);
        // 没有找到相关文件信息
        if (fi == null) {
            return null;
        }
        return getThumbUrlByFilepath(fi.getFilePath());
    }

    @Override
    public String getFileUrlByFilepath(String filepath) {
        return fileGetter.getFileUrl(filepath);
    }

    @Override
    public String getThumbUrlByFilepath(String filepath) {

        // 确保存在缩略图文件
        this.getThumbByFilepath(filepath);

        return fileGetter.getFileUrl(ImageUtil.thumbnailFilename(filepath));
    }

    @Override
    public List<FileIndex> findByBelongTo(String belongTo) {
        return fileIndexService.findByBelongTo(belongTo);
    }

    @Override
    public List<FileIndex> findByBelongToAndExts(String belongTo, String ext1, String ext2) {
        return fileIndexService.findByBelongToAndExts(belongTo, ext1, ext2);
    }
}
