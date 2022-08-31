package com.krt.file.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件md5工具
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月11日
 */
@Slf4j
public class FileMd5Utils {

    /**
     * 推荐此方法获取文件MD5
     *
     * @param file 文件
     * @return 文件Md5
     */
    public static String getMd5File(File file) {
        String md5 = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
            IOUtils.closeQuietly(fis);
        } catch (IOException e) {
            log.error("计算文件md5异常", e);
        }
        return md5;
    }

    /**
     * 推荐此方法获取文件MD5
     *
     * @param inputStream 文件
     * @return 文件Md5
     */
    public static String getMd5File(InputStream inputStream) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            log.error("计算文件md5异常", e);
        }
        return md5;
    }

}
