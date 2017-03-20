package me.bandu.talk.android.phone.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * 创建者：高楠
 * 时间：2016/1/6  10:20
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TimeUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
	/**
	 * yyyy-MM-dd HH:mm 
	 * @return
	 */
	public static String getCurrentTime() {
		return sdf.format(new Date());
	}
	
	/**
	 * 毫秒转化成 00:00 或者 00:00:00
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static String msToTime(long milliseconds) {
		milliseconds /= 1000;
		int s = (int) (milliseconds % 60);
		int m = (int) (milliseconds / 60);
		if (m < 60) {
			return String.format(Locale.getDefault(), "%02d:%02d", m, s);
		}
		int h = m / 60;
		m %= 60;
		return String.format(Locale.getDefault(), "%02d:%02d:%02d", h, m, s);
	}
	
	/**
	 * 毫秒转化成        x分x秒 或者        x时 x分x秒
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static String msToTimeStr(long milliseconds) {
		milliseconds /= 1000;
		int s = (int) (milliseconds % 60);
		int m = (int) (milliseconds / 60);
		if (m < 60) {
			return m + "分" + s + "秒";
		}
		int h = m / 60;
		m %= 60;
		return h + "时" + m + "分" + s + "秒";
	}
}
