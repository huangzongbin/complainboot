package com.krt.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 殷帅
 * @version 1.0
 * @Description:
 * @date 2017年06月21日
 */
public final class CharsetUtils {

    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认ISO-8859-1
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset) {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset) {
            destCharset = StandardCharsets.UTF_8;
        }

        if ("".equals(source) || srcCharset.equals(destCharset)) {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }


    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     * @see CharsetUtils#defaultCharsetName()
     */
    public static String defaultCharsetName() {
        return Charset.defaultCharset().name();
    }
}
