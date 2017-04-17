package com.company.wap.indent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileIndexService;
import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.EnumEvaluateStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentEvaluateService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.api.indent.service.IndentService;
import com.company.api.scnode.service.EnumScnodeStepType;
import com.company.po.fs.FileIndex;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentEvaluate;
import com.company.po.indent.IndentNode;
import com.company.po.indent.IndentNodeStep;
import com.company.po.indent.IndentNodeStepItem;
import com.company.dto.Filter;
import com.company.util.New;

@Controller
public class OController {
    
    @Autowired
    private IndentEvaluateService service;

    @Autowired
    private IndentService indentService;

    @Autowired
    private IndentNodeService indentNodeService;

    @Autowired
    private IndentNodeStepService indentNodeStepService;

    @Autowired
    private FileIndexService fileIndexService;

    @Autowired
    private IndentNodeStepItemService indentNodeStepItemService;

    @Autowired
    private FileManagerService fileManagerService;

    @RequestMapping(value = "o/{code1}")
    public String evaluate(@PathVariable(value = "code1") String code1, ModelMap model) {

        Indent indent = indentService.findByCode1(code1);
        if (indent == null) {
            // 无效订单号，没有找到订单相关信息
            return "o/indentNoFound";
        }
        
        String executeStatus = indent.getExecuteStatus();//订单执行状态
        String evaluateStatus = indent.getEvaluateStatus();//订单评价状态
        
        
        if (evaluateStatus.equals(EnumEvaluateStatus.EVALUATED.name()) && executeStatus.equals(EnumExecuteStatus.AFTER.name())) {//业主已评价，并且完成
            IndentEvaluate indentEvaluate = service.findByIndentId(indent.getId());
            model.put("indentEvaluate", indentEvaluate);
            return "o/evaluateSuccess";
        }
        if (evaluateStatus.equals(EnumEvaluateStatus.EVALUATED.name()) && executeStatus.equals(EnumExecuteStatus.CENTRE.name())) {//业主已评价，但是未完成
            return "o/evaluateUnDone";
        }
        
        
        model.put("workerId", indent.getWorkerId());
        model.put("indentId",indent.getId());
        List<IndentNodeStep> indentNodeStepList = New.list();
        //测量类订单
        if (indent.getServeType().equals("C")) {
            //获取测量的节点
            IndentNode measureNode = indentNodeService.findSpecifiedNodeByIndentId(//
                    indent.getId(), IndentNodeService.MEASURE);
            //获取节点步骤
            IndentNodeStep indentNodeStep = indentNodeStepService.findByNodeIdAndType(//
                    measureNode.getId(), EnumScnodeStepType.UPLOAD.name());
            //获取节点步骤子项
            IndentNodeStepItem indentNodeStepItem = indentNodeStepItemService.findByNodeStepId(//
                    indentNodeStep.getId());
            List<FileIndex> imgUrls = fileIndexService.findByBelongToAndExts(indentNodeStepItem.getId(), "INDENT", "ITEM");
            if (imgUrls != null && imgUrls.size() > 0) {
                for (FileIndex fileIndex : imgUrls) {
                    String filePath = fileIndex.getFilePath();
                    fileIndex.setFileUrl(fileManagerService.getFileUrlByFilepath(filePath));
                    String path = fileManagerService.getThumbUrlById(fileIndex.getId());
                    fileIndex.setThumbUrl(path);
                }
            }
            indentNodeStep.setFileIndexList(imgUrls);
            
            indentNodeStepList.add(indentNodeStep);
        } else {//其他两种订单
            IndentNode selfCheckNode = indentNodeService.findSpecifiedNodeByIndentId(//
                    indent.getId(), IndentNodeService.NODE_SELFCHECK);
            // 根据订单code1获取自检验收节点id
            String indentNodeId = selfCheckNode.getId();
            // 根据自检验收节点ID获取节点步骤的List
            indentNodeStepList = indentNodeStepService
                    .findList(Filter.eq("indentNodeId", indentNodeId));
            for (IndentNodeStep indentNodeStep : indentNodeStepList) {
                List<IndentNodeStepItem> indentNodeStepItems = indentNodeStepItemService //
                        .findList(Filter.eq("indentNodeStepId", indentNodeStep.getId()));
                List<FileIndex> imgUrls = New.list();
                for (IndentNodeStepItem indentNodeStepItem : indentNodeStepItems) {
                    List<FileIndex> imgUrlList = fileIndexService.findByBelongToAndExts(indentNodeStepItem.getId(), "INDENT", "ITEM");
                    if (imgUrlList != null && imgUrlList.size() > 0) {
                        for (FileIndex fileIndex : imgUrlList) {
                            String filePath = fileIndex.getFilePath();
                            fileIndex.setFileUrl(fileManagerService.getFileUrlByFilepath(filePath));
                            String path = fileManagerService.getThumbUrlById(fileIndex.getId());
                            fileIndex.setThumbUrl(path);
                            imgUrls.add(fileIndex);
                        }
                    }
                }
                indentNodeStep.setFileIndexList(imgUrls);
            }
        }
        model.put("indentNodeStepList", indentNodeStepList);
        if (evaluateStatus.equals(EnumEvaluateStatus.UNEVALUATED.name()) && executeStatus.equals(EnumExecuteStatus.AFTER.name())) {//系统自动判定完成
            return "o/evaluateAgain";
        }
        return "o/evaluate";
    }

    @RequestMapping(value = "o/evaluateSuccess")
    public String evaluateSuccess(@RequestParam("indentId") String identId,
            ModelMap model) {
        IndentEvaluate indentEvaluate = service.findByIndentId(identId);
        if (indentEvaluate.getWorkdone().equals(EnumYesno.NO.name())) {
            return "o/evaluateUnDone";
        }
        model.put("indentEvaluate", indentEvaluate);
        return "o/evaluateSuccess";
    }
}
