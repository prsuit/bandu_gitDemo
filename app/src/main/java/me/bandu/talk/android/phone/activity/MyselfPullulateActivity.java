package me.bandu.talk.android.phone.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.utils.LogUtils;
import com.hp.hpl.sparta.Text;
import com.umeng.message.proguard.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.WeakSentenceListviewAdapter;
import me.bandu.talk.android.phone.bean.GraphList;
import me.bandu.talk.android.phone.bean.MyselfPullateSingleBean;
import me.bandu.talk.android.phone.bean.MyselfPullulateBean;
import me.bandu.talk.android.phone.impl.MyOnclik;
import me.bandu.talk.android.phone.middle.MyselfPullulateMiddle;
import me.bandu.talk.android.phone.utils.CacheUtils;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.My_Graph;

/**
 * 创建者：王兰新
 * 时间： 2016/5/29
 * 类描述：个人成长页面
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MyselfPullulateActivity extends BaseAppCompatActivity implements MyOnclik, View.OnClickListener {


    @Bind(R.id.tv_work_count)
    TextView tv_work_count;
    @Bind(R.id.tv_score)
    TextView tv_score;
    @Bind(R.id.tv_score_count)
    TextView tv_score_count;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_time_count)
    TextView tv_time_count;
    @Bind(R.id.tv_time_slot)
    TextView tv_time_slot;
    @Bind(R.id.lv_sentence)
    ListView lv_sentence;
    @Bind(R.id.tv_name)
    TextView tv_name;

    private List<GraphList> graphListList;
    private My_Graph graph;
    private int graphHeight;


    private TextView notify_info;
    private TextView tv_work;
    private String info;
    private Intent intent;
    private String t_uid;
    private String uid;
    private String name;
    private MyselfPullulateBean.DataBean data;
    private int width;
    private boolean isReverse;

    @Override
    protected int getLayoutId() {
        intent = getIntent();
        info = intent.getStringExtra("info");
        if (info.equals("m")) {
            setTheme(R.style.AppTheme1);
            return R.layout.activity_myself_pullulate;
        } else {
            setTheme(R.style.AppTheme);
            return R.layout.activity_teacher_pullulate;
        }
    }

    @Override
    protected void toStart() {
        GlobalParams.isUpdateSort = false;
        tv_work = (TextView) findViewById(R.id.tv_work);
        notify_info = (TextView) findViewById(R.id.notify_info);
        graph = (My_Graph) findViewById(R.id.graph);
        showMyprogressDialog();
        if (info.equals("m")) {
            //学生页面过来
           // String classnum = CacheUtils.getInstance().getStringCache(this, "classnum");
            String cid = UserUtil.getUerInfo(this).getCid();
            title_tv.setText("个人成长记录");
            name = UserUtil.getUerInfo(this).getName();
            tv_name.setText(name);
            //第一次进入个人曲线
            String uid = UserUtil.getUid();
            new MyselfPullulateMiddle(this, this).getFirstInfo(new MyselfPullulateBean(), uid, cid);
        } else if (info.equals("t")) {
            //老师页面
            TextView title_middle = (TextView) findViewById(R.id.title_middle);
            ImageView btn_title_left_image = (ImageView) findViewById(R.id.btn_title_left_image);
            btn_title_left_image.setOnClickListener(this);
            t_uid = intent.getStringExtra("uid");
            name = intent.getStringExtra("name");
            String cid = intent.getStringExtra("cid");
            title_middle.setText("个人成长记录" + "(" + name + ")");
            tv_name.setText(name);
            new MyselfPullulateMiddle(this, this).getFirstInfo(new MyselfPullulateBean(), t_uid, cid);
        }
        graph.setMyOnclik(this);
        /*//模拟网络请求数据，这个方法调用可能在onWindowFocusChanged之前或者之后
        int iii = (int) (Math.random() * 10000);
        timer.schedule(task, iii);*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        graphHeight = graph.getHeight();
        width = this.getWindowManager().getDefaultDisplay().getWidth();
        if (isGraphInit())
            initGraph();
    }

    /**
     * 判断初始化图表的两个参数是否已经具备，
     *
     * @return 满足graph舒适化的条件返回true，否则返回false
     */
    public boolean isGraphInit() {
        return !(graphHeight == 0 || null == graphListList || graphListList.size() == 0);
    }

    /**
     * todo
     * 这个方法的调用必须保证graphHeight有值，graphListList里面有数据
     */
    private void initGraph() {
        isReverse = graph.setInit(this.getApplicationContext(), graphHeight, graphListList, width);
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        hideMyprogressDialog();
        //获取数据显示
        if (requestCode == MyselfPullulateMiddle.FIRST_MYSELF_PULLULATE) {
            final MyselfPullulateBean bean = (MyselfPullulateBean) result;
            //第一次进入的数据
            if (bean != null && bean.getStatus() == 1) {
                if (bean.getData().getHomeworks() != null && bean.getData().getHomeworks().size() > 0) {
                    setData(bean);
                    if (isGraphInit())
                        initGraph();
                } else {
                    showDialog();
                }
            }
        } else if (requestCode == MyselfPullulateMiddle.MYSELF_PULLULATE) {
            final MyselfPullateSingleBean bean = (MyselfPullateSingleBean) result;
            //每次点击的数据
            if (bean != null && bean.getStatus() == 1) {
                seClickData(bean);
            }
        }
    }

    public void showDialog() {
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_show, null);
        TextView msg = (TextView) layout.findViewById(R.id.msg);
        msg.setText("本学期" + name + "还没有已完成的作业！");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final AlertDialog dialog1 = dialog.create();
        dialog1.setView(layout);
        dialog1.setCanceledOnTouchOutside(false);
        layout.findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        hideMyprogressDialog();
//        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog1.dismiss();
//            }
//        });
        dialog1.show();
    }

    private void seClickData(MyselfPullateSingleBean bean) {
        MyselfPullateSingleBean.DataBean data = bean.getData();
        tv_score.setText(data.getUser_score() + "分");
        tv_score_count.setText("班平均分：" + data.getClass_avg_score() + "分");
        if(data.getUser_spend() > 0) {
            tv_time.setText(WorkCatalogUtils.getSpeendTime(String.valueOf(data.getUser_spend())));
        }else {
            tv_time.setText("0秒");
        }
        tv_time_count.setText("班平均用时：" + WorkCatalogUtils.getSpeendTime(String.valueOf(data.getClass_avg_spend())));
        //薄弱句子
        List<MyselfPullateSingleBean.DataBean.WeakSentencesBean> weak_sentences = data.getWeak_sentences();
        //listview的数据
        List<MyselfPullateSingleBean.DataBean.WeakSentencesBean> lv_info = new ArrayList<>();
        for (int i = 0; i < weak_sentences.size(); i++) {
            //内部bean
            MyselfPullateSingleBean.DataBean.WeakSentencesBean weakSentencesBean = new MyselfPullateSingleBean.DataBean.WeakSentencesBean();
            weakSentencesBean.setMin_score(weak_sentences.get(i).getMin_score());
            weakSentencesBean.setWords_score(weak_sentences.get(i).getWords_score());
            //句子
            weakSentencesBean.setEn_content(weak_sentences.get(i).getEn_content());
            weakSentencesBean.setSentence_id(weak_sentences.get(i).getSentence_id());
            lv_info.add(weakSentencesBean);
        }
        //薄弱句子设置数据
        WeakSentenceListviewAdapter adapter = new WeakSentenceListviewAdapter(lv_info, this);
        lv_sentence.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
    }

    private void setData(MyselfPullulateBean bean) {
        data = bean.getData();
        List<MyselfPullulateBean.DataBean.HomeworksBean> homeworks = data.getHomeworks();
        tv_work.setText(data.getUser_done() + "次");
        tv_work_count.setText("作业总次数：" + data.getUser_total() + "次");
        tv_score.setText(data.getUser_avg_score() + "分");
        tv_time_slot.setText(data.getDuration());
        tv_score_count.setText("班平均分：" + data.getClass_avg_score() + "分");
        int time = data.getUser_avg_spend();
        int class_time = data.getClass_avg_spend();
        tv_time.setText(WorkCatalogUtils.getSpeendTime(time + ""));
        tv_time_count.setText("班平均用时："+WorkCatalogUtils.getSpeendTime(class_time + ""));
        graphListList = new ArrayList<>();


        for (int i = homeworks.size()-1 ; i >= 0  ; i--) {
            GraphList graphList = new GraphList();
            MyselfPullulateBean.DataBean.HomeworksBean homeworksBean = homeworks.get(i);
            graphList.setColumn(homeworksBean.getScore() + "");
            graphList.setTopNum(homeworksBean.getScore() + "");
            graphList.setJob_id(homeworksBean.getJob_id() + "");
            graphList.setBottomNum(homeworksBean.getCreate_time().substring(5, 7) + "/" + homeworksBean.getCreate_time().substring(8, 10));
            graphListList.add(graphList);
        }
    }

    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        hideMyprogressDialog();
    }

    @Override
    public void myOnclik(String topNum, int position, boolean flag) {
        LogUtils.i("-----" + flag+"----"+position);
        if (!TextUtils.isEmpty(topNum)) {
            if (flag) {
                notify_info.setVisibility(View.GONE);
                lv_sentence.setVisibility(View.VISIBLE);
                tv_score.setText("--分");
                tv_score_count.setText("班平均分：--分");
                tv_work.setText("--次");
                tv_work_count.setText("作业总次数：--次");
                tv_time.setText("--分--秒");
                tv_time_count.setText("班平均用时：--分--秒");
                if (graphListList != null) {
                    String job_id = graphListList.get(position).getJob_id();
                    if (info.equals("m")) {
                        uid = UserUtil.getUerInfo(this).getUid();
                    } else if (info.equals("t")) {
                        uid = t_uid;
                    }
                    //访问网络
                    showMyprogressDialog();
                    new MyselfPullulateMiddle(this, this).getMyselfPullulate(uid, job_id, new MyselfPullateSingleBean());
                }
            } else {
                notify_info.setVisibility(View.VISIBLE);
                lv_sentence.setVisibility(View.GONE);
                tv_work.setText(data.getUser_done() + "次");
                tv_work_count.setText("作业总次数：" + data.getUser_total() + "次");
                tv_score.setText(data.getUser_avg_score() + "分");
                tv_time_slot.setText(data.getDuration());
                tv_score_count.setText("班平均分：" + data.getClass_avg_score() + "分");
                int time = data.getUser_avg_spend();
                int class_time = data.getClass_avg_spend();
                tv_time.setText(WorkCatalogUtils.getSpeendTime(time + ""));
                tv_time_count.setText("班平均用时："+WorkCatalogUtils.getSpeendTime(class_time + ""));
            }
        }
    }

    @Override
    public void onClick(View v) {
        //老师返回键
        finish();
    }
}
