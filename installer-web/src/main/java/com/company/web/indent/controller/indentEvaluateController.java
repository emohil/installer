package com.company.web.indent.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.IndentContactService;
import com.company.api.indent.service.IndentEvaluateService;
import com.company.api.indent.service.IndentService;
import com.company.po.fs.FileIndex;
import com.company.po.indent.IndentContact;
import com.company.po.indent.IndentEvaluate;
import com.company.util.New;

@Controller("webIndentEvaluateController")
@RequestMapping(value = "/indentEvaluate")
public class indentEvaluateController {
    
    private static final String PATH = "indent";
    
    @Autowired
    private IndentEvaluateService service;
    
    @Autowired
    private IndentContactService indentContactService;
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private FileManagerService fileService;
    
    @RequestMapping(value = "/indentEvaluateMsg")
    public String indentEvaluateMsg(@RequestParam("indentId") String indentId,
            ModelMap model) {
        model.put("indentId", indentId);
        return PATH + "/indentEvaluateMsg";
    }
    
    @RequestMapping(value = "/findByIndentId")
    @ResponseBody
    public IndentEvaluate findByIndentId(@RequestParam("indentId") String indentId) {
        IndentEvaluate indentEvaluate = service.findByIndentId(indentId);
        IndentContact indentContact = indentContactService.findByIndentId(indentId);
        indentEvaluate.setIndentContact(indentContact);
        List<FileIndex> imgList = fileService.findByBelongToAndExts(indentId, "INDENTEVALUATE", "");
        List<String> imgUrlList = New.list();
        if (imgList.size() > 0) {
            for (FileIndex fileIndex : imgList) {
                String imgUrl = fileService.getFileUrlByFilepath(fileIndex.getFilePath());
                imgUrlList.add(imgUrl);
            }
        }
        indentEvaluate.setImgUrlList(imgUrlList);
        return indentEvaluate;
    }

}
