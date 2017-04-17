package com.company.api.fs.service;

import java.io.File;

public interface FileSaver {

    void deleteFile(String filePath);

    void saveFile(File file, String filePath);
}
