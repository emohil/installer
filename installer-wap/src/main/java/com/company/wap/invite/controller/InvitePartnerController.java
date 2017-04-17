package com.company.wap.invite.controller;



import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.api.fs.service.FileManagerService;
import com.company.api.invite.service.InvitePartnerService;
import com.company.po.invite.InvitePartner;
import com.company.api.fw.service.Tag;
import com.company.dto.Filter;
import com.company.util.Dto;
import com.company.util.web.WebUtil;

@Controller("wapInvitePartnerController")
@RequestMapping(value = "/invitePartner")
public class InvitePartnerController {
    
    @Autowired
    private InvitePartnerService invitePartnerService;
    
    @Autowired
    private FileManagerService fileManagerService;
    
    @RequestMapping(value = "/toInvite")
    public String toInvite (ModelMap model, @RequestParam(value = "accountId") String accountId) {
        long count = invitePartnerService.count();
        model.put("accountId", accountId);
        model.put("nums", 1888 + count);
        return "invite/invite";
    }
    
    @RequestMapping(value = "/invite")
    @ResponseBody
    public Dto invite (HttpServletRequest request, @RequestParam(value = "accountId")String accountId,//
            @RequestParam(value = "mobile")String mobile) {
        Dto ret = new Dto();
        long count = invitePartnerService.count(Filter.eq("mobile", mobile));
        if (count == 0) {
            InvitePartner entity = new InvitePartner();
            entity.setAccountId(accountId);
            entity.setInviteDate(new Date());
            entity.setMobile(mobile);
            invitePartnerService.save(entity);
        }
        ret.put(Tag.TAG_SUCCESS, true);
        return ret;
    }
    
    @RequestMapping(value = "/share")
    public String share (HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (WebUtil.isWeixin(request)) {
            return "invite/share";
        }
        response.sendRedirect("http://123.57.205.97:9080/api/static/install.apk");
        return null;
    }
    
}
