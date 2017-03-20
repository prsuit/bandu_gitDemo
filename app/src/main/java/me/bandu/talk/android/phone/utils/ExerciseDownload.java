package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.DFHT.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.bean.ExerciseDowanloadInfoBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;

/**
 * 创建者：gaoye
 * 时间：2015/12/23 19:09
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseDownload {
    boolean isloading;
    boolean isError;
    int index,totle;
    private Context context;
    private OnExerciseDownloadStateChangeListener downloadStateChangeListener;
    private List<CentenceBean> centenceBeans;

    public ExerciseDownload(){}
    public ExerciseDownload(Context context){
        this.context = context;
        this.centenceBeans = new ArrayList<>();
    }

    public void setDownloadStateChangeListener(OnExerciseDownloadStateChangeListener downloadStateChangeListener){
        this.downloadStateChangeListener = downloadStateChangeListener;
    }

    public void startDownload(final ExerciseDowanloadInfoBean dowanloadInfoBean) {
        if (isloading){
            Toast.makeText(context, "正在下载！", Toast.LENGTH_SHORT).show();
            return;
        }

        UIUtils.startTaskInThreadPool(new Runnable() {
            @Override
            public void run() {
                if (downloadStateChangeListener != null)
                    UIUtils.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            downloadStateChangeListener.downloadStart();
                        }
                    });

                isError = false;
                index = 0;
                centenceBeans.removeAll(centenceBeans);
                UnitBean unitBean = new UnitBean();
                unitBean.setTextbook_id(dowanloadInfoBean.getData().getBook_id());
                unitBean.setTextbook_name(dowanloadInfoBean.getData().getBook_name());
                unitBean.setUnit_id((long) dowanloadInfoBean.getData().getUnit_id());
                unitBean.setUnit_name(dowanloadInfoBean.getData().getUnit_name());

                List<ExerciseDowanloadInfoBean.DataBean.LessonListBean> lessons = dowanloadInfoBean.getData().getLesson_list();
                int totle = getCentenceTotal(lessons);
                for (int i = 0; i < lessons.size(); i++) {
                    LessonBean lessonBean = new LessonBean();
                    ExerciseDowanloadInfoBean.DataBean.LessonListBean lesson = lessons.get(i);
                    lessonBean.setIsadd(false);
                    lessonBean.setUnitBean(unitBean);
                    lessonBean.setLesson_id((long) lesson.getLesson_id());
                    lessonBean.setLesson_name(lesson.getLesson_name());
                    final List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean> parts = lesson.getQuiz_list();
                    for (int j = 0; j < parts.size(); j++) {
                        if (isError) {
                            isloading = false;
                            UIUtils.runInMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    downloadStateChangeListener.downloadError();
                                }
                            });
                            return;
                        }
                        ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean part = parts.get(j);
                        PartBean partBean = new PartBean();
                        partBean.setLessonBean(lessonBean);
                        partBean.setCentence_start(0);
                        partBean.setPart_id((long) part.getQuiz_id());
                        partBean.setPart_name(part.getQuiz_name());
                        if (!TextUtils.isEmpty("")){
                            String partFilePath = FileUtil.getExerciseExamplePath(context,unitBean.getUnit_id());
                            String partFileName = partBean.getPart_id() + FileUtil.getUrlName("");
                            partBean.setVideo_path(partFilePath + partFilePath);
                            DownloadUtil.downloadTo("", partFilePath, partFileName, new DownloadUtil.DownloadState() {
                                @Override
                                public void stateChange(int percent) {
                                }

                                @Override
                                public void stateError() {
                                    isError = true;
                                }

                                @Override
                                public void stateStart() {
                                }

                                @Override
                                public void stateFinish() {
                                }
                            });
                        }

                        final List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean> centences = part.getSentence_list();
                        for (int k = 0; k < centences.size(); k++) {
                            if (isError) {
                                isloading = false;
                                UIUtils.runInMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        downloadStateChangeListener.downloadError();
                                    }
                                });
                                return;
                            }

                            ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean centence = centences.get(k);
                            CentenceBean centenceBean = new CentenceBean();
                            String filePath = FileUtil.getExerciseExamplePath(context, unitBean.getUnit_id());
                            String filename = centence.getSentence_id() + FileUtil.getUrlName(centence.getMp3());
                            centenceBean.setUrl_exemple(filePath + filename);
                            centenceBean.setPartBean(partBean);
                            centenceBean.setCentence_id((long) centence.getSentence_id());
                            centenceBean.setChines(centence.getZh());
                            centenceBean.setEnglish(centence.getEn());
                            centenceBean.setSeconds(centence.getSeconds());
                            centenceBeans.add(centenceBean);
                            index++;
                            final int finaltotle = totle;
                            final int finali = i;
                            final int finalj = j;
                            final int finalk = k;
                            if (downloadStateChangeListener != null)
                                UIUtils.runInMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        downloadStateChangeListener.downloadStateChange(100 * index / finaltotle, "正在下载第" + (finali + 1) + "课第" + (finalj + 1) + "题第" + (finalk + 1) + "句子");
                                    }
                                });

                            DownloadUtil.downloadTo(centence.getMp3(), filePath, filename, new DownloadUtil.DownloadState() {
                                @Override
                                public void stateChange(int percent) {
                                }

                                @Override
                                public void stateError() {
                                    isError = true;
                                }

                                @Override
                                public void stateStart() {
                                }

                                @Override
                                public void stateFinish() {
                                }
                            });

                        }
                    }
                }
                if (downloadStateChangeListener != null)
                    if (isError)
                        UIUtils.runInMainThread(new Runnable() {
                            @Override
                            public void run() {

                                downloadStateChangeListener.downloadError();
                            }
                        });
                    else
                        UIUtils.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                downloadStateChangeListener.downloadFinish(centenceBeans);
                            }
                        });
                isloading = false;
            }

        });

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

    public void setStop(){
        DownloadUtil.stop = true;
    }

    public interface OnExerciseDownloadStateChangeListener{
        public void downloadError();
        public void downloadFinish(List<CentenceBean> centences);
        public void downloadStateChange(int percent,String message);
        public void downloadStart();
    }
}
