package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.DFHT.utils.JSONUtils;
import com.DFHT.utils.UIUtils;

import java.io.File;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.LoginBean;

public class PreferencesUtils {

    public static String PREFERENCE_NAME = ConstantValue.SPCONFIG;
    public static String USER_INFO = "userinfo";
    private static int MODE_PRIVATE = Context.MODE_PRIVATE;
    /**
     * put long preferences
     * 
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     * 
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     * 
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }
    
    /**
     * remove obj in preferences 
     * @param context
     * @param key
     * @return
     */
    public static boolean removeSharedPreferenceByKey(Context context, String key){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static LoginBean.DataEntity getUserInfo() {
        LoginBean.DataEntity info = null;
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        String userStr = sp.getString(USER_INFO, "");
        if (!TextUtils.isEmpty(userStr)) {
            info = JSONUtils.readValue(userStr, LoginBean.DataEntity.class);
            GlobalParams.userInfo = info;
        }else {
            info = SaveBeanToFile.readFileToBean(LoginBean.DataEntity.class, new File(UIUtils.getContext().getDir("user",Context.MODE_PRIVATE).getAbsolutePath() + "/user", "user.data"));
            GlobalParams.userInfo = info;
            saveUserInfo(info);
        }
        if(info == null)
            info = new LoginBean.DataEntity();
        return info;
    }
    public static void clean(){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(USER_INFO, "");
        edit.putString("uid", "");
        edit.commit();
    }

    public static boolean saveUserInfo(LoginBean.DataEntity userInfo) {
        //UserUtil.saveUserInfo(UIUtils.getContext(),userInfo);
        String userStr = JSONUtils.getJson(userInfo);
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if(!TextUtils.isEmpty(userStr)) {
            edit.putString(USER_INFO, userStr);
            return edit.commit();
        }
        return false;
    }

    public static void setUid(String uid){
        if(!TextUtils.isEmpty(uid)) {
            SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
            sp.edit().putString("uid", uid).commit();
        }
    }

    public static String getUid(){
        LoginBean.DataEntity info = null;
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        String userStr = sp.getString(USER_INFO, "");
        String uid ;
        if (!TextUtils.isEmpty(userStr)) {
            info = JSONUtils.readValue(userStr, LoginBean.DataEntity.class);
            GlobalParams.userInfo = info;
            if(info != null && !TextUtils.isEmpty(info.getUid()))
                return info.getUid();
        }else{
            uid = sp.getString("uid","");
            if(!TextUtils.isEmpty(uid))
                return uid;
        }
        return UserUtil.getUid();
    }
    public static boolean isFirstLogin(){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        return sp.getBoolean("isFirstLogin",false);
    }
    public static void setFirstLogin(boolean isFirstLogin){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        sp.edit().putBoolean("isFirstLogin",isFirstLogin).commit();
    }
    public static void setFirstBg(boolean firstBg){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        sp.edit().putBoolean("firstBg",firstBg).commit();
    }
    public static boolean isFirstBg(){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        return sp.getBoolean("firstBg",false);
    }
}
