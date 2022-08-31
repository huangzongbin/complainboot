package com.krt.common.util;

import java.security.MessageDigest;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: MD5加密工具类
 * @date 2017年04月11日
 */
public class Md5Utils {
	/**
 	 * MD5加密(32位) 小写
 	 * 
 	 * @param instr
 	 *            要加密的字符串
 	 * @return 返回加密后的字符串
 	 */
 	public final static String encoderByMd5With32Bit(String instr) {
 		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
 		try {
 			if (instr != null && !"".equals(instr)) {
 				byte[] strTemp = instr.getBytes();
 				// MD5计算方法
 				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
 				mdTemp.update(strTemp);
 				byte[] md = mdTemp.digest();
 				int j = md.length;
 				char[] str = new char[j * 2];
 				int k = 0;
 				for (int i = 0; i < j; i++) {
 					byte byte0 = md[i];
 					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
 					str[k++] = hexDigits[byte0 & 0xf];
 				}
 				return new String(str);
 			} else {
 				return null;
 			}
 		} catch (Exception e) {
 			return null;
 		}
 	}

	public static void main(String[] args) {
		System.out.printf(encoderByMd5With32Bit("1120171120144222asdfg12345"));
	}
  
}
