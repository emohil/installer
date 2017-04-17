package com.company.web.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.company.api.fw.EnumCodes;
import com.company.api.mgr.service.ManagerService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.api.wk.service.WorkerService;
import com.company.dto.role.ManagerData;
import com.company.dto.wk.WkData;
import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.mgr.Manager;
import com.company.sf.account.ManagerSf;
import com.company.web.BasePagerController;
import com.company.api.fw.service.SysDictService;
import com.company.api.fw.util.MessagePush;
import com.company.context.ThreadContext;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.util.Dto;
import com.company.util.StringUtil;

@Controller("webManagerController")
@RequestMapping(value = "/manager")
public class ManagerController extends BasePagerController<ManagerSf, Manager> {

    private static final String PATH = "manager";

    @Autowired
    private ManagerService service;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SmsService smsService;

    @Autowired
    public void setBaseService(ManagerService service) {
        super.setBaseService(service);
        this.service = service;
    }

    @Override
    protected int doCount(ManagerSf sf) throws Exception {
        return service.count(sf);
    }

    @Override
    protected List<?> doList(ManagerSf sf, int start, int limit, List<Order> orders) throws Exception {
        return service.list(sf, start, limit, orders);
    }

    @RequestMapping(value = "/findByManagerId")
    @ResponseBody
    public Manager findByManagerId(@RequestParam("id") String id) {
        return service.findByManagerId(id);
    }

    @RequestMapping(value = "managerList")
    protected String managerList(HttpServletRequest request, ModelMap model) {
        model.addAttribute("checkStatusList", sysDictService.listGroupCopy(EnumCodes.CHECK_STATUS, SysDict.CHECK));
        model.addAttribute("roleStatusList", sysDictService.listGroupCopy(EnumCodes.ACCOUNT_STATUS, SysDict.CHECK));
        return PATH + "/managerList";
    }

    @RequestMapping(value = "/managerEdit")
    protected String managerEdit(@RequestParam(value = "id") String id,
            @RequestParam(value = "status") String status,
            ModelMap model) {
        model.put(TAG_ID, id);
        model.put("checkStatusList", sysDictService.listGroupCopy(EnumCodes.CHECK_STATUS));
        if (status.equals("0")) {
            return PATH + "/managerEdit";
        } else {
            return PATH + "/managerMsg";
        }
    }


    @RequestMapping(value = "/checkManager")
    @ResponseBody
    public Dto checkManager(@RequestBody Manager manager) {
        Dto ret = new Dto();
        ret.put(TAG_SUCCESS, false);
        String status1 = manager.getStatus();
        Admin admin = (Admin) ThreadContext.getContextBean();
        manager.setVerifier(admin.getName1());
        manager.setVerifierId(admin.getId());
        service.updateManager(manager);
        Account account = accountService.find(manager.getAccountId());
        if (status1.equals(EnumCheckStatus.NOTPASS.name())) {
            //认证失败   推送
            Map<String, String> extras = new HashMap<String, String>();
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(account.getRegistrationId())) {
                registrationIds.add(account.getRegistrationId());
            }
            extras.put("id", "");
            extras.put("pushType", "");
            MessagePush push  = new MessagePush("您的经理人身份认证失败，请及时更换个人资料", "众联工匠", extras);
            push.pushMsgToRegistrationIds(registrationIds);
        }

        if (status1.equals(EnumCheckStatus.PASS.name())) {
            smsService.sendSms(account.getMobile(), SmsTemplate.MANAGER_APPROVED, manager.getCode1());
            
            //认证完成   推送
            Map<String, String> extras = new HashMap<String, String>();
            List<String> registrationIds = new ArrayList<String>();
            if (StringUtil.isNotBlank(account.getRegistrationId())) {
                registrationIds.add(account.getRegistrationId());
            }
            extras.put("id", "");
            extras.put("pushType", "");
            if (registrationIds.size() > 0 ) {
                MessagePush push  = new MessagePush("恭喜您成为认证经理人，工号"+manager.getCode1(), "众联工匠", extras);
                push.pushMsgToRegistrationIds(registrationIds);
            }
        }
        ret.put(TAG_SUCCESS, true);
        return ret;
    }

    @RequestMapping(value = "/controlManager")
    public String controlManager(@RequestParam(value = "id") String id,
            @RequestParam(value = "status") String status) {
        Manager manager = service.find(id);
        String status1 = manager.getStatus();
        if (status.equals("0")) {
            manager.setRoleStatus(EnumAccountStatus.DISABLED.name());
        } else {
            manager.setRoleStatus(EnumAccountStatus.ENABLED.name());
        }
        service.update(manager);
        String status2 = manager.getStatus();
        if (status2.equals(EnumCheckStatus.PASS.name()) && !status2.equals(status1)) {
            Account account = accountService.find(manager.getAccountId());
            smsService.sendSms(account.getMobile(), SmsTemplate.MANAGER_APPROVED, manager.getCode1());
        }
        return "redirect:/manager/managerList.do";   
    }

    @RequestMapping(value = "managerData")
    @ResponseBody
    public List<ManagerData> managerData() {

        return service.queryPassedManagers();
    }

    @RequestMapping(value = "wkList")
    public String wkList(@RequestParam("id") String id,
            ModelMap model) {
        model.put("id", id);
        return PATH + "/wkList";
    }

    @RequestMapping(value = "findWkList")
    @ResponseBody
    public List<WkData> findWkList(@RequestParam("id") String id) {
        return workerService.findListByManagerId(id);
    }

}
