package com.company.wapi.controller;

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

import com.company.api.account.service.EnumAccountType;
import com.company.api.fs.service.FileIndexService;
import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumIndentStatus;
import com.company.api.indent.service.EnumNodeStepStatus;
import com.company.api.indent.service.IndentContactService;
import com.company.api.indent.service.IndentContentService;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentFreightService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.api.indent.service.IndentService;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.api.sctype.service.SctypeNodeService;
import com.company.api.sctype.service.SctypeService;
import com.company.po.fs.FileIndex;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContact;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentFreight;
import com.company.po.indent.IndentNode;
import com.company.po.indent.IndentNodeStep;
import com.company.po.indent.IndentNodeStepItem;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.util.Dto;

@Controller("wapiIndentNodeController")
@RequestMapping(value = "/indentNode")
public class IndentNodeController extends BaseController {

    @Autowired
    private IndentService indentService;
    
    @Autowired
    private IndentContentService indentContentService;
    
    @Autowired
    private IndentContactService indentContactService;

    @Autowired
    private IndentFreightService indentFreightService;

    @Autowired
    private IndentNodeService indentNodeService;

    @Autowired
    private IndentNodeStepService indentNodeStepService;

    @Autowired
    private IndentNodeStepItemService indentNodeStepItemService;
    
    @Autowired
    private IndentExceptionService indentExceptionService;

    @Autowired
    private FileIndexService fileIndexService;
    
    @Autowired
    private FileManagerService fileManagerService;
    
    @Autowired
    private SctypeService sctypeService;

    @Autowired
    private SctypeNodeService sctypeNodeService;

    @Autowired
    private ScnodeService scnodeService;

    @Autowired
    private ScnodeStepService scnodeStepService;

    @Autowired
    private ScnodeStepItemService scnodeStepItemService;

    @RequestMapping(value = "/indentProgress")
    @ResponseBody
    public Dto indentProgress(HttpServletRequest request,//
            @RequestParam(value = "indentId") String indentId,//
            @RequestParam(value = "accountType") String accountType) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, true);
        //订单查询出节点列表
        List<IndentNode> indentNodeList = indentNodeService.findList(Filter.eq("indentId", indentId),Order.asc("orders"));
        Dto data = new Dto();
        Indent indent = indentService.find(indentId);
        data.put("indent", indent);
        if (EnumAccountType.MANAGER.name().equals(accountType.toUpperCase()) ||//
                EnumExecuteStatus.AFTER.name().equals(indent.getExecuteStatus())) {
            data.put("status", EnumIndentStatus.EXCEPTION.name());
        } else {
            data.put("status", indent.getStatus());
        }
        data.put("indentNodeList", indentNodeList);
        ret.put(TAG_DATA, data);
        return ret;
    }

    @RequestMapping(value = "/indentNodeStep")
    @ResponseBody
    public Dto indentNodeStep(HttpServletRequest request, //
            @RequestParam(value = "indentId") String indentId,//
            @RequestParam(value = "indentNodeId") String indentNodeId) {
        Dto ret = new Dto();
        Dto data = new Dto();
        //查询节点是否有异常
        List<IndentException> findList = indentExceptionService.findList(Filter.eq("indentNodeId", indentNodeId), Order.desc("createDate"));
        IndentFreight indentFreight = indentFreightService.findOne(Filter.eq("indentId", indentId));
        if (indentFreight != null) {
            if ("ALL".equals(indentFreight.getCarModel())) {
                indentFreight.setCarModelDisp("不限");
            }
        }
        IndentContact indentContact = indentContactService.findOne(Filter.eq("indentId", indentId));
        Indent indent = indentService.find(indentId);
        IndentException indentException = new IndentException();
        if (findList.size() > 0) {
            indentException = findList.get(0);
        }
        data.put("indentException", indentException);
        //根据status  来判断是否需要改
        if (EnumExecuteStatus.AFTER.name().equals(indent.getExecuteStatus())) {
            data.put("status", EnumIndentStatus.EXCEPTION.name());
        } else {
            data.put("status", indent.getStatus());
        }
        //判断当前节点是否完成
        IndentNode indentNode = indentNodeService.find(indentNodeId);
        data.add("isFinished", false);
        if (EnumExecuteStatus.AFTER.name().equals(indentNode.getNodeExecuteStatus())) {
            data.put("bottonViem", false);
            data.add("isFinished", true);
        }
        data.put("freight", indentFreight);
        data.put("contact", indentContact);
        List<IndentNodeStep> indentNodeStepList = indentNodeStepService.findList(Filter.eq("indentNodeId", indentNodeId));
        for (IndentNodeStep indentNodeStep : indentNodeStepList) {
            List<IndentNodeStepItem> indentNodeStepItemList = indentNodeStepItemService.findList(Filter.eq("indentNodeStepId", indentNodeStep.getId()));
            for (IndentNodeStepItem indentNodeStepItem : indentNodeStepItemList) {
                List<FileIndex> findByBelongToAndExts = fileManagerService.findByBelongToAndExts(indentNodeStepItem.getId(), "INDENT", "ITEM");
                if (findByBelongToAndExts != null) {
                    //示例图片
                    List<FileIndex> sketchImgs = null;
                    if (indentNodeStepItem.getIsSketch() != null && indentNodeStepItem.getIsSketch() > 0) {
                        sketchImgs = fileIndexService.findByBelongTo(indentNodeStepItem.getNodeStepItemId());
                        for (FileIndex fileIndex : sketchImgs) {
                            fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                            fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
                        }
                    }
                    for (FileIndex fileIndex : findByBelongToAndExts) {
                        fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                        fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
                    }
                    indentNodeStepItem.setImgUrlList(findByBelongToAndExts);
                    indentNodeStepItem.setSketchImgUrlList(sketchImgs);
                }
            }
            indentNodeStep.setIndentNodeStepItemList(indentNodeStepItemList);
        }
        
        List<IndentNodeStep> finishedStepList = indentNodeStepService.findList(Filter.eq("indentNodeId", indentNodeId));
        Long count = indentNodeStepService.count(Filter.eq("nodeStepStatus", EnumNodeStepStatus.FINISH.name()),Filter.eq("indentNodeId", indentNodeId));
        int stepCount= count.intValue();
        if (stepCount == finishedStepList.size()) {
            data.put("bottonViem", true);
        }
        if (EnumExecuteStatus.AFTER.name().equals(indentNode.getNodeExecuteStatus())) {
            data.put("bottonViem", false);
        }
        data.put("indentNodeStepList", indentNodeStepList);
        long finishedNodeCount = indentNodeService.count(Filter.eq("indentId", indentId), Filter.eq("nodeExecuteStatus", EnumExecuteStatus.AFTER.name()));
        long nodeCount = indentNodeService.count(Filter.eq("indentId", indentId));
        data.put("indentFinshed", false);
        if (finishedNodeCount == nodeCount) {
            data.put("indentFinshed", true);
        }
        ret.put(TAG_DATA, data);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    @RequestMapping(value = "/indentNodeStepItem")
    @ResponseBody
    public Dto indentNodeStepItem(HttpServletRequest request,//
            @RequestParam(value = "indentNodeStepItemId") String indentNodeStepItemId) {
        Dto ret = new Dto();
        Map<String, MultipartFile> files = null;
        if (request instanceof MultipartHttpServletRequest) {
            files = ((MultipartHttpServletRequest) request).getFileMap();
        }
        ret = indentNodeStepItemService.dealItem(files, indentNodeStepItemId);
        
        return ret;
    }

    @RequestMapping(value = "/confirmIndentNode")
    @ResponseBody
    public Dto confirmIndentNode(@RequestParam(value = "indentNodeId") String indentNodeId) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, true);
        IndentNode indentNode = indentNodeService.find(indentNodeId);
        List<IndentNodeStep> stepList = indentNodeStepService.findList(Filter.eq("indentNodeId", indentNodeId));
        Long count = indentNodeStepService.count(Filter.eq("nodeStepStatus", EnumNodeStepStatus.FINISH.name()),Filter.eq("indentNodeId", indentNodeId));
        int stepCount= count.intValue();
        if (stepCount == stepList.size()) {
            indentNodeService.nodeFinished(indentNode);
        }
        return ret;
    }

}