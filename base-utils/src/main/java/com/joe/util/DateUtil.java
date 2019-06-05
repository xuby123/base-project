package com.joe.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <dl>
 * <dt>DateUtil</dt>
 * <dd>Description: java8时间新特性 时间工具类</dd>
 * <dd>Copyright: Copyright (C) 2019</dd>
 * <dd>Company:</dd>
 * <dd>CreateDate: 2019-03-27</dd>
 * </dl>
 *
 * @author xby
 */
public class DateUtil {


    /**
     * 线程安全的
     */
    private static DateTimeFormatter dateTimeFormatter = null;


    public DateUtil() {
    }

    /**
     * <p>功能描述:dateToLocalDateTime java.util.Date 转成LocalDateTime</p>
     * <ul>
     * <li>@param date </li>
     * <li>@return java.time.LocalDateTime</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:06</li>
     * </ul>
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        //时间戳
        Instant instant = date.toInstant();
        //时区
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * <p>功能描述:dateToLocalDate Date转</p>
     * <ul>
     * <li>@param date </li>
     * <li>@return java.time.LocalDate</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:15</li>
     * </ul>
     */
    public static LocalDate dateToLocalDate(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.toLocalDate();
    }


    /**
     * <p>功能描述:dateToLocalTime dateToLocalTime</p>
     * <ul>
     * <li>@param date </li>
     * <li>@return java.time.LocalTime</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:18</li>
     * </ul>
     */
    public static LocalTime dateToLocalTime(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.toLocalTime();
    }


    /**
     * <p>功能描述:dateToString 把Date转换成指定类型的时间字符串</p >
     * <ul>
     * <li>@param date </li>
     * <li>@param format </li>
     * <li>@return java.lang.String</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:23</li>
     * </ul>
     */
    public static String dateToString(Date date, String format) {
        if (null != date && null != format) {
            LocalDateTime localDateTime = dateToLocalDateTime(date);
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(dateTimeFormatter);
        }
        return null;
    }


    /**
     * <p>功能描述:localDateTimeToDate localDateTime转Date</p>
     * <ul>
     * <li>@param localDateTime </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:10</li>
     * </ul>
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * <p>功能描述:localDateToDate localDate转Date</p>
     * <ul>
     * <li>@param localDate </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:51</li>
     * </ul>
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * <p>功能描述:getCurrentLocalDateTime 获取当前不含时区的日期</p >
     * <ul>
     * <li>@param  </li>
     * <li>@return java.time.LocalDateTime</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:52</li>
     * </ul>
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * <p>功能描述:getCurrentLocalDate 获取当前不含具体时间的日期</p>
     * <ul>
     * <li>@param  </li>
     * <li>@return java.time.LocalDate</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 15:47</li>
     * </ul>
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * <p>功能描述:getCurrentLocalTime 获取当前不含日期的时间</p>
     * <ul>
     * <li>@param  </li>
     * <li>@return java.time.LocalTime</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:19</li>
     * </ul>
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }


    /**
     * <p>功能描述:getCurrentYear date中获取年份</p>
     * <ul>
     * <li>@param date </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:24</li>
     * </ul>
     */
    public static int getYear(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        return localDate.getYear();
    }

    /**
     * <p>功能描述:getYear 获取年份</p>
     * <ul>
     * <li>@param localDate </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:32</li>
     * </ul>
     */
    public static int getYear(LocalDate localDate) {
        return localDate.getYear();
    }

    /**
     * <p>功能描述:getYear 获取年份</p>
     * <ul>
     * <li>@param localDateTime </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:33</li>
     * </ul>
     */
    public static int getYear(LocalDateTime localDateTime) {
        return localDateTime.getYear();
    }

    /**
     * <p>功能描述:getCurrentMonth 获取月份</p>
     * <ul>
     * <li>@param date </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:31</li>
     * </ul>
     */
    public static int getMonth(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        return localDate.getMonthValue();
    }

    /**
     * <p>功能描述:getMonth 获取月份</p>
     * <ul>
     * <li>@param localDate </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:35</li>
     * </ul>
     */
    public static int getMonth(LocalDate localDate) {
        return localDate.getMonthValue();
    }

    /**
     * <p>功能描述:getMonth 获取月份</p>
     * <ul>
     * <li>@param localDateTime </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:36</li>
     * </ul>
     */
    public static int getMonth(LocalDateTime localDateTime) {
        return localDateTime.getMonthValue();
    }

    /**
     * <p>功能描述:getDay 获取日</p>
     * <ul>
     * <li>@param date </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:43</li>
     * </ul>
     */
    public static int getDay(Date date) {
        LocalDate localDate = dateToLocalDate(date);
        return localDate.getDayOfMonth();
    }

    /**
     * <p>功能描述:getDay 获取日</p>
     * <ul>
     * <li>@param localDate </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:44</li>
     * </ul>
     */
    public static int getDay(LocalDate localDate) {
        return localDate.getDayOfMonth();
    }

    /**
     * <p>功能描述:getDay 获取日</p>
     * <ul>
     * <li>@param localDateTime </li>
     * <li>@return int</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:45</li>
     * </ul>
     */
    public static int getDay(LocalDateTime localDateTime) {
        return localDateTime.getDayOfMonth();
    }


    /**
     * <p>功能描述:getDayOfWeek 获取日期是星期几</p >
     * <ul>
     * <li>@param date </li>
     * <li>@return java.lang.Integer</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 19:40</li>
     * </ul>
     */
    public static Integer getDayOfWeek(Date date) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getDayOfWeek().getValue();
    }

    /**
     * <p>功能描述:getCustomDate 指定 年月日返回 Date</p>
     * <ul>
     * <li>@param year </li>
     * <li>@param month </li>
     * <li>@param day </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:59</li>
     * </ul>
     */
    public static Date getCustomDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDateToDate(localDate);
    }

    /**
     *<p>功能描述:getDateFormatter TODO</p >
     *<ul>
     *<li>@param dateString 比如 2014==04==12 01时06分09秒</li>
     *<li>@param dateFormatter 比如 yyyy==MM==dd HH时mm分ss秒 和dateString对应 </li>
     *<li>@return java.util.Date</li>
     *<li>@throws </li>
     *<li>@author xubiyu</li>
     *<li>@date 2019-03-27 20:59</li>
     *</ul>
    */
    public static Date getDateFormatter(String dateString, String dateFormatter) {
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatter);
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateTimeFormatter);
        return localDateTimeToDate(localDateTime);
    }

    /**
     *<p>功能描述:getCurrentTimeOfLong 获取当前时间</p >
     *<ul>
     *<li>@param  </li>
     *<li>@return java.lang.Long</li>
     *<li>@throws </li>
     *<li>@author xubiyu</li>
     *<li>@date 2019-03-27 21:09</li>
     *</ul>
    */
    public static Long getCurrentTimeOfLong(){
        //北京时间
      return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * <p>功能描述:compareDate 比较两个日期是否相等</p>
     * <ul>
     * <li>@param date1 </li>
     * <li>@param date2 </li>
     * <li>@return boolean</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 16:55</li>
     * </ul>
     */
    public static boolean equlasDate(Date date1, Date date2) {
        if (null != date1 && null != date2) {
            LocalDateTime localDateTime1 = dateToLocalDateTime(date1);
            LocalDateTime localDateTime2 = dateToLocalDateTime(date2);
            return localDateTime1.equals(localDateTime2);
        }
        return false;
    }

    /**
     * <p>功能描述:plusSeconds 获取几秒后的日期</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param seconds </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:48</li>
     * </ul>
     */
    public static Date plusSeconds(Date date, long seconds) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusSeconds(seconds));
    }

    /**
     * <p>功能描述:minusSeconds 获取几秒前的日期</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param seconds </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:50</li>
     * </ul>
     */
    public static Date minusSeconds(Date date, long seconds) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusSeconds(seconds));
    }

    /**
     * <p>功能描述:plusMinutes 获取几分钟后的日期</p >
     * <ul>
     * <li>@param date </li>
     * <li>@param minutes </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:31</li>
     * </ul>
     */
    public static Date plusMinutes(Date date, int minutes) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusMinutes(minutes));
    }


    /**
     * <p>功能描述:minusMinutes 获取几分钟之前的日期</p >
     * <ul>
     * <li>@param date </li>
     * <li>@param minutes </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:32</li>
     * </ul>
     */
    public static Date minusMinutes(Date date, int minutes) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusMinutes(minutes));
    }


    /**
     * <p>功能描述:addHour 计算增加几小时后的时间</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param hour </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:18</li>
     * </ul>
     */
    public static Date plusHour(Date date, int hour) {
        if (null == date) {
            return null;
        }
        LocalDateTime dateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(dateTime.plusHours(hour));
    }

    /**
     * <p>功能描述:minusHour 计算减少几个小时后的时间</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param hour </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:27</li>
     * </ul>
     */
    public static Date minusHour(Date date, int hour) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusHours(hour));
    }

    /**
     * <p>功能描述:plusDay 获取几天后日期</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param day </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:34</li>
     * </ul>
     */
    public static Date plusDay(Date date, int day) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusDays(day));
    }

    /**
     * <p>功能描述:minusDay 获取几天前的日期</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param day </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:36</li>
     * </ul>
     */
    public static Date minusDay(Date date, int day) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusDays(day));
    }


    /**
     * <p>功能描述:plusWeek 获取几周后的日期</p >
     * <ul>
     * <li>@param date </li>
     * <li>@param week </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:35</li>
     * </ul>
     */
    public static Date plusWeek(Date date, int week) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusWeeks(week));
    }

    /**
     * <p>功能描述:minusWeek 获取几周前的日期</p >
     * <ul>
     * <li>@param date </li>
     * <li>@param week </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:35</li>
     * </ul>
     */
    public static Date minusWeek(Date date, int week) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusWeeks(week));
    }

    /**
     * <p>功能描述:plusMonth 获取几个月后的日期</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param month </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:39</li>
     * </ul>
     */
    public static Date plusMonth(Date date, int month) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusMonths(month));
    }

    /**
     * <p>功能描述:minusMonth 获取几个月之前的</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param month </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:40</li>
     * </ul>
     */
    public static Date minusMonth(Date date, int month) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusMonths(month));
    }


    /**
     * <p>功能描述:plusYear 获取几年后的日期 </p>
     * <ul>
     * <li>@param date </li>
     * <li>@param year </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:44</li>
     * </ul>
     */
    public static Date plusYear(Date date, int year) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusYears(year));
    }

    /**
     * <p>功能描述:minusYear 获取几年前的日期</p>
     * <ul>
     * <li>@param date </li>
     * <li>@param year </li>
     * <li>@return java.util.Date</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/27 17:45</li>
     * </ul>
     */
    public static Date minusYear(Date date, int year) {
        if (null == date) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusYears(year));
    }




    /**
     * <p>功能描述:isLeapYear 判断是否是闰年</p >
     * <ul>
     * <li>@param date </li>
     * <li>@return boolean</li>
     * <li>@throws </li>
     * <li>@author xubiyu</li>
     * <li>@date 2019-03-27 20:45</li>
     * </ul>
     */
    public static boolean isLeapYear(Date date) {
        if (null == date) {
            return false;
        }
        LocalDate localDate = dateToLocalDate(date);
        return localDate.isLeapYear();
    }

    /**
     *<p>功能描述:isAfterNow 是否迟于当前时间</p >
     *<ul>
     *<li>@param date </li>
     *<li>@return boolean</li>
     *<li>@throws </li>
     *<li>@author xubiyu</li>
     *<li>@date 2019-03-27 20:54</li>
     *</ul>
    */
    public static boolean isAfterNow(Date date) {
        if(null == date){
            return false;
        }
        LocalDateTime nowLocalDateTime = getCurrentLocalDateTime();
        LocalDateTime localDateTime =dateToLocalDateTime(date);
        return localDateTime.isAfter(nowLocalDateTime);
    }

    /**
     *<p>功能描述:isAfterNow 是否早于当前时间</p >
     *<ul>
     *<li>@param date </li>
     *<li>@return boolean</li>
     *<li>@throws </li>
     *<li>@author xubiyu</li>
     *<li>@date 2019-03-27 20:54</li>
     *</ul>
     */
    public static boolean isBeforNow(Date date) {
        if(null == date){
            return false;
        }
        LocalDateTime nowLocalDateTime = getCurrentLocalDateTime();
        LocalDateTime localDateTime =dateToLocalDateTime(date);
        return localDateTime.isBefore(nowLocalDateTime);
    }


    /**
     *<p>功能描述:compare 两个时间比较 date1大于date2 反之 date1小于date2 </p >
     *<ul>
     *<li>@param date1 </li>
     *<li>@param date2 </li>
     *<li>@return int</li>
     *<li>@throws </li>
     *<li>@author xubiyu</li>
     *<li>@date 2019-03-27 21:04</li>
     *</ul>
    */
    public static int compare(Date date1,Date date2){
        LocalDateTime nowLocalDateTime1 = dateToLocalDateTime(date1);
        LocalDateTime nowLocalDateTime2 = dateToLocalDateTime(date2);
        return nowLocalDateTime1.compareTo(nowLocalDateTime2);
    }


    /**
     *<p>功能描述:convertTimeToString Long 类型的时间戳转Date</p>
     *<ul>
     *<li>@param time </li>
     *<li>@return java.util.Date</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2019/5/22 16:42</li>
     *</ul>
    */
    public static Date convertTimeToString(Long time){
        if(ToolUtil.isEmpty(time)){
            return null;
        }
        return localDateTimeToDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.systemDefault()));
    }



}