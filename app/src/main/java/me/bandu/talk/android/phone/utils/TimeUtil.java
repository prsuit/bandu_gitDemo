package me.bandu.talk.android.phone.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 创建者：gaoye
 * 时间：2015/12/9  10:20
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TimeUtil {
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    //根据两个long值判断是否是同一天
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY && interval > -1L * MILLIS_IN_DAY && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    /**
     * yyyy-MM-dd格式的日期转成MM/dd
     * @param time
     * @return
     */
    public static String getShortDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        int day = 0;
        int month = 0;
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH)+1;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return month+"/"+day;
    }
    /**
     * @param time
     * @return
     */
    public static String getMiaoDate(int time) {
        return time / 1000 + "\";";
    }
}
