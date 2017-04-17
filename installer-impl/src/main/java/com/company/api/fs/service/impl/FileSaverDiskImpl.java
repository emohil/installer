package com.company.api.fs.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.company.api.fs.service.FileSaver;
import com.company.api.fw.util.EnvConfig;
import com.company.util.FileUtil;

@Service("fileSaverDisk")
public class FileSaverDiskImpl implements FileSaver {

    public static String getBasepath() {
        String basepath = EnvConfig.getProperty(EnvConfig.UPLOAD_BASEPATH);

        if (!basepath.endsWith("/")) {
            return basepath + "/";
        }
        return basepath;
    }

    @Override
    public void deleteFile(String filePath) {

        String pathname = getBasepath() + filePath;

        new File(pathname).deleteOnExit();
    }

    @Override
    public void saveFile(File file, String filePath) {

        String pathname = getBasepath() + filePath;

        File target = new File(pathname);

        try {
            FileUtil.copyFile(file, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
