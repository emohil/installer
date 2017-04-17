package com.company.wapi.account.controller;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.company.api.account.service.EnumAccountType;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.bankcard.service.BankCardService;
import com.company.api.dict.service.EnumEnableStatus;
import com.company.api.dict.service.EnumYesno;
import com.company.api.district.service.AreaService;
import com.company.api.district.service.CityService;
import com.company.api.district.service.ProvService;
import com.company.api.fs.service.FileManagerService;
import com.company.api.indent.service.IndentService;
import com.company.api.mgr.service.ManagerReportService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.mgr.service.ManagerTradeService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sctype.service.SctypeSortService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.api.wk.service.WorkerService;
import com.company.api.wk.service.WorkerTradeService;
import com.company.po.account.Account;
import com.company.po.account.Trade;
import com.company.po.bankcard.BankCard;
import com.company.po.district.City;
import com.company.po.district.Prov;
import com.company.po.fs.FileIndex;
import com.company.po.fs.UnFileIndex;
import com.company.po.mgr.Manager;
import com.company.po.mgr.ManagerReport;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeSort;
import com.company.po.wk.Worker;
import com.company.wapi.controller.BaseController;
import com.company.wapi.fw.AccountHelper;
import com.company.api.fw.util.EnvConfig;
import com.company.context.ThreadContext;
import com.company.dto.Filter;
import com.company.util.DigestUtil;
import com.company.util.Dto;
import com.company.util.New;
import com.company.util.NumberUtil;
import com.company.util.RandomUtil;
import com.company.util.StringUtil;

@Controller("wapiAccountController")
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    private static final String TAG_DETAIL = "detail";
    private static final String TAG_ACCOUNTTYPE = "accountType";
    private static final String TAG_TYPE_MEASURE = "C";


    @Autowired
    private AccountService service;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WorkerTradeService workerTradeService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerReportService managerReportService;

    @Autowired
    private ManagerTradeService managerTradeService;

    @Autowired
    private IndentService indentService;

    @Autowired
    private ProvService provService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CityService cityService;

    @Autowired
    private SctypeService sctypeService;

    @Autowired
    private SctypeSortService sctypeSortService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private FileManagerService fileManagerService ;

    @Autowired
    private BankCardService bankCardService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Dto login(HttpServletRequest request, //
            @RequestParam(value = "mobile", required = false) String mobile, //
            @RequestParam(value = "pwd", required = false) String pwd,//
            @RequestParam(value = "registrationId") String registrationId,//
            @RequestParam(value = TAG_ACCOUNTTYPE, required = false) String accountType) {

        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        if (StringUtil.isEmpty(mobile)) {
            // 手机号为必要项
            ret.put(TAG_MSG, "手机号不能为空！");
            return ret;
        }
        if (StringUtil.isEmpty(pwd)) {
            // 密码为必要项
            ret.put(TAG_MSG, "无效的密码！");
            return ret;
        }

        Account account = service.findAccountByMobile(mobile);
        // 没有找到相关用戶信息
        if (account == null) {
            ret.put(TAG_MSG, "无效的用户信息！");
            return ret;
        }

        // 设置默认登陆类型
        if (StringUtil.isEmpty(accountType)) {
            if (account.getManagerType().equals(1)) {
                accountType = EnumAccountType.MANAGER.name();
            } else if (account.getWorkerType().equals(1)) {
                accountType = EnumAccountType.WORKER.name();
            }
        }
        if (StringUtil.isNotEmpty(account.getLastLoginType())) {
            accountType = account.getLastLoginType();
        }

        boolean suitable = false;

        boolean isManager = 1 == NumberUtil.getInt(account.getManagerType(), 0);
        boolean isWorker = 1 == NumberUtil.getInt(account.getWorkerType(), 0);

        if (isManager && EnumAccountType.MANAGER.name().equals(accountType)) {
            // 查看经理人信息
            Manager manager = managerService.find(account.getManagerId());

            if (manager != null) {
                ret.put(TAG_DETAIL, manager);
                ret.put(TAG_ACCOUNTTYPE, accountType);
                suitable = true;
            }
        }

        if (!suitable && isWorker) {
            // 查找工人信息
            Worker worker = workerService.find(account.getWorkerId());

            if (worker != null) {
                ret.put(TAG_DETAIL, worker);
                ret.put(TAG_ACCOUNTTYPE, EnumAccountType.WORKER.name());
                suitable = true;
            }
        }
        service.updateLastLoginTime(account.getId());
        String token = UUID.randomUUID().toString();
        AccountHelper.cacheTokenAccount(token, account);
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_TOKEN, token);
        ret.put(TAG_DATA, account);
        return ret;
    }

    @RequestMapping(value = "/getVcode")
    @ResponseBody
    public Dto getVcode(HttpServletRequest request,//
            @RequestParam(value = "mobile") String mobile) {

        return smsService.getVcode(mobile, SmsTemplate.VCODE, request.getRemoteAddr());
    }

    @RequestMapping(value = "/regiest")
    @ResponseBody
    public Dto regiest(HttpServletRequest request, //
            @RequestParam(value = "mobile", required = false) String mobile, //
            @RequestParam(value = "vcode", required = false) String vcode) {

        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        if (!StringUtil.isNotBlank(mobile)) {
            ret.put(TAG_MSG, "手机号不能为空！");
            return ret;
        }

        if (!StringUtil.isNotBlank(vcode)) {
            ret.put(TAG_MSG, "验证码不能为空！");
            return ret;
        }

        Dto checkResult = smsService.checkVcode(mobile, vcode);

        // 验证 验证码
        if (!checkResult.getAsBoolean(TAG_SUCCESS)) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, checkResult.getAsString(TAG_MSG));
            return ret;
        }

        Account account = service.findAccountByMobile(mobile);

        if (account == null) {
            // 当前手机号没有注册过
            account = new Account();
            account.setMobile(mobile);
            account.setStatus(EnumAccountStatus.ENABLED.name());
            service.createAccount(account);
        }

        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_DATA, account);
        return ret;
    }

    @RequestMapping(value = "/regiestWorker")
    @ResponseBody
    public Dto regiestWorker(HttpServletRequest request, //
            Worker worker, Account accountData) {

        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        Account account = (Account) ThreadContext.getContextBean();
        
        Manager findManager = managerService.find(worker.getManagerId());
        if (findManager.getIsHelper().equals(1)) {
            worker.setManagerIdea(EnumYesno.YES.name());
        }
        
        if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
            Manager manager = managerService.findByManagerId(account.getManagerId());
            if (manager.getStatus().equals(EnumCheckStatus.PASS.name())) {
                worker.setManagerIdea(EnumYesno.YES.name());
            }
        }

        // 判断是否有上传图片
        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

            dealFile(account, files.get("idImgUrlFile"), "idImgUrl", account.getId());
            dealFile(worker, files.get("drivingImgFile"), "drivingImg", worker.getId());
            dealFile(worker, files.get("vehicleImgFile"), "vehicleImg", worker.getId());
        }

        long count = managerReportService.count(Filter.eq("managerId", worker.getManagerId()), Filter.eq("mobile", account.getMobile()));
        if (count > 0) {
            worker.setManagerIdea(EnumYesno.YES.name());
        }

        if (accountData.getIdNum() != null && accountData.getIdNum().length() >= 18) {
            worker.setCode1("W" + accountData.getIdNum().substring(0, 6) + RandomUtil.getRandomStr(4).toUpperCase());
        } else if (account.getIdNum() != null && account.getIdNum().length() >= 18) {
            worker.setCode1("W" + account.getIdNum().substring(0, 6) + RandomUtil.getRandomStr(4).toUpperCase());
        }

        if (StringUtil.isNotEmpty(account.getWorkerId())) {
            worker.setId(account.getWorkerId());
        }
        if (account.getManagerType().equals(0)) {
            this.completeAccount(account, accountData);
        }
        workerService.saveWorker(worker, account);

        ret.put(TAG_DETAIL, worker);
        ret.put(TAG_ACCOUNTTYPE, EnumAccountType.WORKER.name());
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @RequestMapping(value = "/nextGroupManager")
    @ResponseBody
    public Dto nextGroupManager(HttpServletRequest request,//
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,//
            @RequestParam(value = "serviceCity") String serviceCity) {

        Dto ret = new Dto();
        Dto data = new Dto();
        if (!StringUtil.isNotBlank(serviceCity)) {
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "定位失败！");
            return ret;
        }

        Account account = (Account) ThreadContext.getContextBean();
        
        //经理人注册工匠时使用
        if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
            Manager manager = managerService.findByManagerId(account.getManagerId());
            account.setManager(manager);
            List<Account> accountList = New.list();
            accountList.add(account);
            data.put("managers", accountList);
            ret.put(TAG_DATA, data);
            ret.put("pageNum", "1");
            ret.put(TAG_SUCCESS, true);
            return ret;
        }
        
        //工匠被经理人提报
        ManagerReport managerReport = managerReportService.findOne(Filter.eq("mobile", account.getMobile()));
        if (managerReport != null) {
            Manager reportedManager = managerService.find(managerReport.getManagerId());
            if (serviceCity.equals(reportedManager.getServeCity()) && EnumCheckStatus.PASS.name().equals(reportedManager.getStatus())) {
                Account findAccount = service.find(managerReport.getAccountId());
                findAccount.setManager(reportedManager);
                findAccount.setAvatarImg(EnvConfig.getProperty(EnvConfig.DOWNLOAD_BASEPATH) + findAccount.getAvatar());
                List<Account> reportManagers = new ArrayList<Account>();
                reportManagers.add(findAccount);
                data.put("managers", reportManagers);
                ret.put(TAG_DATA, data);
                ret.put("pageNum", "1");
                ret.put(TAG_SUCCESS, true);
                return ret;
            }
        }

        Manager managerQuery = new Manager();
        managerQuery.setServeCity(serviceCity);
        managerQuery.setStatus(EnumCheckStatus.PASS.name());
        managerQuery.setIsHelper(0);
        managerQuery.setStars(3);
        managerQuery.setRoleStatus(EnumAccountStatus.ENABLED.name());
        // 随机选择 3星 经理人
        List<Manager> managerList = managerService.findManagers(managerQuery, 3);
        List<Manager> retManagerList = new ArrayList<Manager>();
        if (managerList.size() > 0) {
            if (managerList.size() < 4) {
                retManagerList = managerList;
                //查找 3星以下 的经理人
                managerQuery.setStars(2);
                int counts = 3 - managerList.size();
                List<Manager> otherManagerList = managerService.findManagers(managerQuery, counts);
                retManagerList.addAll(otherManagerList);
            }
        }

        if (managerList.size() < 1) {
            //查找 3星以下 的经理人
            managerQuery.setStars(2);
            List<Manager> otherManagerList = managerService.findManagers(managerQuery, 3);
            if (otherManagerList.size() > 0) {
                retManagerList = otherManagerList;
            } else {
                // 没有经理人 返回小助手
                Manager managerHelper = managerService.findOne(Filter.eq("serveCity", serviceCity), Filter.eq("status", EnumCheckStatus.PASS.name()), Filter.eq("isHelper", "1"), Filter.eq("roleStatus", EnumAccountStatus.ENABLED.name()));
                if (managerHelper == null) {
                    // 该地区没有小助手 生产一个小助手
                    managerHelper = new Manager();
                    managerHelper.setServeCity(serviceCity);
                    managerHelper.setIsHelper(1);
                    managerHelper.setStatus(EnumCheckStatus.PASS.name());
                    managerHelper.setRoleStatus(EnumAccountStatus.ENABLED.name());
                    managerHelper.setBalAmt(BigDecimal.ZERO);
                    managerHelper.setDepositAmt(BigDecimal.ZERO);

                    Account newAccount = new Account();
                    //newAccount.setAccount(managerHelper.getServeCity());
                    managerHelper = getInitAccount(request, managerHelper, newAccount);
                }
                retManagerList.add(managerHelper);
            }
        }

        List<Account> managerAccountList = New.list();
        for (Manager manager : retManagerList) {
            Account findAccount = service.find(manager.getAccountId());
            findAccount.setManager(manager);
            String imgUrl = EnvConfig.getProperty(EnvConfig.DOWNLOAD_BASEPATH) + findAccount.getAvatar();
            findAccount.setAvatar(imgUrl);
            managerAccountList.add(findAccount);
        }

        data.put("managers", managerAccountList);
        ret.put(TAG_DATA, data);
        ret.put("pageNum", "1");
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    private void completeAccount(Account account, Account accountData) {

        account.setIdNum(accountData.getIdNum());
        account.setAddr1(accountData.getAddr1());
        account.setName1(accountData.getName1());
        account.setBuildNum(accountData.getBuildNum());
        String birthData = accountData.getIdNum().substring(6, 14);
        String birthDay = new StringBuilder().append(birthData.substring(0, 4)).append("-").append(birthData.substring(4, 6)).append("-").append(birthData.substring(6, 8)).toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newBirthDay;
        try {
            newBirthDay = sdf.parse(birthDay);
            account.setBirthDay(newBirthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Manager getInitAccount(HttpServletRequest request, Manager manager, Account account) {

        account.setStatus(EnumAccountStatus.ENABLED.name());
        service.createAccount(account);

        managerService.createHelper(manager, account);
        return manager;
    }

    @RequestMapping(value = "/regiestManager")
    @ResponseBody
    public Dto regiestManager(HttpServletRequest request, //
            Manager manager, Account accountData) {

        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);

        Account account = (Account) ThreadContext.getContextBean();
        if (account.getWorkerType().equals(0)) {
            this.completeAccount(account, accountData);
        }
        if (account.getWorkerType().equals(1) && StringUtil.isNotBlank(account.getWorkerId())) {
            Worker worker = workerService.find(account.getWorkerId());
            manager.setServeCity(worker.getServiceCity());
        }

        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

            dealFile(account, files.get("idImgUrlFile"), "idImgUrl", account.getId());
            dealFile(manager, files.get("charterImgFile"), "charterImg", StringUtil.defaultString(manager.getId(), ""));
            dealFile(manager, files.get("orgCodeImgFile"), "orgCodeImg", StringUtil.defaultString(manager.getId(), ""));
            dealFile(manager, files.get("taxImgFile"), "taxImg", StringUtil.defaultString(manager.getId(), ""));
        }
        managerService.saveManager(manager, account);
        ret.put(TAG_DETAIL, manager);
        ret.put(TAG_ACCOUNTTYPE, EnumAccountType.MANAGER.name());

        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @RequestMapping(value = "/getServeDistrict")
    @ResponseBody
    public Dto getServeDistrict() {
        Dto ret = new Dto();
        Dto data = new Dto();
        List<Prov> provlist = provService.findList(Filter.eq("status", EnumEnableStatus.ENABLED.name()));
        for (Prov prov : provlist) {
            List<City> cityList = cityService.findList(Arrays.asList(Filter.eq("status", EnumEnableStatus.ENABLED.name()), Filter.eq("provId", prov.getId())));
            prov.setCityList(cityList);
            for (City city : cityList) {
                city.setAreaList(areaService.findList(Arrays.asList(Filter.eq("cityId", city.getId()))));
            }
        }
        data.put("proList", provlist);
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_DATA, data);

        return ret;
    }

    @RequestMapping(value = "/getServeType")
    @ResponseBody
    public Dto getServeType() {
        Dto ret = new Dto();
        Dto data = new Dto();
        List<Sctype> serveTypeList = sctypeService.findAll();
        List<SctypeSort> serveSortList = sctypeSortService.findAll();
        data.put("serveTypeList", serveTypeList);
        data.put("serveSortList", serveSortList);
        ret.put(TAG_SUCCESS, true);
        ret.put(TAG_DATA, data);
        return ret;
    }

    @RequestMapping(value = "/getServeSort")
    @ResponseBody
    public Dto getServeSort(@RequestParam(value = "codes") String type) {
        Dto ret = new Dto();
        List<SctypeSort> serveSortList = sctypeSortService.findAll();
        if (!type.contains(TAG_TYPE_MEASURE)) {
            serveSortList = sctypeSortService.findList(Filter.ne("sctypeId", TAG_TYPE_MEASURE));
        }

        ret.put(TAG_DATA, serveSortList);
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    //提现
    @RequestMapping(value = "/drawings")
    @ResponseBody
    public Dto drawings(Trade tradeData,
            @RequestParam(value = "tradePwd") String tradePwd, 
            @RequestParam(value = "role") String role) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        if (role.equals("worker")) {
            Worker worker = workerService.find(account.getWorkerId());
            if (!DigestUtil.md5(tradePwd).equals(worker.getTradePwd())) {
                rt.put(TAG_MSG, "密码不正确，请重新输入");
                return rt;
            }
            return workerTradeService.cashTrade(account.getWorkerId(), tradeData.getCurAmt());
        } else if (role.equals("manager")) {
            Manager manager = managerService.find(account.getManagerId());
            if (!DigestUtil.md5(tradePwd).equals(manager.getTradePwd())) {
                rt.put(TAG_MSG, "密码不正确，请重新输入");
                return rt;
            }
            return managerTradeService.cashTrade(account.getManagerId(), tradeData.getCurAmt());
        } else {
            return rt;
        }
    }

    //绑定银行卡时获取名字
    @RequestMapping(value = "/getName1")
    @ResponseBody
    public Dto getName1() {
        Dto rt = new Dto();
        Account account = (Account) ThreadContext.getContextBean();
        rt.put("name1", account.getName1());
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //绑定银行卡
    @RequestMapping(value = "/bindBankCard")
    @ResponseBody
    public Dto bindBankCard(HttpServletRequest request, 
            Account accountData, BankCard bankCardData, 
            @RequestParam(value = "role") String role,
            @RequestParam(value = "vcode") String vcode) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        bankCardData.setCardOwner(account.getName1());
        Dto data = smsService.checkVcode(accountData.getMobile(), vcode);
        if (!data.getAsBoolean(TAG_SUCCESS)) {
            return data;
        }
        if (role.equals("worker")) {
            Worker worker = workerService.find(account.getWorkerId());
            bankCardData.setOwnerId(worker.getId());
            workerService.bindBankCard(bankCardData, worker);
            String tradePwd = worker.getTradePwd();
            if (StringUtil.isNotBlank(tradePwd)) {
                rt.put("tradePwd", true);
            } else {
                rt.put("tradePwd", false);
            }
        } else if (role.equals("manager")) {
            Manager manager = managerService.find(account.getManagerId());
            bankCardData.setOwnerId(manager.getId());
            managerService.bindBankCard(bankCardData, manager);
            String tradePwd = manager.getTradePwd();
            if (StringUtil.isNotBlank(tradePwd)) {
                rt.put("tradePwd", true);
            } else {
                rt.put("tradePwd", false);
            }
        }
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //验证交易密码
    @RequestMapping(value = "/verifyTradePwd")
    @ResponseBody
    public Dto verifyTradePwd(@RequestParam(value = "tradePwd") String tradePwd, 
            @RequestParam(value = "role") String role) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        String tradePwd1 = "";
        if (role.equals("worker")) {
            Worker worker = workerService.find(account.getWorkerId());
            tradePwd1 = worker.getTradePwd();
        } else if (role.equals("manager")) {
            Manager manager = managerService.find(account.getManagerId());
            tradePwd1 = manager.getTradePwd();
        }
        if (DigestUtil.md5(tradePwd).equals(tradePwd1)) {
            rt.put(TAG_SUCCESS, true);
            return rt;
        }
        rt.put(TAG_MSG, "交易密码错误，请重新输入");
        return rt;
    }

    //设置交易密码
    @RequestMapping(value = "/giveTradePwd")
    @ResponseBody
    public Dto giveTradePwd(HttpServletRequest request,
            @RequestParam(value = "tradePwd") String tradePwd, 
            @RequestParam(value = "role") String role) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        if (role.equals("worker")) {
            Worker worker = workerService.find(account.getWorkerId());
            worker.setTradePwd(DigestUtil.md5(tradePwd));
            workerService.update(worker);
        } else if (role.equals("manager")) {
            Manager manager = managerService.find(account.getManagerId());
            manager.setTradePwd(DigestUtil.md5(tradePwd));
            managerService.update(manager);
        }
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    //获取银行卡信息，以及当前可用余额
    @RequestMapping(value = "getBankCard")
    @ResponseBody
    public Dto getBankCard(@RequestParam(value = "role") String role) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        if (role.equals("worker")) {
            Worker worker = workerService.find(account.getWorkerId());
            BankCard bankCard = bankCardService.findByOwnerId(worker.getId());
            if (bankCard == null) {
                rt.put(TAG_MSG, "没有找到银行卡信息");
                return rt;
            }
            String tradePwd = worker.getTradePwd();
            if (StringUtil.isNotEmpty(tradePwd)) {
                rt.put("tradePwd", true);
            } else {
                rt.put("tradePwd", false);
            }
            rt.put("bankCard", bankCard);
            rt.put("balAmt", worker.getBalAmt());
            rt.put(TAG_SUCCESS, true);
            return rt;
        } else if (role.equals("manager")) {
            Manager manager = managerService.find(account.getManagerId());
            BankCard bankCard = bankCardService.findByOwnerId(manager.getId());
            if (bankCard == null) {
                rt.put(TAG_MSG, "没有找到银行卡信息");
                return rt;
            }
            String tradePwd = manager.getTradePwd();
            if (StringUtil.isNotEmpty(tradePwd)) {
                rt.put("tradePwd", true);
            } else {
                rt.put("tradePwd", false);
            }
            rt.put("balAmt", manager.getBalAmt());
            rt.put("bankCard", bankCard);
            rt.put(TAG_SUCCESS, true);
            return rt;
        }
        return rt;
    }

    //解绑银行卡
    @RequestMapping(value = "/relieveBankCard")
    @ResponseBody
    public Dto relieveBankCard(
            @RequestParam(value = "tradePwd") String tradePwd, 
            @RequestParam(value = "role") String role) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        if (role.equals("worker")) {
            Worker worker = workerService.find(account.getWorkerId());
            return workerService.relieveBankCard(tradePwd, worker);
        } else if (role.equals("manager")) {
            Manager manager = managerService.find(account.getManagerId());
            return managerService.relieveBankCard(tradePwd, manager);
        }
        rt.put(TAG_MSG, "解绑失败");
        return rt;
    }

    //重置密码(确认身份信息)
    @RequestMapping("/affirmMsg")
    @ResponseBody
    public Dto affirmMsg(Account accountData, BankCard bankCardData, 
            @RequestParam(value = "role") String role) {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        BankCard findByOwnerId = null;
        if (role.equals("worker")) {
            findByOwnerId = bankCardService.findByOwnerId(account.getWorkerId());
        } else if (role.equals("manager")) {
            findByOwnerId = bankCardService.findByOwnerId(account.getManagerId());
        }
        if (!accountData.getIdNum().equals(account.getIdNum())) {
            rt.put(TAG_MSG, "身份证号不匹配，请重新输入");
            return rt;
        }
        if (!bankCardData.getCardNo().equals(findByOwnerId.getCardNo())) {
            rt.put(TAG_MSG, "银行卡号不匹配，请重新输入");
            return rt;
        }
        rt.put(TAG_SUCCESS, true);
        return rt;
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
    
    //获取当前手机号
    @RequestMapping("/currentPhone")
    @ResponseBody
    public Dto currentPhone() {
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        rt.put("mobile", account.getMobile());
        rt.put(TAG_SUCCESS, true);
        return rt;
    }

    @RequestMapping(value = "/resetVcode")
    @ResponseBody
    public Dto resetVcode(HttpServletRequest request,//
            @RequestParam(value = "mobile") String mobile) {//新手机号
        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);
        Account account = (Account) ThreadContext.getContextBean();
        Account account2 = service.findOne(Filter.eq("mobile", mobile));
        if (mobile.equals(account.getMobile())) {
            rt.put(TAG_MSG, "不能输入当前手机号");
            return rt;
        } else {
            if (account2 != null) {
                rt.put(TAG_MSG, "此手机号已被使用，请更换其他手机号");
                return rt;
            }
        }
        return smsService.getVcode(account.getMobile(), SmsTemplate.MOBILE_UPDATED, request.getRemoteAddr());
    }
    

    //更换手机号
    @RequestMapping(value = "/updateMobile")
    @ResponseBody
    public Dto updateMobile(HttpServletRequest request, //
            @RequestParam(value = "mobile", required = false) String mobile, //新手机号
            @RequestParam(value = "vcode", required = false) String vcode) {

        Dto rt = new Dto();
        rt.put(TAG_SUCCESS, false);

        Account account = (Account) ThreadContext.getContextBean();
        Dto checkResult = smsService.checkVcode(account.getMobile(), vcode);

        // 验证 验证码
        if (!checkResult.getAsBoolean(TAG_SUCCESS)) {
            rt.put(TAG_SUCCESS, false);
            rt.put(TAG_MSG, checkResult.getAsString(TAG_MSG));
            return rt;
        }
        account.setMobile(mobile);
        account.setAccount(mobile);
        service.update(account);
        rt.put(TAG_SUCCESS, true);
        return rt;
    }
}