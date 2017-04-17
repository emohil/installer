package com.company.context;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.company.context.web.DefaultWebContextInitializer;

/**
 * 初始化各种Context.
 * @author lihong
 *
 */
public class InitializerContext extends DefaultWebContextInitializer {

    
    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);

        AppContext.setAppPath(servletContext.getRealPath("/"));
    }

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);
    }
}
