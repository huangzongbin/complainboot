package com.krt.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * 对接赣州银行支付平台工具类
 * @author zhangdb
 * @date 2019/6/10 17:09
 */
public class PayUtils {

    public static String open_id = "574D3754EB9778DF83BDEC0E067C2A4CE8864AA42825A3C6A94114EC486A2DB93BAE83BFE18B5AA0B18259875988CDED";
    public static String open_key = "7A358BCF386EE98B87CEDEDEBEF011D50EEE75510FBD56B6FDBC0386E9D529973BAE83BFE18B5AA0B18259875988CDED";



    /**
     * 获取unix时间戳
     * @return
     */
    public static String getTimestamp () {
        //初始化时区对象，北京时间是UTC+8，所以入参为8
        ZoneOffset zoneOffset= ZoneOffset.ofHours(8);
        //初始化LocalDateTime对象
        LocalDateTime localDateTime= LocalDateTime.now();
        return localDateTime.toEpochSecond(zoneOffset)+"";
    }

    /**
     * 参数加密获取签名
     * @param params 参数字符串
     * @return
     */
    public static String getSignByParams (String params) {
        return Md5Utils.encoderByMd5With32Bit(DigestUtils.sha1Hex(params));
    }

    /**
     * AES data 加密
     * @param dataObj
     * @param open_key
     * @return
     * @throws Exception
     */
    public static String getDataParams(JSONObject dataObj, String open_key) throws Exception {
        return AES2.Encrypt(dataObj.toString(),open_key,2);
    }

    /**
     * 把unicode编码转换为中文
     *
     * @param str
     * @return
     */
    public static String decode(String str) {
        String sg = "\\u";
        int a = 0;
        List<String> list = new ArrayList<>();
        while (str.contains(sg)) {
            str = str.substring(2);
            String substring;
            if (str.contains(sg)) {
                substring = str.substring(0, str.indexOf(sg));
            } else {
                substring = str;
            }
            if (str.contains(sg)) {
                str = str.substring(str.indexOf(sg));
            }
            list.add(substring);
        }
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)){
            for (String string : list) {
                sb.append((char) Integer.parseInt(string, 16));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // data解密
        // String dataString = AES2.Decrypt(returnData + "", open_key, 2);
        System.out.println(decode("\\u8d63\\u5dde\\u5e02\\u4fdd\\u969c\\u6027\\u4f4f\\u623f\\u5efa\\u8bbe\\u8fd0\\u8425\\u6709\\u9650\\u516c\\u53f8"));
    }

}
