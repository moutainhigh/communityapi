package net.okdi.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	* 是否是今天
	* 
	* @param date
	* @return
	*/
	public static boolean isToday(final Date date) {
	        return isTheDay(date, new Date());
	}
	/**
	* 是否是指定日期
	* 
	* @param date
	* @param day
	* @return
	*/
	public static boolean isTheDay(final Date date, final Date day) {
	        return date.getTime() >= DateUtils.dayBegin(day).getTime()
	                        && date.getTime() <= DateUtils.dayEnd(day).getTime();
	}
	/**
	* 获取指定时间的那天 00:00:00.000 的时间
	* 
	* @param date
	* @return
	*/
	public static Date dayBegin(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 0);
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	        return c.getTime();
	}
	/**
	* 获取指定时间的那天 23:59:59.999 的时间
	* 
	* @param date
	* @return
	*/
	public static Date dayEnd(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 23);
	        c.set(Calendar.MINUTE, 59);
	        c.set(Calendar.SECOND, 59);
	        c.set(Calendar.MILLISECOND, 999);
	        return c.getTime();
	}
	
	public   static   String   getLastDayOfMonth(Date   sDate1)   {  
        Calendar   cDay1   =   Calendar.getInstance();  
        cDay1.setTime(sDate1);  
        final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date   lastDate   =   cDay1.getTime();  
        lastDate.setDate(lastDay);  
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return   sim.format(lastDate);  
}  


	public static void main(String[] args) {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			System.out.println(DateUtils.isToday(sim.parse("2016-03-03 12:00:00")));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String end="2016-03-03 12:00:00";
//		System.out.println(end.substring(0, 4)+end.substring(5,7));
		Date now=new Date();
		String nows=sim.format(now);
		System.out.println(nows);
	}

}
