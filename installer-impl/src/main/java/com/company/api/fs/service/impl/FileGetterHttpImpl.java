package com.company.api.fs.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.company.api.fs.service.FileGetter;
import com.company.api.fw.util.EnvConfig;

@Service("fileGetterHttp")
public class FileGetterHttpImpl implements FileGetter {

    @Override
    public File getFile(String filePath) {
        
        String basepath = EnvConfig.getProperty(EnvConfig.UPLOAD_BASEPATH);
        
        String path = basepath;
        if (!path.endsWith("/")) {
            path += "/";
        }
        path += filePath;
        
        return new File(path);
    }

    @Override
    public String getFileUrl(String filePath) {
        String basepath = EnvConfig.getProperty(EnvConfig.DOWNLOAD_BASEPATH);

        String url = basepath;
        
        if (!url.endsWith("/")) {
            url += "/";
        }
        url += filePath;
        
        return url;
    }

}
