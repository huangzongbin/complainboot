package com.krt.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期工具类
 * @author 殷帅
 * @date 2014年1月14日
 * @version 1.0
 */
public class DateUtils {
	
	private static final long ONE_MINUTE = 60;
	private static final long ONE_HOUR = 3600;
	private static final long ONE_DAY = 86400;
	private static final long ONE_WEEK = 604800;
	private static final long ONE_MONTH = 2592000;
	private static final long ONE_QUARTER = 7776000;
	private static final long ONE_YEAR = 31104000;
	
	/**
	 * 获取当前年份
	 * @return
	 */
	public static String getYear() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
	/**
	 * 获取但前月份
	 * @return
	 */
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		return String.valueOf(month);
	}
	/**
	 * 获取当前日子
	 * @return
	 */
	public static String getDay() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.DATE));
	}
	/**
	 * 获取当前小时
	 *
	 * @return
	 */
	public static String get24Hour() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}
	/**
	 * 获取当前分钟
	 * @return
	 */
	public static String getMinute() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.MINUTE));
	}
	/**
	 * 获取当前秒数
	 * @return
	 */
	public static String getSecond() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.SECOND));
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getDate() {
		return getYear() + "-" + getMonth() + "-" + getDay();
	}

	/**
	 * 获取今天的日期（自定义格式化）
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simple = new SimpleDateFormat(format);
		String date = simple.format(calendar.getTime());
		return date;
	}

	/**
	 * 获取日期（自定义格式化）
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(String date,String format){
		SimpleDateFormat simple = null;
		String d="";
		Date sdate;
		try {
		if(date.length()==6||date.length()==7){
			simple = new SimpleDateFormat("yyyy-MM");
		}else{
			simple = new SimpleDateFormat("yyyy-MM-dd");
		}
		SimpleDateFormat simple2 = new SimpleDateFormat(format);
		sdate = simple.parse(date);
		d = simple2.format(sdate);
		if("MM".equals(format)){
			return String.valueOf(Integer.valueOf(d));
		}
		if("dd".equals(format)){
			return String.valueOf(Integer.valueOf(d));
		}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 日期间隔（相对）
	 * @param type
	 * @param d1
	 * @param d2
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String dayfromDay(String type,String d1,String d2,String format) throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat(format);
		Date date1 = simple.parse(d1);
		Date date2 = simple.parse(d2);
		long t1 = date1.getTime()/1000;
		long t2 = date2.getTime()/1000;
		long remain = t2-t1;
		long minute = remain/ONE_MINUTE;
		long hour = remain/ONE_HOUR;
		long day = remain/ONE_DAY;
		if("year".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR);
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.YEAR)-time);
		  	}

		 if("quarter".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR) * 4;
			   calendar.setTime(date2);
			   time -= calendar.get(Calendar.YEAR) * 4;
			   calendar.setTime(date1);
			   time += calendar.get(Calendar.MONTH) / 4;
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.MONTH)-time / 4);
		  	}

		 if("month".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR) * 12;
			   calendar.setTime(date2);
			   time -= calendar.get(Calendar.YEAR) * 12;
			   calendar.setTime(date1);
			   time += calendar.get(Calendar.MONTH);
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.MONTH)-time);
		  	}

		 if ("week".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR) * 52;
			   calendar.setTime(date2);
			   time -= calendar.get(Calendar.YEAR) * 52;
			   calendar.setTime(date1);
			   time += calendar.get(Calendar.WEEK_OF_YEAR);
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)-time);
		  	}
			if("day".equals(type)){
				return ""+day;
			}
			if("hour".equals(type)){
				return ""+hour;
			}
			if("minute".equals(type)){
				return ""+minute;
			}

		  return "";
		
	}
	/**
	 * 日期间隔（绝对时间）
	 * @param type
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String daytoDay(String type,String date1,String date2,String format) throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat(format);
		Date d1 = simple.parse(date1);
		Date d2 = simple.parse(date2); 
		long t1 = d1.getTime()/1000;
		long t2 = d2.getTime()/1000;
		long remain = t2-t1;
		long minute = remain/ONE_MINUTE;
		long hour = remain/ONE_HOUR;
		long day = remain/ONE_DAY;
		long week = remain/ONE_WEEK;
		long month = remain/ONE_MONTH;
		long quarter = remain/ONE_QUARTER;
		long year = remain/ONE_YEAR;
		if("minute".equals(type)){
			return ""+minute;
		}
		if("hour".equals(type)){
			return ""+hour;
		}
		if("day".equals(type)){
			return ""+day;
		}
		if("week".equals(type)){
			return ""+week;
		}
		if("month".equals(type)){
			return ""+month;
		}
		if("quarter".equals(type)){
			return ""+quarter;
		}
		if("year".equals(type)){
			return ""+year;
		}
		return "";
	}

	/**
	 * 日期间隔（相对）
	 * @param type
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static String datediff(String type,Date date1,Date date2) throws ParseException{
		long t1 = date1.getTime()/1000;
		long t2 = date2.getTime()/1000;
		long remain = t2-t1;
		long minute = remain/ONE_MINUTE;
		long hour = remain/ONE_HOUR;
		long day = remain/ONE_DAY;
		if("year".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR);
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.YEAR)-time);
		  	}

		 if("quarter".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR) * 4;
			   calendar.setTime(date2);
			   time -= calendar.get(Calendar.YEAR) * 4;
			   calendar.setTime(date1);
			   time += calendar.get(Calendar.MONTH) / 4;
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.MONTH)-time / 4);
		  	}

		 if("month".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR) * 12;
			   calendar.setTime(date2);
			   time -= calendar.get(Calendar.YEAR) * 12;
			   calendar.setTime(date1);
			   time += calendar.get(Calendar.MONTH);
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.MONTH)-time);
		  	}

		 if ("week".equals(type)) {
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(date1);
			   int time = calendar.get(Calendar.YEAR) * 52;
			   calendar.setTime(date2);
			   time -= calendar.get(Calendar.YEAR) * 52;
			   calendar.setTime(date1);
			   time += calendar.get(Calendar.WEEK_OF_YEAR);
			   calendar.setTime(date2);
			   return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)-time);
		  	}
			if("day".equals(type)){
				return ""+day;
			}
			if("hour".equals(type)){
				return ""+hour;
			}
			if("minute".equals(type)){
				return ""+minute;
			}

		  return "";
		
	}
	/**
	 * 自定义时间相加
	 * @return
	 */
	public static Date dateAdd(String type,int amount, Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if("minute".equals(type)){
			calendar.add(Calendar.MINUTE, amount);
		}
		if("hour".equals(type)){
			calendar.add(Calendar.HOUR, amount);
		}
		if("day".equals(type)){
			calendar.add(Calendar.DATE, amount);
		}
		if("month".equals(type)){
			calendar.add(Calendar.MONTH, amount);
		}
		if("year".equals(type)){
			calendar.add(Calendar.YEAR, amount);
		}
		return calendar.getTime();
		
			 
	}
	
	/**
	 * 将字符串转换成日期类型
	 * @param format
	 * @param sdate
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String format,String sdate){
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		try {
			date = dateformat.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将日期类型转换成字符串类型
	 * @param format
	 * @param sdate
	 * @return
	 */
	public static String dateToString(String format,Date sdate){
		SimpleDateFormat simple = new SimpleDateFormat(format);
		String date = simple.format(sdate);
		return date;
	}

	/**
	 * 时间戳格式化
	 * @param timeMillis
	 * @return
	 */
	public static String timeStampToDate(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 *  Date转时间戳
	 * @param date
	 * @return
	 */
	public static Long dateToTimeStamp(Date date){ 
	   return  date.getTime();
	}

	
	/**
	 *  主方法测试
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtils.dateToString("yyyy-MM-dd HH:mm:ss", new Date()));
	}

}
