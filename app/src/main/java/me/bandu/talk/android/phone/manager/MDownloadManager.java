package me.bandu.talk.android.phone.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.activity.StudentHomeActivity;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.mdao.MCentenceDao;
import me.bandu.talk.android.phone.db.mdao.MDownloadDao;
import me.bandu.talk.android.phone.db.mdao.MLessonDao;
import me.bandu.talk.android.phone.fragment.StudentExerciseFragment;
import me.bandu.talk.android.phone.utils.StringUtil;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：gaoye
 * 时间：2016/1/4 10:02
 * 类描述：下载管理
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MDownloadManager implements MDownloadTask.DownloadState {
    private final int CORE_POOL_SIZE = 0;
    private final int MAX_POOL_SIZE = 3;
    private final long KEEP_ALIVE_TIME = 0;
    private static MDownloadManager instence;
    private ProgressDialog progressDialog;
    //private ExecutorService executorService;
    private DownloadListener listener;
    private Map<Long, MDownloadTask> tasks;
    private List<MDownloadTask> waittask;
    private Context context;
    private static String userid = "";

    private MDownloadManager() {
    }

    public static synchronized MDownloadManager getInstence(Context context) {
        if (userid.equals(UserUtil.getUid())) {
            if (instence == null)
                instence = new MDownloadManager();
            instence.context = context;
            if (instence.tasks == null)
                instence.tasks = new HashMap<>();
            if (instence.waittask == null) {
                instence.waittask = Collections.synchronizedList(new ArrayList<MDownloadTask>());
            }
        } else {
            instence = new MDownloadManager();
            instence.context = context;
            if (instence.tasks == null)
                instence.tasks = new HashMap<>();
            if (instence.waittask == null) {
                instence.waittask = Collections.synchronizedList(new ArrayList<MDownloadTask>());
            }
            userid = UserUtil.getUid();
        }

        return instence;
    }

    public void unLogin() {
        userid = "";
    }

    public void onResume(Context context) {
        instence.context = context;
    }

    public void onPause() {
        instence.listener = null;
    }

    public void setListener(DownloadListener listener) {
        instence.listener = listener;
    }

    public void addTask(MDownloadTask task) {
        instence.addTask(task, task.getId(), this);
    }

    public void addTask(List<MDownloadTask> tasks){
        for (int i = 0;i<tasks.size();i++){
            addTask(tasks.get(i));
        }
    }

    public void addTask(MDownloadTask task, MDownloadTask.DownloadState state) {
        instence.addTask(task, task.getId(), state);
    }

    public void addTask(final MDownloadTask task, long id, MDownloadTask.DownloadState state) {
        MLessonDao dao = new MLessonDao(context);
        if (dao.hasKey(id)){
            return;
        }
        if (isDownload(task)) {
            //UIUtils.showToastSafe("已经在下载中...");
            Toast.makeText(context, "已经在下载中...", Toast.LENGTH_SHORT).show();
            return;
        }

        task.addDoanloadState(state);
        Toast.makeText(context, StringUtil.getShowText(task.getName()) + "加入下载，请在下载管理页查看", Toast.LENGTH_SHORT).show();
        if (tasks.size() >= MAX_POOL_SIZE) {
            setWaitting(task);
        } else {
            //showProgress(id);
            tasks.put(id, task);
            //UIUtils.startTaskInThreadPool(task);
            //task.run();
            new Thread(task).start();
        }

    }

    public List<MDownloadTask> getAllTasks() {
        List<MDownloadTask> taskArrayList = new ArrayList<>();
        for (MDownloadTask task : tasks.values()) {
            taskArrayList.add(task);
        }
        for (MDownloadTask task : waittask) {
            taskArrayList.add(task);
        }
        return taskArrayList;
    }

    private void setWaitting(MDownloadTask task) {
        waittask.add(task);
        task.setWaitting();
    }

    private void setWaitToTask() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (tasks.size() < MAX_POOL_SIZE) {
                    if (waittask.size() > 0) {
                        MDownloadTask task = waittask.get(0);
                        if (task != null) {
                            waittask.remove(0);
                            addTask(task);
                        }
                    }
                }
            }
        });
    }

    private boolean isDownload(MDownloadTask task) {
        if (task == null)
            return true;
        if (tasks.containsKey(task.getId())) {
            return true;
        }
        if (waittask.contains(task)) {
            return true;
        }
        return false;
    }

    public boolean canDownload(long unitid) {
        if (tasks.size() >= MAX_POOL_SIZE) {
            return false;
        }
        if (tasks.containsKey(unitid)) {
            return false;
        }
        return true;
    }

    public ProgressDialog getShowProgress(final long dialogid) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        }
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                instence.stopTask(dialogid);
            }
        });
        progressDialog.setMessage("正在连接网络...");
        progressDialog.setProgress(0);
        progressDialog.show();
        return progressDialog;
    }

    public void stopTask(long id) {
        for (int i = 0; i < waittask.size(); i++) {
            if (waittask.get(i).getId() == id) {
                waittask.get(i).setStateWaittingStop();
                waittask.remove(i);
                break;
            }
        }

        MDownloadTask task = tasks.get(id);
        if (task != null) {
            task.setStop();
        }
    }

    public void stopAll() {
        for (int i = 0; i < waittask.size(); i++) {
            MDownloadTask task = waittask.get(i);

            if (task != null) {
                /*MDownloadDao downloadDao = new MDownloadDao(context);
                downloadDao.deleteData(task.getId());*/
                stopTask(task.getId());
            }
        }
        for (MDownloadTask task : tasks.values()) {
            if (task != null) {
               /* MDownloadDao downloadDao = new MDownloadDao(context);
                downloadDao.deleteData(task.getId());*/
                stopTask(task.getId());
            }
        }
    }

    public int getState(long id) {
        for (MDownloadTask task : waittask) {
            if (task.getId() == id) {
                return task.getState();
            }
        }
        MDownloadTask task = tasks.get(id);
        if (task != null)
            return task.getState();
        return MDownloadTask.STATE_NOTDOWN;
    }

    public MDownloadTask getTask(long id) {
        for (MDownloadTask task : waittask) {
            if (task.getId() == id) {
                return task;
            }
        }
        MDownloadTask task = tasks.get(id);
        if (task != null)
            return task;
        return null;
    }


    @Override
    public void stateChange(final String userid, final DownloadBean downloadBean) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (UserUtil.getUid().equals(userid)) {
                    MDownloadDao downloadDao = new MDownloadDao(context);
                    downloadDao.addData(downloadBean);
                    if (listener != null) {
                        listener.stateChange(downloadBean);
                    }
                }
            }
        });
    }

    @Override
    public void stateError(final String userid, final DownloadBean downloadBean) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (UserUtil.getUid().equals(userid)) {
                    MDownloadDao downloadDao = new MDownloadDao(context);
                    downloadDao.addData(downloadBean);
                    tasks.remove(downloadBean.getDownload_id());
                    setWaitToTask();
                    Intent intent = new Intent();
                    intent.setAction(StudentHomeActivity.ACTION_DOWNTASK_CHANGE);
                    context.sendBroadcast(intent);
                    GlobalParams.taskChange = true;
                    UIUtils.showToastSafe(downloadBean.getDownload_name() + "下载失败");
                    if (listener != null)
                        listener.stateError(downloadBean);
                }
            }
        });
    }

    @Override
    public void stateFinish(final String userid, final DownloadBean downloadBean, final List<CentenceBean> centences, final long lessonid) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (UserUtil.getUid().equals(userid)) {
                    MDownloadDao downloadDao = new MDownloadDao(context);
                    downloadDao.deleteData(downloadBean.getDownload_id());
                    addCentenceList(centences);
                    addLesson(lessonid);
                    Toast.makeText(context, downloadBean.getDownload_name() + "下载完成", Toast.LENGTH_SHORT).show();
                    tasks.remove(downloadBean.getDownload_id());
                    setWaitToTask();
                    Intent intent = new Intent();
                    intent.setAction(StudentHomeActivity.ACTION_DOWNTASK_CHANGE);
                    context.sendBroadcast(intent);
                    intent.setAction(StudentHomeActivity.ACTION_DATABASE_CHANGE);
                    context.sendBroadcast(intent);
                    GlobalParams.taskChange = true;
                    GlobalParams.exerciseDatabaseChange = true;
                    if (listener != null)
                        listener.stateFinish(downloadBean, centences);
                }
            }
        });
    }


    @Override
    public void stateStop(final String userid, final DownloadBean downloadBean) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (UserUtil.getUid().equals(userid)) {
                    MDownloadDao downloadDao = new MDownloadDao(context);
                    downloadDao.deleteData(downloadBean.getDownload_id());
                    tasks.remove(downloadBean.getDownload_id());
                    setWaitToTask();
                    Intent intent = new Intent();
                    intent.setAction(StudentHomeActivity.ACTION_DOWNTASK_CHANGE);
                    context.sendBroadcast(intent);
                    GlobalParams.taskChange = true;
                    if (listener != null)
                        listener.stateStop(downloadBean);
                }

            }
        });
    }

    @Override
    public void stateStart(final String userid, final DownloadBean downloadBean) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (UserUtil.getUid().equals(userid)) {
                    MDownloadDao downloadDao = new MDownloadDao(context);
                    downloadDao.addData(downloadBean);
                    Intent intent = new Intent();
                    intent.setAction(StudentHomeActivity.ACTION_DOWNTASK_CHANGE);
                    context.sendBroadcast(intent);
                    GlobalParams.taskChange = true;
                    if (listener != null)
                        listener.stateStart(downloadBean);
                }
            }
        });

    }

    @Override
    public void stateWaitting(final String userid, final DownloadBean downloadBean) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (UserUtil.getUid().equals(userid)) {
                    MDownloadDao downloadDao = new MDownloadDao(context);
                    downloadDao.addData(downloadBean);
                    Intent intent = new Intent();
                    intent.setAction(StudentHomeActivity.ACTION_DOWNTASK_CHANGE);
                    context.sendBroadcast(intent);
                    GlobalParams.taskChange = true;
                    if (listener != null)
                        listener.stateWaitting(downloadBean);
                }
            }
        });
    }

    private void addLesson(long lessonid) {
        if (lessonid != -1) {
            MLessonDao dao = new MLessonDao(context);
            dao.addExercise(lessonid);
        }
    }

    public interface DownloadListener {
        public void stateChange(DownloadBean downloadBean);

        public void stateWaitting(DownloadBean downloadBean);

        public void stateError(DownloadBean downloadBean);

        public void stateStart(DownloadBean downloadBean);

        public void stateFinish(DownloadBean downloadBean, List<CentenceBean> centences);

        public void stateStop(DownloadBean downloadBean);
    }

    private void addCentenceList(final List<CentenceBean> centenceBeans) {
        MCentenceDao centenceDao = new MCentenceDao(context);
        centenceDao.addListData(centenceBeans);
    }

    public int getWaittingsum() {
        return waittask.size();
    }

    public int getLoaddingsum() {
        return tasks.size();
    }

}