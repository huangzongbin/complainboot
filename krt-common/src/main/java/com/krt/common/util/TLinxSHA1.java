package com.krt.common.util;

import java.security.MessageDigest;

/**
 * @author zhangdb
 * @date 2019/6/24 17:25
 */
public class TLinxSHA1 {
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes("UTF-8"));
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte message : messageDigest) {
                String shaHex = Integer.toHexString(message & 0xFF);
                if (shaHex.length() < 2)
                    hexString.append(0);

                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
