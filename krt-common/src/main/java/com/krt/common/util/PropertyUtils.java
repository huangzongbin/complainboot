package com.krt.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: Property文件读取工具类
 * @date 2016年7月20日
 */
@Slf4j
public class PropertyUtils {

    private Properties p;

    private PropertyUtils(String fileName) {
        p = new Properties();
        try {
            log.debug("读取配置文件{}", fileName);
            InputStreamReader in = new InputStreamReader(Objects.requireNonNull(PropertyUtils.class.getClassLoader().getResourceAsStream(fileName)), "UTF-8");
            p.load(in);
            in.close();
        } catch (IOException e) {
            log.error("读取配置文件{}异常", fileName, e);
        }
    }

    public String getValue(String key) {
        return p.getProperty(key);
    }

    public static void main(String[] args) {
        PropertyUtils propertyUtil = new PropertyUtils("krt.properties");
        System.out.println(propertyUtil.getValue("web_static"));
    }
}
