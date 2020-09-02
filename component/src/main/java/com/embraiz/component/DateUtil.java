package com.embraiz.component;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {
    public static String getCurrentDate() {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);
        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    public static Integer getSecondTimestamp() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        if (null == today) {
            return 0;
        }
        String timestamp = String.valueOf(today.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    public static long getTimestamp(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getTimestamp(Date date) {
        String dateStr = getDateByFormat(date, "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2.getTime();
    }

    /**
     * 获取时间字符串
     *
     * @param format
     * @return
     */
    public static String getDateByFormat(String format) {
        SimpleDateFormat sdf = null;
        if (format != null && !("").equals(format)) {
            sdf = new SimpleDateFormat(format);
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return sdf.format(new Date());
    }

    public static String getDateByFormat(Date date, String format) {
        SimpleDateFormat sdf = null;
        if (format != null && !("").equals(format)) {
            sdf = new SimpleDateFormat(format);
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return sdf.format(date);
    }

    public static String getDateByFormat(String date, String format) {
        SimpleDateFormat sdf = null;
        String data = "";
        if (format != null && !("").equals(format)) {
            sdf = new SimpleDateFormat(format);
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            data = sdf.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
