package com.xxf.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Description 日期工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 日期格式
     **/
    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static final String format(Object date) {
        return format(date, DATE_PATTERN.YYYY_MM_DD);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static final String format(Object date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null) {
            return format(date);
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static final String getDate() {
        return format(new Date());
    }

    /**
     * 获取日期时间
     *
     * @return
     */
    public static final String getDateTime() {
        return format(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期
     *
     * @param pattern
     * @return
     */
    public static final String getDateTime(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 日期计算
     *
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static final Date addDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取当年日期内到当月的日期列表
     *
     * @return
     */
    public static List<String> getYearMonthList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<String> list = new ArrayList<String>(); //保存日期集合
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        for (int i = 0; i <= month; i++) {
            c.set(Calendar.MONTH, i);
            list.add(sdf.format(c.getTime()));
        }
        return list;
    }

    /**
     * 获取当年的月列表
     *
     * @return
     */
    public static List<String> getAllYearMonthList(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<String> list = new ArrayList<String>(); //保存日期集合
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        for (int i = 0; i <= 11; i++) {
            c.set(Calendar.MONTH, i);
            list.add(sdf.format(c.getTime()));
        }
        return list;
    }

    /**
     * 计算 day 天后的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 取得指定日期格式的字符串
     *
     * @param date
     * @return String
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 格式化日期
     *
     * @return String
     */
    public static Date toDate(String str, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(str);
    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        if (w == 0)
            w = 7;
        return w;
    }

    //获取日期在一年中的第几周
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    //获取日期在本周中的第一天
    public static Date getWeekFistDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - (getWeekOfDate(date) - 1));
        return cal.getTime();
    }

    //获取日期在本周中的最后一天
    public static Date getWeekLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + (7 - getWeekOfDate(date)));
        return cal.getTime();
    }

    //获得日期区间内的年中周数和每周的日期区间
    public static Map<Integer, String> getWeekAndDaytoDay(Date startTime, Date endTime) {
        startTime = getWeekFistDay(startTime);
        endTime = getWeekLastDay(endTime);
        Map<Integer, String> map = new HashMap<>();
        for (; startTime.before(endTime); ) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
            Date end = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dayToday = sdf.format(startTime) + "-" + sdf.format(end);
            map.put(getWeek(startTime), dayToday);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
            startTime = cal.getTime();
        }
        return map;
    }

    public static Date mongoDateTran(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "" +
                "")));
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return date;
        }
    }

    //获取日期区间内的所有天 格式20181012
    public static List<String> getDates(Date startDate, Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        List<String> dates = new ArrayList<>();
        Date flagEndDate = addDay(endDate, 1);
        for (Date date = startDate; date.before(flagEndDate); date = addDay(date, 1)) {
            dates.add(simpleDateFormat.format(date));
        }
        return dates;
    }

    //获取日期的天数差，只适用于整天，不适用于带具体时分秒的
    public static int getDateInterval(String startTime, String endTime) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = simpleDateFormat.parse(startTime);
        Date endDate = simpleDateFormat.parse(endTime);

        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
    }

    //当天
    public static Date getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //查询当前日期的前多少天
    public static Date getBeforeDayByNum(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -num);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
