package com.company.web;

import javax.servlet.http.HttpServletRequest;

import com.company.po.admin.Admin;
import com.company.web.fw.AdminAccountHelper;
import com.company.api.fw.service.Tag;
import com.company.util.StringUtil;

public class BaseController implements Tag {
    
    public static final String TAG_PAGE = "page";
    public static final String TAG_PATH = "path";
    public static final String TAG_ID = "id";
    
    protected String getCurrentUser(HttpServletRequest request) {
        Admin admin = AdminAccountHelper.getAdmin4Session(request);
        if (admin == null) {
            return "";
        }
        return StringUtil.defaultString(admin.getUser());
    }

}
