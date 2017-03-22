package com.hy.lightning.orm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static void main(String[] args) throws ParseException, InterruptedException {
		Date date1 = new Date();
		Date date2 = new Date(date1.getTime()+10000);
		System.out.println(defaultDateToString(date1));
		System.out.println(defaultDateToString(date2));
	}

	public static String defaultDateToString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}

	/**
	 * 计算当前时间距离24点剩余多少毫秒
	 * @param date
	 * @return
	 */
	public static long getDifferentials24(Date date) {
		String date_s = defaultDateToString(date);
		String new_date = date_s.split(" ")[0] + " 24:00:00";
		long l = defaultStringToDate(new_date).getTime() - date.getTime();
		return l;
	}

	public static Date defaultStringToDate(String s) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
