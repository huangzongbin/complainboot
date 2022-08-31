package com.krt.common.util;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: 获取随机字符串
 * @date 2016年7月20日
 */
public class RandomUtils {

	/**
	 * 获取随机字符串
	 * @param j
	 * @return
	 */
	public  static String getRandomStr(int j){
		char[] mapTable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		// 取随机产生的随机码
		StringBuffer strEnsure = new StringBuffer();
		// j代表j位随机码
		for (int i = 0; i < j; ++i) {
			char temp =  mapTable[(int) (mapTable.length * Math.random())];
			strEnsure.append(temp);
		}
		return strEnsure.toString();
	}
	public static void main(String[] args) {
		System.out.println(RandomUtils.getRandomStr(3));
		System.out.println(RandomUtils.getRandomStr(128));
	}
}
