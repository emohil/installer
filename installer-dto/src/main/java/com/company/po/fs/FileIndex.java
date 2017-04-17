package com.company.po.fs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.po.base.StringIdPo;

@Entity
@Table(name = "ZL_FILE_INDEX")
public class FileIndex extends StringIdPo {

    private static final long serialVersionUID = 1L;

    @Column(name = "FILE_NAME", length = 60, columnDefinition = "VARCHAR(60) DEFAULT ''")
    private String fileName;

    @Column(name = "FILE_PATH", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String filePath;

    @Column(name = "BELONG_TO", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String belongTo;

    @Column(name = "SUFFIX", length = 10, columnDefinition = "VARCHAR(10) DEFAULT ''")
    private String suffix;
    
    @Column(name = "EXT1", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String ext1;
    
    @Column(name = "EXT2", length = 32, columnDefinition = "VARCHAR(32) DEFAULT ''")
    private String ext2;
    
    @Transient
    private String fileUrl;
    
    @Transient
    private String thumbUrl;

    public FileIndex() {
        this.suffix = DEF_STRING;
        this.ext1 = DEF_STRING;
        this.ext2 = DEF_STRING;
    }
    
    public FileIndex(UnFileIndex ufi) {
        this();
        this.fileName = ufi.getFileName();
        this.belongTo = ufi.getBelongTo();
        this.ext1 = ufi.getExt1();
        this.ext2 = ufi.getExt2();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
