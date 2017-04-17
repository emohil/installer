package com.company.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.api.auth.service.AuthService;
import com.company.po.admin.Admin;
import com.company.web.fw.AdminAccountHelper;
import com.company.api.fw.service.Tag;
import com.company.context.SpringContextHolder;
import com.company.context.ThreadContext;
import com.company.util.StringUtil;

public class RequestFilter implements Filter, Tag {
    /** 无需过滤的URI */
    private String excludingPattern;
    private Pattern[] eps;

    private List<String> needAuthList;

    private long requestCount = 0;

    private synchronized String nextThreadId() {
        return "Request-" + ++requestCount;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.excludingPattern = config.getInitParameter("excludingPattern");

        if (!StringUtil.isEmpty(this.excludingPattern)) {
            String[] es = this.excludingPattern.split("[,;]");
            List<Pattern> ls = new ArrayList<Pattern>();
            for (String e : es) {
                ls.add(Pattern.compile(e.trim()));
            }
            eps = new Pattern[ls.size()];
            ls.toArray(eps);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();

        if (noCheckUrl(uri)) {
            chain.doFilter(req, resp);
            return;
        }

        Thread.currentThread().setName(nextThreadId());

        Admin admin = AdminAccountHelper.getAdmin4Session(request);

        if (admin != null) {
            ThreadContext.setContextBean(admin);
        } else {
            response.sendRedirect(request.getContextPath());
        }

        if (!checkAuth(uri, admin)) {
            response.sendRedirect(request.getContextPath() + "/noAuth.do");
            return;
        }

        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ThreadContext.clearContext();
        }
    }

    private boolean noCheckUrl(String url) {
        if (eps == null || eps.length == 0) {
            return false;
        }

        for (Pattern p : eps) {
            if (p.matcher(url).find()) {
                return true;
            }
        }

        return false;
    }

    public boolean checkAuth(String url, Admin admin) {

        if (admin == null) {
            return true;
        }

        if (needAuthList == null) {

            AuthService authService = SpringContextHolder.getBean(AuthService.BEAN_NAME);
            this.needAuthList = authService.authCodeList();
        }

        for (String needAuth : this.needAuthList) {
            if (url.contains(needAuth)) {

                if (!admin.getAuthIdList().contains(needAuth)) {
                    return false;
                }
                break;
            }
        }

        return true;
    }

    @Override
    public void destroy() {
    }
}