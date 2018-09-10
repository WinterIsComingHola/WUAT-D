package com.xuxinyu.uidriver.basehandle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xuxinyu
 *
 */

/***
 * 创建日期工具类
 * 用于日期的格式化以及相关日期数据的获取
 * */

public class DateFormatUtil {
	
	public static String format(Date date, String datestring) {

		String dateresult = "";
		if (date != null) {

			DateFormat df = new SimpleDateFormat(datestring);
			dateresult = df.format(date);
		}
		return dateresult;
	}
	
	/*返回年份
	 * 
	 * */
	public static int getYear(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return calendar.get(Calendar.YEAR);
	}
	
	/*返回月份
	 * 
	 * */
	public static int getMouth(Date date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return calendar.get(Calendar.MONTH);
	}


/*返回月份中的第几天
 * 
 * */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

/*返回小时
 * 
 * */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

/*返回分钟
 * 
 * */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

/*返回秒
 * 
 * */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

}
