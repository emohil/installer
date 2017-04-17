package com.company.web.fw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.api.fw.Constants;
import com.company.po.admin.Admin;
import com.company.context.ThreadContext;

public class AdminAccountHelper {

    public static Admin getAdmin4Session(HttpServletRequest request) {
        
        if (request == null) {
            request = getHttpServletRequest();
        }
        
        HttpSession session = request.getSession(true);
        
        return (Admin) session.getAttribute(Constants.USER_BEAN);
        
    }
    
    
    public static void setAdmin2Session(HttpServletRequest request, Admin admin) {
        
        if (request == null) {
            request = getHttpServletRequest();
        }
        HttpSession session = request.getSession(true);
        
        admin.setPwd("");
        
        session.setAttribute(Constants.USER_BEAN, admin);
        ThreadContext.setContextBean(admin);
    }
    
    
    public static void removeAdmin4Session(HttpServletRequest request) {
        if (request == null) {
            request = getHttpServletRequest();
        }
        HttpSession session = request.getSession(true);
        
        try {
            session.removeAttribute(Constants.USER_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ThreadContext.clearContext();
    }
    
    
    private static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        
        return request;
    }
    
}
