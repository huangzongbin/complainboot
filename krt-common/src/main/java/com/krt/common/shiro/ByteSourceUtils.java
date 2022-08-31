package com.krt.common.shiro;

import org.apache.shiro.util.ByteSource;

/**
 * 解决：
 * shiro 使用缓存时出现：java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月10日
 */
public class ByteSourceUtils {

    public static ByteSource bytes(byte[] bytes) {
        return new SimpleByteSource(bytes);
    }

    public static ByteSource bytes(String arg0) {
        return new SimpleByteSource(arg0.getBytes());
    }

}
