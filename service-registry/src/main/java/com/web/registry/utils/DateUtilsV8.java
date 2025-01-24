package com.web.registry.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtilsV8 {
//    version 20240708

    public static final String AM = " aa";
    public static final String ZONE = " Z"; //TimeZone.getTimeZone("GMT-07"), TimeZone.getTimeZone("Europe/Berlin")...
    public static final String TIME_ZONE = " aaa XXX";

    public static final String regex00 = "yyyy/MM/dd";
    public static final String regex01 = regex00 + " HH:mm:ss";
    public static final String regex02 = regex01 + AM;
    public static final String regex03 = regex01 + ZONE;
    public static final String regex04 = regex01 + TIME_ZONE;
    public static final String regex05 = regex00 + " HH:mm:ss.SSS";


    public static final String regex20 = "dd/MM/yyyy";
    public static final String regex21 = regex20 + " HH:mm:ss";
    public static final String regex22 = regex21 + AM;
    public static final String regex23 = regex21 + ZONE;
    public static final String regex24 = regex21 + TIME_ZONE;

    public static final String regex10 = "yyyy_MM_dd";
    public static final String regex11 = regex10 + "_HH_mm_ss";
    public static final String regex12 = regex11 + AM;
    public static final String regex13 = regex11 + ZONE;
    public static final String regex14 = regex11 + TIME_ZONE;

    public static final String regex60 = "dd_MM_yyyy";
    public static final String regex61 = regex60 + "_HH_mm_ss";
    public static final String regex62 = regex11 + AM;
    public static final String regex63 = regex11 + ZONE;
    public static final String regex64 = regex11 + TIME_ZONE;

    public static final String regex30 = "yyyy-MM-dd";
    public static final String regex31 = regex30 + " HH:mm:ss";
    public static final String regex32 = regex31 + AM;
    public static final String regex33 = regex31 + ZONE;
    public static final String regex34 = regex31 + TIME_ZONE;
    public static final String regex35 = regex30 + "'T'HH:mm:ss.SSS";


    public static final String regex50 = "dd-MM-yyyy";
    public static final String regex51 = regex50 + " HH:mm:ss";
    public static final String regex52 = regex51 + AM;
    public static final String regex53 = regex51 + ZONE;
    public static final String regex54 = regex51 + TIME_ZONE;

    public static final String regex40 = "yyyyMMdd";
    public static final String regex41 = regex40 + "HHmmss";
    public static final String regex42 = regex41 + AM;
    public static final String regex43 = regex41 + ZONE;
    public static final String regex44 = regex41 + TIME_ZONE;
    public static final String regex45 = regex40 + "HHmmssSSS";

    public static final Date defaultDate = convertStringToDate("2018/01/01", regex00);

    public static void main(String[] args) {

        Date input = new Date();
        System.out.println("input: " + input);
        Date ouput = convertDatePlusByField(input, Calendar.DAY_OF_MONTH, 2);
        System.out.println("ouput: " + ouput);
    }
    //    ***********************************************************************************************

    public static Date getSysdate(int numberDate) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numberDate);
        return c.getTime();
    }

    public static LocalDateTime getSysDateTimeLocal() {
        return LocalDateTime.now();
    }

    public static LocalDateTime getSysDateTimeLocal(int numberDate) {
        return LocalDateTime.now().plusDays(numberDate);
    }

    public static LocalDate getSysDateLocal() {
        return LocalDate.now();
    }

    public static LocalDate getSysDateLocal(int numberDate) {
        return LocalDate.now().plusDays(numberDate);
    }

    public static String convertDateToString(Date date, String regex) {
        return new SimpleDateFormat(regex).format(date);
    }

    public static Date convertStringToDate(String date, String regex) {
        Date res = null;
        try {
            res = new SimpleDateFormat(regex).parse(date);
        } catch (ParseException e) {

        }
        return res;
    }

    public static String convertDateToStringUTCTime(Date date, String regex) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    /********************
     * 1 : a > b
     * -1 : a < b
     * 0 : a = b
     ********************/
    public static int conpareDateToDate(Date a, Date b) {
        return a.compareTo(b);
    }

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

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date convertTimestampToDate(Timestamp stamp) {
        if (stamp == null) return null;
        return new Date(stamp.getTime());
    }

    public static java.sql.Date convertTimestampToDateSql(Timestamp stamp) {
        if (stamp == null) return null;
        return new java.sql.Date(stamp.getTime());
    }

    public static Timestamp convertDateToTimestamp(Date date) {
        if (date == null) return null;
        return new Timestamp(date.getTime());
    }


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

    //    return false if number of days from FromDate to ToDate is less than "date"
    public static boolean compareFromdateTodate(LocalDate fromDateLocal, LocalDate toDateLocal, int date) {
        return toDateLocal.isAfter(fromDateLocal.plusDays(date));
    }

    //    return false if number of days from FromDate to ToDate is less than "date"
    public static boolean compareFromdateTimeTodateTime(LocalDateTime fromDateLocal, LocalDateTime toDateLocal, int date) {
        return toDateLocal.isAfter(fromDateLocal.plusDays(date).plusNanos(900000000));
    }

    public static String convertLocalDateTimeToString(LocalDateTime dateTimeLocal, String regex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
        return dateTimeLocal.format(formatter);
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime dateTimeLocal) {
        String dateTimeLocalString = dateTimeLocal.format(DateTimeFormatter.ofPattern(regex31));
        return convertStringToDate(dateTimeLocalString, regex31);
    }

    public static LocalDate convertStringToLocalDate(String dateLocal, String regex) {
        if (regex35.equals(regex)) {
            dateLocal = dateLocal.substring(0, dateLocal.length() - 6);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
        LocalDate.parse(dateLocal, formatter);
        return LocalDate.parse(dateLocal, formatter);
    }

    public static String convertLocalDateToString(LocalDate dateLocal, String regex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
        return dateLocal.format(formatter);
    }

    public static Date convertLocalDateToDate(LocalDate dateLocal) {
        String dateLocalString = dateLocal.format(DateTimeFormatter.ofPattern(regex30));
        return convertStringToDate(dateLocalString, regex30);
    }

    //    ***********************************************************************************************

    public static LocalDate getStartLocalDateOfWeek(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfWeek().getValue() + 1);
    }

    public static LocalDate getEndLocalDateOfWeek(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfWeek().getValue() + 7);
    }

    public static LocalDate getStartLocalDateOfMonth(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfMonth() + 1);
    }

    public static LocalDate getEndLocalDateOfMonth(LocalDate fromDate) {
        return fromDate.plusDays(-fromDate.getDayOfMonth() + fromDate.lengthOfMonth());
    }

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

    public static LocalDate getStartLocalDateOfYear(LocalDate fromDate) {
        LocalDate localDateTemp = fromDate.plusMonths(-(fromDate.getMonth().getValue() - 1));
        return getStartLocalDateOfMonth(localDateTemp);
    }

    public static LocalDate getEndLocalDateOfYear(LocalDate fromDate) {
        LocalDate localDateTemp = fromDate.plusMonths(-(fromDate.getMonth().getValue() - 1) + 11);
        return getEndLocalDateOfMonth(localDateTemp);
    }


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

    public static Date convertDatePlusByField(Date date, int field, int fieldDiff) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(field, cal.get(field) + fieldDiff);
        return cal.getTime();
    }
}
