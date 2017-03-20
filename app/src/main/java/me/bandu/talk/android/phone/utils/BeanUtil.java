package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.bandu.talk.android.phone.bean.ExerciseDowanloadInfoBean;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;

/**
 * 创建者：gaoye
 * 时间：2016/1/6 12:40
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BeanUtil {
    public static UnitBean toUnitBean(ExerciseDowanloadInfoBean dowanloadInfoBean){
        UnitBean unitBean = new UnitBean();
        unitBean.setTextbook_id(dowanloadInfoBean.getData().getBook_id());
        unitBean.setTextbook_name(dowanloadInfoBean.getData().getBook_name());
        unitBean.setUnit_id((long) dowanloadInfoBean.getData().getUnit_id());
        unitBean.setUnit_name(dowanloadInfoBean.getData().getUnit_name());
        return unitBean;
    }

    public static LessonBean toLessonBean(ExerciseDowanloadInfoBean.DataBean.LessonListBean lesson,UnitBean unitBean){
        LessonBean lessonBean = new LessonBean();
        lessonBean.setIsadd(false);
        lessonBean.setUnitBean(unitBean);
        lessonBean.setLesson_id((long) lesson.getLesson_id());
        lessonBean.setLesson_name(lesson.getLesson_name());
        return lessonBean;
    }

    public static List<LessonBean> toListLessonBean(List<ExerciseDowanloadInfoBean.DataBean.LessonListBean> lessons,UnitBean unitBean){
        List<LessonBean> lessonBeanList = new ArrayList<>();
        for (ExerciseDowanloadInfoBean.DataBean.LessonListBean lessonListEntity:lessons){
            lessonBeanList.add(toLessonBean(lessonListEntity,unitBean));
        }
        return lessonBeanList;
    }

    public static PartBean toPartBean(ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean part,Context context,LessonBean lessonBean){
        PartBean partBean = new PartBean();
        partBean.setLessonBean(lessonBean);
        partBean.setCentence_start(0);
        partBean.setPart_id((long) part.getQuiz_id());
        partBean.setPart_name(part.getQuiz_name());
        if (!TextUtils.isEmpty(part.getMp4())){
            String filePath = FileUtil.getExerciseExamplePath(context, lessonBean.getUnitBean().getUnit_id());
            String filename = part.getQuiz_id() + FileUtil.getUrlName(part.getMp4());
            partBean.setVideo_path(filePath + filename);
        }

        return partBean;
    }

    public static List<PartBean> toListPartBean(List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean> parts,Context context,LessonBean lessonBean){
        List<PartBean> partBeanList = new ArrayList<>();
        for (ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean quizListEntity:parts){
            partBeanList.add(toPartBean(quizListEntity,context,lessonBean));
        }
        return partBeanList;
    }

    public static CentenceBean toCentenceBean(ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean centence, Context context,PartBean partBean){
        CentenceBean centenceBean = new CentenceBean();
        if (!TextUtils.isEmpty(centence.getMp3())){
            String filePath = FileUtil.getExerciseExamplePath(context, partBean.getLessonBean().getUnitBean().getUnit_id());
            String filename = centence.getSentence_id() + FileUtil.getUrlName(centence.getMp3());
            centenceBean.setUrl_exemple(filePath + filename);
        }else {
            centenceBean.setUrl_exemple("");
        }

        centenceBean.setPartBean(partBean);
        centenceBean.setCentence_id((long) centence.getSentence_id());
        centenceBean.setChines(centence.getZh());
        centenceBean.setEnglish(centence.getEn());
        centenceBean.setSeconds(centence.getSeconds());
        centenceBean.setVideo_time(0);
        centenceBean.setVideo_start(centence.getMp4_start_time());
        centenceBean.setVideo_end(centence.getMp4_end_time());
        return centenceBean;
    }

    public static List<CentenceBean> toListCentence(List<ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean> centences,Context context,PartBean partBean){
        List<CentenceBean> centenceBeanList = new ArrayList<>();
        for (ExerciseDowanloadInfoBean.DataBean.LessonListBean.QuizListBean.SentenceListBean sentenceListEntity:centences){
            centenceBeanList.add(toCentenceBean(sentenceListEntity,context,partBean));
        }
        return centenceBeanList;
    }
}
