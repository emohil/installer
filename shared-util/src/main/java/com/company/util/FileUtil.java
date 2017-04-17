package com.company.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * 文件处理工具类.
 * 
 * @author lihome
 *
 * @see org.apache.commons.io.FileUtils
 */
public class FileUtil extends FileUtils {

    /**
     * 将InputStream流保存到指定的文件中.
     * 
     * @param inputStream
     * @param file
     */
    public static void saveFile(InputStream inputStream, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024 * 100];
            while ((read = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 创建临时文件
     * 
     * @param inputStream
     * @param name
     *            文件名
     * @param ext
     *            扩展名
     * @param tmpDirFile
     *            临时文件夹目录
     * @return
     * @throws IOException
     */
    public static File createTmpFile(InputStream inputStream, String name, String ext, File tmpDirFile)
            throws IOException {
        File tmpFile;
        if (tmpDirFile == null) {
            tmpFile = File.createTempFile(name, '.' + ext);
        } else {
            tmpFile = File.createTempFile(name, '.' + ext, tmpDirFile);
        }
        tmpFile.deleteOnExit();

        saveFile(inputStream, tmpFile);

        return tmpFile;

    }

    /**
     * 创建临时文件
     * 
     * @param inputStream
     * @param name
     *            文件名
     * @param ext
     *            扩展名
     * @return
     * @throws IOException
     */
    public static File createTmpFile(InputStream inputStream, String name, String ext) throws IOException {
        return createTmpFile(inputStream, name, ext, null);
    }
}