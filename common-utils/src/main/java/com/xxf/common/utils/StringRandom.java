package com.xxf.common.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 随机数工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
public class StringRandom {

    // 26个字母大小
    private static final int STRING_LENGTH = 26;
    //数字长度
    private static final int DIGITAL_LENGTH = 10;
    //数字2
    private static final int TWO = 2;
    //数字3
    private static final int THREE = 3;
    //数字4
    private static final int FOUR = 4;
    //密码长度
    private static final int PWD_LENGTH = 8;

    private StringRandom() {
    }

    /**
     * 生成随机8位数密码
     *
     * @return 密码的字符串
     */
    public static String getStringRandom() {
        StringBuilder pwd = new StringBuilder("");
        Random rand = new SecureRandom();
        for (int i = 0; i < PWD_LENGTH; i++) {
            int num = rand.nextInt(THREE);
            switch (num) {
                case 0:
                    // 生成随机小写字母
                    char c1 = (char) (rand.nextInt(STRING_LENGTH) + 'a');
                    pwd.append(c1);
                    break;
                case 1:
                    //生成随机大写字母
                    char c2 = (char) (rand.nextInt(STRING_LENGTH) + 'A');
                    pwd.append(c2);
                    break;
                case TWO:
                    //生成随机数字
                    pwd.append(rand.nextInt(DIGITAL_LENGTH));
                    break;
                default:
                    break;
            }
        }
        if (!isContainNumber(pwd.toString())) {
            pwd.replace(TWO, THREE, String.valueOf(rand.nextInt(DIGITAL_LENGTH)));
        }
        if (!isContainString(pwd.toString())) {
            char c = (char) (rand.nextInt(STRING_LENGTH) + 'a');
            pwd.replace(TWO, THREE, String.valueOf(c));
        }
        return pwd.toString();
    }

    /**
     * 生成随机8位数密码
     *
     * @return 密码的字符串
     */
    public static String getStringRandomOrder() {
        StringBuilder pwd = new StringBuilder("");
        Random rand = new SecureRandom();
        for (int i = 0; i < PWD_LENGTH; i++) {
            int num = i % 3;
            switch (num) {
                case 0:
                    // 生成随机小写字母
                    char c1 = (char) (rand.nextInt(STRING_LENGTH) + 'a');
                    pwd.append(c1);
                    break;
                case 1:
                    //生成随机大写字母
                    char c2 = (char) (rand.nextInt(STRING_LENGTH) + 'A');
                    pwd.append(c2);
                    break;
                case TWO:
                    //生成随机数字
                    pwd.append(rand.nextInt(DIGITAL_LENGTH));
                    break;
                default:
                    break;
            }
        }
        if (!isContainNumber(pwd.toString())) {
            pwd.replace(TWO, THREE, String.valueOf(rand.nextInt(DIGITAL_LENGTH)));
        }
        if (!isContainString(pwd.toString())) {
            char c = (char) (rand.nextInt(STRING_LENGTH) + 'a');
            pwd.replace(TWO, THREE, String.valueOf(c));
        }
        return pwd.toString();
    }

    /**
     * 判断字符串是否含有数字
     */
    private static boolean isContainNumber(String company) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(company);
        if (matcher.find()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断字符串是否含有字母
     */
    private static boolean isContainString(String company) {
        boolean flag = false;
        Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
        Matcher matcher = pattern.matcher(company);
        if (matcher.find()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 生成随机字符串
     *
     * @return 随机字符串
     */
    public static String getRandomChar(VerificationCodeType type) {
        String baseNumLetter = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
        String baseNum = "0123456789";
        String baseLetter = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
        String baseChar;
        switch (type) {
            case LETTER:
                baseChar = baseLetter;
                break;
            case NUM_LET:
                baseChar = baseNumLetter;
                break;
            case NUMBER:
                baseChar = baseNum;
                break;
            default:
                baseChar = baseNum;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FOUR; i++) {
            sb.append(baseChar.charAt(new SecureRandom().nextInt(baseChar.length())));
        }
        return sb.toString();
    }

}
