package me.bandu.talk.android.phone.utils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.activity.BaseAppCompatActivity;

/**
 * 创建者：高楠
 * 时间：on 2016/2/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ActivityManager {
    static ActivityManager activityManager;
    private ActivityManager(){}
    public static ActivityManager getActivityManager(){
        if (activityManager==null){
            activityManager = new ActivityManager();
        }
        return activityManager;
    }
    private List<BaseAppCompatActivity> activities = new ArrayList<>();
    public void addActivity(BaseAppCompatActivity activity){
        if (activities!=null){
            activities.add(activity);
        }
    }

    public void removeActivity(){
        if (activities!=null){
            for (int i = 0 ; i < activities.size() ;i++){
                activities.get(i).finish();
            }
            activities.clear();
        }
    }
}
