package com.krt.common.util;


import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端设备信息
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年08月25日
 */
public class UserAgentUtils {

    private UserAgent userAgent;

    private String userAgentString;

    private HttpServletRequest request;

    public UserAgentUtils(HttpServletRequest request) {
        this.request = request;
        userAgentString = request.getHeader("User-Agent");
        userAgent = UserAgent.parseUserAgentString(userAgentString);
    }

    /**
     * 获取浏览器类型
     */
    public String getBrowser() {
        if (null == userAgent) {
            return "";
        }
        return userAgent.getBrowser().getName();
    }

    /**
     * 获取操作系统
     */
    public String getOS() {
        if (null == userAgent) {
            return "未知设备";
        }
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 获取设备型号
     */
    public String getDevice() {
        String android = "Android";
        String iPhone = "iPhone";
        String iPad = "iPad";
        if (null == userAgentString) {
            return "未知设备";
        }
        if (userAgentString.contains(android)) {
            String[] str = userAgentString.split("[()]+");
            str = str[1].split("[;]");
            String[] res = str[str.length - 1].split("Build/");
            return res[0].trim();
        } else if (userAgentString.contains(iPhone)) {
            String[] str = userAgentString.split("[()]+");
            String res = "iphone" + str[1].split("OS")[1].split("like")[0];
            return res.trim();
        } else if (userAgentString.contains(iPad)) {
            return iPad;
        } else {
            return "PC";
        }
    }

}
