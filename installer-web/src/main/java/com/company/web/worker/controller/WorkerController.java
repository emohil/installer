package com.company.web.worker.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountStatus;
import com.company.api.account.service.EnumCheckStatus;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fw.EnumCodes;
import com.company.api.mgr.service.ManagerService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.api.wk.service.WorkerService;
import com.company.dto.mgr.MgrData;
import com.company.dto.role.WorkerData;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.mgr.Manager;
import com.company.po.wk.Worker;
import com.company.sf.account.WorkerSf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.util.MessagePush;
import com.company.context.ThreadContext;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.util.Dto;
import com.company.util.StringUtil;

@Controller("webWorkerController")
@RequestMapping(value = "/worker")
public class WorkerController extends BasePagerController<WorkerSf, Worker> {

    private static final String PATH = "worker";

    @Autowired
    private WorkerService service;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    public SmsService smsService;

    @Autowired
    public ManagerService managerService;

    @Autowired
    public void setBaseService(WorkerService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(WorkerSf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(WorkerSf sf, int start, int limit, List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }

    @RequestMapping(value = "/findWorkerById")
    @ResponseBody
    public Worker findWorkerById(HttpServletRequest request,
            @RequestParam("id") String id ) {
        return service.findByWorkerId(id);
    }

    @RequestMapping(value = "workerList")
    protected String workerList(HttpServletRequest request, ModelMap model) {
        model.addAttribute("checkStatusList", sysDictService.listGroupCopy(EnumCodes.CHECK_STATUS, SysDict.CHECK));
        model.addAttribute("roleStatusList", sysDictService.listGroupCopy(EnumCodes.ACCOUNT_STATUS, SysDict.CHECK));
        return PATH + "/workerList";
    }

    @RequestMapping(value = "/workerEdit")
    protected String workerEdit(@RequestParam(value = "id") String id, 
            @RequestParam(value = "status") String status,
            ModelMap model) {
        model.put(TAG_ID, id);
        model.put("checkStatusList", sysDictService.listGroupCopy(EnumCodes.CHECK_STATUS));
        if (status.equals("0")) {
            return PATH + "/workerEdit";
        } else {
            return PATH + "/workerMsg";
        }
    }
    @RequestMapping(value = "/vehicleEdit")
    protected String vehicleEdit(@RequestParam(value = "id") String id, 
            @RequestParam(value = "status") String status,
            ModelMap model) {
        model.put(TAG_ID, id);
        model.put("checkStatusList", sysDictService.listGroupCopy(EnumCodes.CHECK_STATUS));
        if (status.equals("0")) {
            return PATH + "/vehicleEdit";
        } else {
            return PATH + "/vehicleMsg";
        }
    }

    @RequestMapping(value = "/checkWorker")
    @ResponseBody
    public Dto checkWorker(@RequestBody Worker worker) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        String status1 = worker.getStatus();
        Admin admin = (Admin) ThreadContext.getContextBean();
        worker.setVerifier(admin.getName1());
        worker.setVerifierId(admin.getId());
        worker.setVerifierDate(new Date());
        service.update(worker);
        Account account = accountService.find(worker.getAccountId());
        if (status1.equals(EnumCheckStatus.NOTPASS.name())) {
            {//认证失败 推送
                Map<String, String> extras = new HashMap<String, String>();
                List<String> registrationIds = new ArrayList<String>();
                if (StringUtil.isNotBlank(account.getRegistrationId())) {
                    registrationIds.add(account.getRegistrationId());
                }
                extras.put("id", "");
                extras.put("pushType", "");

                if (registrationIds.size() > 0) {
                    MessagePush push  = new MessagePush("您的工匠身份审核未通过，请及时更换个人资料", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(registrationIds);
                }
            }
        }

        if (status1.equals(EnumCheckStatus.PASS.name())) {
            if (account.getManagerType().equals(1) && StringUtil.isNotBlank(account.getManagerId())) {
                Manager manager = managerService.find(account.getManagerId());
                worker.setManagerId(manager.getId());
                if (manager.getStatus().equals(EnumCheckStatus.PASS.name())) {
                    worker.setManagerIdea(EnumYesno.YES.name());
                } else {
                    worker.setManagerIdea(SysDict.EMPTY.getValue());
                }
            }

            smsService.sendSms(account.getMobile(), SmsTemplate.WORKER_APPROVED, worker.getCode1());

            {//认证完成  推送
                Map<String, String> extras = new HashMap<String, String>();
                List<String> registrationIds = new ArrayList<String>();
                if (StringUtil.isNotBlank(account.getRegistrationId())) {
                    registrationIds.add(account.getRegistrationId());
                }
                extras.put("id", "");
                extras.put("pushType", "");

                if (registrationIds.size() > 0) {
                    MessagePush push  = new MessagePush("恭喜您成为认证工匠，工号"+worker.getCode1(), "众联工匠", extras);
                    push.pushMsgToRegistrationIds(registrationIds);
                }
            }
        }
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @RequestMapping(value = "/checkVehicle")
    @ResponseBody
    public Dto checkVehicle(@RequestBody Worker worker) {


        Admin admin = (Admin) ThreadContext.getContextBean();
        Dto ret = service.checkVehicle(admin, worker);
        return ret;
    }


    @RequestMapping(value = "/controlWorker")
    public String controlWorker(@RequestParam(value = "id") String id,
            @RequestParam(value = "status") String status) {
        Worker worker = service.find(id);

        if (status.equals("0")) {
            worker.setRoleStatus(EnumAccountStatus.DISABLED.name());
        } else {
            worker.setRoleStatus(EnumAccountStatus.ENABLED.name());
        }
        service.update(worker);

        return "redirect:/worker/workerList.do";
    }

    @RequestMapping(value = "workerData")
    @ResponseBody
    public List<WorkerData> workerData(@RequestParam(value = "managerId", required = false) String managerId,//
            @RequestParam(value = "serveType", required = false) String serveType,//
            @RequestParam(value = "regionDist", required = false ) String regionDist,//
            @RequestParam(value = "sorts", required = false) String sorts) {

        if (StringUtil.isBlank(regionDist)) {
            regionDist = "";
        }
        List<String> sortlist = new ArrayList<String>();
        if (StringUtil.isNotBlank(sorts)) {
            Dto params = new Dto();
            String[] splits = sorts.split("_");
            for (int i = 0; i < splits.length-1; i++) {
                String key = splits[i].substring(1, 2);
                params.put(key, key);
            }
            Iterator<Object> iterator = params.values().iterator();
            while (iterator.hasNext()) {
                String object = (String) iterator.next();
                sortlist.add(object);
            }
        }
        return service.workerData(managerId, serveType, regionDist, sortlist);
    }

    @RequestMapping(value = "mgrList")
    public String mgrList(@RequestParam("id") String id,
            ModelMap model) {
        model.put("id", id);
        return PATH + "/mgrList";
    }

    @RequestMapping(value = "findMgrList")
    @ResponseBody
    public List<MgrData> findMgrList(@RequestParam("id") String id) {
        return managerService.findListByWorkerId(id);
    }
}
