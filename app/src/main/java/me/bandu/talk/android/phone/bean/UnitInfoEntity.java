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
public class UnitInfoEntity{
    private String lesson_id;
    private String lesson_name;


    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    @Override
    public String toString() {
        return "LessonInfoData{" +
                "lesson_id=" + lesson_id +
                ", lesson_name='" + lesson_name + '\'' +
                '}';
    }
}
