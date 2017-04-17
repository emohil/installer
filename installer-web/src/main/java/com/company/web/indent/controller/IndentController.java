package com.company.web.indent.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.api.aparty.service.ApartyService;
import com.company.api.dict.service.EnumPart;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.DictCodes;
import com.company.api.fw.EnumCodes;
import com.company.api.indent.service.EnumExceptionResultStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumIndentStatus;
import com.company.api.indent.service.EnumIndentVisitSource;
import com.company.api.indent.service.IndentContentService;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentFreightService;
import com.company.api.indent.service.IndentSctypeSortService;
import com.company.api.indent.service.IndentService;
import com.company.api.item.service.ItemPriceService;
import com.company.api.item.service.ItemService;
import com.company.api.scnode.service.ScnodeService;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.po.admin.Admin;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContent;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentFreight;
import com.company.po.indent.IndentSort;
import com.company.po.item.Item;
import com.company.po.item.ItemPrice;
import com.company.po.scnode.Scnode;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeContent;
import com.company.po.sctype.SctypeSort;
import com.company.sf.indent.IndentSf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.util.BooleanUtil;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.StringUtil;
import com.company.util.json.JacksonHelper;

@Controller("webIndentController")
@RequestMapping(value = "/indent")
public class IndentController extends BasePagerController<IndentSf, Indent> {

    private static final String PATH = "indent";

    @Autowired
    private IndentService service;

    @Autowired
    private ApartyService apartyService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SctypeContentService sctypeContentService;

    @Autowired
    private SctypeSortService sctypeSortService;

    @Autowired
    private SctypeService sctypeService;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private IndentFreightService indentFreightService;
    
    @Autowired
    private IndentContentService indentContentService;
    
    @Autowired
    private IndentExceptionService indentExceptionService;
    
    @Autowired
    private IndentSctypeSortService indentSctypeSortService;

    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private ScnodeService scnodeService;

    @Autowired
    public void setBaseService(IndentService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(IndentSf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(IndentSf sf, int start, int limit, List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }

    @RequestMapping(value = "/indentList")
    protected String apartyList(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "itemId", required = false) String itemId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "mark", required = false) String mark) {
        if (StringUtil.isNotEmpty(itemId)){
            model.put("itemId", itemId);
        }
        if (StringUtil.isNotEmpty(status)){
            model.put("status", status);
        }
        if (StringUtil.isNotEmpty(mark)){
            model.put("mark", mark);
        }

        model.addAttribute("indentTypeList", sysDictService.listGroupCopy(DictCodes.SERVICE_SORT, SysDict.CHECK));
        List<Sctype> serveTypelist = sctypeService.findAll();
        Sctype sctype = new Sctype();
        sctype.setCode1(SysDict.ALL.getValue());
        sctype.setName1(SysDict.ALL.getText());
        serveTypelist.add(0, sctype);
        model.addAttribute("serveTypelist", serveTypelist);
        model.addAttribute("indentStatusList", sysDictService.listGroupCopy(EnumCodes.INDENT_STATUS, SysDict.CHECK));
        model.addAttribute("indentExcepStatusList",
                sysDictService.listGroupCopy(EnumCodes.INDENT_EXCEP_STATUS, SysDict.CHECK));
        model.addAttribute("indentExecuteStatusList",
                sysDictService.listGroupCopy(EnumCodes.INDENT_EXECUTE_STATUS, SysDict.CHECK));
        model.addAttribute("indentEvaluateStatusList",
                sysDictService.listGroupCopy(EnumCodes.EVALUATE_STATUS, SysDict.CHECK));
        model.addAttribute("indentEvaluateScoreList",
                sysDictService.listGroupCopy(DictCodes.EVALUATE_SCORE, SysDict.CHECK));
        model.addAttribute("sourceList", sysDictService.listGroupCopy(EnumCodes.INDENT_VISIT_SOURCE, SysDict.CHECK));

        List<Scnode> scnodeList = scnodeService.findAll();

        Scnode scnode = new Scnode();
        scnode.setCode1(SysDict.ALL.getValue());
        scnode.setName1(SysDict.ALL.getText());
        scnodeList.add(0, scnode);

        model.addAttribute("scnodeList", scnodeList);

        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/indentList";
    }
    
    @RequestMapping(value = "/indentAdd")
    public String indentAdd(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "id", required = false) String id, //
            @RequestParam(value = "serveType", required = false, defaultValue = "") String serveType) {

        List<Sctype> serveTypeList = New.list();
        if (StringUtil.isNotBlank(id)) {
            Item data = itemService.find(id);
            model.addAttribute(TAG_DATA, data);
            List<ItemPrice> itemPriceList = itemPriceService.findList(Filter.eq("itemId", id));

            excute(itemPriceList, serveTypeList);

            model.addAttribute("itemPriceList", itemPriceList);
            model.addAttribute("serveTypeList", serveTypeList);
        }
        
        model.addAttribute("indentSourceList", sysDictService.listGroupCopy(EnumCodes.INDENT_SOURCE));
        List<SysDict> listGroupCopy = sysDictService.listGroupCopy(DictCodes.VEHICLE);
        listGroupCopy.add(0, new SysDict("ALL", "不限", 0));
        model.addAttribute("vehicleList", listGroupCopy);
        model.addAttribute("serveTypelist", serveTypeList);
        model.addAttribute("serveType", serveType);
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/indentAdd";
    }

    private void excute(List<ItemPrice> itemPriceList, List<Sctype> serveTypeList) {
        List<SctypeContent> serveContentList = New.list();
        List<SctypeSort> serveSortList = New.list();

        for (ItemPrice itemPrice : itemPriceList) {
            String contentId = itemPrice.getServeContentId();
            serveContentList.add(sctypeContentService.find(contentId));
        }
        for (SctypeContent sctypeContent : serveContentList) {
            String sortId = sctypeContent.getSctypeSortId();
            SctypeSort sctypeSort = sctypeSortService.find(sortId);
            if (!serveSortList.contains(sctypeSort)) {
                serveSortList.add(sctypeSort);
            }
        }
        for (SctypeSort sctypeSort : serveSortList) {
            String typeId = sctypeSort.getSctypeId();
            Sctype sctype = sctypeService.find(typeId);
            if (!serveTypeList.contains(sctype)) {
                serveTypeList.add(sctype);
            }
        }
        for (Sctype sctype : serveTypeList) {
            List<SctypeSort> sortList = New.list();
            for (SctypeSort sctypeSort : serveSortList) {
                if (sctype.getId().equals(sctypeSort.getSctypeId())) {
                    sortList.add(sctypeSort);
                }
            }
            for (SctypeSort sctypeSort : sortList) {
                List<SctypeContent> contentList = New.list();
                for (SctypeContent sctypeContent : serveContentList) {
                    if (sctypeSort.getId().equals(sctypeContent.getSctypeSortId())) {
                        contentList.add(sctypeContent);
                    }
                }
                sctypeSort.setContentList(contentList);
            }
            sctype.setSortList(sortList);
        }
    }

    @RequestMapping(value = "/indentEdit")
    public String indentEdit(HttpServletRequest request, ModelMap model, //
            @RequestParam(value = "id", required = false) String id,//
            @RequestParam(value = "code1", required = false) String code1) {

        Indent indent = null;
        if (StringUtil.isNotBlank(id)) {
            indent = service.find(id);
        } else {
            indent = service.find(service.findOne(Filter.eq("code1", code1)).getId());
            
        }

        model.addAttribute("apartyName1", apartyService.find(indent.getApartyId()).getName1());
        model.addAttribute("itemName1", itemService.find(indent.getItemId()).getName1());
        if (StringUtil.isNotBlank(indent.getItemId())) {
            List<Sctype> serveTypeList = sctypeService.findList(Filter.eq("id", indent.getServeType()));
            List<IndentSort> indentSortList = indentSctypeSortService.findList(Filter.eq("indentId", indent.getId()));
            List<IndentContent> indentContentList = indentContentService.findList(Filter.eq("indentId", indent.getId()));
            List<String> sorts = new ArrayList<String>();
            List<String> contents = new ArrayList<String>();
            for (IndentSort indentSort : indentSortList) {
                sorts.add(indentSort.getSctypeSortId());
            }
            for (IndentContent indentContent : indentContentList) {
                contents.add(indentContent.getCode1());
            }
            for (Sctype sctype : serveTypeList) {
                List<SctypeSort> sctypeSortList = sctypeSortService.findList(Filter.in("id", sorts));
                for (SctypeSort sctypeSort : sctypeSortList) {
                    sctypeSort.setContentList(sctypeContentService.findList(Arrays.asList(Filter.in("id", contents), Filter.eq("sctypeSortId", sctypeSort.getId()))));
                }
                sctype.setSortList(sctypeSortList);
            }
            
            model.addAttribute("serveTypeList", serveTypeList);
        }
        model.addAttribute("indentSourceList", sysDictService.listGroupCopy(EnumCodes.INDENT_SOURCE));
        List<FileIndex> findByBelongToAndExts = fileManagerService.findByBelongToAndExts(indent.getId(), "INDENT", "DWG");
        for (FileIndex fileIndex : findByBelongToAndExts) {
            fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
            fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
        }
        indent.setDwgImgList(findByBelongToAndExts);
        List<IndentContent> indentContentList = indentContentService.findList(Filter.eq("indentId", indent.getId()));
        String sorts = "";
        for (IndentContent indentContent : indentContentList) {
            sorts += indentContent.getCode1();
        }
        List<SysDict> listGroupCopy = sysDictService.listGroupCopy(DictCodes.VEHICLE);
        listGroupCopy.add(0, new SysDict("ALL", "不限", 0));
        model.addAttribute("vehicleList", listGroupCopy);
        model.addAttribute("serveType", indent.getServeType());
        model.addAttribute("sorts", sorts);
        
        model.put(TAG_DATA, indent);
        model.put(TAG_PATH, PATH);
        return PATH + "/indentEdit";
    }

    @RequestMapping(value = "/doSave")
    @ResponseBody
    public Dto doSave(HttpServletRequest request,
            @RequestParam(value = "imgFiles", required = false) MultipartFile[] imgFiles, //
            @RequestParam(value = "data", required = false) String data) {

        Indent indent = JacksonHelper.toObject(data, Indent.class);

        Dto ret = new Dto();   
        IndentFreight freight = indent.getIndentFreight();
        if (freight != null) {
            IndentFreight findOne = indentFreightService.findOne(Filter.eq("code1", freight.getCode1()));
            if (findOne != null) {
                ret.put(TAG_SUCCESS, false);
                ret.put(TAG_MSG, "该订单的货运单号已存在！");
                return ret;
            }
        }
        
        if (BooleanUtil.getBoolean(indent.getIndentAssignSelected())) {
            indent.setIndentAssign(EnumYesno.YES.name());
        } else {
            indent.setIndentAssign(EnumYesno.NO.name());
        }
        Admin admin = (Admin) ThreadContext.getContextBean();
        indent.setAdminId(admin.getId());
        indent.setAdminName1(admin.getName1());
        indent.setSource(EnumIndentVisitSource.FIRST.name());
        service.saveIndent(indent);
        if (imgFiles != null && imgFiles.length > 0) {
            for (int i = 0; i < imgFiles.length; i++) {
                UnFileIndex ufi = new UnFileIndex(imgFiles[i], indent.getId(), "INDENT", "DWG");
                fileManagerService.save(ufi);
            }
        }
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_MSG, "订单保存成功！");
        return ret;
    }
    
    @RequestMapping(value = "/indentDelete")
    @ResponseBody
    public Dto indentDelete(HttpServletRequest request,//
            @RequestParam(value = "id") String id) {

        Dto ret = new Dto();
        ret = service.deleteIndent(id);
        return ret;
    }

    @RequestMapping(value = "exceptionIndentCount")
    @ResponseBody
    public Dto exceptionIndentCount() {
        Dto ret = new Dto();
        
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_DATA, service.exceptionIndentCount());
        return ret;
    }
    
    @RequestMapping(value = "indentPush")
    @ResponseBody
    public Dto indentPush(HttpServletRequest request,//
            @RequestParam(value = "indentId") String indentId) {
        Dto ret = new Dto();
        ret.put(TAG_MSG, "订单发布失败！");
        ret.put(TAG_SUCCESS, false);
        
        service.pushIndent(indentId);
        
        ret.put(TAG_MSG, "订单发布成功！");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    @RequestMapping(value = "/extraIndentAdd")
    public String extraIndentAdd(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "indentId") String indentId) {
        
        Indent data = service.find(indentId);

        data.setAparty(apartyService.find(data.getApartyId()));
        data.setItem(itemService.find(data.getItemId()));
        IndentException indentException = indentExceptionService.findOne(Filter.eq("indentId", indentId),//
                Filter.eq("result", EnumExceptionResultStatus.OVER.name()));
        if (StringUtil.isNotBlank(data.getItemId())) {
            List<Sctype> serveTypeList = sctypeService.findList(Filter.eq("id", data.getServeType()));
            List<IndentSort> indentSortList = indentSctypeSortService.findList(Filter.eq("indentId", data.getId()));
            List<IndentContent> indentContentList = indentContentService.findList(Filter.eq("indentId", data.getId()));
            
            IndentContent oldIndentContent = indentContentService.findOne(Filter.eq("indentId", data.getId()));
            List<String> sorts = new ArrayList<String>();
            List<String> contents = new ArrayList<String>();
            if (!EnumPart.WORKER.name().equals(indentException.getPart())) {
                SctypeContent sctypeContent = sctypeContentService.findOne(Filter.eq("sctypeSortId", sctypeContentService.find(oldIndentContent.getCode1()).getSctypeSortId()),//
                        Filter.eq("desc1", "二次上门"));
                sorts.add(sctypeContent.getSctypeSortId());
                contents.add(sctypeContent.getId());
            }
            
            for (IndentSort indentSort : indentSortList) {
                sorts.add(indentSort.getSctypeSortId());
            }
            for (IndentContent indentContent : indentContentList) {
                contents.add(indentContent.getCode1());
            }
            for (Sctype sctype : serveTypeList) {
                List<SctypeSort> sctypeSortList = sctypeSortService.findList(Filter.in("id", sorts));
                for (SctypeSort sctypeSort : sctypeSortList) {
                    sctypeSort.setContentList(sctypeContentService.findList(Arrays.asList(Filter.in("id", contents), Filter.eq("sctypeSortId", sctypeSort.getId()))));
                }
                sctype.setSortList(sctypeSortList);
            }
            model.addAttribute("serveTypeList", serveTypeList);
        }
        
        List<FileIndex> findByBelongToAndExts = fileManagerService.findByBelongToAndExts(data.getId(), "INDENT", "DWG");
        for (FileIndex fileIndex : findByBelongToAndExts) {
            fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
            fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
        }
        data.setDwgImgList(findByBelongToAndExts);
        List<SysDict> listGroupCopy = sysDictService.listGroupCopy(DictCodes.VEHICLE);
        listGroupCopy.add(0, new SysDict("ALL", "不限", 0));
        model.addAttribute("vehicleList", listGroupCopy);
        model.addAttribute("apartyName1", data.getAparty().getName1());
        model.addAttribute("itemName1", data.getItem().getName1());
        model.addAttribute("serveType", data.getServeType());
        model.addAttribute(TAG_DATA, data);
        model.addAttribute("indentSourceList", sysDictService.listGroupCopy(EnumCodes.INDENT_SOURCE));
        model.addAttribute(TAG_PATH, PATH);
        return PATH + "/extraIndentEdit";
    }
    
    @RequestMapping(value = "/loadExtraIndent")
    @ResponseBody
    public Dto loadExtraIndent(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "indentId") String indentId) {
        
        Dto ret = new Dto();
        Indent indent = service.find(indentId);
        IndentException indentException = indentExceptionService.findOne(Filter.eq("indentId", indentId),//
                Filter.eq("result", EnumExceptionResultStatus.OVER.name()));
        String comment = indentException.getIdea();
        indent.setComment(comment);
        if (!EnumPart.WORKER.name().equals(indentException.getPart())) {
            //当不是工匠的责任时  则新加一个二次上门
            IndentContent oldIndentContent = indentContentService.findOne(Filter.eq("indentId", indent.getId()));
            SctypeContent sctypeContent = sctypeContentService.findOne(Filter.eq("sctypeSortId", sctypeContentService.find(oldIndentContent.getCode1()).getSctypeSortId()),//
                    Filter.eq("desc1", "二次上门"));
            
            //报价 表  处理 新加二次上门费
            Dto indentPriceDto = indent.getIndentPriceDto();
            indentPriceDto.put("indentPrice_"+sctypeContent.getId()+"_code1", sctypeContent.getId());
            indentPriceDto.put("indentPrice_"+sctypeContent.getId()+"_checked", true);
            indentPriceDto.put("indentPrice_"+sctypeContent.getId()+"_counts", 1);
        }
        if (indent.getIndentFreight() != null) {
            indent.getIndentFreight().setCode1("");
        }
        ret.put(TAG_DATA, indent);
        return ret;
    }
    
    @RequestMapping(value = "/doExtraIndentAdd")
    @ResponseBody
    public Dto doExtraIndentAdd(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "indentId") String indentId) {
        Dto ret = new Dto();
        Indent indent = service.find(indentId);
        //执行中的返补单
        Long count = service.count(Filter.eq("originalCode1", indent.getCode1()),//
                Filter.ne("status", EnumIndentStatus.CANCEL.name()),//
                Filter.ne("status", EnumIndentStatus.OVER.name()),//
                Filter.ne("executeStatus", EnumExecuteStatus.AFTER.name()));
        if (count > 0) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "已经有一个正在执行的二次上门单！");
            return ret;
        }
        
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_MSG, "添加二次上门订单成功！");
        return ret;
    }
    
    @RequestMapping(value = "/doSaveExtraIndent")
    @ResponseBody
    public Dto doSaveExtraIndent(HttpServletRequest request,//
            @RequestBody Indent data) {

        Dto ret = new Dto();
        
        IndentFreight freight = data.getIndentFreight();
        if (freight != null) {
            IndentFreight findOne = indentFreightService.findOne(Filter.eq("code1", freight.getCode1()));
            if (findOne != null) {
                ret.put(TAG_SUCCESS, false);
                ret.put(TAG_MSG, "该订单的货运单号已存在！");
                return ret;
            }
        } else {
            data.setIndentFreight(null);
        }
        Admin admin = (Admin) ThreadContext.getContextBean();
        service.createAnotherIndent(data.getId(), admin, data);
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_MSG, "订单保存成功！");
        return ret;
    }
    
}
