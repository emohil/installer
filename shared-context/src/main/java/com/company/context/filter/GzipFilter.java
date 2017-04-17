package com.company.context.filter;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * JS CSS压缩Gzip Filter
 *
 */
public class GzipFilter implements Filter {

    Map<String, String> headers = new HashMap<String, String>();

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            doFilter((HttpServletRequest) req, (HttpServletResponse) res, chain);
        } else {
            chain.doFilter(req, res);
        }
    }

    public void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        for (Iterator<?> it = headers.entrySet().iterator(); it.hasNext();) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            response.addHeader((String) entry.getKey(), (String) entry.getValue());
            if (request.getRequestURI().contains(".gzjs")) {
                response.setContentType("text/javascript");
            } else if (request.getRequestURI().contains(".gzcss")) {
                response.setContentType("text/css");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String headersStr = config.getInitParameter("headers");
        String[] headers = headersStr.split(",");
        for (int i = 0; i < headers.length; i++) {
            String[] temp = headers[i].split("=");
            this.headers.put(temp[0].trim(), temp[1].trim());
        }
    }
}