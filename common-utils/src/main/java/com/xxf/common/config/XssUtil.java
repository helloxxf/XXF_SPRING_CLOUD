package com.xxf.common.config;

import java.util.Objects;

/**
 * @Description
 * @Date Created in 2019/6/26 11:44
 * @User xxf
 */
public class XssUtil {

    public static String cleanXSS(String value) {
        if (Objects.isNull(value)) {
            return value;
        }
        //You'll need to remove the spaces from the html entities below
//        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
//        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
//        value = value.replaceAll("'", "& #39;");
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        //  value = value.replaceAll("script", "");
        //  value = value.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]","");
        // value = value.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
        value = value.replaceAll("%", "\\\\%");
        return value;
    }
}
