package me.bandu.talk.android.phone.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.WorkCommiteOverDueAdapter;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
import me.bandu.talk.android.phone.bean.UnitAndLesson;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.mdao.MLessonDao;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.manager.MDownloadTask;
import me.bandu.talk.android.phone.middle.WorkCatalogMiddle;
import me.bandu.talk.android.phone.utils.Animation3DUtil;
import me.bandu.talk.android.phone.utils.ScreenUtil;
import me.bandu.talk.android.phone.utils.WorkCatalogUtils;
import me.bandu.talk.android.phone.view.AlertDialogUtils;
/**
 * 创建者：高楠
 * 类描述：作业目录-已过期有结果
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
//TODO  把本页句子放在String
public class WorkCommitOverDueActivity extends BaseAppCompatActivity implements MDownloadManager.DownloadListener{
    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.commit_image)
    ImageView commit_image;
    @Bind(R.id.commit_score)
    TextView commit_score;
    @Bind(R.id.commit_icon)
    RelativeLayout layout;
    /**
     * 耗时
     */
    @Bind(R.id.spendtime_tv)
    TextView spendtime_tv;
    @Bind(R.id.big_title_over)
    TextView big_title_over;
    @Bind(R.id.tv_overtime)
    TextView tv_overtime;
    @Bind(R.id.iv_evaluated)
    ImageView iv_evaluated;
    @Bind(R.id.add_exercise_book)
    TextView submitBut;
    private long stu_job_id;
    private Bundle data;
    private String uid;
    private String job_id;
    private WorkCommiteOverDueAdapter adapter;
    private HomeWorkCatlogBean data_net;
    private WorkCatalogMiddle middle;
//    CustomDialog customDialog;
    private TextView tv_percent;
    //private ProgressBar progressBar;
    private long lesson_id;
    //private ProgressDialog progressDialog;
    private MDownloadManager downloadManager;
    private boolean evaluated = false;
    private boolean status;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_commit_overdue;
    }

    @Override
    protected void toStart() {
        downloadManager = MDownloadManager.getInstence(this);
        downloadManager.setListener(this);
        middle = new WorkCatalogMiddle(this,this);
        data = getIntent().getBundleExtra("data");
        evaluated = getIntent().getBooleanExtra("evaluated",false);
        status =data.getBoolean("status",false);
            //    .getBooleanExtra("status",false);
        if (evaluated){
            iv_evaluated.setVisibility(View.VISIBLE);
        }else {
            iv_evaluated.setVisibility(View.GONE);
        }
        if (data!=null){
            uid = data.getString("uid");
            job_id = data.getString("job_id");
            stu_job_id = Long.parseLong(data.getString("stu_job_id"));
        }
        adapter = new WorkCommiteOverDueAdapter(this,data_net,data);
        listView.setAdapter(adapter);
        getData();
        Animation3DUtil.getInstance().setView(layout,commit_image,commit_score);
        submitButShow(false);
    }
    @OnClick({R.id.commit_image,R.id.commit_score,R.id.add_exercise_book})
    void onClick(View view){
        switch (view.getId()){
            case R.id.commit_image:
                Animation3DUtil.getInstance().imageStart();
                break;
            case R.id.commit_score:
                Animation3DUtil.getInstance().textStart();
                break;
            case R.id.add_exercise_book:
                showMyprogressDialog();
                middle.getUnitLesson(job_id);
                break;
        }
    }
    /*public String getSpeendTimeStr(String s){
        try {
            if (s.contains(".")){
                s = s.substring(0,s.indexOf("."));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (s!=null){
            int time = Integer.parseInt(s);
            int second = (time)%60;
            int minut = (time/60)%60;
            int hour = (time/3600)%24;
            int day = time/3600/24;
            return (day==0?"":day+"天")+
                    (hour==0?"":hour+"小时")+
                    (minut==0?"":minut+"分钟")+
                    (second==0?"":second+"秒")
                    ;
        }else {
            return "0";
        }
    }*/

    public void submitButShow(boolean isShow) {
        if (isShow)
            submitBut.setVisibility(View.VISIBLE);
        else
            submitBut.setVisibility(View.GONE);
    }

    /**
     * 从网络获取数据
     */
    public void getData(){
        showMyprogressDialog();
        middle.getWorkCatalog(uid,job_id,stu_job_id);
    }
    /**
     * 网络请求成功获取数据
     */
    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (requestCode == WorkCatalogMiddle.WORK_CATALOG){
            HomeWorkCatlogBean bean = (HomeWorkCatlogBean) result;
            if (bean!=null&&bean.getStatus()==1){
                List<HomeWorkCatlogQuiz> quizs = bean.getData().getHomework().getQuizs();
                if (quizs != null && quizs.size() > 0) {
                    String quiz_type = quizs.get(0).getQuiz_type();
                    submitButShow(quiz_type == null || TextUtils.isEmpty(quiz_type));
                }
                if (evaluated){//评价的，真就是已评
                    spendtime_tv.setText(bean.getData().getHomework().getComment());
                    tv_overtime.setVisibility(View.GONE);
                }else if(!status){//是否已撤销，假 撤销
                    spendtime_tv.setText("耗时："+WorkCatalogUtils.getSpeendTime(String.valueOf(bean.getData().getHomework().getTotal_time())));
                    tv_overtime.setVisibility(View.VISIBLE);
                    tv_overtime.setText("此份作业已撤销");
                }else{//就是普通过期的
                    spendtime_tv.setVisibility(View.VISIBLE);
                    spendtime_tv.setText("耗时："+WorkCatalogUtils.getSpeendTime(String.valueOf(bean.getData().getHomework().getTotal_time())));
                    tv_overtime.setVisibility(View.VISIBLE);
                    tv_overtime.setText("最近上传时间："+bean.getData().getHomework().getCommit_time());
                }
                int score = bean.getData().getHomework().getScore();
                big_title_over.setText(bean.getData().getHomework().getTitle());
                commit_score.setText(score+"");
                if (score<55){
                    commit_image.setImageResource(R.mipmap.score_c);
                }else if (score>=85){
                    commit_image.setImageResource(R.mipmap.score_a);
                }else {
                    commit_image.setImageResource(R.mipmap.score_b);
                }

                data_net = bean;
                adapter.setBean(data_net);
            }
            hideMyprogressDialog();
        }else if (requestCode==WorkCatalogMiddle.UNIT_LESSON){
            final UnitAndLesson unitAndLesson = (UnitAndLesson) result;
            if (!isEmpty(unitAndLesson)){
                MLessonDao dao = new MLessonDao(this);
                final List<MDownloadTask> tasks = new ArrayList<>();
                for (int i = 0;i<unitAndLesson.getData().getList().size();i++){
                    lesson_id = unitAndLesson.getData().getList().get(i).getLesson_id();
                    if (!dao.hasKey(lesson_id)){
                        long unitid = unitAndLesson.getData().getList().get(0).getUnit_id();
                        tasks.add(new MDownloadTask(WorkCommitOverDueActivity.this,
                                unitid,unitAndLesson.getData().getList().get(0).getUnit_name(),0,lesson_id,-1));
                    }
                }

                if (tasks.size() == 0){
                    UIUtils.showToastSafe("已经在练习本中");
                }else {
                    if (ScreenUtil.isForeground(this,getClass().getName())&&!this.isFinishing()){
                        AlertDialogUtils.getInstance().init(WorkCommitOverDueActivity.this, "加入练习本",
                                "点击“确定”，本作业将以整单元形式下载。（P.S，请在有流量的情况下添加练习）", new AlertDialogUtils.DialogLestener() {
                                    @Override
                                    public void ok() {
                                        downloadManager.addTask(tasks);
                                        //middle.getDownloadInfo(unitAndLesson.getData().getList().get(0).getUnit_id()+"");
                                        /*long unitid = unitAndLesson.getData().getList().get(0).getUnit_id();
                                        downloadManager.addTask(new MDownloadTask(WorkCommitOverDueActivity.this,
                                                unitid,unitAndLesson.getData().getList().get(0).getUnit_name(),0,lesson_id,-1));*/
                                        //UIUtils.showToastSafe("练习本已开始下载");

                                    }

                                    @Override
                                    public void cancel() {

                                    }
                                });
                    }
                }

                /*if (dao.hasKey(lesson_id)){
                    if (dao.isAddExercise(lesson_id)){
                        UIUtils.showToastSafe("已经在练习本中");
                        return;
                    }
                    dao.addExercise(lesson_id);
                    UIUtils.showToastSafe("加入练习本成功！");
                }else {
                    if (ScreenUtil.isForeground(this,getClass().getName())&&!this.isFinishing()){
                        AlertDialogUtils.getInstance().init(WorkCommitOverDueActivity.this, "加入练习本",
                                "点击“确定”，本作业将以整单元形式下载。（P.S，请在有流量的情况下添加练习）", new AlertDialogUtils.DialogLestener() {
                                    @Override
                                    public void ok() {
                                        //middle.getDownloadInfo(unitAndLesson.getData().getList().get(0).getUnit_id()+"");
                                        long unitid = unitAndLesson.getData().getList().get(0).getUnit_id();
                                        downloadManager.addTask(new MDownloadTask(WorkCommitOverDueActivity.this,
                                                unitid,unitAndLesson.getData().getList().get(0).getUnit_name(),0,lesson_id,-1));
                                        UIUtils.showToastSafe("练习本已开始下载");

                                    }

                                    @Override
                                    public void cancel() {

                                    }
                                });
                    }

                }*/
            }else {
                UIUtils.showToastSafe("请保持网络畅通哟~！");
            }
            hideMyprogressDialog();
        }else if (requestCode==WorkCatalogMiddle.EXERCISE_DOWNLOAD_INFO){
            /*View view = View.inflate(this,R.layout.upload_work_layout,null);
            customDialog = new CustomDialog(this,view);
            tv_percent = (TextView) view.findViewById(R.id.progress_percent);
            TextView textView = (TextView) view.findViewById(R.id.tv_type);
            textView.setText("正在下载练习本...");
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
            tv_percent.setText("0%");
            progressBar.setMax(100);
            progressBar.setProgress(0);
            customDialog.show();
            ExerciseDowanloadInfoBean dowanloadInfoBean = (ExerciseDowanloadInfoBean) obj[0];
            ExerciseDownload download = new ExerciseDownload(this);
            download.setDownloadStateChangeListener(this);
            download.startDownload(dowanloadInfoBean);*/
            hideMyprogressDialog();
        }
    }
    public boolean isEmpty(UnitAndLesson unitAndLesson){
        if (unitAndLesson==null){
            return true;
        }
        if (unitAndLesson.getData()==null){
            return true;
        }
        if (unitAndLesson.getData().getList()==null){
            return true;
        }
        if (unitAndLesson.getData().getList().size()==0){
            return true;
        }
        return false;
    }
    /**
     * 网络请求失败
     */
    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
//        hideMyprogressDialog();
        int requestCode = (int) obj[0];
        if (requestCode == WorkCatalogMiddle.WORK_CATALOG){
            adapter.setBean(null);
        }
    }

    /*@Override
    public void downloadError() {
        UIUtils.showToastSafe("加入练习本失败~");
    }

    @Override
    public void downloadFinish(final List<CentenceBean> centences) {
        DaoSession session = DaoFactory.getInstence(WorkCommitOverDueActivity.this).getSesson();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                MCentenceDao centenceDao = new MCentenceDao(WorkCommitOverDueActivity.this);
                centenceDao.addListData(centences);
                new MLessonDao(WorkCommitOverDueActivity.this).addExercise(lesson_id);
            }
        });
        GlobalParams.ADD_EXERCISE = true;
        UIUtils.showToastSafe("加入练习本成功");
        customDialog.dismiss();
    }

    @Override
    public void downloadStateChange(int percent, String message) {
        tv_percent.setText(percent+"%");
        progressBar.setProgress(percent);
    }

    @Override
    public void downloadStart() {

    }*/

    /*@Override
    public void stateChange(long id, String message, int progress) {
        progressDialog.setProgress(progress);
        progressDialog.setMessage(message);
    }

    @Override
    public void stateWaitting(long id) {

    }

    @Override
    public void stateError(long id) {
        UIUtils.showToastSafe("加入练习本失败~");
        progressDialog.dismiss();
    }

    @Override
    public void stateStart(long id,boolean success, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("开始下载");
        progressDialog.setProgress(0);
    }

    @Override
    public void stateFinish(long id, final List<CentenceBean> centences) {
        DaoSession session = DaoFactory.getInstence(WorkCommitOverDueActivity.this).getSesson();
        session.runInTx(new Runnable() {
            @Override
            public void run() {
                MCentenceDao centenceDao = new MCentenceDao(WorkCommitOverDueActivity.this);
                centenceDao.addListData(centences);
                new MLessonDao(WorkCommitOverDueActivity.this).addExercise(lesson_id);
            }
        });
        GlobalParams.ADD_EXERCISE = true;
        UIUtils.showToastSafe("加入练习本成功");
        progressDialog.dismiss();
    }

    @Override
    public void stateStop(long id) {
        progressDialog.dismiss();
        Toast.makeText(this, "停止下载", Toast.LENGTH_SHORT).show();
    }*/

    /*public void showProgress(final long dialogid) {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        }
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadManager.stopTask(dialogid);
            }
        });
        progressDialog.setMessage("正在连接网络...");
        progressDialog.setProgress(0);
        progressDialog.show();
    }*/

    @Override
    public void stateChange(DownloadBean downloadBean) {

    }

    @Override
    public void stateWaitting(DownloadBean downloadBean) {

    }

    @Override
    public void stateError(DownloadBean downloadBean) {

    }

    @Override
    public void stateStart(DownloadBean downloadBean) {

    }

    @Override
    public void stateFinish(DownloadBean downloadBean, List<CentenceBean> centences) {

    }

    @Override
    public void stateStop(DownloadBean downloadBean) {

    }
    //TODO   放到String中
    @Override
    protected String setTitle() {
        return "作业结果";
    }
}
