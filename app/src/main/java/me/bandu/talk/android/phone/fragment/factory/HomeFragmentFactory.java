package me.bandu.talk.android.phone.fragment.factory;

import android.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.fragment.LiveFragment2;
import me.bandu.talk.android.phone.fragment.StudentExerciseFragment;
import me.bandu.talk.android.phone.fragment.StudentWorkFragment;
import me.bandu.talk.android.phone.fragment.TeacherHomeListFragment;
import me.bandu.talk.android.phone.fragment.TeacherInfoFragment;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/23 16:12
 * 类描述：
 * 修改人：
 * 修改时间：2015/12/21
 * 修改备注：学生主页,教师主页都可以用这个fragmentFactory
 */
public class HomeFragmentFactory {
    private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int viewId) {
        Fragment fragment;
        fragment = mFragments.get(viewId);
        if (fragment == null) {
            if (viewId == R.id.llMyClass) {
                fragment = new TeacherHomeListFragment();
            } else if (viewId == R.id.llMyInfo) {
                fragment = new TeacherInfoFragment();
            } else if (viewId == R.id.rlStudentExercise) {
                fragment = new StudentExerciseFragment();
            } else if (viewId == R.id.rlStudentWork) {
                fragment = new StudentWorkFragment();
            } else if (viewId == R.id.rlMyInfo) {
                fragment = new TeacherInfoFragment();
            }else if (viewId == R.id.rlStudentQingLive) {
                // 学生轻直播
                fragment = new LiveFragment2();
            }else if (viewId == R.id.rlTeacherQingLive) {
                //老师直播
                fragment = new LiveFragment2();
            }
            if (fragment != null) {
                mFragments.put(viewId, fragment);
            }
        }
        return fragment;
    }

    public static void destroyFragment(int id) {
        if (mFragments != null && mFragments.containsKey(id)) mFragments.remove(id);
    }

    public static void cleanFragment(){
        if(mFragments!=null && mFragments.size() >0)
            mFragments.clear();
    }
}
