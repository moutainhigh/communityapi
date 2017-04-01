package net.okdi.core.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期处理工具。执行main方法，看效果。
 * 
 * @date 2014-09-04
 * @author xiaodong.wang
 * @version 1.0
 * 
 */
public class DateUtil {
	private static SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static int weeks = 0;
	private static int MaxDate;
	private static int MaxYear;

	public static String formatUSdate(Date date, String pattern) {
		String s = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
		s = format.format(date);
		return s;
	}

	/**
	 * 日期比较
	 * 
	 * @param d1
	 * @param d2
	 * @return -1 d1 小于d2 ,0 d1等于d2,1 d1 大于d2
	 */
	public static int compare(Date d1, Date d2) {
		long time1 = d1.getTime();
		long time2 = d2.getTime();
		if (time1 < time2) {
			return -1;
		} else if (time1 == time2) {
			return 0;
		} else {
			return 1;
		}
	}

	public static String getWeekByDate(Date date) {
		String[] weekDaysName = { "日", "一", "二", "三", "四", "五", "六" };
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(7) - 1;
		return weekDaysName[intWeek];
	}

	public static String getWeekByDateStr(String date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return getWeekByDate(fmt.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getCurrentYearInt() {
		return Integer.parseInt(getCurrentYearStr());
	}

	public static String getCurrentYearStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}

	public static void main(String[] args) {
		System.out.println("获取当天英文月份:" + formatUSdate(new Date(), "MMM.dd"));
		System.out.println("获取当天日期时间:" + getNowTime("yyyy-MM-dd hh:mm:ss"));
		System.out.println("获取当天日期:" + getNowTime("yyyy-MM-dd"));
		System.out.println("获取本周一日期:" + getMondayOFWeek());
		System.out.println("获取本周日的日期~:" + getCurrentWeekday());
		System.out.println("获取上周一日期:" + getPreviousWeekday());
		System.out.println("获取上周日日期:" + getPreviousWeekSunday());
		System.out.println("获取下周一日期:" + getNextMonday());
		System.out.println("获取下周日日期:" + getNextSunday());
		System.out.println("获取本月第一天日期:" + getFirstDayOfMonth());
		System.out.println("获取本月最后一天日期:" + getDefaultDay());
		System.out.println("获取上月第一天日期:" + getPreviousMonthFirst());
		System.out.println("获取上月最后一天的日期:" + getPreviousMonthEnd());
		System.out.println("获取下月第一天日期:" + getNextMonthFirst());
		System.out.println("获取下月最后一天日期:" + getNextMonthEnd());
		System.out.println("获取本年的第一天日期:" + getCurrentYearFirst());
		System.out.println("获取本年最后一天日期:" + getCurrentYearEnd());
		System.out.println("获取去年的第一天日期:" + getPreviousYearFirst());
		System.out.println("获取去年的最后一天日期:" + getPreviousYearEnd());
		System.out.println("获取明年第一天日期:" + getNextYearFirst());
		System.out.println("获取明年最后一天日期:" + getNextYearEnd());
		System.out.println("获取本季度第一天到最后一天:" + getThisSeasonTime(11));
		System.out.println("获取当前年份：" + getCurrentYearStr());
		System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:" + getTwoDay("2008-09-29", "2008-09-29"));
		System.out.println("获取指定日期的某天之后的日期：" + dateAddDay("2012-12-31", 0));
		System.out.println("格式化指定日期：" + format(parse("2012-12-24", "yyyy-MM-dd"), "MM月dd日"));
	}

	public static String format(Date date, String pattern) {
		String s = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		s = format.format(date);
		return s;
	}

	public static Date parse(String date, String pattern) {
		Date d = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			d = format.parse(date);
		} catch (ParseException e) {
			return d;
		}
		return d;
	}

	public static String dateAddDay(String date, int day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String s = "";
		try {
			Date d = format.parse(date);
			d.setTime(d.getTime() + day * 24 * 60 * 60 * 1000);
			s = format.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static Date datetimeAddDay(Date datetime, int day) {
		datetime.setTime(datetime.getTime() + day * 24 * 60 * 60 * 1000);
		return datetime;
	}

	public static long getTwoDay(String sj1, String sj2) {
		long day = 0L;
		try {
			Date date = ymdFormatter.parse(sj1);
			Date mydate = ymdFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / 86400000L;
		} catch (Exception e) {
			return -1L;
		}
		return day;
	}

	public static String getWeek(String sdate) {
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static Date strToDate(String strDate) {
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = ymdFormatter.parse(strDate, pos);
		return strtodate;
	}

	public static long getDays(String date1, String date2) {
		if ((date1 == null) || (date1.equals("")))
			return 0L;
		if ((date2 == null) || (date2.equals(""))) {
			return 0L;
		}
		Date date = null;
		Date mydate = null;
		try {
			date = ymdFormatter.parse(date1);
			mydate = ymdFormatter.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long day = (date.getTime() - mydate.getTime()) / 86400000L;
		return day;
	}

	public static String getDefaultDay() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, 1);
		lastDate.add(5, -1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getPreviousMonthFirst() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		lastDate.add(2, -1);

		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getFirstDayOfMonth() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(5, 1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 6);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		String hehe = dateFormat.format(now);
		return hehe;
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();

		int dayOfWeek = cd.get(7) - 1;
		if (dayOfWeek == 1) {
			return 0;
		}
		return 1 - dayOfWeek;
	}

	public static String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static String getSaturday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static String getPreviousWeekSunday() {
		weeks = 0;
		weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static String getPreviousWeekday() {
		weeks -= 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static String getNextMonday() {
		weeks += 1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static String getNextSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		String preMonday = ymdFormatter.format(monday);
		return preMonday;
	}

	public static int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(5);
		cd.set(5, 1);
		cd.roll(5, -1);
		MaxDate = cd.get(5);
		if (monthOfNumber == 1) {
			return -MaxDate;
		}
		return 1 - monthOfNumber;
	}

	public static String getPreviousMonthEnd() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(2, -1);
		lastDate.set(5, 1);
		lastDate.roll(5, -1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getNextMonthFirst() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(2, 1);
		lastDate.set(5, 1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getNextMonthEnd() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(2, 1);
		lastDate.set(5, 1);
		lastDate.roll(5, -1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getNextYearEnd() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(1, 1);
		lastDate.set(6, 1);
		lastDate.roll(6, -1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static String getNextYearFirst() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(1, 1);
		lastDate.set(6, 1);
		str = ymdFormatter.format(lastDate.getTime());
		return str;
	}

	public static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(6, 1);
		cd.roll(6, -1);
		int MaxYear = cd.get(6);
		return MaxYear;
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(6);
		cd.set(6, 1);
		cd.roll(6, -1);
		int MaxYear = cd.get(6);
		if (yearOfNumber == 1) {
			return -MaxYear;
		}
		return 1 - yearOfNumber;
	}

	public static String getCurrentYearFirst() {
		String preYearDay = getCurrentYearStr() + "-01-01";
		return preYearDay;
	}

	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-01-01";
	}

	public static String getPreviousYearEnd() {
		weeks -= 1;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		String preYearDay = ymdFormatter.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	public static String getThisSeasonTime(int month) {
		int[][] array = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if ((month >= 1) && (month <= 3)) {
			season = 1;
		}
		if ((month >= 4) && (month <= 6)) {
			season = 2;
		}
		if ((month >= 7) && (month <= 9)) {
			season = 3;
		}
		if ((month >= 10) && (month <= 12)) {
			season = 4;
		}
		int start_month = array[(season - 1)][0];
		int end_month = array[(season - 1)][2];
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		int start_days = 1;
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;
	}

	private static int getLastDayOfMonth(int year, int month) {
		if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
			return 31;
		}
		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			}
			return 28;
		}

		return 0;
	}

	public static boolean isLeapYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}
}
