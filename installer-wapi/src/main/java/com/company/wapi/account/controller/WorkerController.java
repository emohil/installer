package com.company.wapi.account.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountType;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumTradeType;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fs.service.FileManagerService;
import com.company.api.fw.Constants;
import com.company.api.fw.DictCodes;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.sms.service.SmsService;
import com.company.api.wk.service.WkMgrSlaveService;
import com.company.api.wk.service.WorkerDepositService;
import com.company.api.wk.service.WorkerService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.po.account.Account;
import com.company.po.bankcard.BankCard;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.po.mgr.Manager;
import com.company.po.wk.WkMgrSlave;
import com.company.po.wk.Worker;
import com.company.po.wk.WorkerDeposit;
import com.company.po.wk.WorkerTrade;
import com.company.sf.account.WorkerTradeSf;
import com.company.wapi.controller.BaseController;
import com.company.api.fw.service.SysDictService;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.dto.Order;
import com.company.dto.Page;
import com.company.dto.Pageable;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.NumberUtil;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;

/**
 * 
 * @author kouwl
 *
 */
@Controller("wapiWorkerController")
@RequestMapping(value = "/worker")
public class WorkerController extends BaseController {

    private static final Integer LIMIT = 10;

    private static final String TAG_DETAIL = "detail";

    private static final String TAG_ACCOUNTTYPE = "accountType";

    @Autowired
    private WorkerService service;

    @Autowired
    private IndentService indentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private WorkerTradeService workerTradeService;

    @Autowired
    private WorkerDepositService workerDepositService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private WkMgrSlaveService slaveService;

    //工匠我的页面
    @RequestMapping(value = "/myself")
    @ResponseBody
    public Dto myself(HttpServletRequest request) {
        Dto rt = vehicle();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());
        //头像路径
        if (StringUtil.isNotEmpty(account.getAvatar())) {
            account.setAvatarImg(fileManagerService.getFileUrlByFilepath(account.getAvatar()));//头像
        }
        worker.setAccount(account);
        if (worker.getManagerIdea().equals(EnumYesno.NO.name())) {
            rt.put("manager", null);
        } else {
            Manager manager = managerService.find(worker.getManagerId());
            Account managerAccount = accountService.find(manager.getAccountId());
            worker.setManagerIdDisp(managerAccount.getName1());
        }
        rt.put("worker", worker);//头像、姓名、等级（先隐藏）、星级、接单数、钱包余额
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //工匠个人主页
    @RequestMapping(value = "/myHomepage")
    @ResponseBody
    public Dto myHomepage(HttpServletRequest request) {
        Dto rt = myself(request);
        rt.put(TAG_SUCCESS, false);
        Worker worker = (Worker) rt.get("worker");
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
        int finishIndentNum = (int)indentService.count(Filter.eq("workerId", worker.getId()), Filter.eq("executeStatus", EnumExecuteStatus.AFTER.name()));
        BigDecimal finishNum = new BigDecimal(finishIndentNum);
        finishNum.setScale(4, RoundingMode.HALF_UP);
        //统计工人按时交工的订单数量
        int onTimeFinishNum = indentService.onTimeFinishNum(worker.getId(), EnumExecuteStatus.AFTER.name());
        BigDecimal onTimeNum = new BigDecimal(onTimeFinishNum);
        onTimeNum.setScale(4, RoundingMode.HALF_UP);
        //及时交付率
        BigDecimal onTimeRate = BigDecimal.ONE;
        onTimeRate.setScale(4, RoundingMode.HALF_UP);
        if (finishIndentNum != 0) {
            onTimeRate = onTimeNum.divide(finishNum, 4, RoundingMode.HALF_UP);
        }
        onTimeRate = onTimeRate.multiply(BigDecimal.valueOf(100));
        rt.put("onTimeRate", onTimeRate);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //个人钱包
    @RequestMapping(value = "/myWallet")
    @ResponseBody
    public Dto myWallet(HttpServletRequest request) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());
        BankCard bankcard = bankCardService.findByOwnerId(worker.getId());
        if (bankcard != null) {
            rt.put("bankCard", bankcard);
        } else {
            rt.put("bankCard", null);
        }
        rt.put("balAmt", worker.getBalAmt());
        rt.put("depositAmt", worker.getDepositAmt());
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //我的经理人
    @RequestMapping(value = "/myManager")
    @ResponseBody
    public Dto myManager(HttpServletRequest request) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());
        
        if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
            if (worker.getManagerId().equals(account.getManagerId())) {
                rt.put("myMgr", true);
            } else {
                rt.put("myMgr", false);
            }
        } else {
            rt.put("myMgr", false);
        }
        
        if (!worker.getManagerIdea().equals(EnumYesno.NO.name())) {
            Manager manager = managerService.find(worker.getManagerId());
            Account managerAccount = accountService.find(manager.getAccountId());
            if (StringUtil.isNotEmpty(managerAccount.getAvatar())) {
                managerAccount.setAvatarImg(fileManagerService.getFileUrlByFilepath(managerAccount.getAvatar()));
            }
            if (account.getManagerType().equals(0)) {
                rt.put("isManager", false);
            } else {
                rt.put("isManager", true);
            }
            if (worker.getManagerIdea().equals(EnumYesno.YES.name())) {
                rt.put("isAgree", true);
            } else {
                rt.put("isAgree", false);
            }
            manager.setAccount(managerAccount);
            rt.put(TAG_SUCCESS, true);
            rt.put("manager", manager);
        }
        return rt;
    }

    //加载设置信息
    @RequestMapping(value = "/options")
    @ResponseBody
    public Dto options(HttpServletRequest request, //
            @RequestParam(value = "registrationId", required = false) String registrationId,//
            @RequestParam(value = "versionNumber", required = false) String versionNumber,//
            @RequestParam(value = "accountType", required = false) String accountType) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.findByWorkerId(account.getWorkerId());
        if (StringUtil.isNotBlank(versionNumber)) {
            Properties properties = new Properties();
            String newestVersionNum = null;
            String versionUrl = null;
            try {
                properties = PropertiesLoaderUtils.loadAllProperties("environment.properties");
                newestVersionNum = properties.getProperty("versionNumber");
                versionUrl = properties.getProperty("versionUrl");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (worker.getStatus().equals(EnumCheckStatus.PASS.name())) {
            rt.put("isIdentityAuthentication", 0);
        } else {
            if (StringUtil.isNotEmpty(account.getIdImgUrl())) {
                rt.put("isIdentityAuthentication", 1);
            } else {
                rt.put("isIdentityAuthentication", 2);
            }
        }
        if (StringUtil.isNotEmpty(worker.getVehicle())) {
            rt.put("vehicle", worker.getVehicle());
        } else {
            rt.put("vehicle", false);
        }
        rt.put("worker", worker);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //是否认证通过汽车
    @RequestMapping(value = "/isCheckVehicle")
    @ResponseBody
    public Dto isCheckVehicle() {
        Dto rt = vehicle();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());
        rt.put("checkStatus",worker.getVehicleStatus());
        rt.put("holdWay",worker.getHoldWay());
        rt.put("vehicle",worker.getVehicle());
        rt.put(TAG_SUCCESS, true);
        return rt;
    }


    //加载车型，拥有方式
    @RequestMapping(value = "/vehicle")
    @ResponseBody
    public Dto vehicle() {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        rt.put("vehicleList", sysDictService.listGroupCopy(DictCodes.VEHICLE));
        rt.put("belongsList", sysDictService.listGroupCopy(DictCodes.VEHICLE_BELONGS));
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //加载余额明细
    @RequestMapping(value = "/balanceDetail")
    @ResponseBody
    public Dto balanceDetail(HttpServletRequest request,
            @RequestParam(value = "pageNum", required = false, defaultValue = "0") String pageNum,
            @RequestParam(value = "type", required = false) String type) {

        int pageNumber = Integer.parseInt(pageNum);
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());
        WorkerTradeSf sf = new WorkerTradeSf();
        sf.setWorkerId(worker.getId());
        if (StringUtil.isNotBlank(type)) {
            if (type.equals(EnumTradeType.CASH.name())) {
                sf.setTradeType(EnumTradeType.CASH.name());
            } else if (type.equals(EnumTradeType.EARNING.name())) {
                sf.setTradeType(EnumTradeType.EARNING.name());
            }
        }
        int start = (pageNumber - 1) * LIMIT + 1;
        List<Order> orders = New.list();
        orders.add(Order.desc("CREATE_DATE"));
        List<WorkerTrade> workerTradeList = workerTradeService.list(sf, start, LIMIT, orders);
        rt.put("myMoneyList", workerTradeList);
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
        Worker worker = service.find(account.getWorkerId());
        WorkerDeposit wd = new WorkerDeposit();
        wd.setWorkerId(worker.getId());
        if (StringUtil.isNotBlank(type)) {
            if (type.equals(EnumTradeType.DEDUCT.name())) {
                wd.setTradeType(EnumTradeType.DEDUCT.name());
            } else if (type.equals(EnumTradeType.EARNING.name())) {
                wd.setTradeType(EnumTradeType.EARNING.name());
            }
        }
        int start = (pageNumber - 1) * LIMIT + 1;
        List<Order> orders = New.list();
        orders.add(Order.desc("CREATE_DATE"));
        List<WorkerDeposit> depositList = workerDepositService.list(wd, start, LIMIT, orders);
        rt.put("depositList", depositList);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //修改个人信息
    @RequestMapping(value = "/updateMsg")
    @ResponseBody
    public Dto updateMsg(HttpServletRequest request, //
            Worker workerData, Account accountData,
            @RequestParam(value = "birthday", required = false) String birthday) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());


        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

            dealFile(account, files.get("avatarFile"), "avatar", account.getId());
            if (files.get("idImgUrlFile") != null) {
                worker.setStatus(EnumCheckStatus.UNCHECK.name());
            }
            dealFile(account, files.get("idImgUrlFile"), "idImgUrl", account.getId());
            if (files.get("drivingImgFile") != null || files.get("vehicleImgFile") != null) {
                worker.setVehicleStatus(EnumCheckStatus.UNCHECK.name());
            }

            dealFile(worker, files.get("drivingImgFile"), "drivingImg", worker.getId());
            dealFile(worker, files.get("vehicleImgFile"), "vehicleImg", worker.getId());
        }
        //交通工具持有方式
        if (StringUtil.isNotBlank(workerData.getHoldWay())) {
            worker.setHoldWay(workerData.getHoldWay());
            if (workerData.getHoldWay().equals("NONE")) {
                worker.setVehicle("");
                worker.setCarNumber("");
                worker.setDrivingImg("");
                worker.setVehicleImg("");
            }
        }
        //姓名
        if (StringUtil.isNotBlank(accountData.getName1())) {
            account.setName1(accountData.getName1());
            worker.setStatus(EnumCheckStatus.UNCHECK.name());
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
            worker.setStatus(EnumCheckStatus.UNCHECK.name());
        }
        //现住址
        if (StringUtil.isNotBlank(accountData.getAddr1())) {
            account.setAddr1(accountData.getAddr1());
        }
        //现住址楼号门牌号
        if (StringUtil.isNotBlank(accountData.getBuildNum())) {
            account.setBuildNum(accountData.getBuildNum());
        }
        //服务城市code
        if (StringUtil.isNotBlank(workerData.getServiceCity())) {
            worker.setServiceCity(workerData.getServiceCity());
        }
        //服务地区code
        if (StringUtil.isNotBlank(workerData.getServiceCounty())) {
            worker.setServiceCounty(workerData.getServiceCounty());
        }
        //交通工具
        if (StringUtil.isNotBlank(workerData.getVehicle())) {
            worker.setVehicle(workerData.getVehicle());
        }
        //技能类型
        if (StringUtil.isNotBlank(workerData.getServiceCategory())) {
            worker.setServiceCategory(Constants.MULTI_VALUE_SEP + workerData.getServiceCategory());
        }
        //技能种类
        if (StringUtil.isNotBlank(workerData.getSkillType())) {
            Dto skill = new Dto();
            String[] skills = workerData.getSkillType().split("\\|");
            for (int i = 0; i < skills.length; i++) {
                skill.put(skills[i], skills[i]);
            }
            String string = Constants.MULTI_VALUE_SEP;
            Iterator<String> iterator = skill.keySet().iterator();
            while (iterator.hasNext()) {
                string += (String) iterator.next() + Constants.MULTI_VALUE_SEP;
            }
            worker.setSkillType(string);
        }
        accountService.update(account, "pwd");
        service.update(worker);

        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @RequestMapping(value = "/workerRankList")
    @ResponseBody
    public Dto workerRankList() {
        Dto rt = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        Pageable pageable = new Pageable(1, 10);
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(Filter.ne("periodRank", 0));
        List<Order> orders = new ArrayList<Order>();
        orders.add(Order.asc("periodRank"));
        pageable.setFilters(filters);
        pageable.setOrders(orders);
        Page<Worker> workerPage = service.findPage(pageable);
        List<Worker> workerList = workerPage.getContent();
        for (Worker worker : workerList) {
            Account findAccount = accountService.find(worker.getAccountId());
            if (StringUtil.isNotBlank(findAccount.getAvatar())) {
                findAccount.setAvatarImg(fileManagerService.getFileUrlByFilepath(findAccount.getAvatar()));
            }
            worker.setAccount(findAccount);
            worker.setPeriodProfit(workerTradeService.monthEarning(worker.getId())==null? new BigDecimal(0):workerTradeService.monthEarning(worker.getId()));;
        }
        Worker worker = service.find(account.getWorkerId());
        Dto data = new Dto();
        data.put("WorkerList", workerList);
        data.put("worker", worker);
        Long counts = service.count();
        BigDecimal workerCounts = new BigDecimal(counts).setScale(4);
        BigDecimal rank;
        if (0 != worker.getPeriodRank()) {
            rank = new BigDecimal(worker.getPeriodRank()).setScale(4);
        } else {
            rank = workerCounts;
        }
        BigDecimal percent =new BigDecimal(1).subtract(rank.divide(workerCounts,4 , RoundingMode.HALF_DOWN)).multiply(new BigDecimal(100));
        data.put("percent", percent);
        data.put("periodProfit", workerTradeService.monthEarning(worker.getId())==null? new BigDecimal(0):workerTradeService.monthEarning(worker.getId()));
        data.put("periodRank", worker.getPeriodRank());
        data.put("advances", NumberUtil.toInt(worker.getPeriodRank(), 0)-NumberUtil.toInt(worker.getPastRank(), 0));
        if (StringUtil.isNotBlank(account.getAvatar())) {
            data.put("avatarImg", fileManagerService.getFileUrlByFilepath(account.getAvatar()));
        }

        rt.put(TAG_SUCCESS, true);
        rt.put(TAG_DATA, data);
        return rt;
    }

    @RequestMapping(value = "/regiestWorker")
    @ResponseBody
    public Dto regiestWorker(HttpServletRequest request, //
            Worker worker) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        Account account = (Account) ThreadContext.getContextBean();

        // 判断是否有上传图片
        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

            dealFile(account, files.get("idImgUrlFile"), "idImgUrl", account.getId());
            dealFile(worker, files.get("drivingImgFile"), "drivingImg", worker.getId());
            dealFile(worker, files.get("vehicleImgFile"), "vehicleImg", worker.getId());
        }

        if (account.getIdNum() != null && account.getIdNum().length() >= 18) {
            worker.setCode1("W" + account.getIdNum().substring(0, 6) + RandomUtil.getRandomStr(4).toUpperCase());
        }

        worker.setManagerId(account.getManagerId());
        worker.setManagerIdea(EnumYesno.YES.name());

        if (StringUtil.isNotEmpty(account.getWorkerId())) {
            worker.setId(account.getWorkerId());
        }
        service.saveWorker(worker, account);

        ret.put(TAG_DETAIL, worker);
        ret.put(TAG_ACCOUNTTYPE, EnumAccountType.WORKER.name());
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @RequestMapping(value = "/workerIndents")
    @ResponseBody
    public Dto workerIndents() {
        Dto rt = new Dto();

        Account account = (Account) ThreadContext.getContextBean();
        //未完成 或暂停的单子
        long count = indentService.count(Filter.ne("executeStatus", EnumExecuteStatus.AFTER.name()), Filter.eq("workerId", account.getWorkerId()));
        if (count > 0) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "有未完成订单，暂不可更换经理人");
        } else {
            rt.put(TAG_SUCCESS, true);
            rt.put(TAG_MSG, "确定继续操作？");
        }
        if (account.getWorkerType().equals(1) && StringUtil.isNotBlank(account.getWorkerId())) {
            Worker worker = service.find(account.getWorkerId());
            if (!worker.getStatus().equals(EnumCheckStatus.PASS.name())) {
                rt.put(TAG_SUCCESS, false);
                rt.put(TAG_MSG, "您的工人身份暂时没有通过审核，不能进行该操作");
            }
            return rt;
        }
        return rt;
    }
    
    //用于经理人修改身份信息时的控制
    @RequestMapping(value = "/workerIndent")
    @ResponseBody
    public Dto workerIndent() {
        Dto rt = new Dto();

        Account account = (Account) ThreadContext.getContextBean();
        //未完成 或暂停的单子
        rt.put(TAG_SUCCESS, true);
        long count = indentService.count(Filter.ne("executeStatus", EnumExecuteStatus.AFTER.name()), Filter.eq("workerId", account.getWorkerId()));
        if (count > 0) {
            rt.put("msg", "有未完成订单，暂不可操作");
        }
        return rt;
    }

    @RequestMapping(value = "/regManager")
    @ResponseBody
    public Dto regManager() {
        Dto rt = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        //未完成 或暂停的单子
        long count = indentService.count(Filter.ne("executeStatus", EnumExecuteStatus.AFTER.name()), Filter.eq("workerId", account.getWorkerId()));
        if (count > 0) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, "有未完成订单，暂不可申请成为经理人");
        }
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @RequestMapping(value = "/chageManager")
    @ResponseBody
    public Dto chageManager(WkMgrSlave newSlave) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Worker worker = service.find(account.getWorkerId());
        if (worker.getManagerId().equals(newSlave.getManagerId())) {
            rt.put(TAG_MSG, "您已是当前经理人的工人，请选择其他经理人");
            return rt;
        }

        return service.changeManager(worker, newSlave);
    }

    private void dealFile(Object o, MultipartFile file, String fieldName, String id) {

        if (file != null && !file.isEmpty()) {
            UnFileIndex ufi = new UnFileIndex(file, id, "ACCOUNT", fieldName);
            FileIndex fileIndex = fileManagerService.save(ufi);
            String method = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            Class<?> clazz = o.getClass();
            try {
                Method m = clazz.getDeclaredMethod(method, String.class);
                m.invoke(o, fileIndex.getFilePath());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}