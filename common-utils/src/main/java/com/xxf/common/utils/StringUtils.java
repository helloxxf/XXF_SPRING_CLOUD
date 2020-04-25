package com.xxf.common.utils;

/**
 * @Description 字符串工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
public class StringUtils {

    private static final String OVERLAY = "****";
    private static final int START = 3;
    private static final int END = 7;

    public static boolean isEmpty(String... list) {
        for (String str : list) {
            if (StringUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(String... list) {
        for (String str : list) {
            if (StringUtils.isNotEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static String maskPhone(String phoneNum) {

        return org.apache.commons.lang3.StringUtils.overlay(phoneNum, OVERLAY, START, END);
    }
}
