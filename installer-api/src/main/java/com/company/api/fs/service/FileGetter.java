package com.company.api.fs.service;

import java.io.File;

public interface FileGetter {

    File getFile(String filePath);

    String getFileUrl(String filePath);

}
