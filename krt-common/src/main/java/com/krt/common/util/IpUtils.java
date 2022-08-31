package com.krt.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: IP工具类
 * @date 2017年04月11日
 */
public class IpUtils {

    /**
     * 获取当前网络ip
     *
     * @param request 请求
     */
    public static String getIpAddr(HttpServletRequest request) {

        String unknown = "unknown";
        String ipLocation = "127.0.0.1";
        String ipLocationInept = "0:0:0:0:0:0:0:1";
        String ipSplitStr = ",";
        int ipLength = 15;
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (ipLocation.equals(ip) || ipLocationInept.equals(ip)) {
                    //根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if(inet!=null) {
                        ip = inet.getHostAddress();
                    }
                }
            }
        } else if (ip.length() > ipLength) {
            if (ip.indexOf(ipSplitStr) > 0) {
                ip = ip.substring(0, ip.indexOf(ipSplitStr));
            }
        }
        return ip;
    }

    /**
     * 获取本机ip
     *
     * @return ip
     */
    public static String getLocalIp() {
        InetAddress addr;
        String ip = "unknown";
        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
