package me.bandu.talk.android.phone.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.adapter.DownloadManagerAdapter;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.mdao.MDownloadDao;
import me.bandu.talk.android.phone.manager.MDownloadManager;
import me.bandu.talk.android.phone.manager.MDownloadTask;
import me.bandu.talk.android.phone.view.DownLoadButton;
/**
 * 创建者：gaoye
 * 时间：2015/11/24  09:32
 * 类描述：下载管理
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DownloadManagerActivity extends BaseStudentActivity implements View.OnClickListener,MDownloadManager.DownloadListener,DownloadManagerAdapter.OnDownloadClickListener{
    @Bind(R.id.lv_download)
    ListView lv_download;
    @Bind(R.id.title_middle)
    TextView title_middle;
    @Bind(R.id.title_left)
    RelativeLayout title_left;
    private DownloadManagerAdapter adapter;
    private List<DownloadBean> downloads;
    private MDownloadManager downloadManager;
    //private MDownloadDao downloadDao;

    @Override
    protected void onResume() {
        super.onResume();
        downloadManager.onResume(this);
        downloadManager.setListener(this);
        setTasks();
    }

    @Override
    protected void onPause() {
        super.onPause();
        downloadManager.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download_manager;
    }

    @Override
    protected void toStart() {
        initView();
        setData();
        setListeners();
    }

    @Override
    public void initView() {
        title_middle.setText("下载管理");
    }

    @Override
    public void setData() {
        //downloadDao = new MDownloadDao(this);
        downloadManager = MDownloadManager.getInstence(this);
        downloads = new ArrayList<>();
        adapter = new DownloadManagerAdapter(this,downloads,this);
        lv_download.setAdapter(adapter);

    }

    private void setTasks() {
        downloads.clear();
        MDownloadDao downloadDao = new MDownloadDao(this);
        downloads.addAll(downloadDao.getAllData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setListeners() {
        title_left.setOnClickListener(this);
    }


    @Override
    public void stateChange(DownloadBean downloadBean) {
        //downloadDao.addData(downloadBean);
        int position = adapter.getPosition(downloadBean.getDownload_id());
        DownLoadButton button = adapter.getButton(position);
        if (position >= lv_download.getFirstVisiblePosition() && position <= lv_download.getLastVisiblePosition()){
            if (button != null)
                button.setPercent(downloadBean.getDownload_progress());
        }else {
            int state = downloadBean.getDownload_state();
            if(state == MDownloadTask.STATE_NOTDOWN){
                button.setComment();
            }else if(state == MDownloadTask.STATE_WAITTING){
                button.setWaitting();
            }else if(state == MDownloadTask.STATE_DOWNLOADFAIL){
                button.setFail();
            }
        }
    }

    @Override
    public void stateWaitting(DownloadBean downloadBean) {
        //downloadDao.addData(downloadBean);
        setTasks();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateError(DownloadBean downloadBean) {
        //downloadDao.deleteData(downloadBean.getDownload_id());
        setTasks();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateStart(DownloadBean downloadBean) {
        //downloadDao.addData(downloadBean);
        setTasks();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateFinish(DownloadBean downloadBean, List<CentenceBean> centences) {
        //downloadDao.deleteData(downloadBean.getDownload_id());
        //addCentenceList(centences);
        setTasks();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateStop(DownloadBean downloadBean) {
        //downloadDao.deleteData(downloadBean.getDownload_id());
        setTasks();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void downloadStart(DownloadBean downloadBean) {
        downloadManager.addTask(new MDownloadTask(this,downloadBean.getDownload_id(),downloadBean.getDownload_name(),downloadBean.getDownload_subject(),downloadBean.getDownload_category()));
    }

    @Override
    public void downloadStop(DownloadBean downloadBean) {
        MDownloadTask task = downloadManager.getTask(downloadBean.getDownload_id());
        if (task != null){
            downloadManager.stopTask(downloadBean.getDownload_id());
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }

}
