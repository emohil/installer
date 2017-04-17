package com.company.util.web;

import javax.servlet.http.HttpServletRequest;

import com.company.util.StringUtil;

public class WebUtil {
    
    /**
     * BasePath
     * @param request
     * @return
     */
    public static String basePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        int port = request.getServerPort();
        if (port == 80) {
            return String.format("%s://%s%s/", request.getScheme(), request.getServerName(), contextPath);
        }
        return String.format("%s://%s:%d%s/", request.getScheme(), request.getServerName(), port, contextPath);
    }
    
    
    /**
     * 当前请求是否微信浏览器 
     * @param request
     * @return
     */
    public static boolean isWeixin(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        
        return StringUtil.indexOf(userAgent, "MicroMessenger") >= 0;
    }
}
