package com.company.context.web;

import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.context.request.NativeWebRequest;

public class MockedRequest implements NativeWebRequest {
    private NativeWebRequest inner;
    private Map<String, String[]> m;

    public static MockedRequest build(NativeWebRequest req, String prefix) {
        Map<String, String[]> m = new HashMap<String, String[]>();

        for (Map.Entry<String, String[]> ent : req.getParameterMap().entrySet()) {
            if (ent.getKey().startsWith(prefix))
                m.put(ent.getKey().substring(prefix.length()), ent.getValue());
        }

        MockedRequest ret = new MockedRequest();
        ret.inner = req;
        ret.m = m;
        return ret;
    }

    @Override
    public Object getAttribute(String name, int scope) {
        return inner.getAttribute(name, scope);
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        inner.setAttribute(name, value, scope);
    }

    @Override
    public void removeAttribute(String name, int scope) {
        inner.removeAttribute(name, scope);
    }

    @Override
    public String[] getAttributeNames(int scope) {
        return inner.getAttributeNames(scope);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {
        inner.registerDestructionCallback(name, callback, scope);
    }

    @Override
    public Object resolveReference(String key) {
        return inner.resolveReference(key);
    }

    @Override
    public String getSessionId() {
        return inner.getSessionId();
    }

    @Override
    public Object getSessionMutex() {
        return inner.getSessionMutex();
    }

    @Override
    public String getHeader(String headerName) {
        return inner.getHeader(headerName);
    }

    @Override
    public String[] getHeaderValues(String headerName) {
        return inner.getHeaderValues(headerName);
    }

    @Override
    public Iterator<String> getHeaderNames() {
        return inner.getHeaderNames();
    }

    @Override
    public String getParameter(String paramName) {
        String[] arr = inner.getParameterValues(paramName);
        return arr == null || arr.length == 0 ? null : arr[0];
    }

    @Override
    public String[] getParameterValues(String paramName) {
        return m.get(paramName);
    }

    @Override
    public Iterator<String> getParameterNames() {
        return m.keySet().iterator();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return m;
    }

    @Override
    public Locale getLocale() {
        return inner.getLocale();
    }

    @Override
    public String getContextPath() {
        return inner.getContextPath();
    }

    @Override
    public String getRemoteUser() {
        return inner.getRemoteUser();
    }

    @Override
    public Principal getUserPrincipal() {
        return inner.getUserPrincipal();
    }

    @Override
    public boolean isUserInRole(String role) {
        return inner.isUserInRole(role);
    }

    @Override
    public boolean isSecure() {
        return inner.isSecure();
    }

    @Override
    public boolean checkNotModified(long lastModifiedTimestamp) {
        return inner.checkNotModified(lastModifiedTimestamp);
    }

    @Override
    public boolean checkNotModified(String etag) {
        return inner.checkNotModified(etag);
    }

    @Override
    public String getDescription(boolean includeClientInfo) {
        return inner.getDescription(includeClientInfo);
    }

    @Override
    public Object getNativeRequest() {
        Object x = inner.getNativeRequest();
        if (x instanceof HttpServletRequest) {
            return wrapHttpReq((HttpServletRequest) x);
        } else if (x instanceof ServletRequest) {
            return wrapServletReq((ServletRequest) x);
        } else
            throw new RuntimeException("Only HttpServletRequest/ServletRequest are supported here.");
    }

    @Override
    public Object getNativeResponse() {
        return inner.getNativeResponse();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getNativeRequest(Class<T> requiredType) {
        T x = inner.getNativeRequest(requiredType);
        if (x instanceof HttpServletRequest) {
            return (T) wrapHttpReq((HttpServletRequest) x);
        } else if (x instanceof ServletRequest) {
            return (T) wrapServletReq((ServletRequest) x);
        } else
            throw new RuntimeException("Only HttpServletRequest/ServletRequest are supported here.");
    }

    @Override
    public <T> T getNativeResponse(Class<T> requiredType) {
        return inner.getNativeResponse(requiredType);
    }

    private ServletRequest wrapServletReq(ServletRequest req) {
        return new ServletRequestWrapper(req) {

            @Override
            public String getParameter(String name) {
                return MockedRequest.this.getParameter(name);
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return MockedRequest.this.getParameterMap();
            }

            @Override
            public Enumeration<String> getParameterNames() {
                final Iterator<String> names = MockedRequest.this.getParameterNames();
                return new Enumeration<String>() {

                    @Override
                    public boolean hasMoreElements() {
                        return names.hasNext();
                    }

                    @Override
                    public String nextElement() {
                        return names.next();
                    }
                };
            }

            @Override
            public String[] getParameterValues(String name) {
                return MockedRequest.this.getParameterValues(name);
            }

        };
    }

    private HttpServletRequest wrapHttpReq(HttpServletRequest req) {
        return new HttpServletRequestWrapper(req) {

            @Override
            public String getParameter(String name) {
                return MockedRequest.this.getParameter(name);
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return MockedRequest.this.getParameterMap();
            }

            @Override
            public Enumeration<String> getParameterNames() {
                final Iterator<String> names = MockedRequest.this.getParameterNames();
                return new Enumeration<String>() {

                    @Override
                    public boolean hasMoreElements() {
                        return names.hasNext();
                    }

                    @Override
                    public String nextElement() {
                        return names.next();
                    }
                };
            }

            @Override
            public String[] getParameterValues(String name) {
                return MockedRequest.this.getParameterValues(name);
            }
        };
    }
}