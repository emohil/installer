package com.company.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author lihome
 * 
 * @see org.apache.commons.lang3.time.DateUtils
 * 
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    /** Date & Time Default Pattern */
    public static final String DATETIME_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DATETIME_WEB_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    
    /** Date Default Pattern */
    public static final String DATE_DEFAULT_PATTERN = "yyyy-MM-dd";
    
    /** Date Default Pattern */
    public static final String DATE_DEFAULT2_PATTERN = "MM-dd";

    /** Time Default Pattern */
    public static final String TIME_DEFAULT_PATTERN = "HH:mm";

    
    /**
     * Formats a Date into a date & time string with default pattern.
     * @param date the time value to be formatted
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, DATETIME_DEFAULT_PATTERN);
    }


    /**
     * Formats a Date into a date string with default pattern.
     * @param date the time value to be formatted
     * @return
     */
    public static String formatDate(Date date) {
        return format(date, DATE_DEFAULT_PATTERN);
    }
    
    /**
     * Formats a Date into a date string with default pattern.
     * @param date the time value to be formatted
     * @return
     */
    public static String formatDate2(Date date) {
        return format(date, DATE_DEFAULT2_PATTERN);
    }
    
    /**
     * Formats a Date into a date string with default pattern.
     * @param date the time value to be formatted
     * @return
     */
    public static String formatTime(Date date) {
        return format(date, TIME_DEFAULT_PATTERN);
    }
    
    /**
     * Formats a Date into a date/time string.
     * @param date the time value to be formatted
     * @param pattern the pattern describing the date and time format
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) return "";
        
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(date);
    }
    
    
    /**
     * @author wl.kou
     * @param date
     * @return 
     */
    public static Object formatDateByParam(final Date date) {
        Date current = new Date();
        boolean sameDay = isSameDay(date, new Date());
        
        //今天
        if (sameDay) {
            long diffMilliseconds = current.getTime() - date.getTime();
            
            long diffMinute = diffMilliseconds / 1000 / 60;
            
            if (diffMinute > 60) {
                return formatTime(date);
            } else {
                return diffMinute+"分钟之前";
            }
        } else {
            boolean sameYear = isSameYear(date, current);
            if (sameYear) {
                return formatDate2(date);
            } else {
                return formatDate(date);
            }
        }
    }
    
    
    //判断是否 是指定年份
    public static boolean isSameYear(final Date date1, final Date date2) {
        
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
    
    //判断是否 是同一天
    public static boolean isSameDay(final Date date1, final Date date2) {
        
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
    }
    
    public static Date date1900() {
        return new Date(-2209017600000L);
    }
}