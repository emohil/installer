package com.company.po.fs;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class UnFileIndex {
    
    private File upFile;
    
    private MultipartFile mUpfile;
    
    private String fileName;
    
    private String belongTo;
    
    private String ext1;
    
    private String ext2;
    
    public UnFileIndex(File upFile, String fileName, String belongTo) {
        this.upFile = upFile;
        this.fileName = fileName;
        this.belongTo = belongTo;
    }
    
    public UnFileIndex(MultipartFile mUpfile, String belongTo) {
        this.mUpfile = mUpfile;
        this.fileName = mUpfile.getOriginalFilename();
        this.belongTo = belongTo;
    }
    
    

    public UnFileIndex(MultipartFile mUpfile, String belongTo, String ext1, String ext2) {
        super();
        this.mUpfile = mUpfile;
        this.fileName = mUpfile.getOriginalFilename();
        this.belongTo = belongTo;
        this.ext1 = ext1;
        this.ext2 = ext2;
    }

    public File getUpFile() {
        return upFile;
    }

    public void setUpFile(File upFile) {
        this.upFile = upFile;
    }

    public MultipartFile getmUpfile() {
        return mUpfile;
    }

    public void setmUpfile(MultipartFile mUpfile) {
        this.mUpfile = mUpfile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
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
}