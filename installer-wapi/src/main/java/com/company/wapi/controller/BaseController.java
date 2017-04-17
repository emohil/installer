package com.company.wapi.controller;

import javax.servlet.http.HttpServletRequest;

import com.company.po.account.Account;
import com.company.wapi.fw.AccountHelper;
import com.company.api.fw.service.Tag;
import com.company.util.StringUtil;

public class BaseController implements Tag {

    public static final String TAG_PAGE = "page";
    public static final String TAG_PATH = "path";

    protected String getCurrentUser(HttpServletRequest request) {

        String token = StringUtil.defaultString(request.getParameter(TAG_TOKEN));
        Account account = AccountHelper.getAccountByToken(token);
        if (account == null) {
            return "";
        }
        return StringUtil.defaultString(account.getAccount());
    }
}
