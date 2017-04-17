package com.company.api.fw.util;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.company.dto.FileItem;
import com.company.util.DateUtil;

public class FileManangerUtil {

    public static String getUploadBasepath() {
        String basepath = EnvConfig.getProperty(EnvConfig.UPLOAD_BASEPATH);

        if (!basepath.endsWith("/")) {
            return basepath + "/";
        }
        return basepath;
    }

    public static String getDownloadBasepath() {
        String basepath = EnvConfig.getProperty(EnvConfig.DOWNLOAD_BASEPATH);

        if (!basepath.endsWith("/")) {
            return basepath + "/";
        }
        return basepath;
    }
    

    public static FileItem uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String basePath = getUploadBasepath();

        String childPath = DateUtil.format(new Date(), "yyyyMMdd");

        File folder = new File(basePath, childPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        String origFileName = file.getOriginalFilename();

        String saveFileName = UUID.randomUUID().toString();
        if (origFileName.lastIndexOf(".") > -1) {
            saveFileName += origFileName.substring(origFileName.lastIndexOf("."));
        }
        File targetFile = new File(basePath + childPath, saveFileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new FileItem(origFileName, childPath + "/" + saveFileName);
    }
}