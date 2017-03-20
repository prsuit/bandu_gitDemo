package me.bandu.talk.android.phone.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.DFHT.utils.ViewUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.WorkStatisticsActivity;
import me.bandu.talk.android.phone.adapter.WorkStatisticeViewpagerAdapter;
import me.bandu.talk.android.phone.adapter.WorkStatisticsListviewAdapter;
import me.bandu.talk.android.phone.bean.WorkStatisticsBean;
import me.bandu.talk.android.phone.middle.WorkStatisticsMiddle;
import me.bandu.talk.android.phone.utils.CircleIndicatorHelper;
import me.bandu.talk.android.phone.utils.CirclePageIndicator;
import me.bandu.talk.android.phone.utils.Utils;

/**
 * 创建者：王兰新
 * 时间：2016/11/24
 * 类描述：饼状图统计
 * 修改人：
 * 修改时间：
 */
public class WorkStatisticsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final int FROM_WLX_FRAGMENT = 2;
    //完成
    private List<WorkStatisticsBean.DataBean.JoblistBean> finish_list = new ArrayList<>();
    //未完成（提交了）
    private List<WorkStatisticsBean.DataBean.JoblistBean> not_completed_list = new ArrayList<>();
    //未交
    private List<WorkStatisticsBean.DataBean.JoblistBean> unpaid_list = new ArrayList<>();
    //ABC的数量
    private int count_a;
    private int count_b;
    private int count_c;
    //up指数数量
    private int up_positive;
    private int up_zero;
    private int up_negative;
    private int time_least;
    private int time_secondary;
    private int time_latest;
    //饼图
    private List<View> mp_list = new ArrayList<>();
    private View view;
    private ViewPager vp_pie_chart;
    private TextView tv_chart_info;
    private TextView tv_proportion;
    private ListView lv_weak_sentence;
    private View mp_average_score;
    private View mp_average_time;
    private View mp_average_up;
    private String jobId;
    private int max_time;
    private int min_time;
    private int time_difference;
    private int every_time;

    private static final int MP_AVERAGE_SCORE = 0;
    private static final int MP_TIME = 1;
    private static final int MP_UP = 2;
    //计算占比
    int total_score = 0;
    int total_time = 0;
    int total_up = 0;
    //5个等级
    private TextView a;
    private TextView b;
    private TextView c;
    private TextView not_completed;//未完成
    private TextView unpaid;//未交
    private List<WorkStatisticsBean.DataBean.JoblistBean> joblist;
    private List<WorkStatisticsBean.DataBean.WeakSentencesBean> weak_sentences;
    private HomeworkSortFragment homeworkSortFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewUtils.removeParent(view);
        } else {
            view = inflater.inflate(R.layout.fragment_work_statistics, null);
            initView(view);
            setData();
            setListeners();
        }
        return view;
    }

    public void initView(View view) {
        vp_pie_chart = (ViewPager) view.findViewById(R.id.vp_pie_chart);
        tv_chart_info = (TextView) view.findViewById(R.id.tv_chart_info);
        tv_proportion = (TextView) view.findViewById(R.id.tv_proportion);
        lv_weak_sentence = (ListView) view.findViewById(R.id.lv_weak_sentence);
        a = (TextView) view.findViewById(R.id.A);
        b = (TextView) view.findViewById(R.id.B);
        c = (TextView) view.findViewById(R.id.C);
        not_completed = (TextView) view.findViewById(R.id.not_completed);
        unpaid = (TextView) view.findViewById(R.id.unpaid);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        not_completed.setOnClickListener(this);
        unpaid.setOnClickListener(this);
        homeworkSortFragment = new HomeworkSortFragment();


        jobId = ((WorkStatisticsActivity) getActivity()).getJobId();
        //平均分图
        mp_average_score = View.inflate(getActivity(), R.layout.mp_average_score, null);
        //平均用时图
        mp_average_time = View.inflate(getActivity(), R.layout.mp_average_time, null);
        //up指数
        mp_average_up = View.inflate(getActivity(), R.layout.mp_average_up, null);
        mp_list.add(mp_average_score);
        mp_list.add(mp_average_time);
        mp_list.add(mp_average_up);

    }


    public void setData() {
        showMyprogressDialog();
        //进行网络访问WorkStatisticsMiddle
        new WorkStatisticsMiddle(this, getActivity()).getStatisticsInfo(jobId, new WorkStatisticsBean());
    }


    public void setListeners() {

    }


    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        WorkStatisticsBean data = (WorkStatisticsBean) result;
        if (data != null && data.getStatus() == 1) {
            //有数据
            if (requestCode == WorkStatisticsMiddle.STATISTICS_CODE) {
                //计算数据
                calculation(data);

                //设置数据
                tv_proportion.setText(data.getData().getDone_total() + "/" + data.getData().getTotal());
                weak_sentences = data.getData().getWeak_sentences();

                //listviwe 的 adapter
                WorkStatisticsListviewAdapter lv_adapter = new WorkStatisticsListviewAdapter(getActivity(), weak_sentences);
                lv_weak_sentence.setAdapter(lv_adapter);
                lv_weak_sentence.setOnItemClickListener(this);

                //viewpager 的 adapter
                final WorkStatisticeViewpagerAdapter vp_adapter = new WorkStatisticeViewpagerAdapter(this);
                vp_pie_chart.setAdapter(vp_adapter);
                //圆点
                CircleIndicatorHelper mIndicatorHelper = new CircleIndicatorHelper(getActivity(), tv_chart_info);
                mIndicatorHelper.setViewpager(vp_pie_chart);
                mIndicatorHelper.setFillColor("#00B0E5");
                mIndicatorHelper.setDefaultColor("#D0CBCC");
                mIndicatorHelper.setRadius(3);
                mIndicatorHelper.setPageChange(new CirclePageIndicator.PageChange() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        setGrade(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        }
    }

    //计算
    private void calculation(WorkStatisticsBean data) {
        joblist = data.getData().getJoblist();
        time_least = 0;
        time_secondary = 0;
        time_latest = 0;
        count_a = 0;
        count_b = 0;
        count_c = 0;
        up_positive = 0;
        up_zero = 0;
        up_negative = 0;
        for (int i = 0; i < joblist.size(); i++) {
            WorkStatisticsBean.DataBean.JoblistBean bean = joblist.get(i);
            int status = bean.getStatus();
            if (status == 0) {
                //未交
                unpaid_list.add(bean);
            } else if (status == 1) {
                //未完成（提交了）
                not_completed_list.add(bean);
            } else {
                //已完成
                finish_list.add(bean);
            }
        }


        if (finish_list.size() > 0) {
            max_time = finish_list.get(0).getTotal_time();
            min_time = finish_list.get(0).getTotal_time();
        } else {
            max_time = 0;
            min_time = 0;
        }


        for (int i = 0; i < finish_list.size(); i++) {
            WorkStatisticsBean.DataBean.JoblistBean joblistBean = finish_list.get(i);
            total_score += joblistBean.getScore();
            total_time += joblistBean.getTotal_time();
            total_up += joblistBean.getUp_score();
            if (joblistBean.getScore() >= 85) {
                //A
                count_a++;
            } else if (joblistBean.getScore() < 54) {
                //B
                count_c++;
            } else {
                //C
                count_b++;
            }
            if (joblistBean.getUp_score() > 0) {
                up_positive++;
            } else if (joblistBean.getUp_score() == 0) {
                up_zero++;
            } else {
                up_negative++;
            }
            if (joblistBean.getTotal_time() > max_time) {
                max_time = joblistBean.getTotal_time();
            } else if (joblistBean.getTotal_time() < min_time) {
                min_time = joblistBean.getTotal_time();
            }
        }
        //计算时间
        time_difference = max_time - min_time;
        every_time = time_difference / 3;
        for (int i = 0; i < finish_list.size(); i++) {
            WorkStatisticsBean.DataBean.JoblistBean bean = finish_list.get(i);
            if(time_difference > 2) {
                if (bean.getTotal_time() < min_time + every_time) {
                    time_least++;
                } else if (bean.getTotal_time() > min_time + 2 * every_time) {
                    time_latest++;
                } else {
                    time_secondary++;
                }
            }else {
                time_least++;
            }
        }
    }

    //饼图数据
    public View getPieData(int position) {
        float quarterly1 = 0.0f;
        float quarterly2 = 0.0f;
        float quarterly3 = 0.0f;
        View view = mp_list.get(position);
        PieChart pieChart = (PieChart) view.findViewById(R.id.score_piechart);

        //piechart设置样式
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆
        pieChart.setDescription("");
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setCenterTextSize(20.0f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(false); // 不可以手动旋转
        pieChart.setUsePercentValues(true);  //显示成百分比
//        pieChart.setDescriptionTextSize(R.dimen.content_textsize_25);
        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        if (position == 0) {
            //平均分
            if (finish_list.size() > 0) {
                pieChart.setCenterText(total_score / finish_list.size() + "分");  //饼状图中间的文字
            } else {
                pieChart.setCenterText("还没有同学提交");
            }
        } else if (position == 1) {
            //time
            if (finish_list.size() > 0) {
                int avg_time = total_time / finish_list.size();
                if (avg_time > 60) {
                    pieChart.setCenterText(Utils.getTimeFromInt(avg_time));
                } else {
                    pieChart.setCenterText(avg_time + "秒");
                }
            } else {
                pieChart.setCenterText("还没有同学提交");
            }
        } else if (position == 2) {
            //up
            if (finish_list.size() > 0) {
                pieChart.setCenterText("+" + total_up / finish_list.size() + "分");
            } else {
                pieChart.setCenterText("还没有同学提交");
            }
        }


        //每块图上的内容
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        if (position == 0) {
            //score
            xValues.add(getPercentage(count_a, joblist.size()) + "%" + " " + count_a + "人");
            xValues.add(getPercentage(count_b, joblist.size()) + "%" + " " + count_b + "人");
            xValues.add(getPercentage(count_c, joblist.size()) + "%" + " " + count_c + "人");
            // 饼图数据
            /**
             * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
             */
            quarterly1 = Float.parseFloat(getPercentage(count_a, joblist.size()));//A
            quarterly2 = Float.parseFloat(getPercentage(count_b, joblist.size()));//B
            quarterly3 = Float.parseFloat(getPercentage(count_c, joblist.size()));//C

        } else if (position == 1) {
            //time
            xValues.add(getPercentage(time_least, joblist.size()) + "%" + " " + time_least + "人");
            xValues.add(getPercentage(time_secondary, joblist.size()) + "%" + " " + time_secondary + "人");
            xValues.add(getPercentage(time_latest, joblist.size()) + "%" + " " + time_latest + "人");

            quarterly1 = Float.parseFloat(getPercentage(time_least, joblist.size()));
            quarterly2 = Float.parseFloat(getPercentage(time_secondary, joblist.size()));
            quarterly3 = Float.parseFloat(getPercentage(time_latest, joblist.size()));
        } else if (position == 2) {
            //up
            xValues.add(getPercentage(up_positive, joblist.size()) + "%" + " " + up_positive + "人");
            xValues.add(getPercentage(up_zero, joblist.size()) + "%" + " " + up_zero + "人");
            xValues.add(getPercentage(up_negative, joblist.size()) + "%" + " " + up_negative + "人");
            quarterly1 = Float.parseFloat(getPercentage(up_positive, joblist.size()));
            quarterly2 = Float.parseFloat(getPercentage(up_zero, joblist.size()));
            quarterly3 = Float.parseFloat(getPercentage(up_negative, joblist.size()));
        }

        //未完成和未交
        xValues.add(getPercentage(not_completed_list.size(), joblist.size()) + "%" + " " + not_completed_list.size() + "人");
        xValues.add(getPercentage(unpaid_list.size(), joblist.size()) + "%" + " " + unpaid_list.size() + "人");

        float quarterly4 = Float.parseFloat(getPercentage(not_completed_list.size(), joblist.size()));//未完成
        float quarterly5 = Float.parseFloat(getPercentage(unpaid_list.size(), joblist.size()));//未交

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));
        yValues.add(new Entry(quarterly5, 4));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(2.0f); //设置个饼状图之间的距离
        pieDataSet.setDrawValues(false);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(0, 219, 196));
        colors.add(Color.rgb(67, 214, 255));
        colors.add(Color.rgb(255, 117, 113));
        colors.add(Color.rgb(255, 196, 82));
        colors.add(Color.rgb(178, 167, 254));
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
//        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        pieDataSet.setValueTextColor(getResources().getColor(R.color.transparent));
        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        return view;
    }


    @Override
    public void onFailed(int requestCode) {
        hideMyprogressDialog();
        super.onFailed(requestCode);
    }


    //设置五个等级文字
    private void setGrade(int position) {
        if (position == MP_AVERAGE_SCORE) {//平均分图
            a.setText("A");
            b.setText("B");
            c.setText("C");
        } else if (position == MP_TIME) {
            int one_time;
            int two_time;
            if (max_time - min_time < 3) {
                a.setText(min_time + "秒-" + max_time + "秒");
                if(finish_list.size() == 0) {
                    a.setText("--秒--秒");
                }
                b.setText("--秒--秒");
                c.setText("--秒--秒");
            } else {
                one_time = min_time + every_time;
                two_time = min_time + 2 * every_time;
                a.setText(min_time+ "秒-" + one_time+"秒");
                b.setText(one_time+ "秒-" + two_time+"秒");
                c.setText(two_time+ "秒-" + max_time+"秒");
            }
        } else if (position == MP_UP) {
            a.setText("进步");
            b.setText("持平");
            c.setText("退步");
        }
        not_completed.setText("未完成");
        unpaid.setText("未交");
    }

    @Override
    public void onClick(View v) {
        int currentItem = vp_pie_chart.getCurrentItem();
        //类型
        String type = "";
        //等级
        String grade = "";
        if (currentItem == 0) {
            type = "score";
            homeworkSortFragment.setType("score");
        } else if (currentItem == 1) {
            homeworkSortFragment.setType("time");
            type = "time";
        } else {
            homeworkSortFragment.setType("up");
            type = "up";
        }
        switch (v.getId()) {
            case R.id.A:
                grade = "1";
                homeworkSortFragment.setGrade("1");
                break;
            case R.id.B:
                grade = "2";
                homeworkSortFragment.setGrade("2");
                break;
            case R.id.C:
                grade = "3";
                homeworkSortFragment.setGrade("3");
                break;
            case R.id.not_completed:
                grade = "4";
                homeworkSortFragment.setGrade("4");
                break;
            case R.id.unpaid:
                grade = "5";
                homeworkSortFragment.setGrade("5");
                break;
        }

        ((WorkStatisticsActivity) getActivity()).updateUI(R.id.rl_namelist);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("grade", grade);
        bundle.putInt("from", FROM_WLX_FRAGMENT);
        this.homeworkSortFragment.setArguments(bundle);
        ft.replace(R.id.fl_content, this.homeworkSortFragment);
        ft.commit();
    }

    //薄弱句子listview的itemclick
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        homeworkSortFragment.setType("weak_sentences");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ((WorkStatisticsActivity) getActivity()).updateUI(R.id.rl_namelist);
        int sentence_id = weak_sentences.get(position).getSentence_id();
        Bundle bundle = new Bundle();
        bundle.putString("type", "weak_sentences");
        bundle.putString("sentence_id", sentence_id + "");
        homeworkSortFragment.setArguments(bundle);
        ft.replace(R.id.fl_content, homeworkSortFragment);
        ft.commit();
    }

    public String getPercentage(int num1, int num2) {
        if (num2 == 0 && num1 == 0)
            return "0";
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((float) num1 / (float) num2 * 100);
    }
}
