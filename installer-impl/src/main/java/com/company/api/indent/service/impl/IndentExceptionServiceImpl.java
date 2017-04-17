package com.company.api.indent.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.account.service.EnumAccountType;
import com.company.api.dict.service.EnumPart;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumExceptionResultStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.IndentContentService;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentService;
import com.company.api.mgr.service.ManagerDepositService;
import com.company.api.mgr.service.ManagerService;
import com.company.api.sctype.service.SctypeContentService;
import com.company.api.sctype.service.SctypeService;
import com.company.api.sms.service.SmsService;
import com.company.api.sms.service.SmsTemplate;
import com.company.api.wk.service.WorkerDepositService;
import com.company.api.wk.service.WorkerService;
import com.company.po.account.Account;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentContent;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentNode;
import com.company.po.mgr.Manager;
import com.company.po.sctype.Sctype;
import com.company.po.sctype.SctypeContent;
import com.company.po.wk.Worker;
import com.company.dto.Filter;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentExceptionDao;
import com.company.util.Dto;
import com.company.util.StringUtil;

@Service(IndentExceptionService.BEAN_NAME)
public class IndentExceptionServiceImpl extends StringIdBaseServiceImpl<IndentException>implements IndentExceptionService {
    
    private static final String PUSH_TYPE = "pushType";
    
    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private IndentContentService indentContentService;
    
    @Autowired
    private IndentNodeService indentNodeService;
    
    @Autowired
    private SctypeService sctypeService;
    
    @Autowired
    private SctypeContentService sctypeContentService;
    
    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private WorkerDepositService workerDepositService;
    
    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private ManagerDepositService managerDepositService;
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    public void setBaseDao(IndentExceptionDao dao) {
        super.setBaseDao(dao);
    }
    
    @Override
    public IndentException find(String id) {
        IndentException data = super.find(id);
        Account account = accountService.find(data.getRepId());
        data.setAccount(account);
        return data;
    }

    @Override
    public void scanException(String id) {
        IndentException indentException = find(id);
        Indent indent = indentService.find(indentException.getIndentId());
        
        String sql = "update ZL_INDENT_EXCEP set EXECUTE_STATUS = ?, ACCEPTED_DATE = ? where ID = ?";
        sqlDao.execUpdate(sql, EnumExecuteStatus.CENTRE.name(),new Date(), id);
        
        String serveType = indent.getServeType();
        Sctype sctype = sctypeService.find(serveType);
        Worker worker = workerService.find(indent.getWorkerId());
        Account workerAccount = accountService.find(worker.getAccountId());
        List<IndentContent> indentContentList = indentContentService.findList(Filter.eq("indentId", indent.getId()));
        String sortName = completeSortName(indentContentList);
        IndentNode findIndentNode = indentNodeService.find(indentException.getIndentNodeId());
        {//推送给工匠问题反馈受理
            if (EnumAccountType.WORKER.name().equals(workerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", indent.getId());
                extras.put("nodeId", indentException.getIndentNodeId());
                extras.put("accountType", workerAccount.getLastLoginType());
                extras.put(PUSH_TYPE, findIndentNode.getCode1().toUpperCase());
                List<String> workerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(workerAccount.getRegistrationId())) {
                    workerRegistrationId.add(workerAccount.getRegistrationId());
                }
                if (workerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush("问题反馈-客服已受理("+sctype.getName1()+sortName+")", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(workerRegistrationId);
                }
            }
        }

        {//推送给经理人问题反馈受理
            Manager manager = managerService.find(worker.getManagerId());
            Account managerAccount = accountService.find(manager.getAccountId());
            if (EnumAccountType.MANAGER.name().equals(managerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", indent.getId());
                extras.put("nodeId", indentException.getIndentNodeId());
                extras.put("accountType", managerAccount.getLastLoginType());
                extras.put(PUSH_TYPE, findIndentNode.getCode1().toUpperCase());
                List<String> managerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(managerAccount.getRegistrationId())) {
                    managerRegistrationId.add(managerAccount.getRegistrationId());
                }
                
                if (managerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush(workerAccount.getName1()+"问题反馈-客服已受理("+sctype.getName1()+sortName+")", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(managerRegistrationId);
                }
            }
        }
    }
    
    private String completeSortName (List<IndentContent> indentContentList) {
        String sortDisp = "";
        Dto  sortIds = new Dto();
        for (IndentContent indentContent : indentContentList) {
            String indentPriceCode1 = indentContent.getCode1();
                SctypeContent content = sctypeContentService.find(indentPriceCode1);
                String sortId = content.getSctypeSortId();
            if (!sortIds.containsKey(sortId)) {
                sortIds.put(sortId, sortId);
                sortDisp += content.getDesc1();
            }
        }
        return sortDisp;
    }

    @Override
    public void updateException(IndentException data) {
        super.update(data);
        
        IndentNode findIndentNode = indentNodeService.find(data.getIndentNodeId());
        Indent indent = indentService.find(data.getIndentId());
        Worker worker = workerService.find(indent.getWorkerId());
        Account workerAccount = accountService.find(worker.getAccountId());
        Manager manager = managerService.find(worker.getManagerId());
        Account managerAccount = accountService.find(manager.getAccountId());
        String result = "";
        String string = data.getResult();
        if (EnumExceptionResultStatus.CONTINUE.name().equals(string)) {
            result = EnumExceptionResultStatus.CONTINUE.text();
        } else if (EnumExceptionResultStatus.SUSPEND.name().equals(string)) {
            result = EnumExceptionResultStatus.SUSPEND.text();
        } else if (EnumExceptionResultStatus.OVER.name().equals(string)) {
            result = EnumExceptionResultStatus.OVER.text();
            if (EnumPart.WORKER.name().equals(data.getPart())) {
                //工匠的责任  扣除保证金
                BigDecimal deductDepositWorker = data.getWorkerDeposit();//保证金金额
                workerDepositService.deductDeposit(deductDepositWorker, worker, indent);
                //扣除经理人的保证金
                BigDecimal deductDepositManager = data.getManagerDeposit();//保证金金额
                managerDepositService.deductDeposit(deductDepositManager, manager, indent);
            }
        }
        
        String idea = data.getIdea() == null? "":data.getIdea();
        {//推送给工匠异常处理结果
            if (EnumAccountType.WORKER.name().equals(workerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", data.getIndentId());
                extras.put("nodeId", data.getIndentNodeId());
                extras.put("accountType", workerAccount.getLastLoginType());
                extras.put(PUSH_TYPE, findIndentNode.getCode1().toUpperCase());
                List<String> workerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(workerAccount.getRegistrationId())) {
                    workerRegistrationId.add(workerAccount.getRegistrationId());
                }
                if (workerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush("问题反馈-处理结果："+ result + idea, "众联工匠", extras);
                    push.pushMsgToRegistrationIds(workerRegistrationId);
                    if (EnumExecuteStatus.CENTRE.name().equals(data.getExecuteStatus())) {
                        smsService.sendSms(workerAccount.getMobile(), SmsTemplate.FEEDBACK, result + idea, indent.getCode1());
                    }
                }
            }
        }

        {//推送给经理人异常处理结果
            if (EnumAccountType.MANAGER.name().equals(managerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", data.getIndentId());
                extras.put("nodeId", data.getIndentNodeId());
                extras.put("accountType", managerAccount.getLastLoginType());
                extras.put(PUSH_TYPE, findIndentNode.getCode1().toUpperCase());
                List<String> managerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(managerAccount.getRegistrationId())) {
                    managerRegistrationId.add(managerAccount.getRegistrationId());
                }
                if (managerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush(workerAccount.getName1()+"问题反馈-处理结果："+ result + idea, "众联工匠", extras);
                    push.pushMsgToRegistrationIds(managerRegistrationId);
                }
            }
        }
        
        
    }

    @Override
    public void saveException(IndentException indentException) {
        
        super.save(indentException);
        indentService.changeIndentException(indentException.getIndentId());
        //推送订单异常
        IndentNode findIndentNode = indentNodeService.find(indentException.getIndentNodeId());
        Indent indent = indentService.find(indentException.getIndentId());
        String serveType = indent.getServeType();
        Sctype sctype = sctypeService.find(serveType);
        Worker worker = workerService.find(indent.getWorkerId());
        Account workerAccount = accountService.find(worker.getAccountId());
        List<IndentContent> indentContentList = indentContentService.findList(Filter.eq("indentId", indent.getId()));
        String sortName = completeSortName(indentContentList);
        {//推送给工匠问题反馈
            if (EnumAccountType.WORKER.name().equals(workerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", indentException.getIndentId());
                extras.put("nodeId", indentException.getIndentNodeId());
                extras.put("accountType", workerAccount.getLastLoginType());
                extras.put(PUSH_TYPE, findIndentNode.getCode1().toUpperCase());
                List<String> workerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(workerAccount.getRegistrationId())) {
                    workerRegistrationId.add(workerAccount.getRegistrationId());
                }
                if (workerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush("问题反馈-已提交("+sctype.getName1()+sortName+")", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(workerRegistrationId);
                }
            }
        }

        {//推送给经理人问题反馈
            Manager manager = managerService.find(worker.getManagerId());
            Account managerAccount = accountService.find(manager.getAccountId());
            if (EnumAccountType.MANAGER.name().equals(managerAccount.getLastLoginType())) {
                Map<String, String> extras = new HashMap<String, String>();
                extras.put("id", indentException.getIndentId());
                extras.put("nodeId", indentException.getIndentNodeId());
                extras.put("accountType", managerAccount.getLastLoginType());
                extras.put(PUSH_TYPE, findIndentNode.getCode1().toUpperCase());
                List<String> managerRegistrationId = new ArrayList<String>();
                if (StringUtil.isNotBlank(managerAccount.getRegistrationId())) {
                    managerRegistrationId.add(managerAccount.getRegistrationId());
                }
                if (managerRegistrationId.size() > 0) {
                    MessagePush push = new MessagePush(workerAccount.getName1()+"问题反馈-已提交("+sctype.getName1()+sortName+")", "众联工匠", extras);
                    push.pushMsgToRegistrationIds(managerRegistrationId);
                }
            }
        }   
        
    }

    @Override
    public List<IndentException> findExceptions(IndentException indentException) {
        String sql = "SELECT * from ZL_INDENT_EXCEP WHERE EXECUTE_STATUS = ? AND INDENT_ID = ? AND CREATE_DATE < ?";
        return sqlDao.listAll(sql, IndentException.class, EnumExecuteStatus.CENTRE.name(), indentException.getIndentId(), indentException.getCreateDate());
    }

}