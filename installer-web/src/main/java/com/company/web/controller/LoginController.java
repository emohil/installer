package com.company.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.api.admin.service.AdminService;
import com.company.api.role.service.RoleAuthService;
import com.company.po.admin.Admin;
import com.company.po.role.RoleAuth;
import com.company.web.BaseController;
import com.company.web.fw.AdminAccountHelper;
import com.company.context.web.PrefixedParam;
import com.company.util.BooleanUtil;
import com.company.util.StringUtil;

@Controller("loginController")
@RequestMapping
public class LoginController extends BaseController {

    private static final String LOGIN = "login";

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleAuthService roleAuthService;

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, ModelMap model, //
            @PrefixedParam(prefix = PREFIX_DATA) Admin data) {

        model.put(TAG_DATA, data);

        if (StringUtil.isEmpty(data.getUser())) {
            model.put(TAG_MSG, "用户名不能为空！");
            return LOGIN;
        }
        if (StringUtil.isEmpty(data.getPwd())) {
            model.put(TAG_MSG, "密码不能为空！");
            return LOGIN;
        }
        Map<String, Object> result = adminService.validateUser(data);
        if (!BooleanUtil.getBoolean(result.get(TAG_SUCCESS))) {
            model.putAll(result);
            return LOGIN;
        }

        Admin admin = (Admin) result.get(TAG_DATA);

        List<RoleAuth> authList = roleAuthService.findAuthByAdmin(admin.getId());

        for (RoleAuth auth : authList) {
            admin.getAuthIdList().add(auth.getAuthId());
        }

        // 将用户信息存入Session
        AdminAccountHelper.setAdmin2Session(request, admin);

        return "redirect:/homepage.do";
    }
}
