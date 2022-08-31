package com.krt.common.util;

import java.util.HashMap;

/**
 * 上传工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月18日
 */
public class UploadUtils {

    /**
     * 定义允许上传的文件扩展名
     */
    public static HashMap<String, String> extMap = new HashMap<>(16);

    /**
     * 初始化文件后缀配置
     */
    static {
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf,apk");
        extMap.put("all", "gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf,apk");
    }

}
