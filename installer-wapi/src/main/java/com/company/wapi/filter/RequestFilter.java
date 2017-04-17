package com.company.wapi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.WebUtils;

import com.company.po.account.Account;
import com.company.wapi.fw.AccountHelper;
import com.company.api.fw.service.Tag;
import com.company.context.SpringContextHolder;
import com.company.context.ThreadContext;
import com.company.util.New;
import com.company.util.StringUtil;
import com.company.util.json.JacksonHelper;

public class RequestFilter implements Filter, Tag {
    /** 无需过滤的URI */
    private String excludingPattern;
    private Pattern[] eps;

    private long requestCount = 0;

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

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

        //使用此request,可以从 form中取值
//        HttpServletRequest request1 = checkMultipart(request);

        String token = StringUtil.defaultString(request.getParameter(TAG_TOKEN));

        Account userInfo = AccountHelper.getAccountByToken(token);

        if (userInfo != null) {
            ThreadContext.setContextBean(userInfo);
        } else if (uri.startsWith(request.getContextPath() + "/account/getVcode")) {
            //
        } else if (uri.startsWith(request.getContextPath() + "/account/checkVcode")) {
            //
        } else if (uri.startsWith(request.getContextPath() + "/account/login")) {
            //
        } else if (uri.startsWith(request.getContextPath() + "/account/getServeDistrict")) {
            //
        } else if (uri.startsWith(request.getContextPath() + "/account/getServeType")) {
            //
        } else if (uri.startsWith(request.getContextPath() + "/worker/workerRank")) {
            //
        } else if (uri.startsWith(request.getContextPath() + "/manager/managerRank")) {
            //
        } else {
            Map<String, Object> ret = New.hashMap();
            ret.put(TAG_SUCCESS, false);
            ret.put(TAG_MSG, "Invalid Token");

            response.getWriter().write(JacksonHelper.toJson(ret));
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
    

    private HttpServletRequest checkMultipart(HttpServletRequest request) {

        MultipartResolver multipartResolver = SpringContextHolder.getBean(MultipartResolver.class);
        
        if (multipartResolver != null && multipartResolver.isMultipart(request)) {
            if (WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class) != null) {
                logger.debug("Request is already a MultipartHttpServletRequest - if not in a forward, "
                        + "this typically results from an additional MultipartFilter in web.xml");
            } else {
                return multipartResolver.resolveMultipart(request);
            }
        }
        // If not returned before: return original request.
        return request;
    }

    @Override
    public void destroy() {
    }
}