package com.company.api.indent.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.po.account.Account;
import com.company.po.admin.Admin;
import com.company.po.indent.Indent;
import com.company.po.indent.IndentException;
import com.company.po.indent.IndentNode;
import com.company.sf.indent.IndentSf;
import com.company.api.fw.service.StringIdBaseService;
import com.company.dto.Order;
import com.company.util.Dto;

public interface IndentService extends StringIdBaseService<Indent> {
    
    String BEAN_NAME = "indentService";
    
    Indent findByCode1(String code1);

    List<Indent> list(IndentSf sf, int start, int limit, List<Order> orders);

    int count(IndentSf sf);

    boolean scrambleIndent(Account account, String id);

    void changeIndentException(String indentId);
    
    Map<EnumExecuteStatus, Integer> executeStatus2Count();

    Dto indentCancle(String id, String workerId);

    Dto deleteIndent(String id);

    int countByItemIdAndStatus(String itemId, String status);

    void nodeProgress(IndentNode indentNode);

    void changeIndentNormal(IndentException data);
    
    void changeIndentPause(IndentException data);

    void pushToAllWorker(Indent indent);

    void firstOperItem(IndentNode indentNode);
    
    /**
     * 统计工匠本月的结单数
     * @return
     */
    int monthIndent(String workerId);
    
    void overIndent(IndentException data);
    
    /**
     * 统计异常订单数量
     * @return
     */
    int exceptionIndentCount();

    void saveIndent(Indent indent);

    List<Indent> findByCarModel(String workerId, String carModel);
    
    /**
     * 统计工人完成的订单数
     * @param workerId
     * @return
     */
    int finishIndentNum(String workerId);
    
    int onTimeFinishNum(String workerId,String executeStatus);

    List<Indent> findOverTimeIndent();

    /**
     * 产生订单id 是indentId 的返补单（二次上门单）
     * @param indentId
     * @param data 
     * @param admin 
     * @param admin 
     * @return 
     */
    Indent createAnotherIndent(String indentId, Admin admin, Indent data);

    void pushIndent(String indentId);

    List<Indent> findOverTimeEvaluate(Date date);

    void pushExtraIndent(Indent indent);

    List<Indent> unScrambleOverTimeIndent(Date date);

    void clearWorkerIdAndPush(Indent indent);

    void clearManagerIdAndPush(Indent indent);
    
}