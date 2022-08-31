package com.krt.common.shiro;

import java.io.Serializable;

/**
 * 解决：
 * shiro 使用缓存时出现：java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月10日
 */

public class SimpleByteSource extends org.apache.shiro.util.SimpleByteSource implements Serializable {

    SimpleByteSource(byte[] bytes) {
        super(bytes);
    }

}
