package me.bandu.talk.android.phone.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.StudentDownloadAdapter;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadBean;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadInfoBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.manager.MDownloadTask;
import me.bandu.talk.android.phone.middle.ExerciseDownloadInfoMiddle;
import me.bandu.talk.android.phone.middle.ExerciseDownloadMiddle;
import me.bandu.talk.android.phone.view.DownLoadButton;
/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：练习下载
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentExerciseDownloadActivity extends BaseStudentActivity implements MDownloadManager.DownloadListener,View.OnClickListener,StudentDownloadAdapter.OnDownloadClickListener{
    @Bind(R.id.lv_download_units)
    ListView lv_download_units;
    @Bind(R.id.title_middle)
    TextView title_middle;
    @Bind(R.id.title_left)
    RelativeLayout title_left;
    private ExerciseDownloadMiddle downloadMiddle;
    private ExerciseDownloadInfoMiddle downloadInfoMiddle;
    private List<ExerciseDowanloadBean.DataEntity.ContentsEntity> units;
    private StudentDownloadAdapter adapter;

    private ExerciseDowanloadInfoBean dowanloadInfoBean;
    private int bookid,subject,category;
    private MDownloadManager downloadManager;
    private ProgressDialog progressDialog;
    //private MDownloadDao downloadDao;

    @Override
    protected void onResume() {
        super.onResume();
        downloadManager.onResume(this);
        downloadManager.setListener(this);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        downloadManager.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_exercise_download;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
    }
    @Override
    public void initView() {
        title_middle.setText("选择单元");
    }

    @Override
    public void setData() {
        downloadManager = MDownloadManager.getInstence(this);
        bookid = getIntent().getIntExtra("bookid",0);
        subject = getIntent().getIntExtra("subject",0);
        category = getIntent().getIntExtra("category",-1);
        units = new ArrayList<>();
        adapter = new StudentDownloadAdapter(this,units,this);
        lv_download_units.setAdapter(adapter);
        downloadMiddle = new ExerciseDownloadMiddle(this,this);
        downloadInfoMiddle = new ExerciseDownloadInfoMiddle(this);
        downloadMiddle.getDownloadList(bookid + "");
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
    }

    @Override
    public void successFromMid(Object... obj) {
        super.successFromMid(obj);
        int code = (int) obj[1];
        if (code == ExerciseDownloadMiddle.EXERCISE_DOWNLOAD){
            ExerciseDowanloadBean dowanloadBean = (ExerciseDowanloadBean) obj[0];
            units.removeAll(units);
            units.addAll(dowanloadBean.getData().getContents());
            adapter.notifyDataSetChanged();
        }else if(code == ExerciseDownloadInfoMiddle.EXERCISE_DOWNLOAD_INFO){
            /*if (!iscancle){
                dowanloadInfoBean = (ExerciseDowanloadInfoBean) obj[0];
                downloadManager.addTask(new MDownloadTask(this,dowanloadInfoBean));
            }*/
            //download.setDownloadStateChangeListener(this);
            //download.startDownload(dowanloadInfoBean);
        }
    }

    @Override
    public void failedFrom(Object... obj) {
        super.failedFrom(obj);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

  /*  @Override
    public void downloadClickListener(long unitid) {
        showProgress(unitid);
        downloadManager.addTask(new MDownloadTask(this,unitid));
        *//*if (isdownloading){
            Toast.makeText(StudentExerciseDownloadActivity.this, "正在下载", Toast.LENGTH_SHORT).show();
            return;
        }
        isdownloading = true;
        iscancle = false;
        showProgress(unitid);
        downloadInfoMiddle.getDownloadInfo(unitid);*//*
    }*/


    /*private void addCentenceList(final List<CentenceBean> centenceBeans) {
        DaoSession daoSession = DaoFactory.getInstence(this).getSesson();
        daoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                MCentenceDao centenceDao = new MCentenceDao(StudentExerciseDownloadActivity.this);
                centenceDao.addListData(centenceBeans);
            }
        });
    }*/

    @Override
    public void stateChange(DownloadBean downloadBean) {
        //downloadDao.addData(downloadBean);
        int position = adapter.getPosition(downloadBean.getDownload_id());
        DownLoadButton button = adapter.getButton(position);
        if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
            if (button != null)
                button.setPercent(downloadBean.getDownload_progress());
        }else {
            if (downloadBean.getDownload_state() == MDownloadTask.STATE_DOWNLOADING){
                button.setPercent(downloadBean.getDownload_progress());
            }else if(downloadBean.getDownload_state() == MDownloadTask.STATE_NOTDOWN){
                button.setComment();
            }else if(downloadBean.getDownload_state() == MDownloadTask.STATE_WAITTING){
                button.setWaitting();
            }else if(downloadBean.getDownload_state() == MDownloadTask.STATE_DOWNLOADFAIL){
                button.setFail();
            }
        }
    }

    @Override
    public void stateWaitting(DownloadBean downloadBean) {
        /*int position = adapter.getPosition(downloadBean.getDownload_id());
        if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
            DownLoadButton button = adapter.getButton(position);
            if (button != null)
                button.setWaitting();
        }*/
        ///downloadDao.addData(downloadBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateError(DownloadBean downloadBean) {
        //downloadDao.deleteData(downloadBean.getDownload_id());
       adapter.notifyDataSetChanged();
       /* int position = adapter.getPosition(downloadBean.getDownload_id());
        if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
            DownLoadButton button = adapter.getButton(position);
            if (button != null)
                button.setComment();
        }*/
    }

    @Override
    public void stateStart(DownloadBean downloadBean) {
        //downloadDao.addData(downloadBean);
        adapter.notifyDataSetChanged();
       // Toast.makeText(StudentExerciseDownloadActivity.this, message, Toast.LENGTH_SHORT).show();
          /*  int position = adapter.getPosition(id);
            if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
                DownLoadButton button = adapter.getButton(position);
                if (button != null)
                    button.setPercent(0);
            }*/
    }

    @Override
    public void stateFinish(DownloadBean downloadBean,List<CentenceBean> centences) {
        //downloadDao.deleteData(downloadBean.getDownload_id());
        //addCentenceList(centences);
        adapter.notifyDataSetChanged();
        /*change = true;
        //progressDialog.dismiss();
        //Toast.makeText(StudentExerciseDownloadActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
        //adapter.notifyDataSetChanged();
        int position = adapter.getPosition(id);
        if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
            DownLoadButton button = adapter.getButton(position);
            if (button != null)
                button.setDownloaded();
        }*/
    }

    @Override
    public void stateStop(DownloadBean downloadBean) {
        //downloadDao.deleteData(downloadBean.getDownload_id());
        adapter.notifyDataSetChanged();
        /*progressDialog.dismiss();
        Toast.makeText(StudentExerciseDownloadActivity.this, "停止下载", Toast.LENGTH_SHORT).show();
        state[0] = -1;
        adapter.notifyDataSetChanged();*/
        /*int position = adapter.getPosition(id);
        if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
            DownLoadButton button = adapter.getButton(position);
            if (button != null)
                button
        }*/
        //adapter.notifyDataSetChanged();
        /*int position = adapter.getPosition(id);
        if (position >= lv_download_units.getFirstVisiblePosition() || position <= lv_download_units.getLastVisiblePosition()){
            DownLoadButton button = adapter.getButton(position);
            if (button != null)
                button.setComment();
        }*/
    }

    private void showProgress(final long dialogid) {
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
    }

    @Override
    public void downloadStart(ExerciseDowanloadBean.DataEntity.ContentsEntity unitid) {
        downloadManager.addTask(new MDownloadTask(this,unitid.getUnit_id(),unitid.getUnit_name(),subject,category));
    }

    @Override
    public void downloadStop(long unitid) {
        downloadManager.stopTask(unitid);
    }

}
