package me.bandu.talk.android.phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.mikephil.charting.data.Entry;

import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.WorkStatisticsBean;
import me.bandu.talk.android.phone.fragment.WorkStatisticsFragment;

public class WorkStatisticeViewpagerAdapter extends PagerAdapter {
    private WorkStatisticsFragment fragment;

    public WorkStatisticeViewpagerAdapter(WorkStatisticsFragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View mp_chart = fragment.getPieData(position);
        view.addView(mp_chart);
        return mp_chart;
    }

}

