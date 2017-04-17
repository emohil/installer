package com.company.api.indent.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.api.account.service.AccountService;
import com.company.api.dict.service.EnumYesno;
import com.company.api.fw.service.impl.StringIdBaseServiceImpl;
import com.company.api.fw.util.MessagePush;
import com.company.api.indent.service.EnumEvaluateStatus;
import com.company.api.indent.service.EnumExecuteStatus;
import com.company.api.indent.service.EnumIndentStatus;
import com.company.api.indent.service.EnumJpushType;
import com.company.api.indent.service.IndentEvaluateService;
import com.company.api.indent.service.IndentExceptionService;
import com.company.api.indent.service.IndentNodeService;
import com.company.api.indent.service.IndentService;
import com.company.api.wk.service.WorkerService;
import com.company.po.account.Account;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentEvaluate;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentNode;
import com.company.po.wk.Worker;
import com.company.dto.Filter;
import com.company.dto.SysDict;
import com.company.repo.fw.dao.SqlDao;
import com.company.repo.indent.dao.IndentEvaluateDao;
import com.company.util.StringUtil;

@Service(IndentEvaluateService.BEAN_NAME)
public class IndentEvaluateServiceImpl extends StringIdBaseServiceImpl<IndentEvaluate> implements IndentEvaluateService {

    @Autowired
    private SqlDao sqlDao;
    
    @Autowired
    private IndentNodeService indentNodeService;
    
    @Autowired
    private IndentService indentService;
    
    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private IndentExceptionService indentExceptionService;

    @Autowired
    public void setBaseDao(IndentEvaluateDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public IndentEvaluate findByIndentId(String indentId) {
        String sql = "select * from ZL_INDENT_EVALUATE where INDENT_ID=?";
        return sqlDao.unique(sql, IndentEvaluate.class, indentId);
    }

    @Override
    public void saveEvalute(IndentEvaluate indentEvaluate) {
        //保存评价信息
        String workdone = indentEvaluate.getWorkdone();
        if (workdone.equals(EnumYesno.NO.name())) {
            indentEvaluate.setServeScore(0);
            indentEvaluate.setSkillScore(0);
            //设置订单未完成
            String sqlIndent = "update ZL_INDENT set STATUS= ?, EXECUTE_STATUS = ?, EVALUATE_STATUS = ?, EXCEP_STATUS = ? where ID = ?";
            sqlDao.execUpdate(sqlIndent, EnumIndentStatus.EXCEPTION.name(), EnumExecuteStatus.CENTRE.name(), EnumEvaluateStatus.EVALUATED.name(), SysDict.EMPTY.getValue(), indentEvaluate.getIndentId());
            
            //在订单异常表中插入数据
            IndentNode indentNode = indentNodeService.findSpecifiedNodeByIndentId(indentEvaluate.getIndentId(), "SELFCHECK");
            IndentException exception = new IndentException();
            Worker worker = workerService.find(indentEvaluate.getWorkerId());
            exception.setIndentId(indentEvaluate.getIndentId());
            exception.setIndentNodeId(indentNode.getId());
            exception.setExecuteStatus(EnumExecuteStatus.BEFORE.name());
            exception.setRepId(worker.getAccountId());
            exception.setContent("业主评价未完成工作");
            indentExceptionService.save(exception);
        }
        Double evaluateScores = (double) 0;
        if (workdone.equals(EnumYesno.YES.name())) {
            if (indentEvaluate.getSkillScore() > 0 && indentEvaluate.getServeScore() > 0) {
                BigDecimal skillScore = new BigDecimal(indentEvaluate.getSkillScore());
                skillScore.setScale(4, RoundingMode.HALF_UP);
                BigDecimal serveScore = new BigDecimal(indentEvaluate.getServeScore());
                serveScore.setScale(4, RoundingMode.HALF_UP);
                BigDecimal scale = (skillScore.add(serveScore)).divide(new BigDecimal(2), 1, RoundingMode.HALF_UP);
                evaluateScores = scale.doubleValue();
                //设置订单完成
                String sqlIndent = "update ZL_INDENT set FINISH_DATE = ?, STATUS= ?, EXECUTE_STATUS = ?, EVALUATE_STATUS = ?, EXCEP_STATUS = ?, EVALUATE_SCORES = ? where ID = ?";
                sqlDao.execUpdate(sqlIndent, new Date(), EnumIndentStatus.NORMAL.name(), EnumExecuteStatus.AFTER.name(), EnumEvaluateStatus.EVALUATED.name(), SysDict.EMPTY.getValue(), evaluateScores, indentEvaluate.getIndentId());
            } else {
                //设置订单自动评价完成
                String sqlIndent = "update ZL_INDENT set FINISH_DATE = ?, STATUS= ?, EXECUTE_STATUS = ?, EVALUATE_STATUS = ?, EXCEP_STATUS = ?, EVALUATE_SCORES = ? where ID = ?";
                sqlDao.execUpdate(sqlIndent, new Date(), EnumIndentStatus.NORMAL.name(), EnumExecuteStatus.AFTER.name(), EnumEvaluateStatus.UNEVALUATED.name(), SysDict.EMPTY.getValue(), evaluateScores, indentEvaluate.getIndentId());
            }
        }
        save(indentEvaluate);
        push(indentEvaluate.getIndentId(), evaluateScores);
    }
    
    private void push (String id, Double evaluateScores) {
        Indent indent = indentService.find(id);
        Account workerAccount = accountService.findOne(Filter.eq("workerId", indent.getWorkerId()));
        Account managerAccount = accountService.findOne(Filter.eq("managerId", indent.getManagerId()));
        String scores = evaluateScores.toString() + "星";
        {//推送给工匠订单评价完成
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", id);
            extras.put("pushType", EnumJpushType.INDENT_DETAIL.name());
            List<String> workerRegistrationId = new ArrayList<String>();
            if (StringUtil.isNoneBlank(workerAccount.getRegistrationId())) {
                workerRegistrationId.add(workerAccount.getRegistrationId());
            }
            MessagePush push = null;
            if (workerRegistrationId.size() >0) {
                if (EnumExecuteStatus.AFTER.name().equals(indent.getExecuteStatus())) {
                    push = new MessagePush("收到业主评价：已完成工作，"+ scores, "众联工匠", extras);
                } else {
                    push = new MessagePush("收到业主评价：未完成工作，", "众联工匠", extras);
                }
                push.pushMsgToRegistrationIds(workerRegistrationId);
            }
        }

        {//推送给经理人订单评价完成
            Map<String, String> extras = new HashMap<String, String>();
            extras.put("id", id);
            extras.put("pushType", EnumJpushType.INDENT_DETAIL.name());
            List<String> managerRegistrationId = new ArrayList<String>();
            if (StringUtil.isNoneBlank(managerAccount.getRegistrationId())) {
                managerRegistrationId.add(managerAccount.getRegistrationId());
            }
            if (managerRegistrationId.size() > 0) {
                MessagePush push = null;
                if (EnumExecuteStatus.AFTER.name().equals(indent.getExecuteStatus())) {
                    push = new MessagePush(workerAccount.getName1()+"收到业主评价：已完成工作，"+ scores, "众联工匠", extras);
                } else {
                    push = new MessagePush(workerAccount.getName1()+"收到业主评价：未完成工作，", "众联工匠", extras);
                }
                push.pushMsgToRegistrationIds(managerRegistrationId);
            }
        }
    }
}