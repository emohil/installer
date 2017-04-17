package com.company.wapi.fs.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fs.service.FileManagerService;
import com.company.po.fs.FileIndex;
import com.company.util.FileUtil;
import com.company.util.ImageUtil;

@Controller("wapFileManagerController")
@RequestMapping(value = "/fs")
public class FileManagerController {

    private static final Logger log = LoggerFactory.getLogger(FileManagerController.class);

    @Autowired
    private FileManagerService service;

    @RequestMapping(value = "/download")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response, String fileid) {

        String userAgent = request.getHeader("user-agent");

        try {
            FileIndex fi = service.get(fileid);

            if (fi == null) {
                return;
            }

            File file = service.getFileByFilepath(fi.getFilePath());
            String filename = fi.getFileName();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;" + transformFilename(userAgent, filename));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
        }
    }

    @RequestMapping(value = "/thumbnail")
    @ResponseBody
    public void thumbnail(HttpServletRequest request, HttpServletResponse response, String fileid) {
        FileIndex fi = service.get(fileid);
        if (fi == null) {
            log.error("Unable to load file by fileid:{}", fileid);
            return;
        }

        File thumbnailFile = service.getThumbByFilepath(fi.getFilePath());
        
        if (thumbnailFile.exists()) {
            try {
                FileUtil.copyFile(thumbnailFile, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    private String transformFilename(String userAgent, String filename) throws IOException {

        String encodeName = URLEncoder.encode(filename, "UTF8").replaceAll("\\+", "%20");
        // 如果没有UA，则默认使用IE的方式进行编码
        String rtn = "filename=\"" + encodeName + "\"";
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            // IE浏览器，只能采用URLEncoder编码
            if (userAgent.indexOf("msie") != -1) {
                rtn = "filename=\"" + encodeName + "\"";
            }
            // Opera浏览器只能采用filename*
            else if (userAgent.indexOf("opera") != -1) {
                rtn = "filename*=UTF-8''" + encodeName;
            }
            // Safari浏览器，只能采用ISO编码的中文输出
            else if (userAgent.indexOf("safari") != -1) {
                rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
            }
            // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
            else if (userAgent.indexOf("applewebkit") != -1) {
                encodeName = MimeUtility.encodeText(filename, "UTF8", "B");
                rtn = "filename=\"" + encodeName + "\"";
            }
            // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
            else if (userAgent.indexOf("mozilla") != -1) {
                rtn = "filename*=UTF-8''" + encodeName;
            }
        }

        return rtn;
    }
}
