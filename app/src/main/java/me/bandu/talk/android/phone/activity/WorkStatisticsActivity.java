package me.bandu.talk.android.phone.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.fragment.HomeworkSortFragment;
import me.bandu.talk.android.phone.fragment.WorkStatisticsFragment;
import me.bandu.talk.android.phone.utils.PreferencesUtils;

/**
 * 创建者:fanYu
 * 时间：2016/6/6 16:40
 * 类描述：分数统计
 *
 */
public class WorkStatisticsActivity extends BaseAppCompatActivity implements View.OnClickListener /*implements OnChartValueSelectedListener*/ {

    private WorkStatisticsFragment workStatisticsFragment;
    private HomeworkSortFragment workSortFragement;
    private FrameLayout fl_content;
    private RelativeLayout rl_statistics;
    private RelativeLayout rl_namelist;

    private TextView tv_statistics;
    private TextView title_tv;
    private TextView tv_namelist;
    private ImageView iv_statistics;
    private ImageView iv_namelist;
    private ImageView goback;
    private int isChecked;
    public  TextView tv_WriteComments;
    public static WorkStatisticsActivity activity;
    String stutassort;


    String jobId; //作业id
    boolean isOut; //是否过期
    String cid;
    private String over_time;

    private String data_changed;
    /*@Bind(R.id.lv)
    ListView listView;

    @Bind(R.id.chart)
    PieChart mChart;

    @Bind(R.id.radio)
    TextView radioTv;
    @Bind(R.id.score)
    TextView scoreTv;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_right)
    RelativeLayout rightLayout;
    @Bind(R.id.image)
    ImageView img;

    private  WorkStatisticsListAdapter adapter;
    private List<WorkStatisBean.DataEntity.QuizListEntity> mList;
    private String jobId;
    private WorkStatisBean.DataEntity.StatEntity workInfo;*/

   /* @Override
    protected int getLayoutId() {
        return R.layout.activity_work_statistics;
    }

    @Override
    protected void toStart() {
        tv.setText(R.string.work_statistics);
    }*/


    @Override
    protected void toStart() {
        initView();
        initData();
//        UIUtils.showToastSafe("本机:"+PreferencesUtils.isFirstBg());
//        UIUtils.showToastSafe("服务器:"+PreferencesUtils.isFirstLogin());
        if(PreferencesUtils.isFirstBg() || PreferencesUtils.isFirstLogin()) {
            hazyDialog();
            PreferencesUtils.setFirstBg(false);
        }
        if(isOut){
           //说明是过期的那么就跳转到圆饼fragment
            getFragmentManager().beginTransaction().add(R.id.fl_content, workStatisticsFragment,"workStatisticsFragment")
                    .show(workStatisticsFragment).commit();
            tv_WriteComments.setVisibility(View.INVISIBLE);
        }else{
            //显示排序的fragment
            getFragmentManager().beginTransaction()
                    .add(R.id.fl_content, workSortFragement,"workSortFragement").show(workSortFragement).commit();
            updateUI(R.id.rl_namelist);
        }
    }
    public int curr_position = 0;
    public int imageResID[] = new int[]{R.mipmap.bg_bing};
    public void hazyDialog() {
        final ImageView imageView = (ImageView) findViewById(R.id.ivHazy);
        imageView.setVisibility(View.VISIBLE);
        curr_position = 0;
        imageView.setImageResource(imageResID[curr_position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (curr_position == imageResID.length - 1) {
                    imageView.setVisibility(View.GONE);
                }
                else {
                    curr_position++;
                    imageView.setImageResource(imageResID[curr_position]);
                }
            }
        });
        PreferencesUtils.setFirstBg(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_statistics;
    }




    private void initData() {
        title_tv.setText("作业统计");
        //统计fragment
        workStatisticsFragment = new WorkStatisticsFragment();

        workSortFragement=new HomeworkSortFragment();
        //检查作业界面传递过来的
        cid=getIntent().getStringExtra("cid");
        jobId = getIntent().getStringExtra("jobId");
        isOut = getIntent().getBooleanExtra("isOut", true);
        //结束时间
        over_time = getIntent().getStringExtra("over_time");
        isChecked = getIntent().getIntExtra("isChecked",-1);

        //下个页面传递过来的数据
        data_changed=getIntent().getStringExtra("data_changed");
    }

    public int getIsChecked() {
        return isChecked;
    }

    private void initView() {
        activity=this;
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        rl_statistics= (RelativeLayout) findViewById(R.id.rl_statistics);
        rl_namelist= (RelativeLayout) findViewById(R.id.rl_namelist);
        title_tv = (TextView)findViewById(R.id.title_tv);
        rl_statistics.setOnClickListener(this);
        rl_namelist.setOnClickListener(this);
        tv_WriteComments= (TextView) findViewById(R.id.tv_WriteComments);
        tv_WriteComments.setOnClickListener(this);
        goback= (ImageView) findViewById(R.id.goback);
        goback.setOnClickListener(this);

        tv_statistics= (TextView) findViewById(R.id.tv_statistics);
        tv_namelist= (TextView) findViewById(R.id.tv_namelist);
        iv_statistics= (ImageView) findViewById(R.id.iv_statistics);
        iv_namelist= (ImageView) findViewById(R.id.iv_namelist);
    }



    public  String getCid(){
        return cid;
    }

    public String getJobId() {
        return jobId;
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_HOME){
//            GlobalParams.isUpdateSort = false;
//        }
//        return true;
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_namelist://名单fragment
                replaceFragment(workSortFragement);
                updateUI(v.getId());
                workSortFragement.setTvSort("按完成作业时间由新到旧排序");
                workSortFragement.initRadioButton();
                workSortFragement.setType("commit");
                workSortFragement.showCommitTime();
                tv_WriteComments.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_statistics://统计
                tv_WriteComments.setVisibility(View.INVISIBLE);
                if(isOut) {
                    //过期
                    replaceFragment(workStatisticsFragment);
                    updateUI(v.getId());
                }else {
                    //弹框
                    showDialog();
                    tv_WriteComments.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.goback:
                finish();
                break;
            case R.id.tv_WriteComments:
//              调转到写评语的页面
                Intent intent = new Intent(WorkStatisticsActivity.this, TeacherCommentActivity.class);
                intent.putExtra("job_id", jobId);
                startActivity(intent);
                break;
        }
    }
    public void showDialog(){
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_show,null);
        TextView msg = (TextView) layout.findViewById(R.id.msg);
        msg.setText("本份作业截止时间为："+over_time+"请截止后再来查看吧！");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
//        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog1.dismiss();
//            }
//        });
        dialog1.show();
    }
    private void replaceFragment(final Fragment fragment){
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fl_content, fragment).commit();
//                ft.add(R.id.fl_content, fragment).commitAllowingStateLoss();
            }
        }, 100);
    }
    public void updateUI(int viewID) {
        tv_statistics.setTextColor(UIUtils.getColor(viewID == R.id.rl_statistics ? R.color.tv_color_my_pressed : R.color.tv_color_my_default));
        iv_statistics.setImageResource(viewID == R.id.rl_statistics ? R.mipmap.click_tongji : R.mipmap.tongji);
        tv_namelist.setTextColor(UIUtils.getColor(viewID == R.id.rl_namelist ? R.color.tv_color_my_pressed : R.color.tv_color_my_default));
        iv_namelist.setImageResource(viewID == R.id.rl_namelist ? R.mipmap.click_namelist : R.mipmap.name_list);
    }

    @Override
    public void success(Object result, int requestCode) {

    }

    @Override
    public void failed(int requestCode) {

    }
    /* rightLayout.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
        img.setImageResource(R.mipmap.info);
        jobId = getIntent().getStringExtra("jobId");
        initData();
        //initChartView();
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.lv)
        void itemClick(int position){
            Intent intent = new Intent(this,WorkStatisticsDetailActivity.class);
            intent.putExtra("jobId",jobId);
            intent.putExtra("quizId",mList.get(position).getQuiz_id()+"");
            intent.putExtra("title",mList.get(position).getQuiz_name());
            startActivity(intent);
        }
    @OnClick(R.id.title_right)
    void click(View v){
*//*        View layout = LayoutInflater.from(this).inflate(R.layout.activity_score_info1,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.show();*//*
        startActivity(new Intent(this,ScoreInfoActivity.class));
    }


    public void initData(){
        mList = new ArrayList();
        adapter = new WorkStatisticsListAdapter(this,mList);
        new WorkStatisMiddle(this,this).getWork(UserUtil.getUid(),jobId,new WorkStatisBean());
    }

    public void initChartView(){
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

        setData(2);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(143,210, 81));
        colors.add(Color.rgb(0,201,243));
        colors.add(Color.rgb(255,98,95));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("A"+"优秀   "+workInfo.getA()+"人");
        xVals.add("B"+"良好   "+workInfo.getB()+"人");
        xVals.add("C"+"一般   "+workInfo.getC()+"人");
        l.setCustom(colors,xVals);
       // l.setExtra(colors,xVals);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setPosition(Legend.LegendPosition.PIECHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(-5f);
        mChart.notifyDataSetChanged();
    }

    private void setData(int count) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (workInfo.getA() != 0) {
            yVals1.add(new Entry((float) workInfo.getA()/workInfo.getDone()*100, 0));
            xVals.add("A");
            colors.add(Color.rgb(143,210,81));
        }
        if (workInfo.getB() != 0) {
            yVals1.add(new Entry((float) workInfo.getB()/workInfo.getDone()*100, 1));
            xVals.add("B");
            colors.add(Color.rgb(0,201,243));
        }
        if (workInfo.getC() != 0) {
            yVals1.add(new Entry((float) workInfo.getC()/workInfo.getDone()*100, 2));
            xVals.add("C");
            colors.add(Color.rgb(255,98,95));
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


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        WorkStatisBean bean = (WorkStatisBean) result;
        if (bean != null && bean.getStatus() == 1) {
            title.setText(bean.getData().getTitle());
            workInfo = bean.getData().getStat();
            initChartView();
            refreshView();
            List<WorkStatisBean.DataEntity.QuizListEntity> list = new ArrayList<>();
            list = bean.getData().getQuiz_list();
            mList.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    public void refreshView(){
        int doneNum = workInfo.getDone();
        int total = workInfo.getTotal();
        radioTv.setText(doneNum+"/"+total);
        scoreTv.setText(workInfo.getScore_avg()+"");

    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }*/
}
