package com.company.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 
 * @author lihome
 * 
 * @see org.apache.commons.lang3.math.NumberUtils
 * 
 */
public class NumberUtil extends org.apache.commons.lang3.math.NumberUtils {

    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 5;

    /**
     * 使用系统默认方式格式化一个数字为字符串,小数位2,不使用千分位.
     * 
     * 这个是整个应用系统的"风格",一般情况下,前台显示,只能使用该函数.
     * 
     * @param num
     * @return
     */
    public static String format(Number number) {
        return format(number, "##0.00");
    }

    /**
     * @Title dbFormat
     * @Description 格式化数字为数据库使用格式"##0.000"
     * @param number
     * @return String
     */
    public static String dbFormat(Number number) {
        return format(number, "##0.000");
    }

    /**
     * 格式化数字
     * 
     * 格式化前：1231423.3823 "#.##" 格式化后：1231423.38 "0000000000.000000"
     * 格式化后：0001231423.382300 "-##,###.##" 格式化后：-1,231,423.382 "#.##E000"
     * 格式化后：1.23E006 模式中的"#"表示如果该位存在字符，则显示字符，如果不存在，则不显示。
     * 
     * @param num
     * @return
     */
    public static String format(Number number, String format) {
        return new DecimalFormat(format).format(number);
    }

    /**
     * 格式化一个数字为字符串.
     * 
     * @param number
     *            要处理的数字.
     * @param digits
     *            小数位数.
     * @param usesep
     *            是否使用千分位.
     * @return
     */
    public static String format(Number number, int digits, boolean usesep) {
        return _getFormater(digits, usesep).format(number);
    }

    private static NumberFormat _getFormater(int digits, boolean usesep) {
        StringBuilder sb = new StringBuilder();
        sb.append(usesep ? ",##0" : "0");

        if (digits > 0) {
            sb.append(".");
            for (int i = 0; i < digits; i++) {
                sb.append("0");
            }
        }
        return new DecimalFormat(sb.toString());
    }


    /**
     * 尝试将一个值转化为int,如果失败,返回defaultValue
     * 
     * @param val
     * @param def
     * @return
     */
    public static int getInt(Object val, int def) {
        if (val instanceof Number) {
            return ((Number) val).intValue();
        } else if (val instanceof String) {
            String sval = ((String) val).replace(",", "");
            ;
            try {
                return Integer.parseInt(sval);
            } catch (Exception ex) {
                for (String format : new String[] { "#" }) {
                    try {
                        return new DecimalFormat(format).parse(sval).intValue();
                    } catch (Exception e) {
                    }
                }
            }
        }

        return def;
    }
    
    
    public static int toInt(Object o) {
        return toInt(o, 0);
    }

    public static int toInt(Object o, int defaultValue) {
        return toInt(ObjectUtil.toString(o), defaultValue);
    }
    
    
    /**
     * Returns a uppercase string representation of the integer argument as an
     * unsigned integer in base&nbsp;36.
     * @param num
     * @return
     */
    public static String to36HexStringUpper(int num) {
        return to36HexString(num).toUpperCase();
    }
    
    /**
     * Returns a string representation of the integer argument as an
     * unsigned integer in base&nbsp;36.
     * @param num
     * @return
     */
    public static String to36HexString(int num) {
        return Integer.toString(num, 36);
    }
    
    
    /**
     * Parses the string argument as a signed integer in the radix in 36.
     * @param s
     * @return
     */
    public static int toIntRadix36(String s) {
        return toIntRadix36(s, 0);
    }
    
    /**
     * Parses the string argument as a signed integer in the radix in 36.
     * @param s
     * @param defaultValue
     * @return
     */
    public static int toIntRadix36(String s, int defaultValue) {
        if(s == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s, 36);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }
    
    public static float toFloat(Object o) {
        return toFloat(o, 0.0f);
    }

    public static float toFloat(Object o, float defaultValue) {
        return toFloat(ObjectUtil.toString(o), defaultValue);
    }
    
    
    public static double toDouble(Object o) {
        return toDouble(o, 0.0d);
    }

    public static double toDouble(Object o, double defaultValue) {
        return toDouble(ObjectUtil.toString(o), defaultValue);
    }


    public static long toLong(Object o) {
        return toLong(o, 0L);
    }

    public static long toLong(Object o, long defaultValue) {
        return toLong(ObjectUtil.toString(o), defaultValue);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * 
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     * 
     * @param v1被加数
     * @param v2加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * 
     * @param v1被减数
     * @param v2减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * 
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }
    
    
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = BigDecimal.ZERO;
        }
        if (b2 == null) {
            b2 = BigDecimal.ZERO; 
        }
        return b1.add(b2);
    }
    
    
    public static BigDecimal getDecimal(BigDecimal b) {
        return b == null ? BigDecimal.ZERO : b;
    }
}