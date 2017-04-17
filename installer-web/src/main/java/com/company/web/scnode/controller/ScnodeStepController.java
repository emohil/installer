package com.company.web.scnode.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.scnode.service.ScnodeStepService;
import com.company.po.scnode.Scnode;
import com.company.po.scnode.ScnodeStep;
import com.company.web.BaseEntryController;
import com.company.api.fw.service.SysDictService;

@Controller("webScnodeStepController")
@RequestMapping(value = "/scnodeStep")
public class ScnodeStepController extends BaseEntryController<ScnodeStep> {

    private static final String PATH = "scnode/step";

    private ScnodeStepService service;

    @Autowired
    private ScnodeService scnodeService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public void setBaseService(ScnodeStepService service) {
        super.setBaseService(service);

        this.service = service;
    }

    @RequestMapping(value = "/scnodeStepAdd")
    public String scnodeStepAdd(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "scnodeId") String scnodeId) {

        Scnode scnode = scnodeService.find(scnodeId);

        model.addAttribute("stepTypeList", sysDictService.listGroupCopy(EnumCodes.SCNODE_STEP_TYPE));
        model.addAttribute("sortList", sysDictService.listGroupCopy(DictCodes.SERVICE_SORT));
        model.addAttribute("scnode", scnode);
        model.addAttribute(TAG_PATH, PATH);

        return PATH + "/scnodeStepAdd";
    }

    @RequestMapping(value = "/scnodeStepEdit")
    public String scnodeStepEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id") String id) {

        model.addAttribute("stepTypeList", sysDictService.listGroupCopy(EnumCodes.SCNODE_STEP_TYPE));
        model.addAttribute("sortList", sysDictService.listGroupCopy(DictCodes.SERVICE_SORT));
        model.put(TAG_ID, id);
        model.put(TAG_PATH, PATH);
        return PATH + "/scnodeStepEdit";
    }

    @Override
    @RequestMapping("/load")
    @ResponseBody
    public ScnodeStep load(String id) {
        return service.load(id);
    }
}