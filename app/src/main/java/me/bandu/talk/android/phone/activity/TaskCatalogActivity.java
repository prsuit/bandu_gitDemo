package me.bandu.talk.android.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.DFHT.base.engine.BaseActivityIF;
import com.DFHT.net.EasyNetAsyncTask;
import com.DFHT.net.NetworkUtil;
import com.DFHT.net.engine.NetCallback;
import com.DFHT.net.param.EasyNetParam;
import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;
import com.alibaba.fastjson.JSONObject;
import com.chivox.ChivoxConstants;
import com.DFHT.voiceengine.OnSpeechEngineLoaded;
import com.chivox.utils.ChivoxCreateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.DoWorkListAdapter;
import me.bandu.talk.android.phone.adapter.WorkCatalogAdapter;
import me.bandu.talk.android.phone.bean.ChooseengineBean;
import me.bandu.talk.android.phone.bean.CreatWorkContentsBean;
import me.bandu.talk.android.phone.bean.Detail;
import me.bandu.talk.android.phone.bean.GetKeyBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogEntity;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
import me.bandu.talk.android.phone.bean.LoginBean;
import me.bandu.talk.android.phone.dao.Quiz;
import me.bandu.talk.android.phone.greendao.bean.BestSentenceBean;
import me.bandu.talk.android.phone.greendao.bean.TaskDirectoryBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.utils.DBUtils;
import me.bandu.talk.android.phone.middle.CreatWorkContentMiddle;
import me.bandu.talk.android.phone.middle.WorkCatalogMiddle;
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.DateUtils;
import me.bandu.talk.android.phone.utils.DialogUtils;
import me.bandu.talk.android.phone.utils.UserUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.AlertDialogUtils;
import me.bandu.talk.android.phone.view.MyScrollList;
import me.bandu.talk.android.phone.voiceengine.VoiceEngCreateUtils;

/**
 * 创建者：wanglei
 * <p>时间：16/7/15  17:46
 * <p>类描述：作业目录页
 * <p>修改人：TaskCatalogVideoActivity
 * <p>修改时间：
 * <p>修改备注：作业的单个类型，只要提交过，isDone就会变成true
 */
public class TaskCatalogActivity extends BaseAppCompatActivity implements DBUtils.inter, DBUtils.SubmitImp, BaseActivityIF, OnClickListener, WorkCatalogAdapter.GetSingleTypeSentence, OnSpeechEngineLoaded, DBUtils.DBSaveOk {

    private static final int ISHAVETASK = 107;//是否下载过当前作业中的题
    private static final int DIRECTORYLIST = 108;//获取当前目录页的目录列表信息
    private static final int ACTIVITYRESULT = 109;//做作业回来之后重新获取数据
    private static final int ACTIVITYRESULTSUBMIT = 110;//做作业回来之后重新获取数据
    private static final int SUBMITTASKCLEARSUBMITTASK = 111;//
    private static final int HONGGOONACTIVITYRESULT = 112;//
    private static final int ADDSENTENCEOK = 113;//
    private static final int UPDATESENTENCE = 114;//
    public static final int DOWORK_CODE = 100;


    @Bind(R.id.catalog_result_scrollView)
    ScrollView catalog_result_scrollView;
    @Bind(R.id.listview)
    MyScrollList listView;
    @Bind(R.id.notify_info)
    TextView notify_info;
    @Bind(R.id.bigtitle)
    TextView bigtitle;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.level)
    TextView level;
    @Bind(R.id.commit_image)
    ImageView commit_image;
    @Bind(R.id.commit_score)
    TextView commit_score;
    @Bind(R.id.commit_time)
    TextView commit_time;
    /**
     * 耗时
     */
    @Bind(R.id.spend_time)
    TextView spend_time;
    /**
     * 提交按钮
     */
    @Bind(R.id.tv_upload)
    TextView tv_upload;
    @Bind(R.id.bottom_tv_notify_info)
    TextView bottom_tv_notify_info;
    @Bind(R.id.commit_icon)
    RelativeLayout layout;
    /**
     * 最上面的那个布局，包括圆圈分数的那个
     */
    @Bind(R.id.catalog_result)
    RelativeLayout catalog_result;
    @Bind(R.id.linear_commit_time)
    LinearLayout linear_commit_time;
    @Bind(R.id.linear_description)
    LinearLayout linear_description;
    private LoginBean.DataEntity uerInfo;

    /**
     * 0就选用驰声
     * 1就选用新的
     */
    private int voiceEngineOption = -1;

    /**
     * 上一个页过来穿的参数
     */
    private Bundle data;
    //    private String uid;//用户id
//    private boolean isDone;
    private String job_id;//作业id，老师学生一样的
    private long stu_job_id;//作业id，同一份作业，但是不同的学生作业id不同
    private long quiz_id;
    private String quiz_type;
    private String description_net;
    private int percent;//百分比
    private boolean haveTask;//是否下载过题，假、句子下载过，真、没有下载过
    private long taskNum;//作业分数
    private String taskName;//作业名
    private long taskTime;//作业总耗时
    private String taskLastSubmitTime;//上一次的作业提交时间

    /**
     * 在adapter中点击的类型
     */
    private long currentType;

    /**
     * 是否完成了至少一遍作业，真完成，假没完成
     */
    boolean completeTask = false;
    /**
     * 上一个页面传过来的，true作业完成了一遍，false没有完成至少一遍
     */
    boolean isDone = false;
    /**
     * 作业的总耗时，每次提交之后都更新为0的值
     */
    private long taskTimeCurrent1;
    private String level1;        //作业的等级要求
    private String taskExplain;//作业说明
    private List<BestSentenceBean> bsb;
    private boolean clicState = false;

    /**
     * adapter的数据源，等同高楠的data_db
     */
    private List<Quiz> adapterList;
    private WorkCatalogAdapter adapter;
    private WorkCatalogMiddle middle;
    private Map<String, Integer> map;
    /**
     * 需要上传的作业
     */
    private List<BestSentenceBean> submitTask;
    private List<TaskDirectoryBean> tdbList;
    private Detail detail;
    private static long spendtime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_catalog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void toStart() {
        EventBus.getDefault().register(this);//在要接收消息的页面注册
        showMyprogressDialog();
        catalog_result_scrollView.smoothScrollTo(0, 0);
        uerInfo = UserUtil.getUerInfo(this);
        Animation3DUtil.getInstance().setView(layout, commit_image, commit_score);
        data = getIntent().getBundleExtra("data");
        if (data != null) {
//            uid = data.getString("uid");
            isDone = data.getBoolean("isdone");
            job_id = data.getString("job_id");//14236
            stu_job_id = Long.valueOf(data.getString("stu_job_id"));//244881
//            if (isDone)
//                percent = 100;
//            else
            percent = data.getInt("percent"); //设置notify_info作业完成百分比
//            score = data.getInt("score"); //设置notify_info作业完成百分比
        }
        setHeadLayoutGone(false);
        middle = new WorkCatalogMiddle(this, this);
        adapterList = new ArrayList<>();
        showMyprogressDialog();
        DBUtils.getDbUtils().isHaveTask(this, ISHAVETASK, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
        tv_upload.setOnClickListener(this);
        commit_image.setOnClickListener(this);
        commit_score.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) {
            switch (view.getId()) {
                case R.id.tv_upload:
//                    tv_upload.setClickable(false);
                    submitBut();
//                    adapter.notifyDataSetChanged();
                    break;
                case R.id.commit_image:
                    Animation3DUtil.getInstance().imageStart();
                    break;
                case R.id.commit_score:
                    Animation3DUtil.getInstance().textStart();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showMyprogressDialog();
        if (data != null) {
            long quiz_id = data.getLongExtra("quiz_id", -1);
            long spendtime = (data.getLongExtra("spend_time", -1) + 500) / 1000;//耗时
            boolean isdone = data.getBooleanExtra("isdone", false);//真完成当前遍，假没完成当前遍
            int currentType = Integer.valueOf(data.getStringExtra("currentType"));
            switch (currentType) {//0:跟读 1:背诵 2:朗读     wo  1：跟读！2：朗读！3：背诵
                case 0:
                    currentType = 1;
                    break;
                case 1:
                    currentType = 3;
                    break;
                case 2:
                    currentType = 2;
                    break;
            }
            DBUtils.getDbUtils().getSUbmitTaskInfo(this, ACTIVITYRESULTSUBMIT, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, quiz_id, currentType, spendtime, isdone);
        }
    }

    public void onEventMainThread(Bundle data) {
        showMyprogressDialog();
        if (data != null) {
//            long quiz_id1 = data.get("quiz_id");
//            data.get

            long quiz_id = data.getLong("quiz_id", -1);
            spendtime += (data.getLong("spend_time", -1) + 500) / 1000;//耗时
            boolean isdone = data.getBoolean("isdone", false);//真完成当前遍，假没完成当前遍
            int currentType = Integer.valueOf(data.getString("currentType"));
            switch (currentType) {//0:跟读 1:背诵 2:朗读     wo  1：跟读！2：朗读！3：背诵
                case 0:
                    currentType = 1;
                    break;
                case 1:
                    currentType = 3;
                    break;
                case 2:
                    currentType = 2;
                    break;
            }
            DBUtils.getDbUtils().getSUbmitTaskInfo(this, ACTIVITYRESULTSUBMIT, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, quiz_id, currentType, spendtime, isdone);
        }


//        String msg = "onEventMainThread收到了消息：" + event.getMsg();
//        Log.d("harvic", msg);
//        tv.setText(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getList(final List<?> list, final boolean bb, final int sign) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (sign == SUBMITTASKCLEARSUBMITTASK && bb) {
//                    if (!taskComplete) {
                    if (adapter.isHaveHomeWorkNum() || completeTask) {
                        middle.setFinishWorkNew(uerInfo.getUid(), String.valueOf(stu_job_id));
                    } else {
                        hideMyprogressDialog();
                        setHeadLayout();
                    }
                } else if (sign == HONGGOONACTIVITYRESULT) {
                    if (!haveTask) {
                        List<String> quizlist = new ArrayList<>();
                        for (Quiz q : adapterList) {
                            quizlist.add(String.valueOf(q.getQuiz_id()));
                        }
                        new CreatWorkContentMiddle(TaskCatalogActivity.this, TaskCatalogActivity.this).getWorkSentenceList(quizlist);
                    } else
                        DBUtils.getDbUtils().getDirectoryListInfo(TaskCatalogActivity.this, DIRECTORYLIST, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
                } else if (sign == ADDSENTENCEOK) {
                    DBUtils.getDbUtils().getDirectoryListInfo(TaskCatalogActivity.this, DIRECTORYLIST, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
                } else if (sign == UPDATESENTENCE && bb) {
                    goToDoWorkActivity(detail);
                } else if (sign == UPDATESENTENCE && !bb) {
                    clicState = true;
                    onLoadSuccess(1);
                }
            }
        });
    }

    /**
     * @param list          要提交的作业
     * @param completeTask1 是否至少完成过一遍作业，真完成了，假没有
     * @param sign          标识
     */
    @Override
    public void isHaveNum(final int state, final List<?> list, final boolean completeTask1, final TaskListBean taskListBean, final int sign) {
        UIUtils.runInMainThread(new Runnable() {

            private boolean haveTask;

            @Override
            public void run() {
                if (sign == ISHAVETASK && state == 1) {
                    if (list != null && list.size() > 0)
                        submitTask = (List<BestSentenceBean>) list;
                    completeTask = completeTask1;//是否至少完成过一遍作业，真完成了，假没有
//                    taskTimeCurrent1 = taskListBean.getTaskTimeCurrent();
//                    level1 = taskRequirement;//作业等级要求
//                    taskExplain = taskExplai1;//作业说明
                    haveTask = taskListBean.isHaveTask();
                    isHaveTask(haveTask);//当前作业是否下载过所有的句子
                    return;
                }
                hideMyprogressDialog();
            }
        });
    }

    @Override
    public void submitImp(final List<TaskDirectoryBean> tdbList, final TaskListBean tlb, final int sign) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                switch (sign) {
                    case DIRECTORYLIST:
                        goOnDirectory(tdbList, tlb, 0);
                        String time = WorkCatalogUtils.getSpeendTimeStr(String.valueOf(taskTime));
                        spend_time.setText(time.equals("") ? "0" : time);//作业耗时
                        break;
                    case ACTIVITYRESULT:
                        goOnDirectory(tdbList, tlb, 1);
                        break;
                }
            }
        });
    }

    @Override
    public void myResult(final List<BestSentenceBean> submitTaskList, long average, final int sign, final long taskTime, final long taskTimeCurrent, final boolean completeTask1) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                taskTimeCurrent1 = taskTimeCurrent;
                long i = taskTime;
                LogUtils.e("界面返回的数据库提交时间 = " + taskTimeCurrent1);
                Log.e("time", "run: "+taskTimeCurrent1+"" );
                Log.e("time", "run: --i"+i+"" );
                completeTask = completeTask1;
                if (sign == ACTIVITYRESULTSUBMIT /*&& submitTaskList != null && submitTaskList.size() > 0*/) {
                    DBUtils.getDbUtils().getDirectoryListInfo(TaskCatalogActivity.this, ACTIVITYRESULT, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
                    submitTask = submitTaskList;
                } else
                    hideMyprogressDialog();
            }
        });
    }

    @Override
    public void saveOK() {
        DBUtils.getDbUtils().getDirectoryListInfo(this, DIRECTORYLIST, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }

    /**
     * 当前列表的句子没有下载过，去下载
     */
    public void getNetCatlogInfo() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                middle.getWorkCatalog(uerInfo.getUid(), job_id, stu_job_id);
            }
        });
    }

    @Override
    public void getSingleTypeSentence(final List<BestSentenceBean> bsb, long currentType, int state, long quiz_id, boolean clicState1, String quiz_type, String description_net) {
        this.bsb = bsb;
        this.currentType = currentType;
        this.quiz_id = quiz_id;
        this.quiz_type = quiz_type;
        this.description_net = description_net;
        clicState = clicState1;
        middle.getTaskDetail(uerInfo.getUid(), stu_job_id, bsb.get(0).getHw_quiz_id());
    }

    @Override
    public void setSubmitState() {
        setSubmitButState();
    }

    @Override
    public void onSuccess(Object result, int requestCode) {
        switch (requestCode) {
            case WorkCatalogMiddle.WORK_CATALOG:
                addDirectoryDB(result);
                break;
            case CreatWorkContentMiddle.CREAT_WORK_CONTENT:
                addBestenceDB(result);
                break;
            case WorkCatalogMiddle.UPLOAD_TEXT:
                //3.4.4修改计时
                taskLastSubmitTime = DateUtils.getCurrentTimeText("yyyy-MM-dd HH:mm:ss");
                DBUtils.getDbUtils().updateSubmitTaskState(this, SUBMITTASKCLEARSUBMITTASK, submitTask, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, taskLastSubmitTime,spendtime+taskTime);
                String time = WorkCatalogUtils.getSpeendTimeStr(String.valueOf(taskTime + spendtime));
                spend_time.setText(time.equals("") ? "0" : time);//作业耗时
                spendtime = 0L;
                break;
            case WorkCatalogMiddle.UPLOAD_TEXT_V3:
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        setHeadLayout();
                        goToRankActivity();
                    }
                }, 1200);
                break;
            case WorkCatalogMiddle.DETAIL:
                detail = (Detail) result;
                if (TextUtils.isEmpty(ChivoxConstants.secretKey))
                    getKey();
                else{
                    int engine = voiceEngineOption != 1 ? ChivoxCreateUtil.ENGINE_CHIVOX : ChivoxCreateUtil.ENGINE_SK;
                    VoiceEngCreateUtils.createEnginAndAIRecorder(TaskCatalogActivity.this, engine);
                }
//                    ChivoxCreateUtil.createEnginAndAIRecorder(this);
                break;
            case WorkCatalogMiddle.CHOOSEENGINE:
                ChooseengineBean bean = (ChooseengineBean) result;
                if (bean.getStatus() == 1 || bean.getStatus() == 2) {
                    voiceEngineOption = bean.getData().getEngine();
                    if (null != adapter)
                        adapter.setVoiceEngineOption(voiceEngineOption);
                }
                break;
        }
    }

    public void getKey() {
        Map map = new HashMap();
        map.put("pkg", this.getPackageName());
        EasyNetParam param = new EasyNetParam(ConstantValue.GET_KEY, map, new GetKeyBean());
        new EasyNetAsyncTask(-52, new NetCallback() {
            @Override
            public void success(Object result, int requestCode) {
                if (requestCode == -52) {
                    GetKeyBean bean = (GetKeyBean) result;
                    ChivoxConstants.secretKey = bean.getData();
//                    ChivoxCreateUtil.createEnginAndAIRecorder(TaskCatalogActivity.this);
                    int engine = voiceEngineOption != 1 ? ChivoxCreateUtil.ENGINE_CHIVOX : ChivoxCreateUtil.ENGINE_SK;
                    VoiceEngCreateUtils.createEnginAndAIRecorder(TaskCatalogActivity.this, engine);
//                    ChivoxCreateUtil.createEnginAndAIRecorder(TaskCatalogActivity.this, engine);
                }
            }

            @Override
            public void failed(int requestCode) {
                DialogUtils.hideMyprogressDialog(TaskCatalogActivity.this);
                UIUtils.showToastSafe("请检查网络是否畅通");
            }
        }).execute(param);
    }

    @Override
    public void onLoadSuccess(int state) {
        if (clicState) {
            clicState = false;
            Detail.DataEntity data = detail.getData();
            Detail myDetail = null;
            if (state == 1 && data != null) {
                List<Detail.DataEntity.ListEntity> list = data.getList();
                if (list != null && list.size() > 0) {
                    for (Detail.DataEntity.ListEntity ddl : list) {
                        ddl.setCurrent_type(TextUtils.isEmpty(ddl.getStu_mp3()) ? DoWorkListAdapter.WORK_NOTDONE : DoWorkListAdapter.WORK_DONE);
                        ddl.setCurrent_score(ddl.getStu_score());
                        ddl.setCurrent_mp3(ddl.getStu_mp3());
                        ddl.setCurrent_stu_seconds(ddl.getSeconds());
                        ddl.setCurrent_words_score(ddl.getWords_score());
                        ddl.setStu_job_id(stu_job_id);
                        ddl.setQuiz_id(quiz_id);
                    }
                    setDBInfo(Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, list);
                }
            } else {
                myDetail = new Detail();
                List<Detail.DataEntity.ListEntity> entities = new ArrayList<Detail.DataEntity.ListEntity>();
                myDetail.setData(new Detail.DataEntity());
                myDetail.getData().setList(entities);
                for (BestSentenceBean bsb1 : bsb) {
                    Detail.DataEntity.ListEntity le = new Detail.DataEntity.ListEntity();
                    le.setEn(bsb1.getText());
                    le.setMp3(bsb1.getNetAddress());
                    le.setSentence_id(bsb1.getSentenceID());
                    le.setHw_quiz_id(bsb1.getHw_quiz_id());
                    le.setStu_job_id(bsb1.getTaskID());
                    le.setQuiz_id(bsb1.getPartID());
                    le.setStu_done(false);
                    le.setAnswer(bsb1.getAnswer());
                    le.setStu_mp3(bsb1.getMyAddress());
                    le.setWords_score(bsb1.getTextColor());
                    le.setCurrent_words_score(bsb1.getTextColor());
                    le.setSeconds((int) bsb1.getMyVoiceTime());
                    le.setCurrent_stu_seconds((int) bsb1.getMyVoiceTime());
                    le.setStu_score((int) bsb1.getMyNum());
                    le.setCurrent_mp3(TextUtils.isEmpty(bsb1.getMyAddress()) ? bsb1.getChishengNetAddress() : bsb1.getMyAddress());
                    le.setCurrent_score((int) bsb1.getMyNum());
                    le.setCurrent_type(TextUtils.isEmpty(bsb1.getMyAddress()) && TextUtils.isEmpty(bsb1.getChishengNetAddress()) ? DoWorkListAdapter.WORK_NOTDONE : DoWorkListAdapter.WORK_DONE);
                    le.setMp3_url(bsb1.getChishengNetAddress());
                    entities.add(le);
                }
                goToDoWorkActivity(myDetail);
            }
        }
        hideMyprogressDialog();
    }

    public void goToDoWorkActivity(Detail myDetail) {
        Intent intent;
        if (quiz_type != null && quiz_type.contains("video"))
            intent = new Intent(this, TaskCatalogVideoActivity.class);
        else
            intent = new Intent(this, DoWorkActivity.class);
        intent.putExtra("quiz_id", quiz_id);//就是partID
        intent.putExtra("state", -1);//驰声本地状态，不用管
        intent.putExtra("classID", Long.valueOf(uerInfo.getCid()));
        intent.putExtra("detail", myDetail);
        intent.putExtra("showBestWork", false);//已经定死
        intent.putExtra("stu_job_id", stu_job_id);//已经定死
        intent.putExtra("description", description_net);
        intent.putExtra("quiz_type", quiz_type);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("doTheHomework", this);
//        intent.putExtra("bundle",bundle);
        intent.putExtra("currentType", String.valueOf(currentType == 1 ? 0 : currentType == 2 ? 2 : currentType == 3 ? 1 : 0));//已经完事，247onClick方法体中
        startActivity(intent);
//        startActivityForResult(intent, TaskCatalogActivity.DOWORK_CODE);
    }

    @Override
    public void onLoadError() {
        DialogUtils.hideMyprogressDialog(this);//关闭dialog
        UIUtils.showToastSafe("评分系统启动失败,请检查网络");
    }

    /**
     * 网络请求成功获取数据
     */
    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
//        hideMyprogressDialog();
    }

    /**
     * 网络请求失败
     */
    @Override
    public void failedFrom(Object... obj) {
        hideMyprogressDialog();
    }

    @Override
    public void onFailed(int requestCode) {//110
        hideMyprogressDialog();
    }

    /**
     * 往最好句子表里存数据
     */
    private void addBestenceDB(Object result) {
        CreatWorkContentsBean bean = (CreatWorkContentsBean) result;
        List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list = bean.getData().getQuiz_list();
        List<BestSentenceBean> bestSentenceList = new ArrayList<>();
        for (int i = 0; i < quiz_list.size(); i++) {
            List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list = quiz_list.get(i).getSentence_list();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                for (int j = 0; j < sentence_list.size(); j++) {
                    String quiz_id = quiz_list.get(i).getQuiz_id();
                    if (entry.getKey().equals(quiz_id)) {
                        if ((int) entry.getValue() == 1) {//1 reading 2 Recite 4 Repeat
                            saveReading(quiz_list, bestSentenceList, i, sentence_list, j);
                        } else if ((int) entry.getValue() == 2) {
                            saveRecite(quiz_list, bestSentenceList, i, sentence_list, j);
                        } else if ((int) entry.getValue() == 3) {
                            saveReading(quiz_list, bestSentenceList, i, sentence_list, j);
                            saveRecite(quiz_list, bestSentenceList, i, sentence_list, j);
                        } else if ((int) entry.getValue() == 4) {
                            saveRepeat(quiz_list, bestSentenceList, i, sentence_list, j);
                        } else if ((int) entry.getValue() == 5) {
                            saveReading(quiz_list, bestSentenceList, i, sentence_list, j);
                            saveRepeat(quiz_list, bestSentenceList, i, sentence_list, j);
                        } else if ((int) entry.getValue() == 6) {
                            saveRecite(quiz_list, bestSentenceList, i, sentence_list, j);
                            saveRepeat(quiz_list, bestSentenceList, i, sentence_list, j);
                        } else if ((int) entry.getValue() == 7) {
                            saveReading(quiz_list, bestSentenceList, i, sentence_list, j);
                            saveRecite(quiz_list, bestSentenceList, i, sentence_list, j);
                            saveRepeat(quiz_list, bestSentenceList, i, sentence_list, j);
                        }
                    }
                }
            }
        }
        for (BestSentenceBean bsb : bestSentenceList) {
            bsb.setHw_quiz_id(partMap.get(bsb.getPartID()).get(bsb.getType()));
        }
        DBUtils.getDbUtils().addSentence(this, ADDSENTENCEOK, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, bestSentenceList, this);
    }

    Map<Long, Map<Long, Long>> partMap;

    /**
     * 往作业目录的存数据存
     */
    private void addDirectoryDB(Object result) {

        HomeWorkCatlogBean bean = (HomeWorkCatlogBean) result;
        if (bean.getData() == null) {
            setHeadLayout();
            return;
        }
        List<HomeWorkCatlogQuiz> quizs1 = bean.getData().getHomework().getQuizs();
        Map<Long, Long> typeMap;//Map<type,hw_quiz_id>
        partMap = new HashMap<>();//Map<partID,Map<type,hw_quiz_id>>
        if (bean != null && bean.getStatus() == 1) {
            List<HomeWorkCatlogQuiz> quizs = bean.getData().getHomework().getQuizs();
            List<TaskDirectoryBean> set_db_list = new ArrayList<>();
            map = new HashMap<>();
//            setTopInfo(bean.getData().getHomework());
            for (int i = 0; i < quizs.size(); i++) {
                typeMap = new HashMap<>();
                int temp = 0;
                Quiz data_bean = new Quiz();
                HomeWorkCatlogQuiz catlogQuiz = quizs.get(i);
                HomeWorkCatlogEntity homework = bean.getData().getHomework();
                if (catlogQuiz.getReading().getHw_quiz_id() != 0) {
                    temp += 1;
                    set_db_list.add(setBean(2, Long.valueOf(catlogQuiz.getQuiz_id()), catlogQuiz.getName(), catlogQuiz.getReading().getTimes(), catlogQuiz.getReading().is_done(), catlogQuiz));
                    data_bean.setReadingIsDone(catlogQuiz.getReading().is_done());
                    data_bean.setRead_count(0);
                    data_bean.setRead_quiz_id(catlogQuiz.getReading().getHw_quiz_id());
                    data_bean.setRead_times(catlogQuiz.getReading().getTimes());
                    typeMap.put(2l, (long) catlogQuiz.getReading().getHw_quiz_id());
                }
                if (catlogQuiz.getRecite().getHw_quiz_id() != 0) {
                    temp += 2;
                    set_db_list.add(setBean(3, Long.valueOf(catlogQuiz.getQuiz_id()), catlogQuiz.getName(), catlogQuiz.getRecite().getTimes(), catlogQuiz.getRecite().is_done(), catlogQuiz));
                    data_bean.setRecite_count(0);
                    data_bean.setReciteIsDone(catlogQuiz.getRecite().is_done());
                    data_bean.setRecite_quiz_id(catlogQuiz.getRecite().getHw_quiz_id());
                    data_bean.setRecite_times(catlogQuiz.getRecite().getTimes());
                    typeMap.put(3l, (long) catlogQuiz.getRecite().getHw_quiz_id());
                }
                if (catlogQuiz.getRepeat().getHw_quiz_id() != 0) {
                    temp += 4;
                    set_db_list.add(setBean(1, Long.valueOf(catlogQuiz.getQuiz_id()), catlogQuiz.getName(), catlogQuiz.getRepeat().getTimes(), catlogQuiz.getRepeat().is_done(), catlogQuiz));
                    data_bean.setRepeat_count(0);
                    data_bean.setRepeatIsDone(catlogQuiz.getRepeat().is_done());
                    data_bean.setRepeat_quiz_id(catlogQuiz.getRepeat().getHw_quiz_id());
                    data_bean.setRepeat_times(catlogQuiz.getRepeat().getTimes());
                    typeMap.put(1l, (long) catlogQuiz.getRepeat().getHw_quiz_id());
                }
                data_bean.setQuiz_id(Long.valueOf(catlogQuiz.getQuiz_id()));
                data_bean.setScore((int) taskNum);
                data_bean.setStu_job_id(stu_job_id);
                data_bean.setTitle(homework.getTitle());
                data_bean.setDescription(catlogQuiz.getDescription());
                data_bean.setQuiz_type(catlogQuiz.getQuiz_type());
                data_bean.setLevel(homework.getLevel());
                data_bean.setCommit_time(homework.getCommit_time());
                data_bean.setSpend_time(homework.getSpend_time());
                data_bean.setQuiz_name(catlogQuiz.getName());
                data_bean.setUpload_spend_time("0");
                adapterList.add(data_bean);
                map.put(catlogQuiz.getQuiz_id(), temp);
                partMap.put(Long.valueOf(catlogQuiz.getQuiz_id()), typeMap);
            }
            HomeWorkCatlogEntity homework = bean.getData().getHomework();
            DBUtils.getDbUtils().addDirectoryListInfos(this, HONGGOONACTIVITYRESULT, set_db_list, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id,
                    getLevel(homework.getLevel()), homework.getTotal_time(), homework.getScore(), homework.getCommit_time(),
                    homework.getTitle(), homework.getDescription());
        }
    }

    public TaskDirectoryBean setBean(long type, long l, String name, int times, boolean is_done, HomeWorkCatlogQuiz catlogQuiz) {
        TaskDirectoryBean myBean = new TaskDirectoryBean();
        myBean.setClassID(Long.parseLong(uerInfo.getCid()));
        myBean.setUserId(Long.parseLong(uerInfo.getUid()));
        myBean.setTaskID(stu_job_id);
        myBean.setUnitID(0);
        myBean.setLesenID(0);
        myBean.setType(type);
        myBean.setPartID(l);
        myBean.setIsDone(is_done);
        myBean.setPartName(name);
        myBean.setRepeatTotal(times);
        if (!haveTask && is_done)
            myBean.setRepeatCurrent(times);
        myBean.setRepeatTime(0);
        myBean.setQuiz_type(catlogQuiz.getQuiz_type());
        myBean.setDescription(catlogQuiz.getDescription());
        return myBean;
    }

    private void saveReading(List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list, List<BestSentenceBean> set_db_list, int i, List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list, int j) {
        BestSentenceBean set_db_data = new BestSentenceBean();
        set_db_data.setUserId(Long.parseLong(uerInfo.getUid()));
        set_db_data.setClassID(Long.parseLong(uerInfo.getCid()));
        set_db_data.setTaskID(stu_job_id);
        set_db_data.setPartID(Long.parseLong(quiz_list.get(i).getQuiz_id()));
        set_db_data.setText(sentence_list.get(j).getEn());
        set_db_data.setType(2);
        set_db_data.setSentenceID(sentence_list.get(j).getSentence_id());
        set_db_data.setTextColor("");
        set_db_data.setNetAddress(sentence_list.get(j).getMp3());
        set_db_data.setMyAddress("");
        set_db_data.setChishengNetAddress("");
        set_db_data.setMyNum(0);
        set_db_data.setState(false);
        set_db_data.setAnswer(sentence_list.get(j).getAnswer());
        set_db_list.add(set_db_data);
    }

    private void saveRecite(List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list, List<BestSentenceBean> set_db_list, int i, List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list, int j) {
        BestSentenceBean set_db_data = new BestSentenceBean();
        set_db_data.setUserId(Long.parseLong(uerInfo.getUid()));
        set_db_data.setClassID(Long.parseLong(uerInfo.getCid()));
        set_db_data.setTaskID(stu_job_id);
        set_db_data.setPartID(Long.parseLong(quiz_list.get(i).getQuiz_id()));
        set_db_data.setText(sentence_list.get(j).getEn());
        set_db_data.setType(3);
        set_db_data.setSentenceID(sentence_list.get(j).getSentence_id());
        set_db_data.setTextColor("");
        set_db_data.setNetAddress(sentence_list.get(j).getMp3());
        set_db_data.setMyAddress("");
        set_db_data.setChishengNetAddress("");
        set_db_data.setMyNum(0);
        set_db_data.setState(false);
        set_db_data.setAnswer(sentence_list.get(j).getAnswer());
        set_db_list.add(set_db_data);
    }

    private void saveRepeat(List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list, List<BestSentenceBean> set_db_list, int i, List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list, int j) {
        BestSentenceBean set_db_data = new BestSentenceBean();
        set_db_data.setUserId(Long.parseLong(uerInfo.getUid()));
        set_db_data.setClassID(Long.parseLong(uerInfo.getCid()));
        set_db_data.setTaskID(stu_job_id);
        set_db_data.setPartID(Long.parseLong(quiz_list.get(i).getQuiz_id()));
        set_db_data.setText(sentence_list.get(j).getEn());
        set_db_data.setType(1);
        set_db_data.setSentenceID(sentence_list.get(j).getSentence_id());
        set_db_data.setTextColor("");
        set_db_data.setNetAddress(sentence_list.get(j).getMp3());
        set_db_data.setMyAddress("");
        set_db_data.setChishengNetAddress("");
        set_db_data.setMyNum(0);
        set_db_data.setAnswer(sentence_list.get(j).getAnswer());
        set_db_data.setState(false);
        set_db_list.add(set_db_data);
    }


    public Map<String, Object> getDataMap() {
        Map<String, Object> datamap = new HashMap<>();
        datamap.put("uid", uerInfo.getUid());
        datamap.put("stu_job_id", String.valueOf(stu_job_id));//taskID
        datamap.put("format", "mp3");//"mp3"或"wav"就是音频地址的后缀
        datamap.put("percent", percent);//完成比例，0~100
        datamap.put("spend_time", spendtime);//花费时间，单位秒
        LogUtils.e("作业提交的数据库提交时间 = " + taskTimeCurrent1);

        List<Map> list = new ArrayList<>();
        for (int i = 0; i < submitTask.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            BestSentenceBean sentence = submitTask.get(i);
            map.put("hw_quiz_id", String.valueOf(sentence.getHw_quiz_id()));
            map.put("sentence_id", String.valueOf(sentence.getSentenceID()));
            map.put("score", String.valueOf(sentence.getMyNum()));
            map.put("words_score", String.valueOf(sentence.getTextColor()));
            map.put("mp3", TextUtils.isEmpty(sentence.getChishengNetAddress()) ? "" : sentence.getChishengNetAddress());
            map.put("seconds", String.valueOf(sentence.getMyVoiceTime()));
            list.add(map);
        }
        datamap.put("sentences", list);
        JSONObject jsonObject = new JSONObject(datamap);
        String s = jsonObject.toString();
        LogUtils.i("提交的作业是   =  " + s);
        return datamap;
    }

    public void setDBInfo(long userID, long classID, long taskID, List<Detail.DataEntity.ListEntity> list) {
        BestSentenceBean bsb;
        Map<Long, BestSentenceBean> bsbMap = new HashMap<>();//Map<Sentence_id, BestSentenceBean>
        if (list != null && list.size() > 0) {
            for (Detail.DataEntity.ListEntity ddl : list) {
                bsb = new BestSentenceBean();
                bsb.setUserId(Long.valueOf(uerInfo.getUid()));
                bsb.setClassID(Long.valueOf(uerInfo.getCid()));
                bsb.setTaskID(ddl.getStu_job_id());//没值的
                bsb.setPartID(ddl.getQuiz_id());//没值的
                bsb.setSentenceID(ddl.getSentence_id());
                bsb.setText(ddl.getEn());
                bsb.setTextColor(ddl.getWords_score());
                bsb.setNetAddress(ddl.getMp3());
                bsb.setAnswer(ddl.getAnswer());
                bsb.setMyAddress(ddl.getStu_mp3());
                bsb.setChishengNetAddress(ddl.getMp3_url());//没值的
                bsb.setMyVoiceTime(ddl.getSeconds());
                bsb.setMyNum(ddl.getStu_score());
                bsb.setState(false);
                bsb.setHw_quiz_id(ddl.getHw_quiz_id());//没值的
                bsb.setType(currentType);
                bsbMap.put(ddl.getSentence_id(), bsb);
            }
            DBUtils.getDbUtils().updateSentence(this, UPDATESENTENCE, userID, classID, taskID, quiz_id, currentType, bsbMap);
        }
    }

    /**
     * @param ii 1就是初始化的时候调用的
     */
    private void getDbData(List<?> list, int ii) {
        tdbList = (List<TaskDirectoryBean>) list;
        HashMap<Long, Quiz> map = new HashMap<>();
        for (int i = 0; i < tdbList.size(); i++) {
            if (!map.containsKey(tdbList.get(i).getPartID())) {
                map.put(tdbList.get(i).getPartID(), new Quiz());
            }
        }
        adapterList.clear();
        for (int i = 0; i < tdbList.size(); i++) {
            TaskDirectoryBean tdb = tdbList.get(i);
            long partID = tdb.getPartID();
            Quiz quiz1 = map.get(partID);
            String quiz_name = quiz1.getQuiz_name();
            if (TextUtils.isEmpty(quiz_name))
                quiz1.setQuiz_name(tdb.getPartName());
            quiz1.setQuiz_id(partID);
            quiz1.setDescription(tdb.getDescription());
            quiz1.setQuiz_type(tdb.getQuiz_type());
            switch ((int) tdb.getType()) {
                case 1:
                    quiz1.setRepeat_count((int) tdb.getRepeatCurrent());
                    quiz1.setRepeat_times((int) tdb.getRepeatTotal());
                    quiz1.setRepeat_quiz_id((int) tdb.getPartID());
                    quiz1.setRepeatIsDone(tdb.isDone());
                    break;
                case 2:
                    quiz1.setRead_count((int) tdb.getRepeatCurrent());
                    quiz1.setRead_times((int) tdb.getRepeatTotal());
                    quiz1.setRead_quiz_id((int) tdb.getPartID());
                    quiz1.setReadingIsDone(tdb.isDone());
                    break;
                case 3:
                    quiz1.setRecite_count((int) tdb.getRepeatCurrent());
                    quiz1.setRecite_times((int) tdb.getRepeatTotal());
                    quiz1.setRecite_quiz_id((int) tdb.getPartID());
                    quiz1.setReciteIsDone(tdb.isDone());
                    break;
            }
        }
        Iterator<Long> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long partID = iterator.next();
            adapterList.add(map.get(partID));
        }
        hideMyprogressDialog();
        if (ii == 1 && adapter != null) {
            adapter.isShowNotifyInfo = true;
            adapter.finishWorkMap.clear();
            adapter.notifyDataSetChanged();
        } else {
            adapter = new WorkCatalogAdapter(this, this, adapterList, data, notify_info, uerInfo);
            adapter.setVoiceEngineOption(voiceEngineOption);
            listView.setAdapter(adapter);
        }
    }

    /**
     * @param tdbList
     * @param sign    首页进来的是0,做作业回来的是1
     */
    public void goOnDirectory(List<TaskDirectoryBean> tdbList, TaskListBean tlb, int sign) {
        if (tlb != null) {
            taskNum = tlb.getTaskNum();//作业分数
            taskTime = tlb.getTaskTime();//作业耗时，不清零的那个
            taskLastSubmitTime = tlb.getTaskLastSubmitTime();//作业上一次的提交时间
            percent = (int) tlb.getTaskPercentage();//作业百分比
            taskName = tlb.getTaskName();//作业名字
            taskExplain = tlb.getTaskExplain();//作业说明
            level1 = tlb.getTaskRequirement();
            taskTimeCurrent1 = tlb.getTaskTimeCurrent();
        }
        if (tdbList != null && tdbList.size() > 0)
            getCatalogList(tdbList, sign);//首页进来的是0,做作业回来的是1
//        if (sign == 1){
//            taskTime = taskTime - taskTimeCurrent1;
//        }
        setHeadLayout();
    }

    public void isHaveTask(boolean haveTask) {
        this.haveTask = haveTask;
        if (NetworkUtil.checkNetwork(this))
            getNetCatlogInfo();
        else if (haveTask) {//true 数据库有数据  拿
            DBUtils.getDbUtils().getDirectoryListInfo(this, DIRECTORYLIST, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
        } else {
            UIUtils.showToastSafe("请连接网络");
            finish();
        }
    }

    public void getCatalogList(final List<?> list, final int i) {
        if (list != null && list.size() > 0)
            getDbData(list, i);
    }

    private void setHeadLayout() {
        hideMyprogressDialog();
        boolean isShowTopView = taskNum != 0;
        level.setText(level1);
        description.setText(taskExplain);//下面的作业说明
        setHeadLayoutGone(isShowTopView);
        if (!TextUtils.isEmpty(taskName)) {
            bigtitle.setVisibility(View.VISIBLE);
            bigtitle.setText(String.valueOf(taskName));
        } else
            bigtitle.setVisibility(View.INVISIBLE);
        if (isShowTopView) {
            titleText = "作业结果";
            bottom_tv_notify_info.setVisibility(View.GONE);
            setSubmitButState();
            //可点击的圆形的分数
            commit_score.setText(String.valueOf(taskNum));
            commit_image.setImageResource(WorkCatalogUtils.getScoreImage((int) taskNum));

            if (!TextUtils.isEmpty(taskLastSubmitTime)) {
                linear_commit_time.setVisibility(View.VISIBLE);
                commit_time.setText(String.valueOf(taskLastSubmitTime));
            } else
                linear_commit_time.setVisibility(View.INVISIBLE);
            //作业总耗时
//            String time = WorkCatalogUtils.getSpeendTimeStr(String.valueOf(taskTime));
//            spend_time.setText(time.equals("") ? "0" : time);//作业耗时
        } else {
            titleText = "作业目录";
            bottom_tv_notify_info.setVisibility(View.VISIBLE);
        }
        title_tv.setText(setTitle());
    }

    /**
     * 有作业提交：真，可点击
     * 没作业提交：完成一遍进入排名页，否则置灰
     */
    public void setSubmitButState() {
        String submitButText = "";
        if (submitTask != null && submitTask.size() > 0 || adapter != null && adapter.isHaveHomeWorkNum() || completeTask || isDone) {
            tv_upload.setClickable(true);
            tv_upload.setSelected(false);
        } else {
//            tv_upload.setSelected(!taskComplete);
//            tv_upload.setClickable(taskComplete);
            tv_upload.setClickable(false);
            tv_upload.setSelected(true);
        }

        if (submitTask != null && submitTask.size() > 0) {
            submitButText = getResources().getString(R.string.submit_but_text1);
        } else if (adapter != null && adapter.isHaveHomeWorkNum() || completeTask || isDone) {
            submitButText = getResources().getString(R.string.submit_but_text2);
        } else {
            submitButText = getResources().getString(R.string.submit_but_text1);
        }

        tv_upload.setText(submitButText);
    }

    public String getLevel(int level) {
        String l = "";
        switch (level) {
            case 0:
                l = "无要求";
                break;
            case 1:
                l = "及格";
                break;
            case 2:
                l = "良好";
                break;
            case 3:
                l = "优秀";
                break;
        }
        return l;
    }

    /**
     * @param isGone 真显示，假隐藏
     */
    public void setHeadLayoutGone(boolean isGone) {
        if (isGone)
            catalog_result.setVisibility(View.VISIBLE);
        else
            catalog_result.setVisibility(View.GONE);
    }

    String titleText = "";

    @Override
    protected String setTitle() {
        return titleText;
    }

    /**
     * 进入排名也的方法
     */
    private void goToRankActivity() {
        Intent intent = new Intent(TaskCatalogActivity.this, RankActivity.class);
        intent.putExtra("job_id", job_id);
        startActivity(intent);
    }

    private static long lastClickTime;

    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private void submitBut() {
        if (submitTask != null && submitTask.size() > 0) {//有作业提交
            submitTask();
        } else if (adapter != null && adapter.isHaveHomeWorkNum() || isDone || completeTask)//没有作业提交，并且已经至少完成了一遍，直接进入排名页
            goToRankActivity();
    }

    private void submitTask() {
        if (adapter != null && adapter.isHaveHomeWorkNum() || completeTask) {
            showMyprogressDialog();
            middle.uploadText(getDataMap());
        } else {
            AlertDialogUtils.getInstance().init(this, "确认提交", "你还有未完成的部分，确认提交吗？（作业截止日期前，可重复提交）", new AlertDialogUtils.DialogLestener() {
                @Override
                public void ok() {
                    showMyprogressDialog();
                    middle.uploadText(getDataMap());
                }

                @Override
                public void cancel() { /* WorkCatalogUtils.setViewClickable(tv_upload, true);*/}
            });
        }
    }
}


