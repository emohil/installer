package com.company.wapi.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentService;
import com.company.po.account.Account;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.po.indent.IndentException;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.util.Dto;

@Controller("wapiIndentExceptionController")
@RequestMapping(value = "/indentException")
public class IndentExceptionController extends BaseController {
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private IndentExceptionService indentExceptionService;
    
    @Autowired
    private FileManagerService fileManagerService;
    
    //问题反馈
    @RequestMapping(value = "/reportIndentException")
    @ResponseBody
    public Dto reportIndentException(HttpServletRequest request,//
            @RequestParam(value = "indentId") String indentId,//
            @RequestParam(value = "indentNodeId") String indentNodeId,//
            @RequestParam(value = "content") String content) {
        Dto ret = new Dto();
        
        Account account = (Account) ThreadContext.getContextBean();
        ret.put(TAG_SUCCESS, false);
        IndentException indentException = new IndentException();
        indentException.setContent(content);
        indentException.setRepId(account.getId());
        indentException.setIndentId(indentId);
        indentException.setIndentNodeId(indentNodeId);
        indentException.setExecuteStatus(EnumExecuteStatus.BEFORE.name());
        indentException.setResult("");
        indentExceptionService.saveException(indentException);
        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();
            Iterator<MultipartFile> iterator = files.values().iterator();
            while (iterator.hasNext()) {
                MultipartFile multipartFile = (MultipartFile) iterator.next();
                UnFileIndex ufi = new UnFileIndex(multipartFile, indentException.getId(), "INDENT", "EXCEPTION");
                fileManagerService.save(ufi);
            }
        }
        
        
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    @RequestMapping(value = "/indentExceptionList")
    @ResponseBody
    public Dto indentExceptionList(HttpServletRequest request,//
            @RequestParam(value = "indentNodeId") String indentNodeId) {
        Dto ret = new Dto();
        //获取列表
        List<IndentException> indentExceptionList = indentExceptionService.findList(Filter.eq("indentNodeId", indentNodeId));
        for (IndentException indentException : indentExceptionList) {
            List<FileIndex> belongToAndExts = fileManagerService.findByBelongToAndExts(indentException.getId(), "INDENT", "EXCEPTION");
            if (belongToAndExts != null && belongToAndExts.size() > 0) {
                for (FileIndex fileIndex : belongToAndExts) {
                    fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                    fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
                }
                indentException.setImgUrlList(belongToAndExts);
            }
        }
        Dto data = new Dto();
        data.put("indentExceptionList", indentExceptionList);
        ret.put(TAG_DATA, data);
        ret.put(TAG_SUCCESS, true);
        
        return ret;
    }
    
}