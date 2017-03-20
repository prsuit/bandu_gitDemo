package me.bandu.talk.android.phone.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建者:taoge
 * 时间：2015/12/1
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/12/1
 * 修改备注：
 */
public class ViewPageAdapter extends PagerAdapter {

    //界面列表
    private List<View> views;

    public ViewPageAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //获得当前界面数
    @Override
    public int getCount() {
        return views.size();
    }

    //初始化arg1位置的界面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        super.restoreState(arg0,arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
