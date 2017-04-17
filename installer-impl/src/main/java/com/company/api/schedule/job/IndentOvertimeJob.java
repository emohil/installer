package com.company.api.schedule.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountType;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.indent.service.IndentContactService;
import com.company.api.indent.service.IndentService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.po.account.Account;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContact;
import com.company.context.SpringContextHolder;
import com.company.dto.Filter;
import com.company.util.StringUtil;

/**
 * 订单到期提醒
 * 
 * @author lihome
 *
 */
public class IndentOvertimeJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IndentService indentService = SpringContextHolder.getBean(IndentService.BEAN_NAME);
        AccountService accountService = SpringContextHolder.getBean(AccountService.BEAN_NAME);
        SmsService smsService = SpringContextHolder.getBean(SmsService.BEAN_NAME);
        IndentContactService indentContactService = SpringContextHolder.getBean(IndentContactService.BEAN_NAME);
        List<Indent> findOverTimeIndent = indentService.findOverTimeIndent();
        for (Indent indent : findOverTimeIndent) {
            Account workerAccount = accountService.findOne(Filter.eq("workerId", indent.getWorkerId()));
            Account managerAccount = accountService.findOne(Filter.eq("managerId", indent.getManagerId()));
            IndentContact contact = indentContactService.findByIndentId(indent.getId());
            {//推送给工匠订单即将超时
                if (EnumAccountType.WORKER.name().equals(workerAccount.getLastLoginType())) {
                    Map<String, String> extras = new HashMap<String, String>();
                    extras.put("id", indent.getId());
                    extras.put("accountType", EnumAccountType.WORKER.name());
                    extras.put("pushType", EnumJpushType.INDENT_DETAIL.name());
                    List<String> workerRegistrationId = new ArrayList<String>();
                    if (StringUtil.isNotBlank(workerAccount.getRegistrationId())) {
                        workerRegistrationId.add(workerAccount.getRegistrationId());
                    }
                    if (workerRegistrationId.size() > 0) {
                        MessagePush push = new MessagePush("订单即将超时，有问题及时反馈!", "众联工匠", extras);
                        push.pushMsgToRegistrationIds(workerRegistrationId);
                    }
                    smsService.sendSms(workerAccount.getMobile(), SmsTemplate.TIMEOUT_WORKER, indent.getCode1(), contact.getName1());
                }
            }

            {//推送给经理人订单即将超时
                if (EnumAccountType.MANAGER.name().equals(managerAccount.getLastLoginType())) {
                    Map<String, String> extras = new HashMap<String, String>();
                    extras.put("id", indent.getId());
                    extras.put("accountType", EnumAccountType.MANAGER.name());
                    extras.put("pushType", EnumJpushType.INDENT_DETAIL.name());
                    List<String> managerRegistrationId = new ArrayList<String>();
                    if (StringUtil.isNotBlank(managerAccount.getRegistrationId())) {
                        managerRegistrationId.add(managerAccount.getRegistrationId());
                    }
                    MessagePush push = new MessagePush(workerAccount.getName1()+"订单即将超时，有问题及时反馈!", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(managerRegistrationId);
                    smsService.sendSms(workerAccount.getMobile(), SmsTemplate.TIMEOUT_WORKER, indent.getCode1(), contact.getName1());
                }
            }
        }
    }

}
