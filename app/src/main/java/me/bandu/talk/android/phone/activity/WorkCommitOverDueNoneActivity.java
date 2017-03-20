package me.bandu.talk.android.phone.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.DFHT.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogBean;
import me.bandu.talk.android.phone.bean.HomeWorkCatlogQuiz;
import me.bandu.talk.android.phone.bean.UnitAndLesson;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.mdao.MLessonDao;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.manager.MDownloadTask;
import me.bandu.talk.android.phone.middle.WorkCatalogMiddle;
import me.bandu.talk.android.phone.view.AlertDialogUtils;
import me.bandu.talk.android.phone.view.CustomDialog;

import static me.bandu.talk.android.phone.utils.ExerciseDownload.OnExerciseDownloadStateChangeListener;

/**
 * 创建者：高楠
 * 类描述：作业目录-已过期无结果
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkCommitOverDueNoneActivity extends BaseAppCompatActivity implements MDownloadManager.DownloadListener, OnExerciseDownloadStateChangeListener {
    /**
     * 返回按钮
     */
    @Bind(R.id.add_exercise_book)
    TextView submitBut;
    private WorkCatalogMiddle middle;
    private Bundle data;
    private String uid;
    private String job_id;
    private long stu_job_id;
    CustomDialog customDialog;
    private TextView tv_percent;
    private ProgressBar progressBar;
    private long lesson_id;
    private MDownloadManager downloadManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_commit_overdue_none;
    }

    @Override
    protected void toStart() {
        downloadManager = MDownloadManager.getInstence(this);
        downloadManager.setListener(this);
        middle = new WorkCatalogMiddle(this, this);
        data = getIntent().getBundleExtra("data");
        if (data != null) {
            uid = data.getString("uid");
            job_id = data.getString("job_id");
            stu_job_id = Long.parseLong(data.getString("stu_job_id"));
            getData();
            submitButShow(false);
        }
    }

    /**
     * 从网络获取数据
     */
    public void getData() {
        showMyprogressDialog();
        middle.getWorkCatalog(uid, job_id, stu_job_id);
    }

    @OnClick(R.id.add_exercise_book)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_exercise_book:
                showMyprogressDialog();
                middle.getUnitLesson(job_id);
                break;
        }
    }

    public void submitButShow(boolean isShow) {
        if (isShow)
            submitBut.setVisibility(View.VISIBLE);
        else
            submitBut.setVisibility(View.GONE);
    }

    /**
     * 网络请求成功获取数据
     */
    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        hideMyprogressDialog();
        int requestCode = (int) obj[1];
        Object result = obj[0];
        if (requestCode == WorkCatalogMiddle.WORK_CATALOG) {
            HomeWorkCatlogBean bean = (HomeWorkCatlogBean) result;
            List<HomeWorkCatlogQuiz> quizs = bean.getData().getHomework().getQuizs();
            if (quizs != null && quizs.size() > 0) {
                String quiz_type = quizs.get(0).getQuiz_type();
                submitButShow(quiz_type == null || TextUtils.isEmpty(quiz_type));
            }
        }
        if (requestCode == WorkCatalogMiddle.UNIT_LESSON) {
            final UnitAndLesson unitAndLesson = (UnitAndLesson) result;
            if (!isEmpty(unitAndLesson)) {
                MLessonDao dao = new MLessonDao(this);
                lesson_id = unitAndLesson.getData().getList().get(0).getLesson_id();
                if (dao.hasKey(lesson_id)) {
                    if (dao.isAddExercise(lesson_id)) {
                        UIUtils.showToastSafe("已经在练习本中");
                        return;
                    }
                    dao.addExercise(lesson_id);
                    UIUtils.showToastSafe("加入练习本成功！");
                } else {
                    AlertDialogUtils.getInstance().init(this, "加入练习本", "点击“确定”，本作业将以整单元形式下载。（P.S，请在有流量的情况下添加练习）", new AlertDialogUtils.DialogLestener() {
                        @Override
                        public void ok() {
//                            middle.getDownloadInfo(unitAndLesson.getData().getList().get(0).getUnit_id()+"");
                            long unitid = unitAndLesson.getData().getList().get(0).getUnit_id();
                            downloadManager.addTask(new MDownloadTask(WorkCommitOverDueNoneActivity.this, unitid, unitAndLesson.getData().getList().get(0).getUnit_name(), 0, lesson_id, -1));
                            UIUtils.showToastSafe("练习本已开始下载");
                        }

                        @Override
                        public void cancel() {

                        }
                    });

                }
//                if (dao.hasKey(unitAndLesson.getData().getList().get(0).getLesson_id())){
//                    dao.addExercise(unitAndLesson.getData().getList().get(0).getLesson_id());
//                    UIUtils.showToastSafe("加入练习本成功！");
//                }else {
//                    AlertDialogUtils.getInstance().init(this, "下载", "确认下载该练习本?", new AlertDialogUtils.DialogLestener() {
//                        @Override
//                        public void ok() {
//                            middle.getDownloadInfo(unitAndLesson.getData().getList().get(0).getUnit_id()+"");
//                        }
//
//                        @Override
//                        public void cancel() {
//
//                        }
//                    });
//                }
            } else {
                UIUtils.showToastSafe("请保持网络畅通哟~！");
            }
        } else if (requestCode == WorkCatalogMiddle.EXERCISE_DOWNLOAD_INFO) {
//            View view = View.inflate(this,R.layout.upload_work_layout,null);
//            customDialog = new CustomDialog(this,view);
//            tv_percent = (TextView) view.findViewById(R.id.progress_percent);
//            TextView textView = (TextView) view.findViewById(R.id.tv_type);
//            textView.setText("正在下载练习本...");
//            progressBar = (ProgressBar) view.findViewById(R.id.progress);
//            tv_percent.setText("0%");
//            progressBar.setMax(100);
//            progressBar.setProgress(0);
//            customDialog.show();
//            ExerciseDowanloadInfoBean dowanloadInfoBean = (ExerciseDowanloadInfoBean) obj[0];
//            ExerciseDownload download = new ExerciseDownload(this);
//            download.setDownloadStateChangeListener(this);
//            download.startDownload(dowanloadInfoBean);
        }
    }

    public boolean isEmpty(UnitAndLesson unitAndLesson) {
        if (unitAndLesson == null) {
            return true;
        }
        if (unitAndLesson.getData() == null) {
            return true;
        }
        if (unitAndLesson.getData().getList() == null) {
            return true;
        }
        if (unitAndLesson.getData().getList().size() == 0) {
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
        hideMyprogressDialog();
    }

    @Override
    public void downloadError() {
        UIUtils.showToastSafe("加入练习本失败~");
    }

    @Override
    public void downloadFinish(final List<CentenceBean> centences) {
//        DaoSession session = DaoFactory.getInstence(this).getSesson();
//        session.runInTx(new Runnable() {
//            @Override
//            public void run() {
//                MCentenceDao centenceDao = new MCentenceDao(WorkCommitOverDueNoneActivity.this);
//                centenceDao.addListData(centences);
//            }
//        });
//        GlobalParams.ADD_EXERCISE = true;
//        UIUtils.showToastSafe("加入练习本成功");
//        customDialog.dismiss();
    }

    @Override
    public void downloadStateChange(int percent, String message) {
//        tv_percent.setText(percent+"%");
//        progressBar.setProgress(percent);
    }

    @Override
    public void downloadStart() {

    }

    @Override
    protected String setTitle() {
        return "作业结果";
    }

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
}
