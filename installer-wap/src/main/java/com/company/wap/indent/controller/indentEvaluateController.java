package com.company.wap.indent.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.IndentEvaluateService;
import com.company.po.fs.UnFileIndex;
import com.company.po.indent.IndentEvaluate;
import com.company.util.Dto;
import com.company.util.StringUtil;
import com.company.util.json.JacksonHelper;

@Controller("wapIndentEvaluateController")
@RequestMapping(value = "/indentEvaluate")
public class indentEvaluateController {
    
    @Autowired
    private IndentEvaluateService indentEvaluateService;
    
    @Autowired
    private FileManagerService fileService;
    
    @RequestMapping(value = "/evaluate")
    @ResponseBody
    public Dto evaluate (//
            @RequestParam(value = "imgFile", required = false) MultipartFile[] imgFile, //
            @RequestParam(value = "data", required = false) String data) {
        
        Dto rt = new Dto();
        rt.put("success", false);
        IndentEvaluate indentEvaluate = JacksonHelper.toObject(data, IndentEvaluate.class);
        IndentEvaluate findByIndentId = indentEvaluateService.findByIndentId(indentEvaluate.getIndentId());
        String workerDone;
        if (StringUtil.isNotBlank(indentEvaluate.getWorkdone())) {
            workerDone = indentEvaluate.getWorkdone();
        } else {
            workerDone = "1";
        }
        
        if (workerDone.equals("0")) {
            indentEvaluate.setWorkdone(EnumYesno.NO.name());
        } else {
            indentEvaluate.setWorkdone(EnumYesno.YES.name());
        }
        
        if (findByIndentId != null) {
            rt.put("msg", "谢谢您的参与！您已提交评价，请勿重复评价~");
            return rt;
        }
        if (imgFile != null) {
            for (int i = 0; i < imgFile.length; i++) {
                UnFileIndex ufi = new UnFileIndex(imgFile[i], indentEvaluate.getIndentId(), "INDENTEVALUATE", "");
                fileService.save(ufi);
            }
        }
        indentEvaluateService.saveEvalute(indentEvaluate);
        
        rt.put("success", true);
        return rt;
    }

}
