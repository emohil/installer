package com.company.api.fw.service;

import com.company.api.fw.DictEntry;

public enum EnumErrorCode implements DictEntry {

    /** 交易金额必须大于0 */
    TRADE_1001("交易金额必须大于0"),

    /** 余额不足，无法提现！ */
    TRADE_1002("余额不足，无法提现！"),

    /** 账号余额异常，无法提现！ */
    TRADE_1003("账号余额异常，无法提现！")

    ;
    private String text;

    private EnumErrorCode(String text) {
        this.text = text;
    }

    @Override
    public String text() {
        return this.text;
    }
}
