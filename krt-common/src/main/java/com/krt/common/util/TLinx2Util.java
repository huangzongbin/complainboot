package com.krt.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangdb
 * @date 2019/6/24 17:17
 */
@Slf4j
public class TLinx2Util {

    /**
     * 验签
     *
     * @param respObject
     * @return
     */
    public static Boolean verifySign(JSONObject respObject, String openKey) {
        String respSign = respObject.get("sign").toString();

        respObject.remove("sign");    // 删除sign节点
        respObject.put("open_key",openKey);
        String verifySign = sign(JSONObject.toJavaObject(respObject, Map.class));    // 按A~z排序，串联成字符串，先进行sha1加密(小写)，再进行md5加密(小写)，得到签名

        if (respSign.equals(verifySign)) {
            return true;
        }
        log.info("==========验签失败==========");
        return false;
    }

    /**
     * 签名
     *
     * @param postMap
     * @return
     */
    public static String sign(Map<String, String> postMap) {
        String sign = null;

        try {

            /**
             * 1 A~z排序(加上open_key)
             */
            String sortStr = sort(postMap);
            // log.info("====排序后的待签名字符串= " + sortStr);
            /**
             * 2 sha1加密(小写)
             */
            String sha1 = TLinxSHA1.SHA1(sortStr).toLowerCase();
            // log.info("====sha1加密后的待签名字符串= " + sha1);
            /**
             * 3 md5加密(小写)
             */
            sign = TLinxMD5.MD5Encode(sha1).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sign;
    }

    /**
     * 排序
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public static String sort(Map paramMap) throws Exception {
        String sort = "";
        TLinxMapUtil signMap = new TLinxMapUtil();
        if (paramMap != null) {
            String key;
            for (Iterator it = paramMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                String value = ((paramMap.get(key) != null) && (!("".equals(paramMap.get(key).toString())))) ? paramMap.get(key).toString() : "";
                signMap.put(key, value);
            }
            signMap.sort();
            for (Iterator it = signMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                sort = sort + key + "=" + signMap.get(key).toString() + "&";
            }
            if ((sort != null) && (!("".equals(sort)))) {
                sort = sort.substring(0, sort.length() - 1);
            }
        }
        return sort;
    }

    /**
     * byte数组转十六进制字符串
     */
    public static String byte2hex(byte[] result) {
        StringBuffer sb = new StringBuffer(result.length * 2);
        for (int i = 0; i < result.length; i++) {
            int hight = ((result[i] >> 4) & 0x0f);
            int low = result[i] & 0x0f;
            sb.append(hight > 9 ? (char) ((hight - 10) + 'a') : (char) (hight + '0'));
            sb.append(low > 9 ? (char) ((low - 10) + 'a') : (char) (low + '0'));
        }
        return sb.toString();
    }

    /**
     * Method main
     * Description 说明：
     *
     * @param args 说明：
     */
    public static void main(String[] args) {

    }
}
