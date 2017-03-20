package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class LessonInfoData{
    private List<LessonInfoEntity> quiz_list;

    public List<LessonInfoEntity> getQuiz_list() {
        return quiz_list;
    }

    public void setQuiz_list(List<LessonInfoEntity> quiz_list) {
        this.quiz_list = quiz_list;
    }

    @Override
    public String toString() {
        return "LessonInfoData{" +
                '}';
    }
}
