package com.DFHT.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/18 14:09
 * 类描述：视图类型的工具类.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ViewUtils {
    /**
     * 移除 当前view的爹
     * @param v
     */
    public static void removeParent(View v){
        //  先找到爹 在通过爹去移除孩子
        ViewParent parent = v.getParent();
        //所有的控件 都有爹  爹一般情况下 就是ViewGoup
        if(parent instanceof ViewGroup){
            ViewGroup group=(ViewGroup) parent;
            group.removeView(v);
        }
    }
}
