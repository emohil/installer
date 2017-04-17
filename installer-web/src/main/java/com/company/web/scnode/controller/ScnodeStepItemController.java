package com.company.web.scnode.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.fw.EnumCodes;
import com.company.api.scnode.service.ScnodeStepItemService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.po.fs.FileIndex;
import com.company.po.scnode.ScnodeStep;
import com.company.po.scnode.ScnodeStepItem;
import com.company.web.BaseEntryController;
import com.company.api.fw.service.SysDictService;
import com.company.util.Dto;
import com.company.util.json.JacksonHelper;

@Controller("webScnodeStepItemController")
@RequestMapping(value = "/scnodeStepItem")
public class ScnodeStepItemController extends BaseEntryController<ScnodeStepItem> {

    private static final String PATH = "scnode/stepItem";

    private ScnodeStepItemService service;
    
    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private ScnodeStepService scnodeStepService;

    @Autowired
    public void setBaseService(ScnodeStepItemService service) {
        super.setBaseService(service);

        this.service = service;
    }

    @RequestMapping(value = "/scnodeStepItemAdd")
    public String scnodeStepItemAdd(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "scnodeStepId") String scnodeStepId) {

        model.addAttribute("confirmTypeList", sysDictService.listGroupCopy(EnumCodes.SCNODE_CONFIRM_TYPE));
        
        ScnodeStep scnodeStep = scnodeStepService.find(scnodeStepId);
        
        model.addAttribute("scnodeStep", scnodeStep);
        model.addAttribute(TAG_PATH, PATH);
        
        return PATH + "/scnodeStepItemAdd";
    }

    @RequestMapping(value = "/scnodeStepItemEdit")
    public String scnodeStepItemEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.addAttribute("confirmTypeList", sysDictService.listGroupCopy(EnumCodes.SCNODE_CONFIRM_TYPE));
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/scnodeStepItemEdit";
    }
    
    
    @RequestMapping(value = "/doSave")
    @ResponseBody
    public Dto doSave(HttpServletRequest request, //
            @RequestParam(value = "sketchFiles", required = false) MultipartFile[] sketchFiles, //
            @RequestParam(value = "data", required = false) String data) {
        
        ScnodeStepItem item = JacksonHelper.toObject(data, ScnodeStepItem.class);
        
        service.save(item);
        service.saveSketchFiles(item.getId(), sketchFiles);
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    @RequestMapping(value = "/doUpdate")
    @ResponseBody
    public Dto doUpdate(HttpServletRequest request, //
            @RequestParam(value = "sketchFiles", required = false) MultipartFile[] sketchFiles, //
            @RequestParam(value = "data", required = false) String data) {
        
        ScnodeStepItem item = JacksonHelper.toObject(data, ScnodeStepItem.class);
        service.update(item);
        service.saveSketchFiles(item.getId(), sketchFiles);
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    
    @Override
    @RequestMapping("/load")
    @ResponseBody
    public ScnodeStepItem load(String id) {
        return service.load(id);
    }
    
    
    @RequestMapping(value = "/loadSketchFiles")
    @ResponseBody
    public List<FileIndex> loadSketchFiles(@RequestParam(value = "id") String id) {
        return service.loadSketchFiles(id);
    }
}