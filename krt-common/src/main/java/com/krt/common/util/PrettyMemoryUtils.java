package com.krt.common.util;

/**
 * @author 殷帅
 * @version 1.0
 * @Description:
 * @date 2017年05月24日
 */
public class PrettyMemoryUtils {

    private static final int UNIT = 1024;
    private static final String B = "B";
    private static final String KB = "KB";
    private static final String MB = "MB";
    private static final String GB = "GB";
    private static final String TB = "TB";

    /**
     * @param byteSize 字节
     * @return
     */
    public static String prettyByteSize(long byteSize) {

        double size = 1.0 * byteSize;

        String type = B;
        //不足1KB
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = B;
            return format(size, type);
        }

        size = size / UNIT;
        //不足1MB
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = KB;
            return format(size, type);
        }

        size = size / UNIT;
        //不足1GB
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = MB;
            return format(size, type);
        }

        size = size / UNIT;
        //不足1TB
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = GB;
            return format(size, type);
        }

        size = size / UNIT;
        //不足1PB
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = TB;
            return format(size, type);
        }

        size = size / UNIT;
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = "PB";
            return format(size, type);
        }
        return ">PB";
    }

    private static String format(double size, String type) {
        int precision = 0;

        if (size * 1000 % 10 > 0) {
            precision = 3;
        } else if (size * 100 % 10 > 0) {
            precision = 2;
        } else if (size * 10 % 10 > 0) {
            precision = 1;
        } else {
            precision = 0;
        }

        String formatStr = "%." + precision + "f";
        if (KB.equals(type)) {
            return String.format(formatStr, (size)) + KB;
        } else if (MB.equals(type)) {
            return String.format(formatStr, (size)) + MB;
        } else if (GB.equals(type)) {
            return String.format(formatStr, (size)) + GB;
        } else if (TB.equals(type)) {
            return String.format(formatStr, (size)) + TB;
        } else if ("PB".equals(type)) {
            return String.format(formatStr, (size)) + "PB";
        }
        return String.format(formatStr, (size)) + B;
    }

    public static void main(String[] args) {
        System.out.println(PrettyMemoryUtils.prettyByteSize(1023));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT * UNIT));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT * 1023));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * 1023 * 1023 * 1023));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT * UNIT * UNIT));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT * UNIT * UNIT * UNIT));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT * UNIT * UNIT * UNIT * UNIT));
        System.out.println(PrettyMemoryUtils.prettyByteSize(1L * UNIT * UNIT * UNIT * UNIT * UNIT * UNIT));
    }

}
