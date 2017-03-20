package me.bandu.talk.android.phone.manager;

/**
 * 创建者：gaoye
 * 时间：2016/1/4 16:17
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

import android.content.Context;
import android.text.TextUtils;

import com.DFHT.ENGlobalParams;
import com.DFHT.exception.NetException;
import com.DFHT.exception.ParseException;
import com.DFHT.exception.ServerConnException;
import com.DFHT.net.NetworkUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.ConstantValue;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.bean.ExerciseDowanloadInfoBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;
import me.bandu.talk.android.phone.utils.BeanUtil;
import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：gaoye
 * 时间：2016/1/4 10:31
 * 类描述：下载任务
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MDownloadTask implements Runnable {
    public static final int STATE_DOWNLOADFAIL = 0, STATE_DOWNLOADING = 1,STATE_WAITTING = 3,
            STATE_NOTDOWN = 4,STATE_DOWNLOADFINISH = 2,STATE_DOWNLOADSTOP = 5;
    private boolean stop;
    private DownloadState state;
    /*private long id;
    private String name;*/
    private DownloadBean downloadInfo;
    //private ExerciseDowanloadBean.DataEntity.ContentsEntity unit;
    private int subject,category;
    private Context context;
    private List<CentenceBean> centenceBeans;
    private long lessonid;
    private String userId;


    /*public MDownloadTask(Context context,ExerciseDowanloadInfoBean dowanloadInfoBean) {
        this.context = context;
        this.dowanloadInfoBean = dowanloadInfoBean;
        this.centenceBeans = new ArrayList<>();
        UnitBean unitBean = BeanUtil.toUnitBean(dowanloadInfoBean);
        this.id = unitBean.getUnit_id();
    }*/
    private MDownloadTask(Context context,long unitId) {
        this(context,unitId,0,-1);
    }
    private MDownloadTask(Context context,long unitId,int subject) {
        this(context,unitId,"",subject,-1);
    }
    private MDownloadTask(Context context,long unitId,int subject,int category) {
        this(context,unitId,"",subject,category);
    }
    public MDownloadTask(Context context, long unitId,String name, int subject,int category) {
        this(context,unitId,name,subject,-1,category);
    }
    public MDownloadTask(Context context, long unitId,String name, int subject,long lessonid,int category) {
        this(context,unitId,name,subject,-1, UserUtil.getUid(),category);
    }
    public MDownloadTask(Context context, long unitId,String name, int subject,long lessonid,String userid,int category) {
        this.context = context;
        this.subject = subject;
        this.category = category;
        this.lessonid = lessonid;
        this.userId = userid;
        this.downloadInfo = new DownloadBean();
        this.downloadInfo.setDownload_id(unitId);
        this.downloadInfo.setDownload_name(name);
        this.downloadInfo.setDownload_state(STATE_NOTDOWN);
        this.downloadInfo.setDownload_message("");
        this.downloadInfo.setDownload_progress(0);
        this.downloadInfo.setDownload_subject(subject);
        this.downloadInfo.setDownload_category(category);
        this.centenceBeans = new ArrayList<>();
    }
    public void setWaitting(){
        downloadInfo.setDownload_state(STATE_WAITTING);
        if (state != null){
            state.stateWaitting(userId,downloadInfo);
        }
    }

   /* public void setState(int state){
        getDownloadInfo().setDownload_state(state);
    }*/

    public int getState(){
        return getDownloadInfo().getDownload_state();
    }

    public void addDoanloadState(DownloadState state){
        this.state = state;
    }

    public void removeDoanloadState(){
        this.state = null;
    }

    public long getId(){
        return downloadInfo.getDownload_id();
    }

    public String getName(){
        return downloadInfo.getDownload_name();
    }

    @Override
    public void run() {
        downloadInfo.setDownload_state(STATE_DOWNLOADING);
        if (state != null){
            state.stateStart(userId,downloadInfo);
        }

        stop = false;
        index = 0;
        progress = 0;
        message = "";
        try {
            ExerciseDowanloadInfoBean dowanloadInfoBean = getNetData();
            category = dowanloadInfoBean.getData().getBook_category();
            downloadInfo.setDownload_name(dowanloadInfoBean.getData().getUnit_name());
            if (stop) {
                throw new StopException();
            }
            startDownload(dowanloadInfoBean);
            downloadInfo.setDownload_state(STATE_DOWNLOADFINISH);
            downloadInfo.setDownload_progress(100);
            if (state != null){
                state.stateFinish(userId,downloadInfo,centenceBeans,lessonid);
            }
        } catch (NetException e) {
            e.printStackTrace();
            downloadInfo.setDownload_state(STATE_DOWNLOADFAIL);
            if (state != null){
                state.stateError(userId,downloadInfo);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            downloadInfo.setDownload_state(STATE_DOWNLOADFAIL);
            if (state != null){
                state.stateError(userId,downloadInfo);
            }

        } catch (ServerConnException e) {
            e.printStackTrace();
            downloadInfo.setDownload_state(STATE_DOWNLOADFAIL);
            if (state != null){
                state.stateError(userId,downloadInfo);
            }
        } catch (StopException e) {
            e.printStackTrace();
            downloadInfo.setDownload_state(STATE_DOWNLOADSTOP);
            if (state != null){
                state.stateStop(userId,downloadInfo);
            }

        } catch (IOException e) {
            e.printStackTrace();
            downloadInfo.setDownload_state(STATE_DOWNLOADFAIL);
            if (state != null){
                state.stateError(userId,downloadInfo);
            }

        }catch (Exception e){
            e.printStackTrace();
            downloadInfo.setDownload_state(STATE_DOWNLOADFAIL);
            if (state != null){
                state.stateError(userId,downloadInfo);
            }

        }
    }

    public void setStop() {
        stop = true;
    }

    public void setStateWaittingStop(){
        downloadInfo.setDownload_state(STATE_DOWNLOADSTOP);
        if (state != null){
            state.stateStop(userId,downloadInfo);
        }
    }



    private ExerciseDowanloadInfoBean getNetData() throws NetException, ParseException, ServerConnException {
        Map<String,String> data = new HashMap();
        data.put("sue", GlobalParams.sue);
        data.put("sup", GlobalParams.sup);
        data.put("unit_id",downloadInfo.getDownload_id() + "");

        Object result = null;
        if (ENGlobalParams.applicationContext == null) {
            throw new IllegalArgumentException("参数异常 请在Application中初始化ENGlobalParams.applicationContext");
        }
        if (!NetworkUtil.checkNetwork(ENGlobalParams.applicationContext)) {
            throw new NetException();
        }

        String url = ConstantValue.EXERCISE_DOWNLOAD_INFO;
        Object obj = new ExerciseDowanloadInfoBean();
        result = NetworkUtil.postAndParse(url, data, obj.getClass(), 5000);
        if (result == null)
            return null;
        else
            return (ExerciseDowanloadInfoBean) result;
    }

    private int index;
    private int progress;
    private String message;
    public void startDownload(final ExerciseDowanloadInfoBean dowanloadInfoBean) throws IOException, StopException {
        centenceBeans.removeAll(centenceBeans);
        UnitBean unitBean = BeanUtil.toUnitBean(dowanloadInfoBean);
        unitBean.setTextbook_subject(subject);
        unitBean.setCategory(category);
        List<ExerciseDowanloadInfoBean.DataBean.LessonListBean> lessons = dowanloadInfoBean.getData().getLesson_list();
        int totle = getCentenceTotal(lessons);
        for (int i = 0; i < lessons.size(); i++) {
            ExerciseDowanloadInfoBean.DataBean.LessonListBean lesson = lessons.get(i);
            LessonBean lessonBean = BeanUtil.toLessonBean(lesson,unitBean);
            final List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean> parts = lesson.getQuiz_list();
            for (int j = 0; j < parts.size(); j++) {
                ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean part = parts.get(j);
                    /*part.setMp4("http://192.168.1.128:8088/bandu/video/1.mp4");
                    if (j % 2 == 0)
                        part.setMp4("http://192.168.1.128:8088/bandu/video/2.mp4");*/
                PartBean partBean = BeanUtil.toPartBean(part,context,lessonBean);
                final List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean> centences = part.getSentence_list();
                for (int k = 0; k < centences.size(); k++) {
                    final int finaltotle = totle;
                    final int finali = i;
                    final int finalj = j;
                    final int finalk = k;
                    progress = 100 * index / finaltotle;
                    message = "正在下载第" + (finali + 1) + "课第" + (finalj + 1) + "题第" + (finalk + 1) + "句子";
                    downloadInfo.setDownload_state(STATE_DOWNLOADING);
                    downloadInfo.setDownload_progress(progress);
                    downloadInfo.setDownload_message(message);
                    if (state != null){
                        state.stateChange(userId,downloadInfo);
                    }

                    ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean centence = centences.get(k);

                    if (!TextUtils.isEmpty(centence.getMp3())){
                        String filePath = FileUtil.getExerciseExamplePath(context, unitBean.getUnit_id());
                        String filename = centence.getSentence_id() + FileUtil.getUrlName(centence.getMp3());
                        downloadTo(centence.getMp3(), filePath, filename);
                    }
                    CentenceBean centenceBean = BeanUtil.toCentenceBean(centence,context,partBean);
                    centenceBean.setVideo_time((k + 1) * 3000);
                    centenceBeans.add(centenceBean);
                    index++;
                }
                if (!TextUtils.isEmpty(part.getMp4())){
                    String partFilePath = FileUtil.getExerciseExamplePath(context,unitBean.getUnit_id());
                    String partFileName = partBean.getPart_id() + FileUtil.getUrlName(part.getMp4());
                    partBean.setVideo_path(partFilePath + partFileName);
                    downloadTo(part.getMp4(),partFilePath,partFileName);
                }
            }
        }
    }

    private int getCentenceTotal(List<ExerciseDowanloadInfoBean.DataBean.LessonListBean> lessons){
        int totle = 0;
        for (int i = 0;i<lessons.size();i++){
            final List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean> parts =  lessons.get(i).getQuiz_list();
            for (int j = 0;j<parts.size();j++){
                final List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean> centences = parts.get(j).getSentence_list();
                for (int k = 0;k<centences.size();k++){
                    totle++;
                }
            }
        }
        return totle;
    }


    private synchronized void downloadTo(final String urlStr, final String path, final String name) throws IOException, StopException {
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(dir, name);
        InputStream is = null;
        OutputStream os = null;
        if (file.exists())
            file.delete();
        if (!file.exists())
            file.createNewFile();
        // 构造URL
        URL url = new URL(urlStr);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置超时时间
        con.setConnectTimeout(5000);
        is = con.getInputStream();
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        os = new FileOutputStream(file);
        while ((len = is.read(bs)) != -1) {
            if (stop) {
                throw new StopException();
            }
               /* if (state != null)
                    state.stateChange(downloadInfo.getDownload_id(),message ,progress);*/
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    public interface DownloadState {
        public void stateChange(String userid,DownloadBean downloadBean);

        public void stateError(String userid,DownloadBean downloadBean);

        public void stateFinish(String userid,DownloadBean downloadBean,List<CentenceBean> centenceBeens,long lessonid);

        public void stateStop(String userid,DownloadBean downloadBean);

        public void stateStart(String userid,DownloadBean downloadBean);

        public void stateWaitting(String userid,DownloadBean downloadBean);
    }

    private class StopException extends Exception {
    }

    private class NetErrorException extends Exception {
    }

    public DownloadBean getDownloadInfo(){
        return downloadInfo;
    }

    /*public class DownloadInfo{
        private long id;
        private String name;
        private int progress;
        private int state;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getProgress() {
            return progress;
        }

        public int getState() {
            return state;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public void setState(int state) {
            this.state = state;
        }
    }*/
}

