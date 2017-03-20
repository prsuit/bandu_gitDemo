package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CircleIndicatorHelper  {
    private CirclePageIndicator mCircleIndicator;
    private Context mContext;

    public CircleIndicatorHelper(Context context, TextView tv_chart_info) {
        mCircleIndicator = new CirclePageIndicator(context,null,0,tv_chart_info);
        mContext = context;
    }
    public void setPageChange(CirclePageIndicator.PageChange pageChange){
        mCircleIndicator.setChange(pageChange);
    }

    public void setViewpager(ViewPager viewPager) {
        ViewGroup parentView = (ViewGroup) viewPager.getParent();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        lp.setMargins(0, 0, 0, dpToPx(8));
        mCircleIndicator.setLayoutParams(lp);
        parentView.addView(mCircleIndicator);
        mCircleIndicator.setViewPager(viewPager);
    }

    public void setFillColor(String colorString) {
        int color = Color.parseColor(colorString);
        mCircleIndicator.setFillColor(color);
    }

    public void setDefaultColor(String colorString) {
        int color = Color.parseColor(colorString);
        mCircleIndicator.setDefaultColor(color);
    }

    public void setRadius(int radius) {
        mCircleIndicator.setRadius(dpToPx(radius));
    }

    private int dpToPx(int dp) {
        DisplayMetrics resourec = mContext.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resourec);
    }
}
