package com.company.web.indent.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.api.indent.service.EnumExceptionResultStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentExceptionDetailService;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentService;
import com.company.po.admin.Admin;
import com.company.po.fs.FileIndex;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentExceptionDetail;
import com.company.po.indent.IndentNode;
import com.company.web.BaseController;
import com.company.api.fw.service.SysDictService;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.dto.SysDict;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.StringUtil;

@Controller("webIndentExcController")
@RequestMapping(value = "/indentExc")
public class IndentExcController extends BaseController {

    private static final String PATH = "indent";

    @Autowired
    private IndentExceptionService service;
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private IndentNodeService indentNodeService;
    
    @Autowired
    private IndentExceptionDetailService indentExceptionDetailService;
    
    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private FileManagerService fileManagerService;
    
    @Autowired
    public void setBaseService(IndentExceptionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/indentExcList")
    protected String indentExcList(@RequestParam(value = "indentNodeId", required = false) String indentNodeId,
            @RequestParam(value = "indentId", required = false) String indentId,
            ModelMap model) {
        if (StringUtil.isNotEmpty(indentNodeId)) {
            model.addAttribute("indentNodeId", indentNodeId);
            IndentNode indentNode = indentNodeService.find(indentNodeId);
            model.put("indentId", indentNode.getIndentId());
            return PATH + "/indentNodeExceptionList";
        }
        if (StringUtil.isNotEmpty(indentId)) {
            model.put("indentId", indentId);
            return PATH + "/indentExceptionList";
        }
        return "";
    }
    
    @RequestMapping(value = "/nodeList")
    @ResponseBody
    public List<IndentException> nodeList(String indentNodeId) {
        List<IndentException> ieList = service.findList(Filter.eq("indentNodeId", indentNodeId));
        return ieList;
    }
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<IndentException> list(@RequestParam("indentId") String indentId) {
        List<IndentException> ieList = New.list();
        if (StringUtil.isNotEmpty(indentId)) {
            ieList = service.findList(Filter.eq("indentId", indentId));
        }
        return ieList;
    }
    
    @RequestMapping(value = "/manageException")
    public String manageException(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {
        model.put("id", id);
        model.put("resultList", sysDictService.listGroupCopy(EnumCodes.EXCEPTION_RESULT, SysDict.CHECK));
        model.put("partList", sysDictService.listGroupCopy(DictCodes.PART, SysDict.CHECK));
        return PATH + "/manageException";
    }
    
    @RequestMapping(value = "/findException")
    @ResponseBody
    public IndentException findException(@RequestParam("id") String id) {
        IndentException exception = service.find(id);
        
        if (EnumExecuteStatus.BEFORE.name().equals(exception.getExecuteStatus())) {
            service.scanException(id);
        }
        
        List<FileIndex> belongToAndExts = fileManagerService.findByBelongToAndExts(id, "INDENT", "EXCEPTION");
        if (belongToAndExts != null && belongToAndExts.size() > 0 ) {
            for (FileIndex fileIndex : belongToAndExts) {
                fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
            }
            exception.setImgUrlList(belongToAndExts);
        }
        return exception;
    }
    
    @RequestMapping(value = "/update")
    @ResponseBody
    public Dto update(@RequestBody IndentException data) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        String id = data.getId();
        IndentException indentException = service.find(id);
        //有未处理的异常暂不能操作
        List<IndentException> indentExceptionFindList = service.findExceptions(indentException);
        
        if (indentExceptionFindList != null && indentExceptionFindList.size() > 0) {
            ret.put(TAG_MSG, "该订单你还有未处理完的异常信息！");
            ret.put(TAG_SUCCESS, false);
            return ret;
        }
        
        Admin admin = (Admin) ThreadContext.getContextBean();
        data.setAdminId(admin.getId());
        data.setAdminName1(admin.getName1());
        if (EnumExceptionResultStatus.SUSPEND.name().equals(data.getResult())) {
            data.setExecuteStatus(EnumExecuteStatus.CENTRE.name());
            data.setAcceptedDate(new Date());
        } else {
            data.setExecuteStatus(EnumExecuteStatus.AFTER.name());
            data.setFinishDate(new Date());
        }
        service.updateException(data);
        IndentExceptionDetail indentExceptionDetail = new IndentExceptionDetail();
        indentExceptionDetail.setAdminId(admin.getId());
        indentExceptionDetail.setAdminName1(admin.getName1());
        indentExceptionDetail.setExceptionId(data.getId());
        indentExceptionDetail.setOperateDate(new Date());
        indentExceptionDetail.setResult(data.getResult());
        indentExceptionDetail.setComment(data.getIdea());
        String part = data.getPart() == null? "" : data.getPart();
        indentExceptionDetail.setPart(part);
        indentExceptionDetailService.save(indentExceptionDetail);
        String indentId = data.getIndentId();
        Long count = service.count(Filter.eq("indentId", indentId), Filter.ne("executeStatus", EnumExecuteStatus.AFTER.name()));
        if (count.intValue() <= 1) {
            if (EnumExceptionResultStatus.CONTINUE.name().equals(data.getResult())) {
                indentService.changeIndentNormal(data);
            } else if (EnumExceptionResultStatus.SUSPEND.name().equals(data.getResult())) {
                indentService.changeIndentPause(data);
            } else if (EnumExceptionResultStatus.OVER.name().equals(data.getResult())) {
                indentService.overIndent(data);
            }
        }
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
}