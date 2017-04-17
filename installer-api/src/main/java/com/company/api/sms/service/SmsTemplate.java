package com.company.api.sms.service;

public enum SmsTemplate {

    // 1.抢单成功（工匠）- 抢单成功（订单号【可变内容（最多15个字）】），24小时内要联系客户哦~
    TAKE_INDENT("抢单成功（订单号%s），24小时内要联系客户哦~"),

    // 2.审核通过（经理人） - 恭喜您成为认证经理人，工号【可变内容（最多15个字）】！打开众联工匠APP赚钱喽~
    MANAGER_APPROVED("恭喜您成为认证经理人，工号%s！打开众联工匠APP赚钱喽~"),

    // 3.审核通过 - 恭喜您成为认证工匠，工号【可变内容（最多15个字）】！打开众联工匠APP赚钱喽~
    WORKER_APPROVED("恭喜您成为认证工匠，工号%s！打开众联工匠APP赚钱喽~"),

    // 4.修改交易密码 - 交易密码修改成功，提现时请用新密码。
    TRADING_PASSWROD_UPDATED("交易密码修改成功，提现时请用新密码。"),

    // 5.修改手机号 - 验证码：【可变内容（最多6个字）】（修改绑定手机号），打死都不能告诉别人哦！
    MOBILE_UPDATED("验证码：%s（修改绑定手机号），打死都不能告诉别人哦！"),

    // 6.注册登录 - 验证码：【可变内容（最多6个字）】，打死都不能告诉别人哦！
    VCODE("验证码：%s，打死都不能告诉别人哦！"),

    // 7.问题反馈-处理结果（工匠）- 问题反馈处理结果：【可变内容（最多20个字）】。
    FEEDBACK("问题反馈处理结果：%s（订单号%s）。"),
    
    // 8.工匠申请加入（经理人）【可变内容（最多6个字）】
    WORKER_APPLY("工匠%s申请加入您的团队，打开众联工匠看看他的资料吧~"),
    
    // 9.订单超时（工匠）
    TIMEOUT_WORKER("订单%s即将超时（业主：%s），干完活别忘了邀请业主评价哦~如有问题，可在订单进程里反馈。"),
    
    // 10.订单超时（经理人）
    TIMEOUT_MANAGER("工匠%s订单%s即将超时（业主：%s），可提醒工匠邀请业主评价~"),
    
    // 11.订单继续执行（工匠）
    INDENT_CONTINUE("订单%s已恢复，可继续执行（业主：%s）。如有问题，可在订单进程里反馈。"),
    
    // 12.提现结果（工匠/经理人）
    DEPOSIT_RESULT("【提现】%s元已到账，到账银行：%s（%s）。"),
    
    // 17.您家的【可变内容（最多50个字）】已经安装完成，查看照片戳 【可变内容（最多100个字）】。对工匠服务做评价可得【可变内容（最多50个字）】哦~ 
    EVALUATION("您家的%s已经安装完成，查看照片戳 %s。对工匠服务做评价可得%s哦~ ")

    ;

    private String tpl;

    private SmsTemplate(String tpl) {
        this.tpl = tpl;
    }

    public String getTpl() {
        return tpl;
    }
    
    public static void main(String[] args) {
        System.out.println(String.format(TRADING_PASSWROD_UPDATED.getTpl(), "789443"));
        System.out.println(String.format(VCODE.getTpl(), "789443"));
    }
}