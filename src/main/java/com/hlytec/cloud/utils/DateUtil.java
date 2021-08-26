package com.hlytec.cloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: DateUtil 日期工具类，包含常用的日期的转化和计算
 * @author: zero
 * @date: 2021/4/16 15:25
 */
public final class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {}

    /**
     * 格式化日期
     *
     * @param date date
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }


    /**
     * 格式化时间
     *
     * @param date date
     * @return HH:mm:ss
     */
    public static String getFormatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(date);
    }

    /**
     * 格式化日期及时间
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        return format.format(date);
    }

    /**
     * 格式化日期
     *
     * @param localDate localDate
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    /**
     * 格式化时间
     *
     * @param localDateTime localDateTime
     * @return HH:mm:ss
     */
    public static String getFormatTime(LocalDateTime localDateTime) {
        return localDateTime.format(TIME_FORMATTER);
    }

    /**
     * 格式化日期及时间
     *
     * @param localDateTime localDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */

    public static String getFormatDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 日期时间字符串转LocalDateTime
     *
     * @param dateStr dateStr
     * @return LocalDateTime
     */
    public static LocalDateTime dateStrToLocalDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER);
    }

    /**
     * 日期字符串转LocalDate
     *
     * @param dateStr dateStr
     * @return LocalDate
     */
    public static LocalDate dateStrToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 日期字符串转Date
     *
     * @param dateStr dateStr
     * @return Date
     */
    public static Date dateStrToDate(String dateStr) {
        return Date.from(LocalDate.parse(dateStr, DATE_FORMATTER).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期时间字符串转Date
     *
     * @param dateStr dateStr
     * @return Date
     */
    public static Date dateTimeStrToDate(String dateStr) {
        return Date.from(LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转Date
     *
     * @param localDate localDate
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime localDateTime
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转LocalDate
     *
     * @param date date
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 计算两个日期时间差
     *
     * @param start yyyy-MM-dd HH:mm:ss
     * @param end   yyyy-MM-dd HH:mm:ss
     * @return long
     */
    public static long dateDifference(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date startTime = format.parse(start);

            Date endTime = format.parse(end);
            return endTime.getTime() - startTime.getTime();

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return -1;
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param start start
     * @param end   end
     * @return long
     */
    public static long calculationDays(String start, String end) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date fromDate = simpleFormat.parse(start);

            Date toDate = simpleFormat.parse(end);
            long from = fromDate.getTime();
            long to = toDate.getTime();
            long days = (int) ((to - from) / (1000 * 60 * 60 * 24));
            return days;

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return -1;
    }

    /**
     * 计算小时差
     *
     * @param start start
     * @param end   end
     * @return long
     */
    public static long calculationHours(String start, String end) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date fromDate = simpleFormat.parse(start);

            Date toDate = simpleFormat.parse(end);
            long from = fromDate.getTime();
            long to = toDate.getTime();
            long hours = (int) ((to - from) / (1000 * 60 * 60));
            return hours;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 计算分钟差
     *
     * @param start start
     * @param end   end
     * @return long
     */
    public static long calculationMinutes(String start, String end) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date fromDate = simpleFormat.parse(start);

            Date toDate = simpleFormat.parse(end);
            long from = fromDate.getTime();
            long to = toDate.getTime();
            long minutes = (int) ((to - from) / (1000 * 60));
            return minutes;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 计算两个日期之间的秒数差
     *
     * @param start start
     * @param end   end
     * @return long
     */
    public static long calculationSecond(String start, String end) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date fromDate = simpleFormat.parse(start);

            Date toDate = simpleFormat.parse(end);
            long from = fromDate.getTime();
            long to = toDate.getTime();
            long seconds = (int) ((to - from) / 1000);
            return seconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取两个日期的差 field参数为ChronoUnit.*
     *
     * @param startTime startTime
     * @param endTime   endTime
     * @param field     单位(年月日时分秒)
     * @return long
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     *
     * @param time   time
     * @param number number
     * @param field  field
     * @return LocalDateTime
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     *
     * @param time   time
     * @param number number
     * @param field  field
     * @return LocalDateTime
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /***
     * 根据field不同加减不同值
     *@param date date
     *@param field Calendar.YEAR
     *@param number 1000/-1000
     *
     */
    public static Date calculationDate(Date date, int field, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, number);
        return calendar.getTime();
    }

    /**
     * 比较两个日期先后
     *
     * @param firstDate  firstDate
     * @param secondDate secondDate
     * @return boolean
     */
    public static boolean compareDate(Date firstDate, Date secondDate) {
        return firstDate.getTime() > secondDate.getTime();
    }

    /**
     * 比较第一个日期是否大于第二个日期
     *
     * @param firstDate  第一个日期
     * @param secondDate 第二个日期
     * @return true-大于;false-不大于
     */
    public boolean localDateIsBefore(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isBefore(secondDate);
    }

    /**
     * 获取当前时间毫秒数
     *
     * @return Long
     */
    public static Long getCurrentMills() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
