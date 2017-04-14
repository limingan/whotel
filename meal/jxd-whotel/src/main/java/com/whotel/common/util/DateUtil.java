package com.whotel.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public abstract class DateUtil {
	// 秒、分、时、天的毫秒数
	public static final long SEC_MILLIS = 1000L;
	public static final long MIN_MILLIS = 60 * SEC_MILLIS;
	public static final long HOUR_MILLIS = 60 * MIN_MILLIS;
	public static final long DAY_MILLIS = 24 * HOUR_MILLIS;
	// 日期/时间格式
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");

	private static final String YYYY = "yyyy";
	private static final String MM = "MM";
	private static final String DD = "dd";
	private static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final String YYYY_MM = "yyyy-MM";
	private static final String HH_MM_SS = "HH:mm:ss";
	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private DateUtil() {}
	/**
	 * Get current date
	 * 
	 * @return <code>Date</code>
	 */
	public static synchronized Date getCurrDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * Get current date and convert to a String in yyyy-MM-dd pattern.
	 * 
	 * @return <code>String</code> in yyyy-MM-dd pattern
	 */
	public static final String getCurrDateStr() {
		return format(getCurrDate(), YYYY_MM_DD);
	}

	/**
	 * Get current time and convert to a String in HH:mm:ss pattern.
	 * 
	 * @return <code>String</code> in hh:mm:ss pattern
	 */
	public static final String getCurrTimeStr() {
		return format(getCurrDate(), HH_MM_SS);
	}

	/**
	 * Get current date and time and convert to a String in yyyy－MM－dd hh:mm:ss pattern.
	 * 
	 * @return <code>String</code> in yyyy-MM-dd hh:mm:ss pattern
	 */
	public static final String getCurrDateTimeStr() {
		return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * Get current year and convert to a String in yyyy pattern.
	 * 
	 * @return Year
	 */
	public static final String getYear() {
		return format(getCurrDate(), YYYY);
	}

	/**
	 * Get current month and convert to a String in MM pattern.
	 * 
	 * @return Month
	 */
	public static final String getMonth() {
		return format(getCurrDate(), MM);
	}

	/**
	 * Get current day and convert to a String in dd pattern.
	 * 
	 * @return Day
	 */
	public static final String getDay() {
		return format(getCurrDate(), DD);
	}

	/**
	 * Validate date string
	 * 
	 * @param strDate
	 * @param pattern
	 * @return boolean
	 */
	public static final boolean isDate(String strDate, String pattern) {
		return parseDate(strDate, pattern) != null;
	}

	/**
	 * 判断给定字符串是否为特定格式年份（格式：yyyy）数据
	 * 
	 * @param strDate 要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static final boolean isYYYY(String strDate) {
		return parseDate(strDate, YYYY) != null;
	}

	/**
	 * 判断给定字符串是否为特定格式年份（格式：yyyy-MM）数据
	 * 
	 * @param strDate 要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static final boolean isYYYY_MM(String strDate) {
		return parseDate(strDate, YYYY_MM) != null;
	}

	/**
	 * 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据
	 * 
	 * @param strDate 要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static final boolean isYYYY_MM_DD(String strDate) {
		return parseDate(strDate, YYYY_MM_DD) != null;
	}

	/**
	 * 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据
	 * 
	 * @param strDate 要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static final boolean isYYYY_MM_DD_HH_MM_SS(String strDate) {
		return parseDate(strDate, YYYY_MM_DD_HH_MM_SS) != null;
	}

	/**
	 * 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据
	 * 
	 * @param strDate 要判断的日期
	 * @return true 如果是，否则返回false
	 */
	public static final boolean isHH_MM_SS(String strDate) {
		return parseDate(strDate, HH_MM_SS) != null;
	}

	/**
	 * 获取给定日前的后intevalDay天的日期
	 * 
	 * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
	 * @param intevalDays 间隔天数
	 * @return 计算后的日期
	 */
	public static final String getNextDate(String refenceDate, int intevalDays) {
		try {
			return getNextDate(parseDate(refenceDate, YYYY_MM_DD), intevalDays);
		} catch (Exception ee) {
			return null;
		}
	}

	/**
	 * 获取给定日前的后intevalDay天的日期
	 * 
	 * @param refenceDate 给定日期
	 * @param intevalDays 间隔天数
	 * @return String 计算后的日期
	 */
	public static final String getNextDate(Date refenceDate, int intevalDays) {
		try {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(refenceDate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + intevalDays);
			return format(calendar.getTime(), YYYY_MM_DD);
		} catch (Exception ee) {
			return null;
		}
	}

	/**
	 * 返回两个日期间的间隔天数
	 * 
	 * @param startDate represent a date value
	 * @param endDate represent a date value
	 * @return 两个日期之间的间隔天数，正数表示起始日期在结束日期之后，0表示两个日期同天，负数表示起始日期在结束日期之前
	 */
	public static final long getIntevalDays(String startDate, String endDate) {
		try {
			return getIntevalDays(parseDate(startDate, YYYY_MM_DD), parseDate(endDate, YYYY_MM_DD));
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * 返回两个日期间的间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 两个日期之间的间隔天数，正数表示起始日期在结束日期之后，0表示两个日期同天，负数表示起始日期在结束日期之前
	 */
	public static final long getIntevalDays(Date startDate, Date endDate) {
		try {
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			java.util.Calendar endCalendar = java.util.Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
			return (diff / DAY_MILLIS);
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * 求当前日期和指定字符串日期的相差天数
	 * 
	 * @param startDate
	 * @return 两个日期之间的间隔天数，正数表示起始日期在结束日期之后，0表示两个日期同天，负数表示起始日期在结束日期之前
	 */
	public static final long getTodayIntevalDays(String startDate) {
		try {
			Date currentDate = new Date();
			Date theDate = parseDate(startDate);
			long days = (currentDate.getTime() - theDate.getTime()) / DAY_MILLIS;
			return days;
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * 获得指定年月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static final String getLastDayOfMonth(String year, String month) throws ParseException {
		String LastDay = "";
		Calendar cal = Calendar.getInstance();
		Date date_;
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-14");
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		date_ = cal.getTime();
		LastDay = new SimpleDateFormat("yyyy-MM-dd").format(date_);
		return LastDay;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return 输入的年、月、日是否是有效日期
	 */
	public static final boolean isValid(int year, int month, int day) {
		if (month > 0 && month < 13 && day > 0 && day < 32) {
			// month of calendar is 0-based
			int mon = month - 1;
			Calendar calendar = new GregorianCalendar(year, mon, day);
			if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == mon
					&& calendar.get(Calendar.DAY_OF_MONTH) == day) {
				return true;
			}
		}
		return false;
	}

	private static final Calendar convert(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回指定年数位移后的日期
	 * 
	 * @param date 基准日期
	 * @param offset 负数回到过去，正数去到未来
	 * @return
	 */
	public static final Date yearOffset(Date date, int offset) {
		return offsetDate(date, Calendar.YEAR, offset);
	}

	/**
	 * 返回指定月数位移后的日期
	 * 
	 * @param date 基准日期
	 * @param offset 负数回到过去，正数去到未来
	 * @return
	 */
	public static final Date monthOffset(Date date, int offset) {
		return offsetDate(date, Calendar.MONTH, offset);
	}

	/**
	 * 返回指定天数位移后的日期
	 * 
	 * @param date 基准日期
	 * @param offset 负数回到过去，正数去到未来
	 * @return
	 */
	public static final Date dayOffset(Date date, int offset) {
		return offsetDate(date, Calendar.DATE, offset);
	}

	/**
	 * 返回指定日期相应位移后的日期
	 * 
	 * @param date 参考日期
	 * @param field 位移单位，见 {@link Calendar}
	 * @param offset 位移数量，正数表示之后的时间，负数表示之前的时间
	 * @return 位移后的日期
	 */
	public static final Date offsetDate(Date date, int field, int offset) {
		Calendar calendar = convert(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期所在月份的月初日期
	 * 
	 * @param date
	 * @return
	 */
	public static final Date firstDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期所在月份的月末日期
	 * 
	 * @param date
	 * @return
	 */
	public static final Date lastDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * <p>
	 * Checks if two calendar objects are on the same day ignoring time.
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return
	 * false.
	 * </p>
	 * 
	 * @param startDate the first calendar, not altered, not null
	 * @param endDate the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException if either calendar is <code>null</code>
	 * @since 2.1
	 */
	public static final boolean isSameDay(Calendar startDate, Calendar endDate) {
		
		if (startDate == null && endDate == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (startDate == null || endDate == null) {
			return false;
		}
		return (startDate.get(Calendar.ERA) == endDate.get(Calendar.ERA)
				&& startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) && startDate.get(Calendar.DAY_OF_YEAR) == endDate
				.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * Check whether the range between startDate and endDate exceed months
	 * 
	 * <pre>
	 * DateUtils.isInMonth(2012-01-31, 2012-02-28, 2) = false
	 * DateUtils.isInMonth(2012-01-31, 2012-03-30, 2) = false
	 * DateUtils.isInMonth(2012-01-31, 2012-03-31, 2) = true
	 * </pre>
	 */
	public static final boolean isInMonth(Date startDate, Date endDate, int months) {
		if (startDate == null && endDate == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (startDate == null || endDate == null) {
			return false;
		}
		Date tmpDate = DateUtils.addMonths(startDate, months);
		return DateUtil.getIntevalDays(tmpDate, endDate) >= 0;
	}

	/**
	 * Check whether two date are in same day
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static final boolean isSameDay(Date date1, Date date2) {
		if ((date1 != null && date2 == null) || (date1 == null && date2 != null)) {
			return false;
		}
		return DateUtils.isSameDay(date1, date2);
	}

	/**
	 * Parse date from string
	 * 
	 * @param date the string represent a date value
	 * @return null if string is not in yyyy-MM-dd format
	 */
	public static final Date parseDate(String date) {
		return parseDate(date, DATE_PATTERN);
	}

	/**
	 * Parse datetime from string
	 * 
	 * @param datetime the string represent a datetime value
	 * @return null if string is not in yyyy-MM-dd HH:mm:ss format
	 */
	public static final Date parseDatetime(String datetime) {
		return parseDate(datetime, DATETIME_PATTERN);
	}

	/**
	 * Parse date from string according pattern
	 * 
	 * @param date the string represent a date value
	 * @param pattern the string represent a date format
	 * @return null if string is not in pattern or pattern is not a valid value
	 */
	public static final Date parseDate(String date, String pattern) {
		if (StringUtils.isBlank(date))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			//e.printStackTrace();
			return null;
		}
	}

	public static final long getHours(Date date) {
		return getHours(date, DEFAULT_TIME_ZONE);
	}

	public static final long getHours(Date date, TimeZone timeZone) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static final long getMinutes(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * Format a Date to String in yyyy-MM-dd pattern.
	 * 
	 * @param Date
	 * @return String
	 */
	public static final String formatDate(Date date) {
		if (date == null)
			return null;
		return format(date, DATE_PATTERN);
	}

	/**
	 * Format a Date to String in yyyy-MM-dd mm:HH:ss pattern.
	 * 
	 * @param date
	 * @return
	 */
	public static final String formatDatetime(Date date) {
		return format(date, DATETIME_PATTERN);
	}

	public static final Date getDayBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获得指定日期的起始时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date getStartTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得指定日期的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static final Date getEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 获得当天的起始时间
	 * 
	 * @return
	 */
	public static final Date today() {
		return getStartTime(new Date());
	}
	
	/**
	 * 获得指定日期的月初时间
	 */
	public static final Date getStartMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获得指定日期的月末时间
	 */
	public static final Date getEndMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	/**
	 * 获得指定日期的年初时间
	 */
	public static final Date getStartYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获得指定日期的年末时间
	 */
	public static final Date getEndYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), 11, cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 日期格式化为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String format(Date date, String pattern) {
		if (date == null)
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 日期格式化为字符串，yyyy-MM-dd HH:mm:ss
	 * 
	 * @param calendar
	 * @return
	 */
	public static final String format(Calendar calendar) {
		return format(calendar, DATETIME_PATTERN);
	}

	public static final String format(Calendar calendar, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(calendar.getTime());
	}

}
