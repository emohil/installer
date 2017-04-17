package com.company.context.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.company.util.DateUtil;
import com.company.util.StringUtil;

public class DateConverter implements Converter<String, Date> {

    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
    
    private static final SimpleDateFormat parser = new SimpleDateFormat();

    @Override
    public Date convert(String source) {
        if (StringUtil.isEmpty(source)) {
            return null;
        }
        String pattern = DateUtil.DATE_DEFAULT_PATTERN;
        if (source.contains("T")) {
            pattern = DateUtil.DATETIME_WEB_PATTERN;
        } else if (source.length() > 10) {
            pattern = DateUtil.DATETIME_DEFAULT_PATTERN;
        }
        parser.applyPattern(pattern);
        try {
            return parser.parse(source);
        } catch (ParseException e) {
            logger.error("{} cannot be cast to java.util.Date, Detail:{}", source, e);
            return null;
        }
    }
}