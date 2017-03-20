package me.bandu.talk.android.phone.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 创建者：gaoye
 * 时间：2015/11/26  15:18
 * 类描述：动画的工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class AnimationUtil {
    public static  TranslateAnimation getYShowTranslateAnimation(long duration){
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(duration);
        return mShowAction;
    }
    public static  TranslateAnimation getYUpShowTranslateAnimation(long duration){
        TranslateAnimation  mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mShowAction.setDuration(duration);
        return mShowAction;
    }
    public static  TranslateAnimation getYUpHideTranslateAnimation(long duration){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }
    public static  TranslateAnimation getXRightHideTranslateAnimation(long duration){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }
    public static  TranslateAnimation getXLeftShowTranslateAnimation(long duration){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }
}
