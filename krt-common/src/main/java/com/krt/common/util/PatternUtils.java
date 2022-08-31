package com.krt.common.util;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: 正则表达式
 * @date 2017年06月21日
 */
public class PatternUtils {

    /** 英文字母 、数字和下划线 */
    public final static String GENERAL = "^\\w+$";
    /** 数字 */
    public final static String NUMBERS = "\\d+";
    /**单个中文汉字 */
    public final static String CHINESE = "[\u4E00-\u9FFF]";
    /**中文汉字 */
    public final static String CHINESES = "[\u4E00-\u9FFF]+";
    /** IP v4 */
    public final static String IPV4 = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    /** 货币 */
    public final static String MONEY = "^(\\d+(?:\\.\\d+)?)$";
    /** 邮件 */
    public final static String EMAIL = "(\\w|.)+@\\w+(\\.\\w+){1,2}";
    /** 移动电话 */
    public final static String MOBILE = "1\\d{10}";
    /** 身份证号码 */
    public final static String CITIZEN_ID = "[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|X|x)";
    /** 邮编 */
    public final static String ZIP_CODE = "\\d{6}";
    /** 生日 */
    public final static String BIRTHDAY = "^(\\d{2,4})([/\\-\\.年]?)(\\d{1,2})([/\\-\\.月]?)(\\d{1,2})日?$";
    /** URL */
    public final static String URL = "(https://|http://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /** 中文字、英文字母、数字和下划线 */
    public final static String GENERAL_WITH_CHINESE = "^[\u4E00-\u9FFF\\w]+$";
    /** UUID */
    public final static String UUID = "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$";
    /** 不带横线的UUID */
    public final static String UUID_SIMPLE = "^[0-9a-z]{32}$";
    /** 中国车牌号码 */
    public final static String PLATE_NUMBER = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
}