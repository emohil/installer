package com.company.wapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountStatus;
import com.company.api.account.service.EnumAccountType;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.DictCodes;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumIndentExcepStatus;
import com.company.api.indent.service.EnumIndentStatus;
import com.company.api.indent.service.EnumIndentVisitSource;
import com.company.api.indent.service.IndentContactService;
import com.company.api.indent.service.IndentContentService;
import com.company.api.indent.service.IndentFreightService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentNodeStepItemService;
import com.company.api.indent.service.IndentNodeStepService;
import com.company.api.indent.service.IndentSctypeSortService;
import com.company.api.indent.service.IndentService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.api.wk.service.WorkerService;
import com.company.po.account.Account;
import com.company.po.fs.FileIndex;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContact;
import com.company.po.indent.IndentContent;
import com.company.po.indent.IndentFreight;
import com.company.po.indent.IndentSort;
import com.company.po.mgr.Manager;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeContent;
import com.company.po.sctype.SctypeSort;
import com.company.po.wk.Worker;
import com.company.sf.indent.IndentSf;
import com.company.api.fw.service.SysDictService;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.Page;
import com.company.dto.Pageable;
import com.company.dto.SysDict;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.StringUtil;
import com.company.util.web.WebUtil;

@Controller("wapiIndentController")
@RequestMapping(value = "/indent")
public class IndentController extends BaseController {

    private static final String TAG_SERVE_TYPE = "serveType";
    private static final String TAG_ACCOUNTTYPE = "accountType";
    private static final Integer LIMIT = 10;

    @Autowired
    private IndentService indentService;
    
    @Autowired
    private IndentSctypeSortService indentSortService;
    
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
    private AccountService accountService;
    
    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private IndentContentService indentContentService;
    
    @Autowired
    private SctypeService sctypeService;
    
    @Autowired
    private SctypeContentService sctypeContentService;
    
    @Autowired
    private SctypeSortService sctypeSortService;
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    private SysDictService sysDictService;
    
    @Autowired
    private FileManagerService fileManagerService;

    @RequestMapping(value = "/unScrambleIndent")
    @ResponseBody
    public Dto unScrambleIndent(HttpServletRequest request, //
            @RequestParam(value = "pageNum", defaultValue = "1") String pageNum,//
            @RequestParam(value = "serveType") String serveType) {
        Dto ret = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = workerService.find(account.getWorkerId());
        //后期可以使用 managerType判断
        int pageNumber = Integer.parseInt(pageNum);
        //未通过认证的工匠  查看未抢订单的规则
        IndentSf sf = new IndentSf();
        sf.setServeType(serveType);
        sf.setIsAPI("1");
        sf.setRegionProv(worker.getServiceProvince());
        sf.setRegionCity(worker.getServiceCity());
        sf.setRegionDist(worker.getServiceCounty());
        sf.setWorkerId(account.getWorkerId());
        sf.setManagerId(worker.getManagerId());
        int start = (pageNumber - 1) * LIMIT + 1;
        List<Indent> indentList = indentService.list(sf, start, LIMIT, null);
        if (indentList.size() > 0) {
            this.completeIndentList(indentList);
        }
        int total = indentService.count(sf);
        ret.put(TAG_SUCCESS, true);
        String[] strings = worker.getServiceCategory().split("\\|");
        List<Sctype> sctypes  = New.list();
        for (int i = 0; i < strings.length; i++) {
            Sctype sctype = sctypeService.findOne(Filter.eq("code1", strings[i]));
            sctypes.add(sctype);
        }
        //  删除分割产生的垃圾数据
        sctypes.remove(0);
        Dto data = new Dto();
        data.put(TAG_SERVE_TYPE, sctypes);
        data.put("change", false);
        if (account.getManagerType() > 0) {
            data.put("change", true);
        }
        data.put("total", total);
        data.put("indentList", indentList);

        ret.put(TAG_DATA, data);
        return ret;
    }
    
    
    @RequestMapping(value = "/unScrambleIndentDetails")
    @ResponseBody
    public Dto unScrambleIndentDetails(HttpServletRequest request, //
            @RequestParam(value = "id") String id,//
            @RequestParam(value = "city") String city) {
        Dto ret = new Dto();
        
        String accountId = ThreadContext.getKeyId();
        Account account = accountService.find(accountId);
        Indent indent = indentService.find(id);
        indent.setContact(indentContactService.findOne(Filter.eq("indentId", indent.getId())));
        Worker worker = workerService.find(account.getWorkerId());
        IndentFreight indentFreight = indentFreightService.findOne(Filter.eq("indentId", indent.getId()));
        if (indentFreight != null) {
            if ("ALL".equals(indentFreight.getCarModel())) {
                indentFreight.setCarModelDisp("不限");
            }
        }
        indent.setIndentFreight(indentFreight);
        List<IndentContent> list = indentContentService.findList(Filter.eq("indentId", id));
        indent.setIndentPriceList(list);
        Sctype sctype = sctypeService.findOne(Filter.eq("code1", indent.getServeType()));
        indent.setServeTypeDisp(sctype.getName1());
        List<FileIndex> findByBelongToAndExts = fileManagerService.findByBelongToAndExts(indent.getId(), "INDENT", "DWG");
        if (findByBelongToAndExts != null) {
            for (FileIndex fileIndex : findByBelongToAndExts) {
                fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
            }
        }
        indent.setDwgImgList(findByBelongToAndExts);
        Dto data = new Dto();
        data.put("canScramble", false);
        data.put("isScrambled", false);
        
        if (StringUtil.isNotBlank(city)) {
            String serveCity = "";
            if (city.length() > 4 ) {
                serveCity = city.substring(0, 4);
            } else {
                serveCity = city;
            }
            //地区符合   并且账户通过审核
            if (indent.getRegionCity().equals(serveCity) && EnumCheckStatus.PASS.name().equals(worker.getStatus())) {
                data.put("canScramble", true);
            }
        }
        Indent findIndent = indentService.findOne(Filter.eq("id", id), Filter.eq("executeStatus", EnumExecuteStatus.BEFORE.name()));
        if (findIndent == null) {
            data.put("isScrambled", true);
        }
        
        ret.put(TAG_SUCCESS, true);
        data.put("checkStatus", worker.getStatus());
        data.put("indent", indent);
        ret.put(TAG_DATA, data);
        return ret;
    }

    @RequestMapping(value = "/scrambleIndent")
    @ResponseBody
    public Dto scrambleIndent(HttpServletRequest request,//
            @RequestParam(value = "id") String id) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = workerService.find(account.getWorkerId());
        if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
            Manager manager = managerService.find(account.getManagerId());
            if (manager.getStatus().equals(EnumCheckStatus.UNCHECK.name())) {
                ret.put(TAG_MSG, "您的经理人角色尚未通过审核！");
                return ret;
            }
            if (manager.getStatus().equals(EnumCheckStatus.PASS.name()) && !worker.getManagerId().equals(manager.getId())) {
                ret.put(TAG_MSG, "请更换到自己的经理人名下");
                return ret;
            }
            
        }
        if (!EnumCheckStatus.PASS.name().equals(worker.getStatus())) {
            
            ret.put(TAG_MSG, "您尚未通过审核！");
            return ret;
        }
        if (!EnumYesno.YES.name().equals(worker.getManagerIdea())) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "经理人尚未同意您的请求哦~");
            return ret;
        }
        
        if (EnumAccountStatus.DISABLED.name().equals(worker.getRoleStatus())) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "该角色已被停用！如有疑问请联系工作人员哦~");
            return ret;
        }
        
        IndentFreight freight = indentFreightService.findOne(Filter.eq("indentId", id));
        if (freight != null) {
            //抢单的时候判断   订单 的要求车型 和 工匠填报的车型进行匹配
            if (!StringUtil.isNotBlank(worker.getVehicle())) {
                ret.put(TAG_MSG, "要有交通工具才可以抢运输安装的订单哦~");
                ret.put("choseVehicle", true);
                return ret;
            }
            //工匠交通工具 未审核通过
            if (!EnumCheckStatus.PASS.name().equals(worker.getVehicleStatus())) {
                ret.put(TAG_MSG, "车辆资质审核中，请耐心等候哦~");
                return ret;
            }
            List<SysDict> listGroupCopy = sysDictService.listGroupCopy(DictCodes.VEHICLE);
            Dto vehicle = new Dto();
            for (SysDict sysDict : listGroupCopy) {
                vehicle.put(sysDict.getValue(), sysDict.getOrders());
            }
            
            //当前订单的要求车型 不限 则都可以
            if (vehicle.getAsInt(freight.getCarModel()) > vehicle.getAsInt(worker.getVehicle())) {
                ret.put(TAG_MSG, "交通工具不符合哦~");
                ret.put("choseVehicle", true);
                return ret;
            }
        }
        if (worker.getAstrictDate().after(new Date())) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "您被经理人限制接单了");
            return ret;
        }
        if (indentService.scrambleIndent(account, id)) {
            ret.put(TAG_SUCCESS, true);
            ret.put(TAG_MSG, "抢单成功");
            return ret;
        } else {
            ret.put(TAG_MSG, "手太慢，订单已被抢...");
            return ret;
        }
    }

    //顶部搜索
    @RequestMapping(value = "/titleSearchIndent")
    @ResponseBody
    public Dto titleSearchIndent(@RequestParam(value = "searchValue") String searchValue,//
            @RequestParam(value = "accountType") String accountType,
            @RequestParam(value = "pageNum", defaultValue = "1") String pageNum) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        accountType = accountType.toUpperCase();
        Account account = (Account) ThreadContext.getContextBean();
        int pageNumber = Integer.parseInt(pageNum);
        IndentSf sf = new IndentSf();
        if (EnumAccountType.MANAGER.name().equals(accountType)) {
            sf.setManagerId(account.getManagerId());
        } else if (EnumAccountType.WORKER.name().equals(accountType)) {
            sf.setWorkerId(account.getWorkerId());
        }
        sf.setIsAPI("2");
        sf.setSearchName1(searchValue);
        sf.setSearchAddr1(searchValue);
        int start = (pageNumber - 1) * LIMIT + 1;
        List<Indent> indentList = indentService.list(sf, start, LIMIT, null);
        int count = indentService.count(sf);
        Dto data = new Dto();
        this.completeIndentList(indentList);
        data.put("indentList", indentList);
        data.put("count", count);
        data.put("sctypes", sctypeService.findAll());
        
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_DATA, data);
        return ret;
    }

    //全部已抢订单
    @RequestMapping(value = "/allScrambleIndent")
    @ResponseBody
    public Dto allScrambleIndent(@RequestParam(value = "status", defaultValue = "") String status,//
            @RequestParam(value = "accountType") String accountType,//
            @RequestParam(value = "pageNum", defaultValue = "1") String pageNum) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        status = status.toUpperCase();
        if (StringUtil.isBlank(accountType)) {
            ret.put(TAG_MSG, "角色类型不能为空");
            return ret;
        }
        Dto data = new Dto();
        
        Account account = (Account) ThreadContext.getContextBean();
        
        int pageNumber = Integer.parseInt(pageNum);
        Pageable pageable = new Pageable(pageNumber, LIMIT);
        List<Filter> filters = New.list();
        if (EnumExecuteStatus.CENTRE.name().equals(status)) {
            //执行中的订单  待完成  不包含异常订单
            filters.add(Filter.eq("executeStatus", EnumExecuteStatus.CENTRE.name()));
            filters.add(Filter.eq("excepStatus", SysDict.EMPTY.getValue()));
        } else if (EnumIndentExcepStatus.PAUSE.name().equals(status)) {
            //暂停中
            filters.add(Filter.eq("excepStatus", EnumIndentExcepStatus.PAUSE.name()));
            filters.add(Filter.eq("status", EnumIndentStatus.EXCEPTION.name()));
        } else if (EnumExecuteStatus.AFTER.name().equals(status)) {
            //已完成
            filters.add(Filter.eq("status", EnumIndentStatus.NORMAL.name()));
            filters.add(Filter.eq("executeStatus", EnumExecuteStatus.AFTER.name()));
            filters.add(Filter.eq("excepStatus", SysDict.EMPTY.getValue()));
        } else if (EnumIndentStatus.CANCEL.name().equals(status)) {
            //已取消
            filters.add(Filter.eq("status", EnumIndentStatus.CANCEL.name()));
            filters.add(Filter.eq("executeStatus", EnumExecuteStatus.AFTER.name()));
        } else if (EnumIndentStatus.EXCEPTION.name().equals(status)) {
            //经理人  查询  异常订单
            filters.add(Filter.eq("status", EnumIndentStatus.EXCEPTION.name()));
            filters.add(Filter.eq("executeStatus", EnumExecuteStatus.CENTRE.name()));
        } else if (EnumExecuteStatus.BEFORE.name().equals(status)) {
            //经理人  查询  待接单
            filters.add(Filter.eq("executeStatus", EnumExecuteStatus.BEFORE.name()));
        } else if (StringUtil.isBlank(status)) {
            //全部
            filters.add(Filter.ne("executeStatus", EnumExecuteStatus.BEFORE.name()));
        }
        if (EnumAccountType.MANAGER.name().equals(accountType.toUpperCase())) {
            String managerId = account.getManagerId() == null? "":account.getManagerId();
            filters.add(Filter.eq("managerId", managerId));
        } else if (EnumAccountType.WORKER.name().equals(accountType.toUpperCase())) {
            String workerId = account.getWorkerId() == null? "":account.getWorkerId();
            filters.add(Filter.eq("workerId", workerId));
            data.put("status", EnumIndentStatus.NORMAL.name());  
        }
        if (StringUtil.isNotBlank(account.getWorkerId()) && StringUtil.isNotBlank(account.getManagerId())) {
            data.put("change", true); 
        } else {
            data.put("change", false);
        }
        pageable.setFilters(filters);
        pageable.setOrders(Arrays.asList(Order.desc("serviceDate")));
        Page<Indent> page = new Page<Indent>();
        page = indentService.findPage(pageable);
        
        List<Indent> indentList  = new ArrayList<Indent>();
        ret.put(TAG_SUCCESS, true);
        if (!(((pageNumber-1) * LIMIT) > page.getTotal())) {
            indentList = page.getContent();
            if (indentList.size() > 0) {
                this.completeIndentList(indentList);
            }
        } else {
            page.getContent().clear();
        }
        data.put("page", page);
        data.put("sctypes", sctypeService.findAll());
        data.put(IndentController.TAG_ACCOUNTTYPE, accountType);
        
        ret.put(TAG_DATA, data);
        
        return ret;
    }
    
    private void completeIndentList(List<Indent> indentList) {
        for (Indent indent : indentList) {
            indent.setContact(indentContactService.findOne(Filter.eq("indentId", indent.getId())));
            indent.setIndentFreight(indentFreightService.findOne(Filter.eq("indentId", indent.getId())));
            if (EnumIndentVisitSource.FIRST.name().equals(indent.getSource())) {
                indent.setSourceDisp(EnumIndentVisitSource.FIRST.text());
            } else {
                indent.setSourceDisp(EnumIndentVisitSource.SECOND.text());
            }
            List<IndentContent> indentPriceList = indentContentService.findList(Filter.eq("indentId", indent.getId()));
            if (!EnumExecuteStatus.BEFORE.name().equals(indent.getExecuteStatus())) {
                if (StringUtil.isNotBlank(indent.getWorkerId())) {
                    Account account = accountService.findOne(Filter.eq("workerId", indent.getWorkerId()));
                    indent.setWorkerName1(account.getName1());
                    indent.setWorkerMobile(account.getMobile());
                }
                if (StringUtil.isNotBlank(indent.getManagerId())) {
                    String managerName1 = accountService.findOne(Filter.eq("managerId", indent.getManagerId())).getName1();
                    indent.setManagerName1(managerName1);
                }
            }
            indent.setIndentContent(indentPriceList.get(0));
            indent.setIndentPriceList(indentPriceList);
            if (EnumIndentStatus.NORMAL.name().equals(indent.getStatus())) {
                if (EnumExecuteStatus.CENTRE.name().equals(indent.getExecuteStatus())) {
                    indent.setStatusDisp(EnumExecuteStatus.CENTRE.text());
                } else {
                    indent.setStatusDisp(EnumExecuteStatus.AFTER.text());
                }
            } else if (EnumIndentStatus.CANCEL.name().equals(indent.getStatus())) {
                indent.setStatusDisp(EnumIndentStatus.CANCEL.text());
            } else if (EnumIndentStatus.OVER.name().equals(indent.getStatus())) {
                indent.setStatusDisp(EnumIndentStatus.OVER.text());
            } else {
                if (EnumIndentExcepStatus.PAUSE.name().equals(indent.getExcepStatus())) {
                    indent.setStatusDisp("暂停中");
                } else {
                    indent.setStatusDisp("异常订单");
                }
            }
            List<SctypeSort> sctypeSortList = New.list();
            Dto  sortIds = new Dto();
            for (IndentContent indentContent : indentPriceList) {
                String indentPriceCode1 = indentContent.getCode1();
                    SctypeContent content = sctypeContentService.find(indentPriceCode1);
                    String sortId = content.getSctypeSortId();
                if (!sortIds.containsKey(sortId)) {
                    sortIds.put(sortId, sortId);
                    sctypeSortList.add(sctypeSortService.find(sortId));
                }
            }
            indent.setSctypeSortList(sctypeSortList);
        }
    }

    //订单详情
    @RequestMapping(value = "/indentDetails")
    @ResponseBody
    public Dto indentDetails(HttpServletRequest request,//
            @RequestParam(value = "id") String id,//
            @RequestParam(value = "accountType") String accountType) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        if (StringUtil.isBlank(accountType)) {
            ret.put(TAG_MSG, "角色类型不能为空");
            return ret;
        }
        Indent indent = indentService.find(id);
        indent.setContact(indentContactService.findOne(Filter.eq("indentId", indent.getId())));
        Worker worker = workerService.find(indent.getWorkerId());
        Manager manager = managerService.find(indent.getManagerId());
        IndentFreight indentFreight = indentFreightService.findOne(Filter.eq("indentId", indent.getId()));
        if (indentFreight != null) {
            if ("ALL".equals(indentFreight.getCarModel())) {
                indentFreight.setCarModelDisp("不限");
            }
        }
        List<FileIndex> findByBelongToAndExts = fileManagerService.findByBelongToAndExts(indent.getId(), "INDENT", "DWG");
        if (findByBelongToAndExts != null) {
            for (FileIndex fileIndex : findByBelongToAndExts) {
                fileIndex.setFileUrl(fileManagerService.getFileUrlById(fileIndex.getId()));
                fileIndex.setThumbUrl(fileManagerService.getThumbUrlById(fileIndex.getId()));
            }
        }
        indent.setDwgImgList(findByBelongToAndExts);
        indent.setIndentFreight(indentFreight);
        List<IndentContent> list = indentContentService.findList(Filter.eq("indentId", id));
        indent.setIndentPriceList(list);
        
        Dto data = new Dto();
        //订单的状态  api 显示
        if (EnumIndentStatus.NORMAL.name().equals(indent.getStatus())) {
            if (EnumExecuteStatus.CENTRE.name().equals(indent.getExecuteStatus()) && SysDict.EMPTY.getValue().equals(indent.getExcepStatus())) {
                indent.setIndentShowStatus("CENTRE");
                indent.setIndentShowStatusDisp("待完成");
            } else if (EnumExecuteStatus.AFTER.name().equals(indent.getExecuteStatus()) &&//
                    SysDict.EMPTY.getValue().equals(indent.getExcepStatus())) {
                indent.setIndentShowStatus("AFTER");
                indent.setIndentShowStatusDisp("已完成");
            }
        } else {
            if (EnumExecuteStatus.AFTER.name().equals(indent.getExecuteStatus()) &&//
                    EnumIndentStatus.CANCEL.name().equals(indent.getStatus())) {
                indent.setIndentShowStatus("CANCLED");
                indent.setIndentShowStatusDisp("已取消");
            } else if (EnumIndentExcepStatus.PAUSE.name().equals(indent.getExcepStatus())) {
                indent.setIndentShowStatus("PAUSE");
                indent.setIndentShowStatusDisp("暂停中");
            } else {
                indent.setIndentShowStatus("EXCEPTION");
                indent.setIndentShowStatusDisp("异常订单");
            }
        }
        
        data.put("worker", worker);
        data.put("manager", manager);
        data.put("indent", indent);
        data.put(IndentController.TAG_ACCOUNTTYPE, accountType);
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_DATA, data);
        return ret;
    }

    //订单申请取消
    @RequestMapping(value = "/indentCancle")
    @ResponseBody
    public Dto indentCancle(HttpServletRequest request,//
            @RequestParam(value = "id") String id) {
        Dto ret = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        ret = indentService.indentCancle(id, account.getWorkerId());
        return ret;
    }
    

    //邀请用户评价
    @RequestMapping(value = "inviteEvaluation")
    @ResponseBody
    public Dto inviteEvaluation(HttpServletRequest request,//
            @RequestParam(value = "indentId") String indentId,
            @RequestParam(value = "indentNodeId") String indentNodeId) {
        Dto ret = new Dto();
        
        Indent data = indentService.find(indentId);
        if (data == null) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "要评提供的订单ID无效");
            return ret;
        }
        
        IndentContact contact = indentContactService.findOne(Filter.eq("indentId", data.getId()));
        if (contact == null || StringUtil.isEmpty(contact.getMobile())) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "没有找到订单业主的联系方式");
            return ret;
        }
        
        List<IndentSort> sortList = indentSortService.findList(Filter.eq("indentId", data.getId()));
        StringBuilder sortStrs = new StringBuilder();
        
        if (sortList.size() == 0) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "没有找到订单的相关服务类别");
            return ret;
        }
        
        for (int i = 0; i < sortList.size(); i++) {
            if (i >= 3) {
                //最多列出三项
                break;
            }
            sortStrs.append(sortList.get(i).getName1()).append("、");
        }
        sortStrs.deleteCharAt(sortStrs.length() - 1);
        
        if (sortList.size() > 3) {
            sortStrs.append("等");
        }
        
        String basePath = WebUtil.basePath(request);
        String contextPath = request.getContextPath();
        String linkBase = basePath.substring(0, basePath.length() - contextPath.length());
        
        String link = linkBase + "wap/o/" + data.getCode1();
        indentNodeService.inviteEvaluation(indentNodeId, indentId);
        
        smsService.sendSms(contact.getMobile(), SmsTemplate.EVALUATION, sortStrs, link, "保养保洁");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    //获取上次 运输安装 填的车牌号
    @RequestMapping(value = "getCarNum")
    @ResponseBody
    public Dto getCarNum(@RequestParam(value = "id") String id) {
        Dto ret = new Dto();
        
        //当前订单Id  取到   车辆num   车型 
        String keyId = ThreadContext.getKeyId();
        String workerId = accountService.find(keyId).getWorkerId();
        IndentFreight findOne = indentFreightService.findOne(Filter.eq("indentId", id));
        List<Indent> indentList = indentService.findByCarModel(workerId, findOne.getCarModel());
        Indent indent = indentList.get(0);
        IndentFreight freight = indentFreightService.findOne(Filter.eq("indentId", indent.getId()));
        ret.put(TAG_SUCCESS, true);
        if ("ALL".equals(findOne.getCarModel())) {
            findOne.setCarModelDisp("不限");
        }
        Dto data = new Dto();
        data.put("freight", findOne);
        data.put("carNum", freight.getCarNumber());
        ret.put(TAG_DATA, data);
        return ret;
    }
    
    
    //提交汽车牌照
    @RequestMapping(value = "commitCarNum")
    @ResponseBody
    public Dto commitCarNum(HttpServletRequest request,//
            @RequestParam(value = "id") String id,
            @RequestParam(value = "carNum") String carNum) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        IndentFreight freight = indentFreightService.findOne(Filter.eq("indentId", id));
        freight.setCarNumber(carNum);
        indentFreightService.update(freight);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }
    
    
    //委托书 的数据
    @RequestMapping(value = "getProxyData")
    @ResponseBody
    public Dto getProxyData(HttpServletRequest request,//
            @RequestParam(value = "id") String id) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        
        Indent indent = indentService.find(id);
        Dto data = new Dto();
        Worker worker = workerService.find(indent.getWorkerId());
        Account account = accountService.find(worker.getAccountId());
        Dto proxy = new Dto();
        proxy.put("deadLine", DateUtils.addDays(indent.getServiceDate(), 3));
        proxy.put("name1", account.getName1());
        proxy.put("IdNum", account.getIdNum());
        proxy.put("carNumber", indent.getIndentFreight().getCarNumber());
        proxy.put("code1", indent.getIndentFreight().getCode1());
        data.put("proxy", proxy);
        ret.put(TAG_DATA, data);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

}