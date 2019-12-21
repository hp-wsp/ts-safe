package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author WangWei
 */
public class DateUtils {
	private static final DateTimeFormatter defaultDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter defaultTimestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ;
	private static final long dayTimeMillis = 24 * 60 * 60 * 1000L;
	private static final long minuteMillis = 60 * 1000L;

	/**
	 * 格式化日期
	 * 
	 * @param time 时间
	 * @return 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 */
	public static String formatDate(LocalDateTime time){
		return format(defaultDateFormat, time);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param time 时间
	 * @return 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 */
	public static String formatDate(LocalDate time){
		return format(defaultDateFormat, time);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param time 时间
	 * @return 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 */
	public static String formatDate(Date time){
		return format(defaultDateFormat, time.getTime());
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param milli 毫秒
	 * @return 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 */
	public static String formatDate(long milli){
		return format(defaultDateFormat, milli);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param time 时间
	 * @return 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 */
	public static String formatTimestamp(LocalDateTime time){
		return format(defaultTimestampFormat, time);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param time  时间
	 * @return 日期字符串，格式为yyyy-MM-dd HH:mm:ss(2015-01-01 01:01:01)
	 */
	public static String formatTimestamp(Date time) {
		return format(defaultTimestampFormat, time);
	}

	/**
	 * 格式化日期
	 * 
	 * @param milli  当前毫秒
	 * @return 日期字符串，格式为yyyy-MM-dd HH:mm:ss(2015-01-01 01:01:01)
	 */
	public static String formatTimestamp(long milli) {
		return format(defaultTimestampFormat, milli);
	}

	/**
	 * 格式化日期
	 * 
	 * @param pattern 日期格式
	 * @param time 时间
	 * @return 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 */
	public static String format(String pattern, LocalDateTime time){
		return format(DateTimeFormatter.ofPattern(pattern), time);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param pattern 日期格式
	 * @param time    时间
	 * @return 日期字符串，格式为{@code pattern}
	 */
	public static String format(String pattern, Date time) {
		return format(DateTimeFormatter.ofPattern(pattern), time);
	}

	/**
	 * 格式化日期
	 * 
	 * @param pattern 日期格式
	 * @param milli   毫秒
	 * @return 日期字符串，格式为{@code pattern}
	 */
	public static String format(String pattern, long milli) {
		return format(DateTimeFormatter.ofPattern(pattern), milli);
	}
	
	/**
	 * 格式日期
	 * 
	 * @param formatter 日期格式对象
	 * @param milli     毫秒
	 * @return 日期字符串，格式为{@code formatter}
	 */ 
	public static String format(DateTimeFormatter formatter, Date time){
		LocalDateTime t = LocalDateTime.ofInstant(Instant.ofEpochMilli(time.getTime()), ZoneId.systemDefault());
		return format(formatter, t);
	}
	
	/**
	 * 格式日期
	 * 
	 * @param formatter 日期格式对象
	 * @param milli     毫秒
	 * @return 日期字符串，格式为{@code formatter}
	 */ 
	public static String format(DateTimeFormatter formatter, long milli){
		LocalDateTime t = LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault());
		return format(formatter, t);
	}
	
	/**
	 * 格式日期
	 * 
	 * @param formatter 日期格式对象
	 * @param time      日期
	 * @return 日期字符串，格式为{@code formatter}
	 */
	public static String format(DateTimeFormatter formatter, LocalDateTime time){
		return time.format(formatter);
	}
	
	/**
	 * 格式日期
	 * 
	 * @param formatter 日期格式对象
	 * @param date      日期
	 * @return 日期字符串，格式为{@code formatter}
	 */
	public static String format(DateTimeFormatter formatter, LocalDate date){
		return date.format(formatter);
	}

	/**
	 * 解析日期字符串
	 * 
	 * @param text 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 * @return {@link LocalDateTime}
	 */
	public static LocalDate parseLocalDate(String text) {
		return LocalDate.parse(text, defaultDateFormat);
	}
	
	/**
	 * 解析日期字符串为日期
	 * 
	 * @param pattern 日期格式
	 * @param text 日期字符串
	 * @return {@link LocalDateTime}
	 */
	public static LocalDate parseLocalDate(String pattern, String text) {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * 解析日期字符串为日期
	 * 
	 * @param text 日期字符串，格式为yyyy-MM-dd HH:mm:ss(2015-01-01 01:01:01)
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime parseLocalTimestamp(String text) {
		boolean hasTime = StringUtils.contains(text, ":");
		String t = hasTime ? text : text + " 00:00:00";
		return LocalDateTime.parse(t, defaultTimestampFormat);
	}
	
	/**
	 * 解析日期字符串为日期
	 * 
	 * @param pattern 日期格式
	 * @param text 日期字符串
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime parseLocalTimestamp(String pattern, String text) {
		return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * 解析日期字符串
	 * 
	 * @param text 日期字符串，格式为yyyy-MM-dd(2015-01-01)
	 * @return {@link Date}
	 */
	public static Date parseDate(String text) {
		LocalDate lt = LocalDate.parse(text, defaultDateFormat);
		return Date.from(lt.atTime(0,0).atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 解析日期字符串为日期
	 * 
	 * @param pattern 日期格式
	 * @param text    日期字符串
	 * @return {@link Date}
	 */
	public static Date parseDate(String pattern, String text) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate t = LocalDate.parse(text, formatter);
		return Date.from(t.atTime(0,0).atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 解析日期字符串为日期
	 * 
	 * @param text 日期字符串，格式为yyyy-MM-dd HH:mm:ss(2015-01-01 01:01:01)
	 * @return {@link Date}
	 */
	public static Date parseTimestamp(String text) {
		boolean hasTime = StringUtils.contains(text, ":");
		String t = hasTime ? text : text + " 00:00:00";
		LocalDateTime lt = LocalDateTime.parse(t, defaultTimestampFormat);
		return Date.from(lt.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 解析日期字符串为日期
	 * 
	 * @param pattern 日期格式
	 * @param text 日期字符串
	 * @return {@link Date}
	 */
	public static Date parseTimestamp(String pattern, String text) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		boolean hasTime = StringUtils.contains(text, ":");
		String t = hasTime ? text : text + " 00:00:00";
		LocalDateTime lt = LocalDateTime.parse(t, formatter);
		return Date.from(lt.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 一天有多少毫秒
	 * 
	 * @return
	 */
	public static long dayTimeMillis(){
		return dayTimeMillis;
	}
	
	/**
	 * 一分钟有多少毫秒
	 * 
	 * @return
	 */
	public static long minuteMillis(){
		return minuteMillis;
	}
	
	/**
	 * 得到首次运行延迟时间
	 * 
	 * @param now 现在时间毫秒
	 * @param startHour 开始运行小时
	 * @param startMinute 开始运行分钟
	 * @param startSecond 开始运行秒
	 * @return 毫秒
	 */
	public static long getDelay(long now, int startHour, int startMinute, int startSecond){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		calendar.set(Calendar.HOUR_OF_DAY, startHour);
		calendar.set(Calendar.MINUTE, startMinute);
		calendar.set(Calendar.SECOND, startSecond);
		calendar.set(Calendar.MILLISECOND, 0);
		long start = calendar.getTimeInMillis();
		
		return start > now ? (start - now) : (dayTimeMillis + start - now) ; 
	}
	
	/**
	 * 得到今天日期范围
	 * 
	 * @return array[0] 开始时间 array[1] 结束时间
	 */
	public static Date[] getTodayRange(){
		LocalDate local = LocalDate.now();
		Date from = toDate(local, 0, 0, 0);
		Date to = new Date(toDate(local.plus(1, ChronoUnit.DAYS), 0, 0, 0).getTime() -1);
		return new Date[]{from, to};
	}
	
	/**
	 * 得到指定日期中，星期范围
	 * 
	 * @return array[0] 星期开始时间 array[1] 星期结束时间
	 */
	public static Date[] getWeekRange(LocalDate local, int offset){
		int nowWeek = local.getDayOfWeek().getValue();
		LocalDate from = local.plusDays((offset * 7 + 1) - nowWeek);
		LocalDate to = local.plusDays((offset + 1) * 7 - nowWeek);
		
		return new Date[]{toDate(from, 0, 0, 0), toDate(to, 23, 59, 59)};
	}
	
	/**
	 * 转换为日期类型
	 * 
	 * @param local  日期
	 * @param hour   小时
	 * @param minute 分
	 * @param second 秒
	 * @return
	 */
	public static Date toDate(LocalDate local, int hour, int minute, int second){
		return Date.from(local.atTime(hour, minute, second).atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 得到指定时间星期
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		return getWeek(date.toInstant());
	}
	
	/**
	 * 得到指定时间星期
	 * 
	 * @param instant
	 * @return
	 */
	public static int getWeek(Instant instant){
		return instant.atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue();
	}

    /**
     * 得到添加天数日期
     *
     * @param date {@link Date}
     * @param days 添加天数
     * @return
     */
	public static Date plusDays(Date date, int days){
	    final ZoneId zoneId = ZoneId.systemDefault();
	    return Date.from(LocalDateTime.ofInstant(date.toInstant(), zoneId).plusDays(days).atZone(zoneId).toInstant());
    }

    /**
     * 得到添加分钟日期
     *
     * @param date {@link Date}
     * @param minutes 添加分钟
     * @return
     */
    public static Date plusMinutes(Date date, int minutes){
        final ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), zoneId).plusMinutes(minutes).atZone(zoneId).toInstant());
    }
}
