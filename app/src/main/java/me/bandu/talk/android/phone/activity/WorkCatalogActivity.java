//package me.bandu.talk.android.phone.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.DFHT.utils.UIUtils;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//import me.bandu.talk.android.phone.R;
//import me.bandu.talk.android.phone.adapter.WorkCatalogAdapter;
//import me.bandu.talk.android.phone.bean.CreatWorkContentsBean;
//import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
//import me.bandu.talk.android.phone.bean.HomeWorkCatlogEntity;
//import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
//import me.bandu.talk.android.phone.bean.LoginBean;
//import me.bandu.talk.android.phone.bean.TotalDoneBean;
//import me.bandu.talk.android.phone.bean.WorkDoneBean;
//import me.bandu.talk.android.phone.dao.DaoUtils;
//import me.bandu.talk.android.phone.dao.Quiz;
//import me.bandu.talk.android.phone.dao.Sentence;
//import me.bandu.talk.android.phone.greendao.bean.BestSentenceBean;
//import me.bandu.talk.android.phone.greendao.bean.TaskDirectoryBean;
//import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
//import me.bandu.talk.android.phone.greendao.utils.DBUtils;
//import me.bandu.talk.android.phone.impl.UploadListener;
//import me.bandu.talk.android.phone.middle.CreatWorkContentMiddle;
//import me.bandu.talk.android.phone.middle.TotalDoneMiddle;
//import me.bandu.talk.android.phone.middle.WorkCatalogMiddle;
//import me.bandu.talk.android.phone.utils.Animation3DUtil;
//import me.bandu.talk.android.phone.utils.CacheUtils;
//import me.bandu.talk.android.phone.utils.DialogUtils;
//import me.bandu.talk.android.phone.utils.UpLoadUtils;
//import me.bandu.talk.android.phone.utils.UploadUtilZip;
//import me.bandu.talk.android.phone.utils.UserUtil;
//import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
//import me.bandu.talk.android.phone.view.AlertDialogUtils;
//
///**
// * 创建者：高楠
// * 类描述：作业目录-有结果未过期
// * 修改人：
// * 修改时间：
// * 修改备注：
// */
//public class WorkCatalogActivity extends BaseAppCompatActivity implements UploadListener, WorkCatalogUtils.WorkCatalogLisenter, DBUtils.inter {
//
//    private static final int WORKCATALOG = 106;
//    private static final int FIRSTCLICK = 107;
//    private static final int DATA_FROM_DB = 108;
//    @Bind(R.id.listview)
//    ListView listView;
//    @Bind(R.id.notify_info)
//    TextView notify_info;
//    @Bind(R.id.bigtitle)
//    TextView bigtitle;
//    @Bind(R.id.description)
//    TextView description;
//    @Bind(R.id.level)
//    TextView level;
//    @Bind(R.id.commit_image)
//    ImageView commit_image;
//    @Bind(R.id.commit_score)
//    TextView commit_score;
//    @Bind(R.id.commit_time)
//    TextView commit_time;
//    /**
//     * 耗时
//     */
//    @Bind(R.id.spend_time)
//    TextView spend_time;
//    /**
//     * 提交按钮
//     */
//    @Bind(R.id.tv_upload)
//    TextView tv_upload;
//    @Bind(R.id.commit_icon)
//    RelativeLayout layout;
//    @Bind(R.id.catalog_result)
//    RelativeLayout catalog_result;
//    @Bind(R.id.linear_commit_time)
//    LinearLayout linear_commit_time;
//    @Bind(R.id.linear_description)
//    LinearLayout linear_description;
//    public static final int DOWORK_CODE = 100;
//    private Bundle data;
//    private String uid;
//    private String job_id;
//    private long stu_job_id;
//    private List<Quiz> data_db = new ArrayList<>();
//    private UpLoadUtils upLoadUtils;
//    private WorkCatalogAdapter adapter;
//    private WorkCatalogMiddle middle;
//    private List<Sentence> upload_data = new ArrayList<>();
//    private boolean isdone;//是否做过作业-显示分数和提交时间与否
//
//    private Handler handler = new Handler();
//    /***********
//     * 新增作业上传
//     **************/
//    private WorkCatalogUtils catalogUtils;
//    private boolean haveMp3;
//    private int score;
//    private int workScore;
//    private LoginBean.DataEntity uerInfo = UserUtil.getUerInfo(this);
//    private HomeWorkCatlogBean bean;
//    private Map<String, Integer> map;
//    private long classID;
//    /**
//     * 提交按钮的状态，真就是提交作业然后跳转排名页，否则直接跳转
//     */
//    private boolean isSubmitTask = false;
//    private boolean haveTask = false;
//    /**
//     * 需要提交的作业
//     */
//    private List<TaskListBean> submitTaskList;
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;
//    private int percent;
//
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_work_catalog;
//    }
//
//    @Override
//    protected void toStart() {
//        catalogUtils = WorkCatalogUtils.getInstance();
//        catalogUtils.setLisenter(this);
//        Animation3DUtil.getInstance().setView(layout, commit_image, commit_score);
//        data = getIntent().getBundleExtra("data");
//        if (data != null) {
//            uid = data.getString("uid");
//            isdone = data.getBoolean("isdone");
//            job_id = data.getString("job_id");
//            stu_job_id = Long.valueOf(data.getString("stu_job_id"));
//            //设置notify_info作业完成百分比
//            percent = data.getInt("percent");
//            setNotifyInfo(percent);
//            if (UserUtil.getUerInfo(this) != null) {
//                CacheUtils.getInstance().putStringCache(this, stu_job_id + "", UserUtil.getUerInfo(this).getUid() + "" + stu_job_id);
//            }
//        }
//        catalog_result.setVisibility(View.GONE);
//        //判断是否从网络拿数据
//        DBUtils.getDbUtils().isHaveTask(this, FIRSTCLICK, Long.valueOf(uid), Long.valueOf(uerInfo.getCid()), stu_job_id);
//        middle = new WorkCatalogMiddle(this, this);
//
//
////        data_db = getDataFromDb();
//        /*setVisbale();
//        middle = new WorkCatalogMiddle(this, this);
//        if (data_db != null && data_db.size() != 0) {
//            initView(data_db.get(0));
//            adapter = new WorkCatalogAdapter(this, data_db, data, notify_info,uerInfo);
//            listView.setAdapter(adapter);
//            // 此处请求分数
//            score = catalogUtils.getHomeWorkScore(this, data_db);
//            commit_score.setText(score + "");
//            commit_image.setImageResource(WorkCatalogUtils.getScoreImage(catalogUtils.getHomeWorkScore(this, data_db)));
//        } else {
//
//        }*/
//    }
//
//    @Override
//    public void getList(List<?> list, boolean bb, int sign) {
//        if (sign == FIRSTCLICK) {//是否从数据库拿数据
//            List<TaskListBean> bean = (List<TaskListBean>) list;
//            if (bean != null && bean.size() > 0) {
//                haveTask = bean.get(0).isHaveTask();
////                if(false){
//                if (haveTask) {//true 数据库有数据  拿
//                    DBUtils.getDbUtils().getSubmitTask(new DBUtils.SubmitInter() {
//                        @Override
//                        public void submitInter(final List<?> list, final long repeatTime, long num, int sign) {
//                            UIUtils.runInMainThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    submitTaskList = (List<TaskListBean>) list;
//                                    spend_time.setText(WorkCatalogUtils.getSpeendTimeStr(String.valueOf(repeatTime)));//耗时
//                                    isSubmitTask = list != null && list.size() > 0;
//                                    setUploadClickable();
//                                }
//                            });
//                        }
//                    }, 0, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, 0, 0, 0, false, true);
//
////                    DBUtils.getDbUtils().getDirectoryListInfo(new DBUtils.inter() {
////                        @Override
////                        public void getList(List<?> list, boolean bb, int sign) {
////                            if (list != null && list.size() > 0)
////                                getDbData(list, 0);
////                            UIUtils.runInMainThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    LogUtils.i("haveTask = "+haveTask);
////                                    setVisbale();
////                                }
////                            });
////                        }
////                    }, DATA_FROM_DB, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
//                    return;
//                }
//            }
//            UIUtils.runInMainThread(new Runnable() {
//                @Override
//                public void run() {
//                    getData();
//                }
//            });
//        }
//    }
//
//    @Override
//    public void isHaveNum(int state, List<?> list, boolean b, final TaskListBean taskListBean, int sign) {
//
//    }
//
//    private void getDbData(List<?> list, int ii) {
//        ArrayList<TaskDirectoryBean> db_data = (ArrayList<TaskDirectoryBean>) list;
//
//        ArrayList<Long> longs = new ArrayList<>();
//        data_db = new ArrayList<>();
////        List<TaskDirectoryBean> same_quiz = new ArrayList<>();
//        HashMap<Long, Quiz> map = new HashMap<>();
//        for (int i = 0; i < db_data.size(); i++) {
//            if (!map.containsKey(db_data.get(i).getPartID())) {
//                map.put(db_data.get(i).getPartID(), new Quiz());
//            }
//        }
//        for (int i = 0; i < db_data.size(); i++) {
//            TaskDirectoryBean tdb = db_data.get(i);
//            long partID = tdb.getPartID();
//            Quiz quiz1 = map.get(partID);
//            String quiz_name = quiz1.getQuiz_name();
//            if (TextUtils.isEmpty(quiz_name))
//                quiz1.setQuiz_name(tdb.getPartName());
//            quiz1.setQuiz_id(partID);
//
//            switch ((int) tdb.getType()) {
//                case 1:
//                    quiz1.setRepeat_count((int) tdb.getRepeatCurrent());
//                    quiz1.setRepeat_times((int) tdb.getRepeatTotal());
//                    quiz1.setRepeat_quiz_id((int) tdb.getPartID());
//                    break;
//                case 2:
//                    quiz1.setRead_count((int) tdb.getRepeatCurrent());
//                    quiz1.setRead_times((int) tdb.getRepeatTotal());
//                    quiz1.setRead_quiz_id((int) tdb.getPartID());
//                    break;
//                case 3:
//                    quiz1.setRecite_count((int) tdb.getRepeatCurrent());
//                    quiz1.setRecite_times((int) tdb.getRepeatTotal());
//                    quiz1.setRecite_quiz_id((int) tdb.getPartID());
//                    break;
//            }
//        }
//        Iterator<Long> iterator = map.keySet().iterator();
//        while (iterator.hasNext()) {
//            Long partID = iterator.next();
//            data_db.add(map.get(partID));
//        }
//        if (ii == 1) {
//            adapter.notifyDataSetChanged();
//        } else {
//            adapter = new WorkCatalogAdapter(this, null, data_db, data, notify_info, uerInfo);
//            listView.setAdapter(adapter);
//        }
//        //传递给adapter
//    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        if (null != adapter) {
////            adapter.setHaveHomeWorkNum(true);
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    @OnClick({R.id.commit_image, R.id.commit_score, R.id.tv_upload})
//    void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.commit_image:
//                Animation3DUtil.getInstance().imageStart();
//                break;
//            case R.id.commit_score:
//                Animation3DUtil.getInstance().textStart();
//                break;
//            case R.id.tv_upload:
//                if (!isSubmit) {
//                    isSubmit = true;
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            isSubmit = false;
//                            submitButClik();
//                        }
//                    }, 1000);
//                }
//
//                //是否都有录音
////                haveMp3 = isHaveMp3();
//
////                if (upload_data != null && upload_data.size() > 0) {
//////                    WorkCatalogUtils.setViewClickable(tv_upload, false);
//////                    String haveHoemeWork = isHaveHoemeWork();
//////                    if (!adapter.getisHaveHomeWorkNum()) {
////                    if (adapter.isHaveHomeWorkNum) {
//////                    if (!haveMp3) {
////                        AlertDialogUtils.getInstance().init(this, "确认提交", isHaveHoemeWork(), new AlertDialogUtils.DialogLestener() {
////                            @Override
////                            public void ok() {
////                                showMyprogressDialog();
////                                handler.postDelayed(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        //TODO 回调*/
////                                        middle.uploadText(catalogUtils.getDataMap(UserUtil.getUerInfo(WorkCatalogActivity.this).getUid(), stu_job_id + "", upload_data, DaoUtils.getInstance().getUploadSpendTime(stu_job_id + ""), DaoUtils.getInstance().getPercent(stu_job_id)));
////                                    }
////                                }, 1000);
////                            }
////
////                            @Override
////                            public void cancel() {
//////                                WorkCatalogUtils.setViewClickable(tv_upload, true);
////                            }
////                        });
////                   /* }*/
////                    } else {
////                        showMyprogressDialog();
////                        handler.postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                //TODO 回调
////                                workScore = catalogUtils.getHomeWorkScore(WorkCatalogActivity.this, data_db);
////                                if (workScore > score) {
////                                    middle.uploadText(catalogUtils.getDataMap(UserUtil.getUerInfo(WorkCatalogActivity.this).getUid(), stu_job_id + "", upload_data, DaoUtils.getInstance().getUploadSpendTime(stu_job_id + ""), DaoUtils.getInstance().getPercent(stu_job_id)));
////                                } else {
////                                    WorkCatalogUtils.getInstance().finishWork(upload_data);
////                                    setUploadClickable();
////                                    hideMyprogressDialog();
////                                    Intent intent = new Intent(WorkCatalogActivity.this, RankActivity.class);
////                                    intent.putExtra("job_id", job_id);
////                                    startActivity(intent);
////                                }
////                            }
////                        }, 1000);
////                    }
////                } else {
////                    //TODO 提示信息
////                    if (tv_upload.isClickable()) {
////                        UIUtils.showToastSafe("正在查询数据，请稍后");
////                    }
////                }
//                break;
//        }
//    }
//
//    boolean isSubmit = false;
//
//    private void submitButClik() {
//        if (isSubmitTask) {//提交作业然后跳转RankActivity
//            if (submitTaskList != null && submitTaskList.size() > 0) {
//                AlertDialogUtils.getInstance().init(this, "确认提交", isHaveHoemeWork(), new AlertDialogUtils.DialogLestener() {
//                    @Override
//                    public void ok() {
//                        showMyprogressDialog();
//                        //TODO 回调*/
//                        middle.uploadText(catalogUtils.getDataMap(UserUtil.getUerInfo(WorkCatalogActivity.this).getUid(), stu_job_id + "", upload_data, DaoUtils.getInstance().getUploadSpendTime(stu_job_id + ""), DaoUtils.getInstance().getPercent(stu_job_id)));
//                    }
//
//                    @Override
//                    public void cancel() { /* WorkCatalogUtils.setViewClickable(tv_upload, true);*/}
//                });
//            }
//        } else {//直接跳转RankActivity
//            showMyprogressDialog();
//            //TODO 回调
//            workScore = catalogUtils.getHomeWorkScore(WorkCatalogActivity.this, data_db);
//            if (workScore > score) {
//                middle.uploadText(catalogUtils.getDataMap(UserUtil.getUerInfo(WorkCatalogActivity.this).getUid(), stu_job_id + "", upload_data, DaoUtils.getInstance().getUploadSpendTime(stu_job_id + ""), percent));
//            } else {
////                WorkCatalogUtils.getInstance().finishWork(upload_data);
////                setUploadClickable();
//                hideMyprogressDialog();
//                Intent intent = new Intent(WorkCatalogActivity.this, RankActivity.class);
//                intent.putExtra("job_id", job_id);
//                startActivity(intent);
//            }
//        }
//    }
//
////    private boolean isHaveMp3() {
////        boolean read = true;
////        boolean recite = true;
////        boolean repeat = true;
////        //判断是否调用接口是否跳转Ranking
////        List<Quiz> quizList = DaoUtils.getInstance().getQuizList(stu_job_id);
////        List<Sentence> quizRead = null;
////        List<Sentence> quizRecite = null;
////        List<Sentence> quizRepeat = null;
////        for (int i = 0; i < quizList.size(); i++) {
////            Quiz quiz = quizList.get(i);
////            if (quiz.getRecite_times() != 0)
////                quizRecite = DaoUtils.getInstance().getQuizMp3(quizList.get(i).getRecite_quiz_id());
////            if (quiz.getRead_times() != 0)
////                quizRead = DaoUtils.getInstance().getQuizMp3(quizList.get(i).getRead_quiz_id());
////            if (quiz.getRepeat_times() != 0)
////                quizRepeat = DaoUtils.getInstance().getQuizMp3(quizList.get(i).getRepeat_quiz_id());
////            if (quizRead != null) {
////                if (quizRead.size() > 0) {
////                    for (int j = 0; j < quizRead.size(); j++) {
////                        String read_mp3 = quizRead.get(j).getMp3_url();
////                        if (null == read_mp3) {
////                            if (quizRecite != null) {
////                                if (quizRecite.size() > 0) {
////                                    String recite_mp3 = quizRecite.get(j).getMp3_url();
////                                    if (null == recite_mp3) {
////                                        if (quizRepeat != null) {
////                                            if (quizRepeat.size() > 0) {
////                                                String repeat_mp3 = quizRepeat.get(j).getMp3_url();
////                                                if (null == repeat_mp3)
////                                                    return false;
////                                            }
////                                        } else {
////                                            return false;
////                                        }
////                                    }
////                                } else {
////                                    if (quizRepeat != null) {
////                                        if (quizRepeat.size() > 0) {
////                                            String repeat_mp3 = quizRepeat.get(j).getMp3_url();
////                                            if (null == repeat_mp3)
////                                                return false;
////                                        } else {
////                                            return false;
////                                        }
////                                    }
////                                }
////                            } else {
////                                if (quizRepeat != null) {
////                                    if (quizRepeat.size() > 0) {
////                                        String repeat_mp3 = quizRepeat.get(j).getMp3_url();
////                                        if (null == repeat_mp3)
////                                            return false;
////                                    } else {
////                                        return false;
////                                    }
////                                }
////                            }
////                        }
////                    }
////                } else {
////                    if (quizRecite != null) {
////                        if (quizRecite.size() > 0) {
////                            for (int n = 0; i < quizRecite.size(); i++) {
////                                String recite_mp3 = quizRecite.get(n).getMp3_url();
////                                if (null == recite_mp3) {
////                                    if (quizRepeat != null) {
////                                        if (quizRepeat.size() > 0) {
////                                            String repeat_mp3 = quizRepeat.get(n).getMp3_url();
////                                            if (null == repeat_mp3)
////                                                return false;
////                                        }
////                                    }
////                                }
////                            }
////                        } else {
////                            if (quizRepeat != null) {
////                                if (quizRepeat.size() > 0) {
////                                    for (int m = 0; m < quizRepeat.size(); m++) {
////                                        String repeat_mp3 = quizRepeat.get(m).getMp3_url();
////                                        if (null == repeat_mp3)
////                                            return false;
////                                    }
////                                } else {
////                                    return false;
////                                }
////                            }
////                        }
////                    } else {
////                        if (quizRepeat != null) {
////                            if (quizRepeat.size() > 0) {
////                                for (int m = 0; m < quizRepeat.size(); m++) {
////                                    String repeat_mp3 = quizRepeat.get(m).getMp3_url();
////                                    if (null == repeat_mp3)
////                                        return false;
////                                }
////                            }
////                        } else {
////                            return false;
////                        }
////                    }
////                }
////            } else {
////                if (quizRecite != null) {
////                    if (quizRecite.size() > 0) {
////                        for (int j = 0; j < quizRecite.size(); j++) {
////                            String recite_mp3 = quizRecite.get(j).getMp3_url();
////                            if (null == recite_mp3) {
////                                if (quizRepeat != null) {
////                                    if (quizRepeat.size() > 0) {
////                                        String repeat_mp3 = quizRepeat.get(j).getMp3_url();
////                                        if (null == repeat_mp3)
////                                            return false;
////                                    }
////                                } else {
////                                    return false;
////                                }
////                            }
////                        }
////                    } else {
////                        if (quizRepeat != null) {
////                            if (quizRepeat.size() > 0) {
////                                for (int j = 0; j < quizRepeat.size(); j++) {
////                                    String repeat_mp3 = quizRepeat.get(j).getMp3_url();
////                                    if (null == repeat_mp3)
////                                        return false;
////                                }
////                            } else {
////                                return false;
////                            }
////                        }
////                    }
////                } else {
////                    if (quizRepeat != null) {
////                        if (quizRepeat.size() > 0) {
////                            for (int j = 0; j < quizRepeat.size(); j++) {
////                                String repeat_mp3 = quizRepeat.get(j).getMp3_url();
////                                if (null == repeat_mp3)
////                                    return false;
////                            }
////                        } else {
////                            return false;
////                        }
////                    }
////                }
////            }
////
////        }
////        return repeat && read && recite == true ? true : false;
////    }
//
//    @Override//70
//    public void onSuccess(Object result, int requestCode) {
//        super.onSuccess(result, requestCode);
//        hideMyprogressDialog();
//        if (requestCode == TotalDoneMiddle.TOTAL_DONE) {
//            final TotalDoneBean bean = (TotalDoneBean) result;
//            if (bean != null && bean.getStatus() == 1) {
//                String commitScore = bean.getData().getCommit_time();
//                int score = bean.getData().getScore();
//                int up_score = bean.getData().getUp_score();
//                Intent intent = new Intent(this, RankActivity.class);
//                intent.putExtra("job_id", job_id);
//                startActivity(intent);
//            }
//
//                /*//TODO 回调
//                data_db = DaoUtils.getInstance().beanToDao(bean, stu_job_id);
//                DaoUtils.getInstance().saveQuizList(data_db, stu_job_id);//第一次存储数据  -  句子
//                DaoUtils.getInstance().updateHomeWorkPercent(stu_job_id);
//                adapter = new WorkCatalogAdapter(this, data_db, data, notify_info);
//                listView.setAdapter(adapter);
//                if (data_db != null && data_db.size() != 0) {
//                    initView(data_db.get(0));
//                }
//                //此处请求分数
//                commit_score.setText(catalogUtils.getHomeWorkScore(this, data_db) + "");
//                commit_image.setImageResource(WorkCatalogUtils.getScoreImage(catalogUtils.getHomeWorkScore(this, data_db)));
//            */
//        } else if (requestCode == WorkCatalogMiddle.WORK_CATALOG) {
//            bean = (HomeWorkCatlogBean) result;
//            List<HomeWorkCatlogQuiz> quizs1 = bean.getData().getHomework().getQuizs();
//
//            if (bean != null && bean.getStatus() == 1) {
//                List<HomeWorkCatlogQuiz> quizs = bean.getData().getHomework().getQuizs();
//                List<TaskDirectoryBean> set_db_list = new ArrayList<>();
//                map = new HashMap<>();
//                for (int i = 0; i < quizs.size(); i++) {
//                    int temp = 0;
//                    BestSentenceBean add_type = new BestSentenceBean();
//                    Quiz data_bean = new Quiz();
//                    TaskDirectoryBean reading_bean = new TaskDirectoryBean();
//                    TaskDirectoryBean recite_bean = new TaskDirectoryBean();
//                    TaskDirectoryBean repeat_bean = new TaskDirectoryBean();
//                    HomeWorkCatlogEntity homework = bean.getData().getHomework();
//                    HomeWorkCatlogQuiz catlogQuiz = quizs.get(i);
//                    if (catlogQuiz.getReading().getHw_quiz_id() != 0) {
//                        temp += 1;
//                        add_type.setUserId(Long.parseLong(uid));
//                        add_type.setClassID(Long.parseLong(uerInfo.getCid()));
//                        add_type.setType(2);
//                        add_type.setTaskID(stu_job_id);
//
//                        data_bean.setRead_count(0);
//                        data_bean.setRead_quiz_id(catlogQuiz.getReading().getHw_quiz_id());
//                        data_bean.setRead_times(catlogQuiz.getReading().getTimes());
//
//                        reading_bean.setClassID(Long.parseLong(uerInfo.getCid()));
//                        reading_bean.setUserId(Long.parseLong(uid));
//                        reading_bean.setTaskID(stu_job_id);
//                        reading_bean.setUnitID(0l);
//                        reading_bean.setLesenID(0l);
//                        reading_bean.setPartID(Long.parseLong(catlogQuiz.getQuiz_id()));
//                        reading_bean.setType(2l);
//                        reading_bean.setIsDone(true);
//                        reading_bean.setPartName(catlogQuiz.getName());
//                        reading_bean.setRepeatTotal(catlogQuiz.getReading().getTimes());
//                        reading_bean.setRepeatCurrent(0l);
//                        reading_bean.setRepeatTime(0l);
//                    }
//                    if (catlogQuiz.getRecite().getHw_quiz_id() != 0) {
//                        temp += 2;
//                        add_type.setUserId(Long.parseLong(uid));
//                        add_type.setClassID(Long.parseLong(uerInfo.getCid()));
//                        add_type.setType(3);
//                        add_type.setTaskID(stu_job_id);
//
//                        data_bean.setRecite_count(0);
//                        data_bean.setRecite_quiz_id(catlogQuiz.getRecite().getHw_quiz_id());
//                        data_bean.setRecite_times(catlogQuiz.getRecite().getTimes());
//
//                        recite_bean.setClassID(Long.parseLong(uerInfo.getCid()));
//                        recite_bean.setUserId(Long.parseLong(uid));
//                        recite_bean.setTaskID(stu_job_id);
//                        recite_bean.setUnitID(0l);
//                        recite_bean.setLesenID(0l);
//                        recite_bean.setPartID(Long.parseLong(catlogQuiz.getQuiz_id()));
//                        recite_bean.setType(3l);
//                        recite_bean.setIsDone(true);
//                        recite_bean.setPartName(catlogQuiz.getName());
//                        recite_bean.setRepeatTotal(catlogQuiz.getRecite().getTimes());
//                        recite_bean.setRepeatCurrent(0l);
//                        recite_bean.setRepeatTime(0l);
//                    }
//                    if (catlogQuiz.getRepeat().getHw_quiz_id() != 0) {
//                        temp += 4;
//                        add_type.setUserId(Long.parseLong(uid));
//                        add_type.setClassID(Long.parseLong(uerInfo.getCid()));
//                        add_type.setType(1);
//                        add_type.setTaskID(stu_job_id);
//
//                        data_bean.setRepeat_count(0);
//                        data_bean.setRepeat_quiz_id(catlogQuiz.getRepeat().getHw_quiz_id());
//                        data_bean.setRepeat_times(catlogQuiz.getRepeat().getTimes());
//
//                        repeat_bean.setClassID(Long.parseLong(uerInfo.getCid()));
//                        repeat_bean.setUserId(Long.parseLong(uid));
//                        repeat_bean.setTaskID(stu_job_id);
//                        repeat_bean.setUnitID(0l);
//                        repeat_bean.setLesenID(0l);
//                        repeat_bean.setPartID(Long.parseLong(catlogQuiz.getQuiz_id()));
//                        repeat_bean.setType(1l);
//                        repeat_bean.setIsDone(true);
//                        repeat_bean.setPartName(catlogQuiz.getName());
//                        repeat_bean.setRepeatTotal(catlogQuiz.getRepeat().getTimes());
//                        repeat_bean.setRepeatCurrent(0l);
//                        repeat_bean.setRepeatTime(0l);
//                    }
//
//                    data_bean.setQuiz_id(Long.parseLong(catlogQuiz.getQuiz_id()));
//                    data_bean.setScore(score);
//                    data_bean.setStu_job_id(stu_job_id);
//                    data_bean.setTitle(homework.getTitle());
//                    data_bean.setDescription(homework.getDescription());
//                    data_bean.setLevel(homework.getLevel());
//                    data_bean.setCommit_time(homework.getCommit_time());
//                    data_bean.setSpend_time(homework.getSpend_time());
//                    data_bean.setQuiz_name(catlogQuiz.getName());
//                    data_bean.setUpload_spend_time("0");
//                    if (reading_bean.getPartName() != null)
//                        set_db_list.add(reading_bean);
//                    if (recite_bean.getPartName() != null)
//                        set_db_list.add(recite_bean);
//                    if (repeat_bean.getPartName() != null)
//                        set_db_list.add(repeat_bean);
//                    data_db.add(data_bean);
//                    map.put(catlogQuiz.getQuiz_id(), temp);
//                }
////                DBUtils.getDbUtils().addDirectoryListInfos(set_db_list, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, quiz_id);
//
//                adapter = new WorkCatalogAdapter(this, null, data_db, data, notify_info, uerInfo);
//                /*//TODO 回调
//                data_db = DaoUtils.getInstance().beanToDao(bean, stu_job_id);
//                DaoUtils.getInstance().saveQuizList(data_db, stu_job_id);//第一次存储数据  -  句子
//                DaoUtils.getInstance().updateHomeWorkPercent(stu_job_id);
//                adapter = new WorkCatalogAdapter(this, data_db, data, notify_info);
//                listView.setAdapter(adapter);
//                if (data_db != null && data_db.size() != 0) {
//                    initView(data_db.get(0));
//                }
//                //此处请求分数
//                commit_score.setText(catalogUtils.getHomeWorkScore(this, data_db) + "");
//                commit_image.setImageResource(WorkCatalogUtils.getScoreImage(catalogUtils.getHomeWorkScore(this, data_db)));
//            */
//            }
//            //获取所有句子
//            List<String> quizlist = new ArrayList<>();
//            for (int i = 0; i < quizs1.size(); i++) {
//                String str = String.valueOf(quizs1.get(i).getQuiz_id());
//                quizlist.add(str);
//            }
//            new CreatWorkContentMiddle(this, this).getWorkSentenceList(quizlist);
//
//        } else if (requestCode == CreatWorkContentMiddle.CREAT_WORK_CONTENT) {
//            //存句子
//            CreatWorkContentsBean bean = (CreatWorkContentsBean) result;
//            List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list = bean.getData().getQuiz_list();
//            List<BestSentenceBean> set_db_list = new ArrayList<>();
//            for (int i = 0; i < quiz_list.size(); i++) {
//                List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list = quiz_list.get(i).getSentence_list();
//                Iterator iter = map.entrySet().iterator();
//                while (iter.hasNext()) {
//                    Map.Entry entry = (Map.Entry) iter.next();
//                    for (int j = 0; j < sentence_list.size(); j++) {
//                        String quiz_id = quiz_list.get(i).getQuiz_id();
//                        if (entry.getKey().equals(quiz_id)) {
//                            if ((int) entry.getValue() == 1) {//1 reading 2 Recite 4 Repeat
//                                saveReading(quiz_list, set_db_list, i, sentence_list, j);
//                            } else if ((int) entry.getValue() == 2) {
//                                saveRecite(quiz_list, set_db_list, i, sentence_list, j);
//                            } else if ((int) entry.getValue() == 3) {
//                                saveReading(quiz_list, set_db_list, i, sentence_list, j);
//                                saveRecite(quiz_list, set_db_list, i, sentence_list, j);
//                            } else if ((int) entry.getValue() == 4) {
//                                saveRepeat(quiz_list, set_db_list, i, sentence_list, j);
//                            } else if ((int) entry.getValue() == 5) {
//                                saveReading(quiz_list, set_db_list, i, sentence_list, j);
//                                saveRepeat(quiz_list, set_db_list, i, sentence_list, j);
//                            } else if ((int) entry.getValue() == 6) {
//                                saveRecite(quiz_list, set_db_list, i, sentence_list, j);
//                                saveRepeat(quiz_list, set_db_list, i, sentence_list, j);
//                            } else if ((int) entry.getValue() == 7) {
//                                saveReading(quiz_list, set_db_list, i, sentence_list, j);
//                                saveRecite(quiz_list, set_db_list, i, sentence_list, j);
//                                saveRepeat(quiz_list, set_db_list, i, sentence_list, j);
//                            }
//                        }
//                    }
//                }
//            }
////            DBUtils.getDbUtils().addSentence(Long.parseLong(uid), Long.parseLong(uerInfo.getCid()), stu_job_id, set_db_list);
//            listView.setAdapter(adapter);
//            initView(data_db.get(0));
//        } else if (requestCode == WorkCatalogMiddle.FINISH_WORK) {
//            WorkCatalogUtils.setViewClickable(tv_upload, true);
//            WorkDoneBean baseBean = (WorkDoneBean) result;
//            if (baseBean.getStatus() == 1) {
//                setCommitTime(baseBean.getData().getScore(), baseBean.getData().getCommit_time());
//            } else {
//                UIUtils.showToastSafe(baseBean.getMsg());
//            }
//        } else if (requestCode == WorkCatalogMiddle.UPLOAD_TEXT) {
///*            UploadBean uploadBean = (UploadBean) result;
//            if (uploadBean.getStatus() == 1) {
//                setCommitTime(uploadBean.getData().getScore(), uploadBean.getData().getCommit_time());
//                //3.2 新加 压缩开始
//                catalogUtils.uploadFileToZip(upload_data, FileUtil.DOWNLOAD_DIR + File.separator + uploadBean.getData().getZip());
//            } else if (uploadBean.getStatus() == 2) {
//                catalogUtils.finishWork(upload_data);
//                setUploadClickable();
//            }*/
//            if (workScore > score)
//                score = workScore;
//            if (haveMp3) {
//                //所有的录音全部上传完毕并且有分数这时就调done接口
//                showMyprogressDialog();
//                new TotalDoneMiddle(this, this).TotalDone(uid, stu_job_id + "");
//            }
//
//            //3.3作业提交方式
////            UploadBean uploadBean = (UploadBean) result;
////            setCommitTime(uploadBean.getData().getScore(), uploadBean.getData().getCommit_time());
////            DaoUtils.getInstance().cleanUploadSpendTime(String.valueOf(stu_job_id));
//            //TODO    更新数据库  使作业提交状态设置为不可提交
////            WorkCatalogUtils.getInstance().finishWork(this,upload_data);//更新数据库   让提交成功的句子改成不可提交状态
////            WorkCatalogUtils.getInstance().finishWork(upload_data);//更新数据库   让提交成功的句子改成不可提交状态
//            //跳转排行榜
//            /*if (*//*!adapter.getIsDoneMap().containsKey(false)*//*adapter.getTurn_flag()) {
//                Intent intent = new Intent(this, RankActivity.class);
//                LogUtils.i("onsuccess---》intent");
//                intent.putExtra("job_id", job_id);
//                startActivity(intent);
//            }*/
//
//            setUploadClickable();
//
//        }
//
//    }
//
//    private void saveReading(List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list, List<BestSentenceBean> set_db_list, int i, List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list, int j) {
//        BestSentenceBean set_db_data = new BestSentenceBean();
//        set_db_data.setUserId(Long.parseLong(uid));
//        set_db_data.setClassID(Long.parseLong(uerInfo.getCid()));
//        set_db_data.setTaskID(stu_job_id);
//        set_db_data.setPartID(Long.parseLong(quiz_list.get(i).getQuiz_id()));
//        set_db_data.setText(sentence_list.get(j).getEn());
//        set_db_data.setType(2);
//        set_db_data.setSentenceID(sentence_list.get(j).getSentence_id());
//        set_db_data.setTextColor("");
//        set_db_data.setNetAddress(sentence_list.get(j).getMp3());
//        set_db_data.setMyAddress("");
//        set_db_data.setChishengNetAddress("");
//        set_db_data.setMyNum(0);
//        set_db_data.setState(false);
//        set_db_list.add(set_db_data);
//    }
//
//    private void saveRecite(List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list, List<BestSentenceBean> set_db_list, int i, List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list, int j) {
//        BestSentenceBean set_db_data = new BestSentenceBean();
//        set_db_data.setUserId(Long.parseLong(uid));
//        set_db_data.setClassID(Long.parseLong(uerInfo.getCid()));
//        set_db_data.setTaskID(stu_job_id);
//        set_db_data.setPartID(Long.parseLong(quiz_list.get(i).getQuiz_id()));
//        set_db_data.setText(sentence_list.get(j).getEn());
//        set_db_data.setType(3);
//        set_db_data.setSentenceID(sentence_list.get(j).getSentence_id());
//        set_db_data.setTextColor("");
//        set_db_data.setNetAddress(sentence_list.get(j).getMp3());
//        set_db_data.setMyAddress("");
//        set_db_data.setChishengNetAddress("");
//        set_db_data.setMyNum(0);
//        set_db_data.setState(false);
//        set_db_list.add(set_db_data);
//    }
//
//    private void saveRepeat(List<CreatWorkContentsBean.DataBean.QuizListBean> quiz_list, List<BestSentenceBean> set_db_list, int i, List<CreatWorkContentsBean.DataBean.QuizListBean.SentenceListBean> sentence_list, int j) {
//        BestSentenceBean set_db_data = new BestSentenceBean();
//        set_db_data.setUserId(Long.parseLong(uid));
//        set_db_data.setClassID(Long.parseLong(uerInfo.getCid()));
//        set_db_data.setTaskID(stu_job_id);
//        set_db_data.setPartID(Long.parseLong(quiz_list.get(i).getQuiz_id()));
//        set_db_data.setText(sentence_list.get(j).getEn());
//        set_db_data.setType(1);
//        set_db_data.setSentenceID(sentence_list.get(j).getSentence_id());
//        set_db_data.setTextColor("");
//        set_db_data.setNetAddress(sentence_list.get(j).getMp3());
//        set_db_data.setMyAddress("");
//        set_db_data.setChishengNetAddress("");
//        set_db_data.setMyNum(0);
//        set_db_data.setState(false);
//        set_db_list.add(set_db_data);
//    }
//
//
//    @Override
//    public void onFailed(int requestCode) {
//        super.onFailed(requestCode);
//        hideMyprogressDialog();
//    }
//
//    public String isHaveHoemeWork() {
////        if (!adapter.isHaveHomeWorkNum)
////            return "作业截止日期前，可重复提交";
//        return "你还有未完成的部分，确认提交吗？（作业截止日期前，可重复提交）";
//    }
//
//    /**
//     * 界面数据填充
//     */
//
//    public void initView(Quiz quiz) {
//        bigtitle.setText(quiz.getTitle());
//        level.setText(WorkCatalogUtils.getLevel(quiz.getLevel()));
//        description.setText(quiz.getDescription());
//        if (quiz.getCommit_time() == null || quiz.getCommit_time().equals("")) {
//            linear_commit_time.setVisibility(View.INVISIBLE);
//        } else {
//            linear_commit_time.setVisibility(View.VISIBLE);
//            commit_time.setText(quiz.getCommit_time());
//        }
//        String time = WorkCatalogUtils.getSpeendTimeStr(quiz.getSpend_time());
//        spend_time.setText(time.equals("") ? "0" : time);
//
//        int score = quiz.getScore();
//        commit_score.setText(score + "");
//        commit_image.setImageResource(WorkCatalogUtils.getScoreImage(score));
//    }
//
//
//    /**
//     * 从网络获取数据
//     */
//    public void getData() {
//        showMyprogressDialog();
//        middle.getWorkCatalog(uid, job_id, stu_job_id);
//    }
//
//    /**
//     * 网络请求成功获取数据
//     */
//    @Override
//    public void successFromMid(Object... obj) {
//        super.successFromMid(obj);
//        hideMyprogressDialog();
//        /*int requestCode = (int) obj[1];
//        Object result = obj[0];
//        if (requestCode == WorkCatalogMiddle.FINISH_WORK) {
//            WorkCatalogUtils.setViewClickable(tv_upload, true);
//            WorkDoneBean baseBean = (WorkDoneBean) result;
//            if (baseBean.getStatus() == 1) {
//                setCommitTime(baseBean.getData().getScore(), baseBean.getData().getCommit_time());
//            } else {
//                UIUtils.showToastSafe(baseBean.getMsg());
//            }
//        } else if (requestCode == WorkCatalogMiddle.UPLOAD_TEXT) {
//*//*            UploadBean uploadBean = (UploadBean) result;
//            if (uploadBean.getStatus() == 1) {
//                setCommitTime(uploadBean.getData().getScore(), uploadBean.getData().getCommit_time());
//                //3.2 新加 压缩开始
//                catalogUtils.uploadFileToZip(upload_data, FileUtil.DOWNLOAD_DIR + File.separator + uploadBean.getData().getZip());
//            } else if (uploadBean.getStatus() == 2) {
//                catalogUtils.finishWork(upload_data);
//                setUploadClickable();
//            }*//*
//            if (workScore > score)
//                score = workScore;
//            if (haveMp3) {
//                //所有的录音全部上传完毕并且有分数这时就调done接口
//                showMyprogressDialog();
//                new TotalDoneMiddle(this, this).TotalDone(uid, stu_job_id + "");
//            }
//
//            //3.3作业提交方式
////            UploadBean uploadBean = (UploadBean) result;
////            setCommitTime(uploadBean.getData().getScore(), uploadBean.getData().getCommit_time());
////            DaoUtils.getInstance().cleanUploadSpendTime(String.valueOf(stu_job_id));
//            //TODO    更新数据库  使作业提交状态设置为不可提交
////            WorkCatalogUtils.getInstance().finishWork(this,upload_data);//更新数据库   让提交成功的句子改成不可提交状态
////            WorkCatalogUtils.getInstance().finishWork(upload_data);//更新数据库   让提交成功的句子改成不可提交状态
//            //跳转排行榜
//            *//*if (*//**//*!adapter.getIsDoneMap().containsKey(false)*//**//*adapter.getTurn_flag()) {
//                Intent intent = new Intent(this, RankActivity.class);
//                LogUtils.i("onsuccess---》intent");
//                intent.putExtra("job_id", job_id);
//                startActivity(intent);
//            }*//*
//
//            setUploadClickable();
//        }*/
//    }
//
//    /**
//     * 网络请求失败
//     */
//    @Override
//    public void failedFrom(Object... obj) {
//        super.failedFrom(obj);
//        hideMyprogressDialog();
//        int requestCode = (int) obj[0];
//        if (requestCode == WorkCatalogMiddle.FINISH_WORK || requestCode == WorkCatalogMiddle.UPLOAD_TEXT) {
//            WorkCatalogUtils.setViewClickable(tv_upload, true);
//        }
//        setUploadClickable();
//    }
//
//    /**
//     * 从数据库获取数据
//     */
//    public List<Quiz> getDataFromDb() {
//        //TODO 回调
//        return DaoUtils.getInstance().getQuizList(stu_job_id);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        hideMyprogressDialog();
//        data_db = getDataFromDb();
//        //3.2 此处请求分数
//        commit_score.setText(catalogUtils.getHomeWorkScore(this, data_db) + "");//上面不带动画的分数
//        commit_image.setImageResource(WorkCatalogUtils.getScoreImage(catalogUtils.getHomeWorkScore(this, data_db)));//带动画的分数
//        if (requestCode == DOWORK_CODE && resultCode == RESULT_OK) {
//
//            DialogUtils.hideMyprogressDialog(this);//关闭dialog
//
//            long spendtime = data.getLongExtra("spend_time", -1l);//耗时
//
//            long quiz_id = data.getLongExtra("quiz_id", -1l);
//            long hw_quiz_id = data.getLongExtra("hw_quiz_id", 0l);
//            isdone = data.getBooleanExtra("isdone", false);//真完成当前遍，假没完成当前遍
//            int currentType = Integer.valueOf(data.getStringExtra("currentType"));
//            switch (currentType) {//0:跟读 1:背诵 2:朗读     wo  1：跟读！2：朗读！3：背诵
//                case 0:
//                    currentType = 1;
//                    break;
//                case 1:
//                    currentType = 3;
//                    break;
//                case 2:
//                    currentType = 2;
//                    break;
//            }
//            try {
////                    DaoUtils.getInstance().addCount(hw_quiz_id + "", stu_job_id + "", spendtime, currentType, quiz_id);
//                DBUtils.getDbUtils().getSubmitTask(new DBUtils.SubmitInter() {
//                    @Override
//                    public void submitInter(final List<?> list, final long repeatTime, long num, int sign) {
//                        if (isdone) {
////                            DBUtils.getDbUtils().getDirectoryListInfo(new DBUtils.inter() {
////                                @Override
////                                public void getList(final List<?> list, boolean bb, int sign) {
////                                    UIUtils.runInMainThread(new Runnable() {
////                                        @Override
////                                        public void run() {
////                                            if (list != null && list.size() > 0)
////                                                getDbData(list, 1);
//////                                            setVisbale();
////                                        }
////                                    });
////                                }
////                            }, DATA_FROM_DB, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id);
//                        }
//                        UIUtils.runInMainThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                submitTaskList = (List<TaskListBean>) list;
//                                spend_time.setText(WorkCatalogUtils.getSpeendTimeStr(String.valueOf(repeatTime)));//耗时
//                                haveTask = true;
//                                setVisbale();
//                                isSubmitTask = list != null && list.size() > 0;
//                                setUploadClickable();
//                            }
//                        });
//                    }
//                }, 0, Long.valueOf(uerInfo.getUid()), Long.valueOf(uerInfo.getCid()), stu_job_id, quiz_id, currentType, spendtime, isdone, false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            if (isdone) {
////                data_db = getDataFromDb();
////                adapter.notifyDataSetChanged();
////            } else {
////                try {
////                    DaoUtils.getInstance().addTime(stu_job_id + "", spendtime);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    LogUtils.e("数据库时间递增出现问题了stu_job_id：" + stu_job_id + " spendtime:" + stu_job_id);
////                }
////            }
//            //TODO 回调
//        }
////        setUploadClickable();
//    }
//
//    /**
//     * 设置当前作业有没有做过-没做过不显示分数 - 做过显示分数
//     */
//    public void setVisbale() {
//        if (haveTask) {//下载过句子
//            catalog_result.setVisibility(View.VISIBLE);
//        } else {//没有下载过句子
//            catalog_result.setVisibility(View.GONE);
//        }
//        Log.i("haveTask", "haveTask = " + haveTask);
////        if (isdone) {
////            catalog_result.setVisibility(View.VISIBLE);
////            linear_description.setVisibility(View.GONE);
////        } else {
////            //TODO 回调
////            if (DaoUtils.getInstance().getSentenceCount(stu_job_id) == 0) {
////                catalog_result.setVisibility(View.GONE);
////                linear_description.setVisibility(View.VISIBLE);
////            } else if (DaoUtils.getInstance().getSentenceCount(stu_job_id) > 0) {
////                catalog_result.setVisibility(View.VISIBLE);
////                linear_description.setVisibility(View.GONE);
////            }
////        }
//    }
//
//    /**
//     * 设置提交按钮状态
//     */
//    //TODO   功能有待考虑
//    public void setUploadClickable() {
//        //TODO 回调
//        if (isSubmitTask) {
//            tv_upload.setText("上传并提交");
//        } else
//            tv_upload.setText("进入排名页");
//
////        upload_data = DaoUtils.getInstance().getUploadData(stu_job_id + "");
////        if (upload_data != null && upload_data.size() > 0) {
////            tv_upload.setBackgroundResource(R.drawable.btn_green_shap);
////            WorkCatalogUtils.setViewClickable(tv_upload, true);
////        } else {
////            tv_upload.setBackgroundResource(R.drawable.btn_gray_shap);
////            WorkCatalogUtils.setViewClickable(tv_upload, false);
////        }
//    }
//
//    /**
//     * 每次判断下是否有可提交的作业
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setUploadClickable();
//    }
//
//
//    @Override
//    protected String setTitle() {
//        return "作业目录";
//    }
//    /*****************此类的中间类  用于调取数据*****************************************/
//    /**
//     * 压缩文件结束回调方法
//     *
//     * @param flag
//     */
//    @Override
//    public void zipEnd(boolean flag, File file) {
//        hideMyprogressDialog();
//        if (flag) {
//            //上传压缩文件
//            UploadUtilZip.getInstance().upload(UserUtil.getUerInfo(this).getUid(), stu_job_id + "", this, file, this);
//        } else {
//            //挨个上传文件
//            upLoadUtils = new UpLoadUtils();
//            upLoadUtils.setQueues(upload_data, this, this);
//            upLoadUtils.startUpLoad(getString(R.string.textsuccess_medianext));
//        }
//    }
//
//    /**
//     * 上传结果返回
//     *
//     * @param state
//     */
//    @Override
//    public void uploadResult(int state, int flag, File file) {
//        if (state == UploadListener.NEVER || state == UploadListener.CANCEL) {
//            setUploadClickable();
//        } else if (state == UploadListener.FINISH) {
//            if (flag == UploadListener.ONE) {
//                if (file != null && file.isFile())
//                    file.delete();
//                WorkCatalogUtils.getInstance().finishWork(upload_data);//更新数据库   让提交成功的句子改成不可提交状态
//            } else if (flag == UploadListener.MORE) {
//                //TODO 回调
//                middle.setFinishWork(UserUtil.getUserInfoUid(this), stu_job_id + "", DaoUtils.getInstance().getTime(stu_job_id + ""), DaoUtils.getInstance().getPercent(stu_job_id) + "");
//            }
//            setUploadClickable();
//        } else if (state == UploadListener.ERROR) {
//            setUploadClickable();
//        }
////        清除文件
//        if (state == UploadListener.FINISH) {
//            catalogUtils.deleteZip();
//        }
//    }
//
//    public void setCommitTime(int score, String committime) {
//        for (int i = 0; i < data_db.size(); i++) {
//            data_db.get(i).setScore(score);
//            data_db.get(i).setCommit_time(committime);
//        }
//        DaoUtils.getInstance().saveQuizList(data_db);
////        data_db = getDataFromDb();
//        if (data_db != null && data_db.size() > 0) {
//            initView(data_db.get(0));
//        }
//        adapter.notifyDataSetChanged();
//    }
//
//    /**
//     * 设置屏幕最下方--点击
//     *
//     * @param notifyInfo
//     */
//    public void setNotifyInfo(int notifyInfo) {
//        if (notifyInfo == 100) {
//            notify_info.setVisibility(View.GONE);
//        } else {
//            notify_info.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
////        Action viewAction = Action.newAction(
////                Action.TYPE_VIEW, // TODO: choose an action type.
////                "WorkCatalog Page", // TODO: Define a title for the content shown.
//        // TODO: If you have web page content that matches this app activity's content,
//        // make sure this auto-generated web page URL is correct.
//        // Otherwise, set the URL to null.
////                Uri.parse("http://host/path"),
//        // TODO: Make sure this auto-generated app URL is correct.
////                Uri.parse("android-app://me.bandu.talk.android.phone.activity/http/host/path")
////        );
////        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
////        Action viewAction = Action.newAction(
////                Action.TYPE_VIEW, // TODO: choose an action type.
////                "WorkCatalog Page", // TODO: Define a title for the content shown.
//        // TODO: If you have web page content that matches this app activity's content,
//        // make sure this auto-generated web page URL is correct.
//        // Otherwise, set the URL to null.
////                Uri.parse("http://host/path"),
//        // TODO: Make sure this auto-generated app URL is correct.
////                Uri.parse("android-app://me.bandu.talk.android.phone.activity/http/host/path")
////        );
////        AppIndex.AppIndexApi.end(client, viewAction);
////        client.disconnect();
//    }
//}
