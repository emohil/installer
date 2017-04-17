package com.company.util;

import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

/**
 * 随机数工具类.
 * @author lihome
 *
 * @see org.apache.commons.lang3.RandomUtils
 */
public class RandomUtil extends RandomUtils {

    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final java.util.Random RANDOM = new java.util.Random();

    public static String getRandomStr(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
        }
        return sb.toString();
    }

    public static int random(int length) {

        if (length <= 0)
            return 0;

        int start = (int) Math.pow(10, length - 1);
        int end = start * 10 - 1;

        return nextInt(start, end);
    }
    
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    public static void main(String[] args) {
       System.out.println(uuid());
    }
}
