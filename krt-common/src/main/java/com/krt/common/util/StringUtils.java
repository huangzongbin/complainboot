package com.krt.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: 字符串工具类
 * @date 2018年05月03日
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 字符串正则表达式
     */
    private final static String RULE = "([A-Za-z\\d]+)(_)?";

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(RULE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(RULE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    /**
     * 判断str1是否包含str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Boolean contains(String str1, String str2) {
        if (isBlank(str1) || isBlank(str2)) {
            return false;
        } else {
            str1 = "," + str1 + ",";
            str2 = "," + str2 + ",";
            if (str1.contains(str2)) {
                return true;
            } else {
                return false;
            }

        }

    }

    /**
     * 转码
     *
     * @param str
     * @return
     */
    public static String toUTF(String str) throws UnsupportedEncodingException {
        if (isBlank(str)) {
            return "";
        } else {
            str = new String(str.trim().getBytes("ISO-8859-1"), "UTF-8");
            return str;
        }
    }
}
