package com.company.context.web;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.company.context.DefaultContextInitializer;

public class DefaultWebContextInitializer extends DefaultContextInitializer
        implements ServletContextAware {

    @Override
    public void setServletContext(ServletContext servletContext) {
        ServletContextHolder.setServletContext(servletContext);
    }
}