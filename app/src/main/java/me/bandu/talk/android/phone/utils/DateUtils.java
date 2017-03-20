package me.bandu.talk.android.phone.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 创建者：高楠
 * 时间：on 2016/4/20
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DateUtils {
//        format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        format=new SimpleDateFormat("yyyy/MM/dd");
//        format=new SimpleDateFormat("HH:mm:ss");
//        format=new SimpleDateFormat("EEEE");
//        format=new SimpleDateFormat("E");星期
    public static String getCurrentTimeText(String formatstr){
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat(formatstr);
        return format.format(date);
    }
    public static String getNumAfterTimeText(String formatstr,int num){
        long time=System.currentTimeMillis()+num*86400000+getHMiao();
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat(formatstr);
        return format.format(date);
    }
    public static String stringToDate(String datestr,String formatstr){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = null;

        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return new SimpleDateFormat(formatstr).format(date);
        }else {
            return getCurrentTimeText(formatstr);
        }
    }
    public static String string2Date(String datestr,String formatstr){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return new SimpleDateFormat(formatstr).format(date);
        }else {
            return getCurrentTimeText(formatstr);
        }
    }
    //return year+"-"+(month+1)+"-"+day+" 23:59:59";
    public static String getNumAfterDeadline(String formatstr,int num){
        long time=System.currentTimeMillis()+num*86400000+getHMiao();
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat(formatstr);
        return format.format(date);
    }
    /**
     * 获取今天还剩下多少毫秒秒
     * @return
     */
    public static long getHMiao(){
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return tommorowDate.getTimeInMillis() - curDate .getTimeInMillis();
    }

    public static String parseTime(String time){
        if("0000-00-00 00:00:00".equals(time)){
            return "0";
        }
        long timeL = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = format.parse(time);
            timeL = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                timeL = Long.valueOf(time);
            }catch (Exception e1){
                timeL = 0L;
            }
        }
        return timeL + "";
    }
}
