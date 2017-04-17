package com.company.api.fw;

import com.company.api.fw.EnumDict;

/**
 * @see EnumDict
 * @author lihome
 *
 */
public interface EnumCodes {
    
    /** 是/否*/
    String YESNO = "YESNO";

    /** 账号类型 */
    String ACCOUNT_TYPE = "ACCOUNT_TYPE";

    /** 账号状态 */
    String ACCOUNT_STATUS = "ACCOUNT_STATUS";

    /** 账号审核状态 */
    String CHECK_STATUS = "CHECK_STATUS";

    /** 启用状态 */
    String ENABLE_STATUS = "ENABLE_STATUS";

    /** 服务节点步骤类型 */
    String SCNODE_STEP_TYPE = "SCNODE_STEP_TYPE";

    /** 服务节点确认类型 */
    String SCNODE_CONFIRM_TYPE = "SCNODE_CONFIRM_TYPE";

    /** 甲方/乙方 */
    String PARTY_STAMP = "PARTY_STAMP";

    /** 账期 */
    String REVOLVE_TIME = "REVOLVE_TIME";

    /** 管理员状态 */
    String ADMIN_STATUS = "ADMIN_STATUS";

    /** 订单上门来源 */
    String INDENT_VISIT_SOURCE = "INDENT_VISIT_SOURCE";
    
    /** 订单来源 */
    String INDENT_SOURCE = "INDENT_SOURCE";

    /** 订单状态 */
    String INDENT_STATUS = "INDENT_STATUS";
    
    /** 订单发布状态 */
    String INDENT_RELEASE_STATUS = "INDENT_RELEASE_STATUS";

    /** 订单执行状态 */
    String INDENT_EXECUTE_STATUS = "INDENT_EXCE_STATUS";

    /** 订单异常状态 */
    String INDENT_EXCEP_STATUS = "INDENT_EX_STATUS";

    /** 订单节点步骤状态 */
    String INDENT_NODE_STEP_STATUS = "INDENT_NODE_STEP_STATUS";

    /** 订单异常的处理结果 */
    String EXCEPTION_RESULT = "EXCEPTION_RESULT";

    /** 评价状态 */
    String EVALUATE_STATUS = "EVALUATE_STATUS";

    /** 结算状态 */
    String CLEARING_STATUS = "CLEARING_STATUS";

    /** 付款状态 */
    String PAY_STATUS = "PAY_STATUS";

    /** 交易类型 */
    String TRADE_TYPE = "TRADE_TYPE";
    
    /** 交易状态*/
    String TRADE_STATUS = "TRADE_STATUS";
    
    /** 批次状态 */
    String BATCH_STATUS = "BATCH_STATUS";
    
    /** 工人、经理人从属状态枚举*/
    String SLAVE_STATUS = "SLAVE_STATUS";
}