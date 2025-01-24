package com.auth.core.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type Date utils v 8.
 */
public class DateUtilsV8 {
//    version 20241016

    /**
     * The constant AM.
     */
    public static final String AM = " aa";
    /**
     * The constant ZONE.
     */
    public static final String ZONE = " Z"; //TimeZone.getTimeZone("GMT-07"), TimeZone.getTimeZone("Europe/Berlin")...
    /**
     * The constant TIME_ZONE.
     */
    public static final String TIME_ZONE = " aaa XXX";

    /**
     * The constant regex00.
     */
    public static final String regex00 = "yyyy/MM/dd";
    /**
     * The constant regex01.
     */
    public static final String regex01 = regex00 + " HH:mm:ss";
    /**
     * The constant regex02.
     */
    public static final String regex02 = regex01 + AM;
    /**
     * The constant regex03.
     */
    public static final String regex03 = regex01 + ZONE;
    /**
     * The constant regex04.
     */
    public static final String regex04 = regex01 + TIME_ZONE;
    /**
     * The constant regex05.
     */
    public static final String regex05 = regex00 + " HH:mm:ss.SSS";


    /**
     * The constant regex20.
     */
    public static final String regex20 = "dd/MM/yyyy";
    /**
     * The constant regex21.
     */
    public static final String regex21 = regex20 + " HH:mm:ss";
    /**
     * The constant regex22.
     */
    public static final String regex22 = regex21 + AM;
    /**
     * The constant regex23.
     */
    public static final String regex23 = regex21 + ZONE;
    /**
     * The constant regex24.
     */
    public static final String regex24 = regex21 + TIME_ZONE;

    /**
     * The constant regex10.
     */
    public static final String regex10 = "yyyy_MM_dd";
    /**
     * The constant regex11.
     */
    public static final String regex11 = regex10 + "_HH_mm_ss";
    /**
     * The constant regex12.
     */
    public static final String regex12 = regex11 + AM;
    /**
     * The constant regex13.
     */
    public static final String regex13 = regex11 + ZONE;
    /**
     * The constant regex14.
     */
    public static final String regex14 = regex11 + TIME_ZONE;

    /**
     * The constant regex60.
     */
    public static final String regex60 = "dd_MM_yyyy";
    /**
     * The constant regex61.
     */
    public static final String regex61 = regex60 + "_HH_mm_ss";
    /**
     * The constant regex62.
     */
    public static final String regex62 = regex11 + AM;
    /**
     * The constant regex63.
     */
    public static final String regex63 = regex11 + ZONE;
    /**
     * The constant regex64.
     */
    public static final String regex64 = regex11 + TIME_ZONE;

    /**
     * The constant regex30.
     */
    public static final String regex30 = "yyyy-MM-dd";
    /**
     * The constant regex31.
     */
    public static final String regex31 = regex30 + " HH:mm:ss";
    /**
     * The constant regex32.
     */
    public static final String regex32 = regex31 + AM;
    /**
     * The constant regex33.
     */
    public static final String regex33 = regex31 + ZONE;
    /**
     * The constant regex34.
     */
    public static final String regex34 = regex31 + TIME_ZONE;
    /**
     * The constant regex35.
     */
    public static final String regex35 = regex30 + "'T'HH:mm:ss.SSS";


    /**
     * The constant regex50.
     */
    public static final String regex50 = "dd-MM-yyyy";
    /**
     * The constant regex51.
     */
    public static final String regex51 = regex50 + " HH:mm:ss";
    /**
     * The constant regex52.
     */
    public static final String regex52 = regex51 + AM;
    /**
     * The constant regex53.
     */
    public static final String regex53 = regex51 + ZONE;
    /**
     * The constant regex54.
     */
    public static final String regex54 = regex51 + TIME_ZONE;

    /**
     * The constant regex40.
     */
    public static final String regex40 = "yyyyMMdd";
    /**
     * The constant regex41.
     */
    public static final String regex41 = regex40 + "HHmmss";
    /**
     * The constant regex42.
     */
    public static final String regex42 = regex41 + AM;
    /**
     * The constant regex43.
     */
    public static final String regex43 = regex41 + ZONE;
    /**
     * The constant regex44.
     */
    public static final String regex44 = regex41 + TIME_ZONE;
    /**
     * The constant regex45.
     */
    public static final String regex45 = regex40 + "HHmmssSSS";

    /**
     * The constant defaultDate.
     */
    public static final Date defaultDate = convertStringToDate("2018/01/01", regex00);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        Date input = new Date();
        System.out.println("input: " + input);
        Date ouput = convertDatePlusByField(input, Calendar.DAY_OF_MONTH, 2);
        System.out.println("ouput: " + ouput);
    }
    //    ***********************************************************************************************

    /**
     * Gets sysdate.
     *
     * @param numberDate the number date
     * @return the sysdate
     */
    public static Date getSysdate(int numberDate) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numberDate);
        return c.getTime();
    }

    /**
     * Gets sys date time local.
     *
     * @return the sys date time local
     */
    public static LocalDateTime getSysDateTimeLocal() {
        return LocalDateTime.now();
    }

    /**
     * Gets sys date time local.
     *
     * @param numberDate the number date
     * @return the sys date time local
     */
    public static LocalDateTime getSysDateTimeLocal(int numberDate) {
        return LocalDateTime.now().plusDays(numberDate);
    }

    /**
     * Gets sys date local.
     *
     * @return the sys date local
     */
    public static LocalDate getSysDateLocal() {
        return LocalDate.now();
    }

    /**
     * Gets sys date local.
     *
     * @param numberDate the number date
     * @return the sys date local
     */
    public static LocalDate getSysDateLocal(int numberDate) {
        return LocalDate.now().plusDays(numberDate);
    }

    /**
     * Convert date to string string.
     *
     * @param date  the date
     * @param regex the regex
     * @return the string
     */
    public static String convertDateToString(Date date, String regex) {
        return new SimpleDateFormat(regex).format(date);
    }

    /**
     * Convert string to date date.
     *
     * @param date  the date
     * @param regex the regex
     * @return the date
     */
    public static Date convertStringToDate(String date, String regex) {
        Date res = null;
        try {
            res = new SimpleDateFormat(regex).parse(date);
        } catch (ParseException e) {

        }
        return res;
    }

    /**
     * Convert date to string utc time string.
     *
     * @param date  the date
     * @param regex the regex
     * @return the string
     */
    public static String convertDateToStringUTCTime(Date date, String regex) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    public static Date convertStringToDateUTCTime(String date, String regex) {
        Date res = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            res = simpleDateFormat.parse(date);
        } catch (ParseException e) {
        }
        return res;
    }

    /********************
     * 1 : a > b
     * -1 : a < b
     * 0 : a = b
     * @param a the a
     * @param b the b
     * @return the int
     */
    public static int conpareDateToDate(Date a, Date b) {
        return a.compareTo(b);
    }

    /**
     * Gets value date.
     *
     * @param date the date
     * @return the value date
     */
    public static Map<String, Integer> getValueDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Map<String, Integer> res = new HashMap<>();
        res.put("YEAR", cal.get(Calendar.YEAR));
        res.put("MONTH", cal.get(Calendar.MONTH));
        res.put("DATE", cal.get(Calendar.DATE));
        res.put("DAY_OF_WEEK", cal.get(Calendar.DAY_OF_WEEK));
        res.put("HOUR_OF_DAY", cal.get(Calendar.HOUR_OF_DAY));
        res.put("MINUTE", cal.get(Calendar.MINUTE));
        res.put("SECOND", cal.get(Calendar.SECOND));
        return res;
    }

    /**
     * Gets current timestamp.
     *
     * @return the current timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Convert timestamp to date date.
     *
     * @param stamp the stamp
     * @return the date
     */
    public static Date convertTimestampToDate(Timestamp stamp) {
        if (stamp == null) return null;
        return new Date(stamp.getTime());
    }

    /**
     * Convert timestamp to date sql java . sql . date.
     *
     * @param stamp the stamp
     * @return the java . sql . date
     */
    public static java.sql.Date convertTimestampToDateSql(Timestamp stamp) {
        if (stamp == null) return null;
        return new java.sql.Date(stamp.getTime());
    }

    /**
     * Convert date to timestamp timestamp.
     *
     * @param date the date
     * @return the timestamp
     */
    public static Timestamp convertDateToTimestamp(Date date) {
        if (date == null) return null;
        return new Timestamp(date.getTime());
    }


    /**
     * Converte string to date time zone date.
     *
     * @param str   the str
     * @param regex the regex
     * @param tz    the tz
     * @return the date
     */
    /*
     * return Date current zone
     * */
    public static Date converteStringToDateTimeZone(String str, String regex, TimeZone tz) {
        SimpleDateFormat sdf = new SimpleDateFormat(regex);
        sdf.setTimeZone(tz);
        Date res = null;
        try {
            res = sdf.parse(str);
        } catch (ParseException e) {
        }
        return res;
    }

    /**
     * Convert string time zone to date date.
     *
     * @param strDate the str date
     * @param regex   the regex
     * @return the date
     */
    /*
     * return Date current zone
     * */
    public static Date convertStringTimeZoneToDate(String strDate, String regex) {
        DateFormat parseFormat = new SimpleDateFormat(regex);
        Date dt = null;
        try {
            dt = parseFormat.parse(strDate);
        } catch (ParseException e) {
        }
        return dt;
    }

    /**
     * Compare fromdate todate boolean.
     *
     * @param fromDateLocal the from date local
     * @param toDateLocal   the to date local
     * @param date          the date
     * @return the boolean
     */
//    return false if number of days from FromDate to ToDate is less than "date"
    public static boolean compareFromdateTodate(LocalDate fromDateLocal, LocalDate toDateLocal, int date) {
        return toDateLocal.isAfter(fromDateLocal.plusDays(date));
    }

    /**
     * Compare fromdate time todate time boolean.
     *
     * @param fromDateLocal the from date local
     * @param toDateLocal   the to date local
     * @param date          the date
     * @return the boolean
     */
//    return false if number of days from FromDate to ToDate is less than "date"
    public static boolean compareFromdateTimeTodateTime(LocalDateTime fromDateLocal, LocalDateTime toDateLocal, int date) {
        return toDateLocal.isAfter(fromDateLocal.plusDays(date).plusNanos(900000000));
    }

    /**
     * Convert local date time to string string.
     *
     * @param dateTimeLocal the date time local
     * @param regex         the regex
     * @return the string
     */
    public static String convertLocalDateTimeToString(LocalDateTime dateTimeLocal, String regex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
        return dateTimeLocal.format(formatter);
    }

    /**
     * Convert local date time to date date.
     *
     * @param dateTimeLocal the date time local
     * @return the date
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime dateTimeLocal) {
        String dateTimeLocalString = dateTimeLocal.format(DateTimeFormatter.ofPattern(regex31));
        return convertStringToDate(dateTimeLocalString, regex31);
    }

    /**
     * Convert string to local date local date.
     *
     * @param dateLocal the date local
     * @param regex     the regex
     * @return the local date
     */
    public static LocalDate convertStringToLocalDate(String dateLocal, String regex) {
        if (regex35.equals(regex)) {
            dateLocal = dateLocal.substring(0, dateLocal.length() - 6);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
        LocalDate.parse(dateLocal, formatter);
        return LocalDate.parse(dateLocal, formatter);
    }

    /**
     * Convert local date to string string.
     *
     * @param dateLocal the date local
     * @param regex     the regex
     * @return the string
     */
    public static String convertLocalDateToString(LocalDate dateLocal, String regex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
        return dateLocal.format(formatter);
    }

    /**
     * Convert local date to date date.
     *
     * @param dateLocal the date local
     * @return the date
     */
    public static Date convertLocalDateToDate(LocalDate dateLocal) {
        String dateLocalString = dateLocal.format(DateTimeFormatter.ofPattern(regex30));
        return convertStringToDate(dateLocalString, regex30);
    }

    //    ***********************************************************************************************

    /**
     * Gets start local date of week.
     *
     * @param fromDate the from date
     * @return the start local date of week
     */
    public static LocalDate getStartLocalDateOfWeek(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfWeek().getValue() + 1);
    }

    /**
     * Gets end local date of week.
     *
     * @param fromDate the from date
     * @return the end local date of week
     */
    public static LocalDate getEndLocalDateOfWeek(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfWeek().getValue() + 7);
    }

    /**
     * Gets start local date of month.
     *
     * @param fromDate the from date
     * @return the start local date of month
     */
    public static LocalDate getStartLocalDateOfMonth(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfMonth() + 1);
    }

    /**
     * Gets end local date of month.
     *
     * @param fromDate the from date
     * @return the end local date of month
     */
    public static LocalDate getEndLocalDateOfMonth(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfMonth() + fromDate.lengthOfMonth());
    }

    /**
     * Gets start local date of quater.
     *
     * @param fromDate the from date
     * @return the start local date of quater
     */
    public static LocalDate getStartLocalDateOfQuater(LocalDate fromDate) {
        int month = fromDate.getMonth().getValue();
        int dis = 0;
        if (month % 3 != 0) {
            dis = ((month / 3) * 3 + 1) - month;
        } else {
            dis = -2;
        }
        LocalDate localDateTemp = fromDate.plusMonths(dis);
        return getStartLocalDateOfMonth(localDateTemp);
    }

    /**
     * Gets end local date of quater.
     *
     * @param fromDate the from date
     * @return the end local date of quater
     */
    public static LocalDate getEndLocalDateOfQuater(LocalDate fromDate) {
        int month = fromDate.getMonth().getValue();
        int dis = 0;
        if (month % 3 != 0) {
            dis = ((month / 3) * 3 + 3) - month;
        } else {
            dis = 0;
        }
        LocalDate localDateTemp = fromDate.plusMonths(dis);
        return getEndLocalDateOfMonth(localDateTemp);
    }

    /**
     * Gets start local date of year.
     *
     * @param fromDate the from date
     * @return the start local date of year
     */
    public static LocalDate getStartLocalDateOfYear(LocalDate fromDate) {
        LocalDate localDateTemp = fromDate.plusMonths(-(fromDate.getMonth().getValue() - 1));
        return getStartLocalDateOfMonth(localDateTemp);
    }

    /**
     * Gets end local date of year.
     *
     * @param fromDate the from date
     * @return the end local date of year
     */
    public static LocalDate getEndLocalDateOfYear(LocalDate fromDate) {
        LocalDate localDateTemp = fromDate.plusMonths(-(fromDate.getMonth().getValue() - 1) + 11);
        return getEndLocalDateOfMonth(localDateTemp);
    }


    /**
     * Gets start date of day.
     *
     * @param date the date
     * @return the start date of day
     */
//    ***********************************************************************************************
    public static Date getStartDateOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
    }


    /**
     * Gets start date of week.
     *
     * @param fromDate the from date
     * @return the start date of week
     */
    public static Date getStartDateOfWeek(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return cal.getTime();
    }

    /**
     * Gets end date of week.
     *
     * @param fromDate the from date
     * @return the end date of week
     */
    public static Date getEndDateOfWeek(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }

    /**
     * Gets start date of month.
     *
     * @param fromDate the from date
     * @return the start date of month
     */
    public static Date getStartDateOfMonth(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets end date of month.
     *
     * @param fromDate the from date
     * @return the end date of month
     */
    public static Date getEndDateOfMonth(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.getTime();
    }

    /**
     * Gets start date of quater.
     *
     * @param fromDate the from date
     * @return the start date of quater
     */
    public static Date getStartDateOfQuater(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        int currentMonths = cal.get(Calendar.MONTH);
        int months = 3 * (currentMonths / 3);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, months);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets end date of quater.
     *
     * @param fromDate the from date
     * @return the end date of quater
     */
    public static Date getEndDateOfQuater(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        int currentMonths = cal.get(Calendar.MONTH);
        int months = 3 * (currentMonths / 3) + 3;

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, months);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.getTime();
    }

    /**
     * Gets start date of year.
     *
     * @param fromDate the from date
     * @return the start date of year
     */
    public static Date getStartDateOfYear(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * Gets end date of year.
     *
     * @param fromDate the from date
     * @return the end date of year
     */
    public static Date getEndDateOfYear(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        return cal.getTime();
    }

    //    ***********************************************************************************************
    // plus

    /**
     * Convert date plus by field date.
     *
     * @param date      the date
     * @param field     the field
     * @param fieldDiff the field diff
     * @return the date
     */
    public static Date convertDatePlusByField(Date date, int field, int fieldDiff) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(field, cal.get(field) + fieldDiff);
        return cal.getTime();
    }


    public static Date getStartDateOfCurrentDay(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


}
