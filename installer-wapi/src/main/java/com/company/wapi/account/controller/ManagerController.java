package com.company.wapi.account.controller;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountStatus;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumTradeType;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentService;
import com.company.api.mgr.service.ManagerDepositService;
import com.company.api.mgr.service.ManagerReportService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.mgr.service.ManagerTradeService;
import com.company.api.wk.service.WorkerService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.dto.vehicle.VehicleData;
import com.company.po.account.Account;
import com.company.po.bankcard.BankCard;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerDeposit;
import com.company.po.mgr.ManagerReport;
import com.company.po.mgr.ManagerTrade;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerAstrict;
import com.company.sf.account.ManagerSf;
import com.company.sf.account.ManagerTradeSf;
import com.company.sf.account.WorkerSf;
import com.company.wapi.controller.BaseController;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.util.EnvConfig;
import com.company.api.fw.util.FileManangerUtil;
import com.company.context.ThreadContext;
import com.company.dto.FileItem;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.Page;
import com.company.dto.Pageable;
import com.company.dto.SysDict;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.NumberUtil;
import com.company.util.StringUtil;

/**
 * 
 * @author kouwl
 *
 */
@Controller("wapiManagerController")
@RequestMapping(value = "/manager")
public class ManagerController extends BaseController {
    
    private static final Integer LIMIT = 10;

    @Autowired
    private ManagerService service;

    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private WorkerTradeService workerTradeService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ManagerReportService managerReportService;

    @Autowired
    private FileManagerService fileManagerService;
    
    @Autowired
    private BankCardService bankCardService;
    
    @Autowired
    private ManagerTradeService managerTradeService;
    
    @Autowired
    private ManagerDepositService managerDepositService;
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private SysDictService sysDictService;

    //显示经理人基本信息
    @RequestMapping(value = "/myself")
    @ResponseBody
    public Dto myself(HttpServletRequest request) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());
        
        
        if (StringUtil.isNotEmpty(account.getAvatar())) {
            account.setAvatarImg(fileManagerService.getFileUrlByFilepath(account.getAvatar()));
        }
        manager.setAccount(account);
        rt.put("manager", manager);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //经理人个人主页
    @RequestMapping(value = "/myHomepage")
    @ResponseBody
    public Dto myHomepage(HttpServletRequest request) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());
        if (StringUtil.isNotEmpty(account.getAvatar())) {
            account.setAvatarImg(fileManagerService.getFileUrlByFilepath(account.getAvatar()));
        }
        
        List<Worker> workerList = workerService.findList(Filter.eq("managerId", manager.getId()));
        //本月订单数
        int monthIndentCount = 0;
        //累计订单数
        //需要变，可以直接从manager中获取totalIndent
        int totalIndentCount = 0;
        for (Worker worker : workerList) {
            int workerIndent = indentService.monthIndent(worker.getId());
            monthIndentCount += workerIndent;
            int finishIndentNum = indentService.finishIndentNum(worker.getId());
            totalIndentCount += finishIndentNum;
        }
        rt.put("monthIndentCount", monthIndentCount);
        rt.put("totalIndentCount", totalIndentCount);
        //统计本月收入
        BigDecimal monthAmt = managerTradeService.monthEarning(manager.getId());
        if (monthAmt == null) {
            monthAmt = BigDecimal.ZERO;
        }
        rt.put("monthAmt", monthAmt);
        
        //计算工匠总人数
        int amount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            amount = workerService.count(sf);
        }
        rt.put("amount", amount);
        //送货安装人数
        int tCount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            sf.setServiceCategory("|T|");
            tCount = workerService.count(sf);

        }
        rt.put("tCount", tCount);
        //入户安装人数
        int rCount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            sf.setServiceCategory("|R|");
            rCount = workerService.count(sf);

        }
        rt.put("rCount", rCount);
        //测量人数
        int cCount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            sf.setServiceCategory("|C|");
            cCount = workerService.count(sf);

        }
        rt.put("cCount", cCount);
        //送货安装+入户安装人数
        int trCount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            sf.setServiceCategory("|T|R|");
            sf.setServiceCategory2("|R|T|");
            trCount = workerService.count(sf);

        }
        rt.put("trCount", trCount);
        //送货安装+测量人数
        int tcCount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            sf.setServiceCategory("|T|C|");
            sf.setServiceCategory2("|C|T|");
            tcCount = workerService.count(sf);

        }
        rt.put("tcCount", tcCount);
        //入户安装+测量人数
        int rcCount = 0;
        {
            WorkerSf sf = new WorkerSf();
            sf.setManagerId(account.getManagerId());
            sf.setServiceCategory("|R|C|");
            sf.setServiceCategory2("|C|R|");
            rcCount = workerService.count(sf);

        }
        rt.put("rcCount", rcCount);
        //送货安装+入户安装+测量人数
        int allSkillCount = amount - tCount - rCount - cCount - trCount - tcCount - rcCount;
        rt.put("allSkillCount", allSkillCount);
        //工匠技能工种
        String workerSkill = workerService.findWorkerSkill(manager.getId());
        rt.put("workerSkill", workerSkill);
        //工匠服务区域
        List<String> serviceArea = workerService.findServiceArea(manager.getId());
        rt.put("serviceArea", serviceArea);
        //工匠交通工具
        List<VehicleData> vehicleList = workerService.findVehicle(manager.getId());
        rt.put("vehicleList", vehicleList);

        manager.setAccount(account);
        rt.put("manager", manager);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //经理人钱包
    @RequestMapping(value = "/myWallet")
    @ResponseBody
    public Dto myWallet() {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());
        BankCard bankcard = bankCardService.findByOwnerId(manager.getId());
        if (bankcard != null) {
            rt.put("bankCard", bankcard);
        } else {
            rt.put("bankCard", null);
        }
        rt.put("balAmt", manager.getBalAmt());
        rt.put("depositAmt", manager.getDepositAmt());
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    //加载余额明细
    @RequestMapping(value = "/balanceDetail")
    @ResponseBody
    public Dto balanceDetail(HttpServletRequest request,
            @RequestParam(value = "pageNum") String pageNum,
            @RequestParam(value = "type", required = false) String type) {

        int pageNumber = Integer.parseInt(pageNum);
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());
        ManagerTradeSf sf = new ManagerTradeSf();
        sf.setManagerId(manager.getId());
        if (StringUtil.isNotBlank(type)) {
            if (type.equals(EnumTradeType.CASH.name())) {
                sf.setTradeType(EnumTradeType.CASH.name());
            } else if (type.equals(EnumTradeType.EARNING.name())) {
                sf.setTradeType(EnumTradeType.EARNING.name());
            }
        }
        int limit = 10;
        int start = (pageNumber - 1) * limit + 1;
        List<Order> orders = New.list();
        orders.add(Order.desc("CREATE_DATE"));
        List<ManagerTrade> managerTradeList = managerTradeService.list(sf, start, limit, orders);
        rt.put("myMoneyList", managerTradeList);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    //加载保证金明细
    @RequestMapping(value = "/depositDetail")
    @ResponseBody
    public Dto depositDetail(HttpServletRequest request,
            @RequestParam(value = "pageNum") String pageNum,
            @RequestParam(value = "type", required = false) String type) {

        int pageNumber = Integer.parseInt(pageNum);
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());
        ManagerDeposit md = new ManagerDeposit();
        md.setManagerId(manager.getId());
        if (StringUtil.isNotBlank(type)) {
            if (type.equals(EnumTradeType.DEDUCT.name())) {
                md.setTradeType(EnumTradeType.DEDUCT.name());
            } else if (type.equals(EnumTradeType.EARNING.name())) {
                md.setTradeType(EnumTradeType.EARNING.name());
            }
        }
        int limit = 10;
        int start = (pageNumber - 1) * limit + 1;
        List<Order> orders = New.list();
        orders.add(Order.desc("CREATE_DATE"));
        List<ManagerDeposit> depositList = managerDepositService.list(md, start, limit, orders);
        rt.put("depositList", depositList);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    //设置
    @RequestMapping(value = "/options")
    @ResponseBody
    public Dto options(HttpServletRequest request, //
            @RequestParam(value = "registrationId", required = false) String registrationId,//
            @RequestParam(value = "versionNumber", required = false) String versionNumber,//
            @RequestParam(value = "accountType", required = false) String accountType) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());
        
        if (StringUtil.isNotBlank(versionNumber)) {
            String newestVersionNum = EnvConfig.getProperty(EnvConfig.VERSIONNUMBER);
            String versionUrl = EnvConfig.getProperty(EnvConfig.VERSIONURL);
            if (versionNumber.equals(newestVersionNum)) {
                rt.put("isUpdate", true);
            } else {
                rt.put("isUpdate", false);
                rt.put("versionUrl", versionUrl);
            }
        }
        //判断登录账户类型  一并更新
        accountType = accountType.toUpperCase();
        if (!account.getLastLoginType().equals(accountType)) {
            account.setLastLoginType(accountType);
            accountService.updateLastLoginType(account);
        }
        
        //查询是否有重复的registrationId
        long count = accountService.count(Filter.eq("registrationId", registrationId), Filter.ne("id", account.getId()));
        if (count > 0) {
            accountService.emptyRegistrationId(registrationId, account.getId());
        }

        // 通过registrationId 判断手机的唯一registrationId 不唯一 则更新
        if (account.getRegistrationId() == null || !account.getRegistrationId().equals(registrationId)) {
            account.setRegistrationId(registrationId);
            accountService.update(account);
        }

        if (StringUtil.isNotEmpty(account.getAvatar())) {
            rt.put("isUpdateHead", true);
        } else {
            rt.put("isUpdateHead", false);
        }
        if (manager.getStatus().equals(EnumCheckStatus.PASS.name())) {
            rt.put("isIdentityAuthentication", 0);
            rt.put("isCompanyAuthentication", 0);
        } else {
            if (StringUtil.isNotEmpty(account.getIdImgUrl())) {
                rt.put("isIdentityAuthentication", 1);
            } else {
                rt.put("isIdentityAuthentication", 2);
            }
            if (StringUtil.isNotEmpty(manager.getCharterImg()) || 
                    StringUtil.isNotEmpty(manager.getOrgCodeImg()) ||
                    StringUtil.isNotEmpty(manager.getTaxImg())) {
                rt.put("isCompanyAuthentication", 1);
            } else {
                rt.put("isCompanyAuthentication", 2);
            }
        }
        manager.setAccount(account);
        rt.put("manager", manager);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @RequestMapping(value = "/updateMsg")
    @ResponseBody
    public Dto updateMsg(HttpServletRequest request,
            Account accountData,
            @RequestParam(value = "birthday", required = false) String birthday) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Manager manager = service.find(account.getManagerId());

        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

            dealFile(account, files.get("avatarFile"), "avatar");
            
            if (files.get("charterImgFile") != null || files.get("orgCodeImgFile") != null//
                    || files.get("taxImgFile") != null || files.get("idImgUrlFile") != null) {
                manager.setStatus(EnumCheckStatus.UNCHECK.name());
            }
            dealFile(account, files.get("idImgUrlFile"), "idImgUrl");
            dealFile(manager, files.get("charterImgFile"), "charterImg");
            dealFile(manager, files.get("orgCodeImgFile"), "orgCodeImg");
            dealFile(manager, files.get("taxImgFile"), "taxImg");

        }
        //姓名
        if (StringUtil.isNotEmpty(accountData.getName1())) {
            account.setName1(accountData.getName1());
            manager.setStatus(EnumCheckStatus.UNCHECK.name());
        }
        //性别
        if (StringUtil.isNotBlank(accountData.getSex())) {
            account.setSex(accountData.getSex());
        }
        //生日
        if (StringUtil.isNotBlank(birthday)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date newBirthDay;
            try {
                newBirthDay = sdf.parse(birthday);
                account.setBirthDay(newBirthDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //身份证号
        if (StringUtil.isNotBlank(accountData.getIdNum())) {
            account.setIdNum(accountData.getIdNum());
            manager.setStatus(EnumCheckStatus.UNCHECK.name());
        }
        //现住址
        if (StringUtil.isNotBlank(accountData.getAddr1())) {
            account.setAddr1(accountData.getAddr1());
        }
        //现住址楼号门牌号
        if (StringUtil.isNotBlank(accountData.getBuildNum())) {
            account.setBuildNum(accountData.getBuildNum());
        }
        
        accountService.update(account);
        service.update(manager);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    //查找经理人信息
    @RequestMapping(value = "/findManagerById")
    @ResponseBody
    public Dto findManagerById(@RequestParam("managerId") String managerId) {
        Dto rt = new Dto();
        Manager manager = service.find(managerId);
        Account account = accountService.find(manager.getAccountId());
        if (StringUtil.isNotEmpty(account.getAvatar())) {
            account.setAvatarImg(fileManagerService.getFileUrlByFilepath(account.getAvatar()));
        }
        manager.setAccount(account);
        rt.put("manager", manager);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    //新工匠请求数量
    @RequestMapping(value = "/newWorkerCounts")
    @ResponseBody
    public Dto newWorkerCounts(HttpServletRequest request) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        long workers = workerService.count(Filter.eq("managerId", account.getManagerId()), Filter.eq("managerIdea", SysDict.EMPTY.getValue()));
        Dto data = new Dto();
        data.put("workers", workers);
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, data);
        return rt;
    }
    
    //工匠管理页面
    @RequestMapping(value = "/dealWorkers")
    @ResponseBody
    public Dto dealWorkers(HttpServletRequest request,//
            @RequestParam(value = "pageNum")String pageNum,//
            @RequestParam(value = "sortField")String sortField,//
            @RequestParam(value = "sortValue")String sortValue) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        int pageNumber = Integer.parseInt(pageNum);
        Account account = (Account) ThreadContext.getContextBean();
        Pageable pageable = new Pageable(pageNumber, LIMIT);
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.eq("managerId", account.getManagerId()));
        filters.add(Filter.eq("managerIdea", EnumYesno.YES.name()));
        List<Order> orders = new ArrayList<Order>();
        if (sortValue.equals("DESC")) {
            orders.add(Order.desc(sortField));
        } else if (sortValue.equals("ASC")) {
            orders.add(Order.asc(sortField));
        }
        pageable.setFilters(filters);
        pageable.setOrders(orders);
        Page<Worker> findPage = workerService.findPage(pageable);
        List<Worker> workerList = findPage.getContent();
        for (Worker worker : workerList) {
            Account findAccount = accountService.find(worker.getAccountId());
            if (StringUtil.isNotBlank(findAccount.getAvatar())) {
                worker.setAvatar(fileManagerService.getFileUrlByFilepath(findAccount.getAvatar()));
            }
            worker.setName1(findAccount.getName1());
        }
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, workerList);
        return rt;
    }
    
    //顶部搜索工匠
    @RequestMapping(value = "/titleSearchWorker")
    @ResponseBody
    public Dto titleSearchWorker(@RequestParam(value = "workerName1") String workerName1) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        
        Account account = (Account) ThreadContext.getContextBean();
        
        List<Worker> workerList = workerService.searchWorkerByName(workerName1, account.getManagerId());
        for (Worker worker : workerList) {
            Account findAccount = accountService.find(worker.getAccountId());
            if (StringUtil.isNotBlank(findAccount.getAvatar())) {
                worker.setAvatar(fileManagerService.getFileUrlByFilepath(findAccount.getAvatar()));
            }
            worker.setName1(findAccount.getName1());
        }
        
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, workerList);
        return rt;
    }

    //新工匠请求页面
    @RequestMapping(value = "/dealWorkerList")
    @ResponseBody
    public Dto dealWorkerList(HttpServletRequest request) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        
        Account account = (Account) ThreadContext.getContextBean();
        List<Worker> workerList = workerService.findList(Arrays.asList(Filter.eq("managerId", account.getManagerId()), Filter.eq("managerIdea", SysDict.EMPTY.getValue())));
        for (Worker worker : workerList) {
            Account findAccount = accountService.find(worker.getAccountId());
            worker.setAvatar(fileManagerService.getFileUrlByFilepath(findAccount.getAvatar()));
            worker.setName1(findAccount.getName1());
        }
        
        rt.put(TAG_SUCCESS, true);
        Dto data = new Dto();
        data.put("workerList", workerList);
        
        rt.put(TAG_DATA, data);
        return rt;
    }

    //工匠加入请求处理
    @RequestMapping(value = "/accepWorker")
    @ResponseBody
    public Dto accepWorker(HttpServletRequest request,//
            Worker worker) {
        Dto rt = new Dto();
        rt = service.acceptWorker(worker);
        return rt;
    }
    
    // 加入工匠详情
    @RequestMapping(value = "/workerDetails")
    @ResponseBody
    public Dto workerDetails(HttpServletRequest request,//
            @RequestParam(value = "workerId") String workerId) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Dto data = new Dto();
        Worker worker = workerService.findByWorkerId(workerId);
        //统计本月的接单数
        int finishCount = indentService.monthIndent(worker.getId());
        rt.put("finishCount", finishCount);
        //统计本月收入
        BigDecimal monthAmt = workerTradeService.monthEarning(worker.getId());
        if (monthAmt == null) {
            monthAmt = BigDecimal.ZERO;
        }
        rt.put("monthAmt", monthAmt);
        //统计完成的订单数量
        int finishIndentNum = (int) indentService.count(Filter.eq("workerId", worker.getId()), Filter.eq("executeStatus", EnumExecuteStatus.AFTER.name()));
        //统计工人按时交工的订单数量
        int onTimeFinishNum = indentService.onTimeFinishNum(worker.getId(), EnumExecuteStatus.AFTER.name());
        //及时交付率
        BigDecimal onTimeRate = BigDecimal.ONE;
        onTimeRate.setScale(4, RoundingMode.HALF_UP);
        if (finishIndentNum != 0) {
            onTimeRate = new BigDecimal(onTimeFinishNum/finishIndentNum);
        }
        onTimeRate = onTimeRate.multiply(BigDecimal.valueOf(100));
        rt.put("onTimeRate", onTimeRate);
        data.put("worker", worker);
        rt.put("astrictStatus", false);
        if (worker.getAstrictDate().after(new Date())) {
            rt.put("astrictStatus", true);
        }
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, data);
        return rt;
    }
    @RequestMapping(value = "/managerRank")
    @ResponseBody
    public Dto managerRank() {
        Dto rt = new Dto();
        service.reSetRank();
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
    
    @RequestMapping(value = "/astrictWorker")
    @ResponseBody
    public Dto astrictWorker(@RequestParam(value = "astrictDays") String astrictDays,//
            @RequestParam(value = "workerId") String workerId,//
            @RequestParam(value = "comment") String comment) {
        Dto rt = new Dto();
        
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = workerService.find(workerId);
        int astrictDay = Integer.parseInt(astrictDays);
        WorkerAstrict workerAstrict = new WorkerAstrict();
        workerAstrict.setAstrictTime(astrictDay);
        workerAstrict.setWorkerId(workerId);
        workerAstrict.setwAccountId(worker.getAccountId());
        workerAstrict.setComment(comment);
        workerAstrict.setmAccountId(account.getId());
        workerAstrict.setManagerId(account.getManagerId());
        
        rt = workerService.astrictIndent(workerAstrict);
        return rt;
    }
    
    @RequestMapping(value = "/managerRankList")
    @ResponseBody
    public Dto managerRankList() {
        Dto rt = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        Pageable pageable = new Pageable(1, 10);
        List<Order> orders = new ArrayList<Order>();
        orders.add(Order.asc("periodRank"));
        pageable.setOrders(orders);
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.ne("periodRank", 0));
        filters.add(Filter.ne("isHelper", "1"));
        pageable.setFilters(filters);
        Page<Manager> page = service.findPage(pageable);
        List<Manager> ManagerList = page.getContent();
        for (Manager manager : ManagerList) {
            Account findAccount = accountService.find(manager.getAccountId());
            findAccount.setAvatarImg(fileManagerService.getFileUrlByFilepath(findAccount.getAvatar()));
            manager.setAccount(findAccount);
            manager.setPeriodProfit(managerTradeService.monthEarning(manager.getId())==null? new BigDecimal(0):managerTradeService.monthEarning(manager.getId()));
        }
        Manager manager = service.find(account.getManagerId());
        Dto data = new Dto();
        data.put("ManagerList", ManagerList);
        Long counts = service.count();
        BigDecimal managerCounts = new BigDecimal(counts).setScale(4);
        BigDecimal rank;
        if (0 != manager.getPeriodRank()) {
            rank =new BigDecimal(manager.getPeriodRank()).setScale(4);
        } else {
            rank = managerCounts;
        }
        BigDecimal percent =new BigDecimal(1).setScale(4).subtract(rank.divide(managerCounts, 4, RoundingMode.HALF_DOWN)).multiply(new BigDecimal(100));
        data.put("percent", percent);
        data.put("periodProfit", managerTradeService.monthEarning(manager.getId())==null? new BigDecimal(0):managerTradeService.monthEarning(manager.getId()));
        data.put("periodRank", manager.getPeriodRank());
        data.put("advances", NumberUtil.toInt(manager.getPeriodRank(), 0)-NumberUtil.toInt(manager.getPastRank(), 0));
        if (StringUtil.isNotBlank(account.getAvatar())) {
            data.put("avatarImg", fileManagerService.getFileUrlByFilepath(account.getAvatar()));
        }
        
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, data);
        return rt;
    }
    
    
    @RequestMapping(value = "/inviteWorkerList")
    @ResponseBody
    public Dto inviteWorkerList() {
        Dto rt = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        
        List<ManagerReport> managerReports = new ArrayList<ManagerReport>();
        List<String> mobiles = new ArrayList<String>();
        managerReports = managerReportService.findList(Filter.eq("accountId", account.getId()));
        if (managerReports != null) {
            for (ManagerReport managerReport : managerReports) {
                mobiles.add(managerReport.getMobile());
            }
        }
        
        List<Worker> WorkerList = workerService.findList(Filter.eq("managerId", account.getManagerId()));
        if (WorkerList != null) {
            for (Worker worker : WorkerList) {
                Account findAccount = accountService.find(worker.getAccountId());
                mobiles.add(findAccount.getMobile());
            }
        }
        Dto data = new Dto();
        data.put("mobiles", mobiles);
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, data);
        return rt;
    }
    
    //邀请工匠加入
    @RequestMapping(value = "/inviteWorker")
    @ResponseBody
    public Dto inviteWorker(@RequestParam (value = "mobile") String mobile) {
        Dto rt = new Dto();
        //判断手机号 是否注册
        long count = accountService.count(Filter.eq("account", mobile));
        if (count != 0) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "该工匠已经注册哦~");
            return rt;
        }
        //判断手机号是否被提报
        long count2 = managerReportService.count(Filter.eq("mobile", mobile));
        if (count2 != 0) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "该工匠已经被提报哦~");
            return rt;
        }
        Account account = (Account) ThreadContext.getContextBean();
        ManagerReport managerReport = new ManagerReport();
        managerReport.setMobile(mobile);
        managerReport.setAccountId(account.getId());
        managerReport.setManagerId(account.getManagerId());
        managerReport.setReportDate(new Date());
        managerReportService.save(managerReport);
        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_MSG, "添加工匠成功");
        return rt;
    }

    
    private void dealFile(Object o, MultipartFile file, String fieldName) {

        if (file != null && !file.isEmpty()) {
            FileItem fileItem = FileManangerUtil.uploadFile(file);

            String method = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Class<?> clazz = o.getClass();
            try {
                Method m = clazz.getDeclaredMethod(method, String.class);
                m.invoke(o, fileItem.getFilePath());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    //工人更换经理人时的经理人列表
    @RequestMapping(value = "/managerList")
    @ResponseBody
    public Dto managerList(HttpServletRequest request,//
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = workerService.findByWorkerId(account.getWorkerId());
        
        
        if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
            Manager manager = service.findByManagerId(account.getManagerId());
            if (StringUtil.isNotBlank(account.getAvatar())) {
                account.setAvatarImg(fileManagerService.getFileUrlByFilepath(account.getAvatar()));
            }
            manager.setAccount(account);
            List<Manager> list = New.list();
            list.add(manager);
            rt.put(TAG_SUCCESS, true);
            rt.put("managerS", list);
            return rt;
        }

        
        ManagerSf sf = new ManagerSf();
        sf.setRoleStatus(EnumAccountStatus.ENABLED.name());
        sf.setStatus(EnumCheckStatus.PASS.name());
        sf.setServeCity(worker.getServiceCity());
        sf.setIsHelper(0);
        int limit = 5;
        int start = (Integer.valueOf(pageNum) - 1) * limit;
        List<Order> orders = New.list();
        Order order = Order.desc("STARS");
        Order order1 = Order.desc("TOTAL_INDENT");
        orders.add(order);
        orders.add(order1);
        @SuppressWarnings("unchecked")
        List<Manager> list = (List<Manager>) service.list(sf, start, limit, orders);
        for (Manager manager : list) {
            manager.getAccount().setAvatarImg(fileManagerService.getFileUrlByFilepath(manager.getAccount().getAvatar()));
        }
        rt.put("managerS", list);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
    
}