package me.bandu.talk.android.phone.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.activity.SeeStudentWorkActivity;
import me.bandu.talk.android.phone.activity.WorkStatisticsActivity;
import me.bandu.talk.android.phone.adapter.HomeworkSortAdapter;
import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.bean.NameListSortBean;
import me.bandu.talk.android.phone.bean.WeakSentenceBean;
import me.bandu.talk.android.phone.middle.NameListSortMiddle;
import me.bandu.talk.android.phone.middle.WeakSentenceRankMiddle;

/**
 * Created by fanYu on 2016/6/2.
 * 作业排序的fragment
 */
public class HomeworkSortFragment extends BaseFragment implements View.OnClickListener{
    public static final int Type_SCORE = 0;
    public static final int Type_TOTALTIME = 1;
    public static final int Type_UP = 2;
    public static final int Type_COMMITTIME = 3;
    public static final int Type_WEAKSENTENCE=4;

    private ListView listView_sort;
    private View view;
    private HomeworkSortAdapter adapter;
    private RadioGroup rg_info;
    private RadioButton radio_left;
    private RadioButton radio_middle;
    private RadioButton radio_right;
    private TextView tv_sort;
    private String jobId; //作业id
    private NameListSortMiddle middle;
    private WeakSentenceRankMiddle weakMiddle;
    private  List<NameListSortBean.DataBean.ListBean> userinfolist;
    private List<WeakSentenceBean.DataBean.ListBean> weakinfolist;

    public static Class clazz = null;
    public static List dataList = null;

    private String cid;
    private String type;
    private String grade;
    private String sentence_id;
    boolean isOut;
    private  int from;
    private int cid1;
    private int isChecked;
    private int status1=-1;

    boolean isNeedDefault = false;
    int currPosition = -1;
    static final int Left_position = 0;
    static final int Mid_position = 1;
    static final int Right_position = 2;

    public static  HomeworkSortFragment fragment;
    UpdateReceiver receiver;
    public ArrayList<Integer>  selections;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = this;
        if (view != null) {
            ViewGroup parent = null;
            ViewParent v = view.getParent();
            if (v instanceof ViewGroup) {
                parent = (ViewGroup) v;
            }
            if (null != parent) {
                parent.removeView(view);
            }

        } else {
            view = inflater.inflate(R.layout.homework_bigdata, null);
            //初始化操作
            init(view);
        }


        //注册更新界面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("update_homework_sort");
        receiver = new UpdateReceiver();
        getActivity().registerReceiver(receiver,filter);
        return view;
    }

    public int getStatus() {
        return status1;
    }

    public void setStatus(int status1) {
        this.status1 = status1;
    }

    private void init(View view) {



        listView_sort = (ListView) view.findViewById(R.id.lv_listview);
        rg_info = (RadioGroup) view.findViewById(R.id.textinfo);
        radio_left = (RadioButton) view.findViewById(R.id.radio_left);
        radio_middle = (RadioButton) view.findViewById(R.id.radio_middle);
        radio_right = (RadioButton) view.findViewById(R.id.radio_right);
        tv_sort = (TextView) view.findViewById(R.id.tv_sort);

        radio_left.setOnClickListener(this);
        radio_middle.setOnClickListener(this);
        radio_right.setOnClickListener(this);

        cid = ((WorkStatisticsActivity) getActivity()).getCid();
        cid1= Integer.valueOf(((WorkStatisticsActivity) getActivity()).getCid());
        middle = new NameListSortMiddle(this, getActivity());
        weakMiddle=new WeakSentenceRankMiddle(this,getActivity());


        //检查作业界面传递过来的
        jobId = getActivity().getIntent().getStringExtra("jobId");
        isOut = getActivity().getIntent().getBooleanExtra("isOut", true);
        isChecked = getActivity().getIntent().getIntExtra("isChecked", -1);
        //刷新小红点
        if(isChecked != -1)
            GlobalParams.WORK_CHANGED = isChecked;


        if (isOut && getArguments() != null) {
            //是过期的 。就从圆饼图携带的数据做不同的判断来显示不同的排序
            type = getArguments().getString("type");
            grade = getArguments().getString("grade");

        }

        listView_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (userinfolist.size() > position) {
                    NameListSortBean.DataBean.ListBean userlist = userinfolist.get(position);
                    Intent intent = new Intent(getActivity(), SeeStudentWorkActivity.class);
                    intent.putExtra("job_id", jobId);
                    intent.putExtra("stu_job_id", userlist.getStu_job_id() + "");
                    intent.putExtra("isEvaluated", userlist.getEvaluated() == 1 ? true : false);
                    intent.putExtra("status", userlist.getStatus() + "");
                    getActivity().startActivity(intent);
                }
            }
        });

    }
    public void setType (String string) {
        this.type = string;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if( null!=receiver){
            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(GlobalParams.isUpdateSort) {
            if(isOut && getArguments() != null){
                sentence_id = getArguments().getString("sentence_id");
                from = getArguments().getInt("from", -1);
            }
            if ("score".equals(type)) {
                WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
                radio_left.setChecked(true);
                isNeedDefault = true;
                currPosition = Left_position;
                tv_sort.setText("按作业成绩由高到低排序");
                showScore();
            } else if ("time".equals(type)) {
                radio_middle.setChecked(true);
                isNeedDefault = true;
                currPosition = Mid_position;
                WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
                tv_sort.setText("按作业时间由长到短排序");
                showTotalTime();
            } else if ("up".equals(type)) {
                radio_right.setChecked(true);
                isNeedDefault = true;
                currPosition = Right_position;
                WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
                tv_sort.setText("按学生进步程度由高到低排序");
                showUpTime();
            } else if ("weak_sentences".equals(type)) {
                tv_sort.setText("按薄弱句子分数由低到高排序");
                initRadioButton();
                WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
                showWeakSentence();
                listView_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if( weakinfolist != null && weakinfolist.size() > position){
                            WeakSentenceBean.DataBean.ListBean weaklist = weakinfolist.get(position);
                            Intent intent = new Intent(getActivity(), SeeStudentWorkActivity.class);
                            intent.putExtra("job_id", jobId);
                            intent.putExtra("stu_job_id", weaklist.getStu_job_id() + "");
                            intent.putExtra("isEvaluated", weaklist.getEvaluated() == 1 ? true : false);
                            intent.putExtra("status", weaklist.getStatus() + "");
                            getActivity().startActivity(intent);
                        }
                    }
                });

            }else  {
                tv_sort.setText("按完成作业时间由新到旧排序");
                type = "commit";
                showCommitTime();
            }
            ((WorkStatisticsActivity) getActivity()).showMyprogressDialog();
        }else {

            GlobalParams.isUpdateSort =!GlobalParams.isUpdateSort;
        }

    }

    /**
     * 根据类型和等级定位到某一行
     * @param type
     * @param grade
     */
    private void setSelection(String type, String grade) {
        int level = Integer.valueOf(grade);
        if ("score".equals(type)) {
            WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
            tv_sort.setText("按作业成绩由高到低排序");
            int[] levelIndex = getLevelIndex(type);
            switch (level) {
                case 1:
                    listView_sort.setSelection(levelIndex[0]);
                    break;
                case 2:
                    listView_sort.setSelection(levelIndex[1]);
                    break;
                case 3:
                    listView_sort.setSelection(levelIndex[2]);
                    break;
                case 4:
                    listView_sort.setSelection(levelIndex[3]);
                    break;
                case 5:
                    listView_sort.setSelection(levelIndex[4]);
                    break;
            }
        } else if ("time".equals(type)) {
            WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
            tv_sort.setText("按作业时间由长到短排序");
            //时间
            int[] levelIndex = getLevelIndex(type);
            switch (level) {
                case 1:
                    listView_sort.setSelection(levelIndex[0]);
                    break;
                case 2:
                    listView_sort.setSelection(levelIndex[1]);
                    break;
                case 3:
                    listView_sort.setSelection(levelIndex[2]);
                    break;
                case 4:
                    listView_sort.setSelection(levelIndex[3]);
                    break;
                case 5:
                    listView_sort.setSelection(levelIndex[4]);
                    break;
            }
        } else if ("up".equals(type)) {
            WorkStatisticsActivity.activity.tv_WriteComments.setVisibility(View.VISIBLE);
            tv_sort.setText("按学生进步程度由高到低排序");

            int[] levelIndex = getLevelIndex(type);
            switch (level) {
                case 1:
                    listView_sort.setSelection(levelIndex[0]);
                    break;
                case 2:
                    listView_sort.setSelection(levelIndex[1]);
                    break;
                case 3:
                    listView_sort.setSelection(levelIndex[2]);
                    break;
                case 4:
                    listView_sort.setSelection(levelIndex[3]);
                    break;
                case 5:
                    listView_sort.setSelection(levelIndex[4]);
                    break;
            }
        }
    }

    /**
     * 返回每个等级中的索引
     * @param type
     * @return
     */
    private int[] getLevelIndex(String type) {
        int[] levelIndex = new int[5];
        for (int i = 0; i < levelIndex.length; i++) {
            levelIndex[i] = -1;
        }

        int[] levelbest = new int[3];
        for (int i = 0; i < levelbest.length; i++) {
            levelbest[i] = Integer.MIN_VALUE;
        }
        boolean IsNotCompleteFirst = true; // 是否第一个未完成
        boolean IsNotSendFirst = true; // 是否第一个未交

        if(userinfolist!=null){
            int maxtime = -1;
            int mintime = Integer.MAX_VALUE;
            int t = -1;
            if("time".equals(type)){
                for (int i = 0; i < userinfolist.size(); i++) {
                    if(userinfolist.get(i).getStatus()==2){
                        int time = userinfolist.get(i).getTotal_time();
                        if (time > maxtime) {
                            maxtime = time;
                        } else if (time < mintime) {
                            mintime = time;
                        }
                    }
                }
                t = (maxtime - mintime) / 3;
            }

            for (int i = 0; i < userinfolist.size(); i++) {
                if (userinfolist.get(i).getStatus() == 1) {  //未完成（提交了）
                    if (IsNotCompleteFirst) {
                        levelIndex[3] = i;
                        IsNotCompleteFirst = false;
                    }
                }else if(userinfolist.get(i).getStatus() == 0){  //未交
                    if (IsNotSendFirst) {
                        levelIndex[4] = i;
                        IsNotSendFirst = false;
                    }
                } else {
                    if("score".equals(type)){
                        if(userinfolist.get(i).getStatus()==2){
                            int score = userinfolist.get(i).getScore();
                            if (score >= 85 && score > levelbest[0]) {
                                levelbest[0] = score;
                                levelIndex[0] = i;
                            } else if (score >= 55 && score < 85 && score > levelbest[1]) {
                                levelbest[1] = score;
                                levelIndex[1] = i;
                            } else if (score >= 0 && score < 55 && score > levelbest[2]) {
                                levelbest[2] = score;
                                levelIndex[2] = i;
                            }
                        }
                    }else if("time".equals(type)){
                        int time=userinfolist.get(i).getTotal_time();
                        if (time >= mintime && time <= (mintime + t) && time >levelbest [0]){
                            levelbest[0] = time;
                            levelIndex[0] = i;
                        } else if (time >= (mintime + t) && time <=((mintime + t) + t) && time > levelbest[1]){
                            levelbest[1] = time;
                            levelIndex[1] = i;
                        }else if (time >= (mintime + 2*t) && time <= maxtime && time > levelbest[2]){
                            levelbest[2] = time;
                            levelIndex[2] = i;
                        }
                    }else if("up".equals(type)){
                        if(userinfolist.get(i).getStatus()==2){
                            int up_score=userinfolist.get(i).getUp_score();
                            if(up_score > 0 &&  up_score <= 100 && up_score > levelbest[0]){
                                levelbest[0]=up_score;
                                levelIndex[0]=i;
                            }else if(up_score == 0 && up_score > levelbest[1]) {
                                levelbest[1] = up_score;
                                levelIndex[1] = i;
                            }else if(up_score < 0 && up_score > levelbest[2]) {
                                levelbest[2] = up_score;
                                levelIndex[2] = i;
                            }
                        }
                    }
                }
            }
        }
        return levelIndex;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.radio_left:
                if(!isNeedDefault || currPosition != Left_position){
                    radio_left.setChecked(true);
                    tv_sort.setText("按作业成绩由高到低排序");
                    type = "score";
                    showScore();
                    isNeedDefault = true;
                    currPosition = Left_position;
                    break;
                }
                radio_left.setChecked(false);
                tv_sort.setText("按完成作业时间由新到旧排序");
                type = "commit";
                showCommitTime();
                isNeedDefault = !isNeedDefault;
                break;
            case R.id.radio_middle:
                if(!isNeedDefault || currPosition != Mid_position){
                    radio_middle.setChecked(true);
                    tv_sort.setText("按作业时间由长到短排序");
                    type = "time";
                    showTotalTime();
                    isNeedDefault = true;
                    currPosition = Mid_position;
                    break;
                }
                radio_middle.setChecked(false);
                tv_sort.setText("按完成作业时间由新到旧排序");
                type = "commit";
                showCommitTime();
                isNeedDefault = !isNeedDefault;
                break;
            case R.id.radio_right:
                if(!isNeedDefault || currPosition != Right_position){
                    radio_right.setChecked(true);
                    tv_sort.setText("按学生进步程度由高到低排序");
                    type = "up";
                    showUpTime();
                    isNeedDefault = true;
                    currPosition = Right_position;
                    break;
                }
                radio_right.setChecked(false);
                tv_sort.setText("按完成作业时间由新到旧排序");
                type = "commit";
                showCommitTime();
                isNeedDefault = !isNeedDefault;
                break;
        }
        ((WorkStatisticsActivity)getActivity()).showMyprogressDialog();
    }
    public void showWeakSentence(){
        status1 = Type_WEAKSENTENCE;
        weakMiddle.getWeakSentenceRank(jobId,sentence_id);
    }
    public void showCommitTime(){
        if(middle!=null) {
            status1 = Type_COMMITTIME;
            middle.getCommitTimeInfo(jobId);
        }
    }
    private void showScore(){
        status1 = Type_SCORE;
        middle.getScoreInfo(jobId);
    }
    private void showTotalTime(){
        status1 = Type_TOTALTIME;
        middle.getTotalTimeInfo(jobId);
    }
    private void showUpTime(){
        status1 = Type_UP;
        middle.getUpScoreInfo(jobId);
    }
    @Override
    public void onSuccess(Object result, int requestCode) {
        super.onSuccess(result, requestCode);
        try{
            ((WorkStatisticsActivity)getActivity()).hideMyprogressDialog();
        }catch(Exception e){
            e.printStackTrace();
        }

        // 薄弱句子排序的请求码
        if (requestCode == WeakSentenceRankMiddle.Type_WEAKSENTENCERANK) {
            WeakSentenceBean weakBean= (WeakSentenceBean) result;
            if(null != weakBean){
                WeakSentenceBean.DataBean dataBeanWeak=weakBean.getData();
                weakinfolist=dataBeanWeak.getList();
                clazz = WeakSentenceBean.class;
                dataList = weakinfolist;
                if(getActivity() != null){
                    adapter = new HomeworkSortAdapter(getActivity(),weakinfolist,Type_WEAKSENTENCE,cid1);
                    listView_sort.setAdapter(adapter);
                }
            }
        } else {
            //名单页的四种排序
            NameListSortBean bean=(NameListSortBean)result;
            if(bean!=null) {
                NameListSortBean.DataBean dataBean = bean.getData();
                userinfolist = dataBean.getList();
                clazz = NameListSortBean.class;
                dataList = userinfolist;
                rg_info.clearCheck();
                switch (requestCode) {
                    case NameListSortMiddle.Type_COMMITTIME://默认排序，按提交的时间
                        adapter = new HomeworkSortAdapter(getActivity(), userinfolist, Type_COMMITTIME,cid);
                        listView_sort.setAdapter(adapter);
                        break;
                    case NameListSortMiddle.Type_SCORE: //分数排序
                        adapter=new HomeworkSortAdapter(getActivity(),userinfolist,Type_SCORE,cid);
                        listView_sort.setAdapter(adapter);
                        radio_left.setChecked(true);
                        break;
                    case NameListSortMiddle.Type_TOTALTIME://用时的时间
                        adapter = new HomeworkSortAdapter(getActivity(),userinfolist,Type_TOTALTIME,cid);
                        listView_sort.setAdapter(adapter);
                        radio_middle.setChecked(true);
                        break;
                    case NameListSortMiddle.Type_UP: //up指数
                        adapter = new HomeworkSortAdapter(getActivity(),userinfolist,Type_UP,cid);
                        listView_sort.setAdapter(adapter);
                        radio_right.setChecked(true);
                        break;
                }
            }
        }
        if(from != WorkStatisticsFragment.FROM_WLX_FRAGMENT && from == -1 && !"weak_sentences".equals(type)){
            if("time".equals(type)){
                type = "time";
                grade = "1";
                from = WorkStatisticsFragment.FROM_WLX_FRAGMENT;
            }else if("score".equals(type)){
                type = "score";
                grade = "1";
                from = WorkStatisticsFragment.FROM_WLX_FRAGMENT;
            }else if("up".equals(type)){
                type = "up";
                grade = "1";
                from = WorkStatisticsFragment.FROM_WLX_FRAGMENT;
            }else if("commit".equals(type)){
                type = "commit";
                from = WorkStatisticsFragment.FROM_WLX_FRAGMENT;
            }
        }

        if(from == WorkStatisticsFragment.FROM_WLX_FRAGMENT){
            setSelection(type, grade);
            from = -2;
        }
    }
    @Override
    public void onFailed(int requestCode) {
        super.onFailed(requestCode);
        if(null != getActivity()){
            ((WorkStatisticsActivity)getActivity()).hideMyprogressDialog();
            UIUtils.showToastSafe("网络异常，请检查网络");
        }

    }
    public void setTvSort(String text) {
        if(tv_sort!=null){
            tv_sort.setText(text);
        }
    }

    public void initRadioButton() {
        if( radio_left!=null && radio_right!=null && radio_middle!=null){
            radio_left.setChecked(false);
            radio_middle.setChecked(false);
            radio_right.setChecked(false);
            isNeedDefault = false;
        }
    }
    public class UpdateReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            selections = getActivity().getIntent().getIntegerArrayListExtra("selections");
            status1 =  intent.getIntExtra("status1", -1);
            switch (action) {
                case "update_homework_sort":
//                    if(selections!=null){
                    switch (status1) {
                        case Type_SCORE: // 分数排序
                            showScore();
                            break;
                        case Type_TOTALTIME: // 时间排序
                            showTotalTime();
                            break;
                        case Type_UP: // UP排序
                            showUpTime();
                            break;
                        case Type_COMMITTIME: // 默认排序
                            showCommitTime();
                            break;
                        case Type_WEAKSENTENCE: // 薄弱排序
                            showWeakSentence();
                            break;
                    }
//                     }
                    break;
            }
        }
    }
}
