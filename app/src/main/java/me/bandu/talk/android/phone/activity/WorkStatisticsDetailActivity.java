package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.WorkStatisticsDetailListAdapter;
import me.bandu.talk.android.phone.bean.SingleStatisBean;
import me.bandu.talk.android.phone.middle.SingleStatisMiddle;

/**
 * 创建者:taoge
 * 时间：2015/11/23 16:40
 * 类描述：单题统计
 * 修改人:taoge
 * 修改时间：2015/11/23 16:40
 * 修改备注：
 */
public class WorkStatisticsDetailActivity extends BaseAppCompatActivity implements OnChartValueSelectedListener {

    @Bind(R.id.lv)
    ListView listView;

    @Bind(R.id.chart)
    PieChart mChart;

    @Bind(R.id.title_tv)
    TextView tv;
    @Bind(R.id.title)
    TextView titleTv;
    @Bind(R.id.title_right)
    RelativeLayout rightLayout;
    @Bind(R.id.image)
    ImageView img;

    String jobId;
    String quizId;
    String title;
    SingleStatisBean.DataEntity.StatEntity question;
    int doneNum;

    private WorkStatisticsDetailListAdapter adapter;
    private List<SingleStatisBean.DataEntity.SentenceListEntity> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_statistics_detail;
    }

    @Override
    protected void toStart() {
        tv.setText(R.string.work_statistics);
        rightLayout.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
        img.setImageResource(R.mipmap.info);
        jobId = getIntent().getStringExtra("jobId");
        quizId = getIntent().getStringExtra("quizId");
        title = getIntent().getStringExtra("title");
        titleTv.setText(title);
        initData();
        //initChartView();

    }
    @OnClick(R.id.title_right)
    void click(View v){
        startActivity(new Intent(this,ScoreInfoActivity.class));
    }

    public void initChartView() {
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        //tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setOnChartValueSelectedListener(this);

        setData(2, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(143, 210, 81));
        colors.add(Color.rgb(0, 201, 243));
        colors.add(Color.rgb(255, 98, 95));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("A" + "   "+question.getA()+"人");
        xVals.add("B" + "   "+question.getB()+"人");
        xVals.add("C" + "   "+question.getC()+"人");
        l.setCustom(colors, xVals);
        l.setPosition(Legend.LegendPosition.PIECHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(-5f);
        mChart.notifyDataSetChanged();
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (question.getA() != 0) {
            yVals1.add(new Entry((float) question.getA()/question.getDone(), 0));
            xVals.add("A");
            colors.add(Color.rgb(143, 210, 81));
        }
        if (question.getB() != 0) {
            yVals1.add(new Entry((float) question.getB()/question.getDone(), 0));
            xVals.add("B");
            colors.add(Color.rgb(0, 201, 243));
        }
        if (question.getC() != 0) {
            yVals1.add(new Entry((float) question.getC()/question.getDone(), 0));
            xVals.add("C");
            colors.add(Color.rgb(255, 98, 95));
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        // data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    public void initData() {
        mList = new ArrayList();
        adapter = new WorkStatisticsDetailListAdapter(this, mList,question);
        listView.setAdapter(adapter);
        new SingleStatisMiddle(this, this).getSingleWork(GlobalParams.userInfo.getUid() + "", jobId, quizId, new SingleStatisBean());
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        SingleStatisBean bean = (SingleStatisBean) result;
        if (bean != null && bean.getStatus() == 1) {
            List<SingleStatisBean.DataEntity.SentenceListEntity> list = new ArrayList<>();
            question = bean.getData().getStat();
            doneNum = question.getDone();
            adapter.setQuestion(question);
            if (question.getDone() != 0) {
                initChartView();
            }
            list = bean.getData().getSentence_list();
            if (list != null && list.size() != 0) {
                mList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
