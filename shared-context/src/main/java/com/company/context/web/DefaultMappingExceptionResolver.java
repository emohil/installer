package com.company.context.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.company.context.SpringContextHolder;

/**
 * 默认映射 异常解析器.
 * 
 * @author lihome
 *
 */
public class DefaultMappingExceptionResolver extends SimpleMappingExceptionResolver {

    /** 默认Logger */
    private static final Logger logger0 = LoggerFactory.getLogger(DefaultMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        Logger logger = null;

        if (handler != null) {
            logger = LoggerFactory.getLogger(handler.getClass());
        } else {
            logger = logger0;
        }

        logger.error("PrintStackTrace:", ex);

        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);
        if (viewName != null) { // JSP格式返回
            if (!(request.getHeader("accept").indexOf("application/json") > -1
                    || (request.getHeader("X-Requested-With") != null
                            && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }

                return getModelAndView(viewName, ex, request);
            } else { // JSON格式返回
                try {
                    String message = ex.getMessage();
                    logger.error(message);
                    // write response stream
                    HttpMessageConverter<Object> converter = SpringContextHolder
                            .getBean(MappingJackson2HttpMessageConverter.class);
                    ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
                    converter.write(message, MediaType.APPLICATION_OCTET_STREAM, outputMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        } else {
            return null;
        }
    }
}