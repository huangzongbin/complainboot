package com.krt.common.util;

import java.util.regex.Pattern;

/**
 * @Description: 去掉html标签工具类
 * @author 殷帅
 * @date 2014年1月14日
 * @version 1.0
 */
public class HtmlUtils {

    public static String getText(String htmlStr) {
        if (htmlStr == null || "".equals(htmlStr)) {
            return "";
        }
        String textStr = "";
        Pattern pattern;
        java.util.regex.Matcher matcher;

        try {
            String regexRemark = "<!--.+?-->";
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regexScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regexStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            ////定义HTML标签的正则表达式
            String regexHtml = "<[^>]+>";
            String regexHtml1 = "<[^>]+";
            htmlStr = htmlStr.replaceAll("\n", "");
            htmlStr = htmlStr.replaceAll("\t", "");
            htmlStr = htmlStr.replaceAll("\r", "");
            htmlStr = htmlStr.replaceAll("&nbsp;", "");
            //过滤注释标签
            pattern = Pattern.compile(regexRemark);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll("");

            pattern = Pattern.compile(regexScript, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            ////过滤script标签
            htmlStr = matcher.replaceAll("");

            pattern = Pattern.compile(regexStyle, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            ////过滤style标签
            htmlStr = matcher.replaceAll("");

            pattern = Pattern.compile(regexHtml, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            //过滤html标签
            htmlStr = matcher.replaceAll("");

            pattern = Pattern.compile(regexHtml1, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            ////过滤html标签
            htmlStr = matcher.replaceAll("");

            textStr = htmlStr.trim();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回文本字符串
        return textStr;
    }
}
