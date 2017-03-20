package me.bandu.talk.android.phone.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.WindowManager;
import android.app.ActivityManager;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：获取屏幕信息的工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class ScreenUtil {
	//获取屏幕的宽度
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	//dp转px
	public static int dp2px(int dp,Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	//获取屏幕高度
	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}

	//获取应用高度
	public static int getAppHeight(Context context){
		return getScreenHeight(context) - getStatusBarHeight(context);
	}

	//获取状态栏高度
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	//判断activity是否在前台显示
	public static boolean isForeground(Activity context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}
		if (context.isFinishing()){
			return false;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<android.app.ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}

		return false;
	}
}
