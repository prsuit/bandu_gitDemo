package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.File;

import me.bandu.talk.android.phone.App;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.LoginBean;

public class UserUtil {
	public static boolean isLogin(){
		if ("".equals(getUid()))
			return false;
		else
			return true;
	}
	public static String getUid(){
		if (GlobalParams.userInfo == null)
			return "";
		else
			return StringUtil.getShowText(getUerInfo(App.getApplication()).getUid());
	}
	//获取上次练习的时间
	public static long getExerciseTime(Context context) {
		SharedPreferences sp = context.getSharedPreferences(getUid() + "_user_info",
				Context.MODE_PRIVATE);
		return sp.getLong("exercise_time", 0);
	}
	//保存上次练习的时间
	public static void saveExerciseTime(Context context, long time) {
		SharedPreferences sp = context.getSharedPreferences(getUid() + "_user_info",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("exercise_time", time);
		editor.commit();
	}
	//获取已练习天数
	public static int getExerciseDay(Context context) {
		SharedPreferences sp = context.getSharedPreferences(getUid() + "_user_info",
				Context.MODE_PRIVATE);
		return sp.getInt("exercise_day", 0);
	}
	//保存已练习天数
	public static void saveExerciseTime(Context context, int day) {
		SharedPreferences sp = context.getSharedPreferences(getUid() + "_user_info",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("exercise_day", day);
		editor.commit();
	}
	public static boolean getDefaultExerciseLoad(Context context) {
		SharedPreferences sp = context.getSharedPreferences(getUid() + "_user_info",
				Context.MODE_PRIVATE);
		boolean b = sp.getBoolean("exercise_default", false);
		return sp.getBoolean("exercise_default", false);
	}
	public static void saveDefaultExerciseLoad(Context context, boolean isload) {
		SharedPreferences sp = context.getSharedPreferences(getUid() + "_user_info",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("exercise_default", isload);
		editor.commit();
	}
	public static LoginBean.DataEntity getUerInfo(Context context){
		if (null == GlobalParams.userInfo){
			GlobalParams.userInfo =  SaveBeanToFile.readFileToBean(LoginBean.DataEntity.class, new File(context.getDir("user",context.MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
			PreferencesUtils.saveUserInfo(GlobalParams.userInfo);
		}
		return GlobalParams.userInfo;
	}
	public static String getUserInfoUid(Context context){
		if (context==null){
			return "";
		}
		if (GlobalParams.userInfo==null){
			GlobalParams.userInfo =  SaveBeanToFile.readFileToBean(LoginBean.DataEntity.class, new File(context.getDir("user",context.MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
			PreferencesUtils.saveUserInfo(GlobalParams.userInfo);
		}
		if (GlobalParams.userInfo!=null){
			return GlobalParams.userInfo.getUid();
		}else {
			return "";
		}
	}

	public static void saveUserInfo(Context context,LoginBean.DataEntity info){
		PreferencesUtils.saveUserInfo(info);
		File tempFile = new File(context.getDir("user",context.MODE_PRIVATE).getAbsolutePath() + "/user");
		tempFile.mkdirs();
		SaveBeanToFile.beanToFile(info, new File(tempFile, "user.data"));
	}
}
