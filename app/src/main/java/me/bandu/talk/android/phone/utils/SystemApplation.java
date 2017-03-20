package me.bandu.talk.android.phone.utils;

/**
 * 创建者:taoge
 * 时间：2015/12/21
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/21
 * 修改备注：
 */

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

import me.bandu.talk.android.phone.activity.LoginActivity;


public class SystemApplation {

    private List<Activity> mList = new LinkedList<Activity>();
    private static SystemApplation instance;

    private SystemApplation() {
    }

    public synchronized static SystemApplation getInstance() {
        if (null == instance) {
            instance = new SystemApplation();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit(boolean b) {
        try {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i) != null) {
                    if (!b) {
                        if (!mList.get(i).equals(LoginActivity.class)) {
                            mList.get(i).finish();
                        }
                    } else {
                        mList.get(i).finish();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
    }

}

