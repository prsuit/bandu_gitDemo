package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

import me.bandu.talk.android.phone.model.Lesson;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UnitInfoData extends  BaseBean{
    private List<UnitInfoEntity> lesson_list;

    public List<UnitInfoEntity> getLesson_list() {
        return lesson_list;
    }

    public void setLesson_list(List<UnitInfoEntity> lesson_list) {
        this.lesson_list = lesson_list;
    }


    @Override
    public String toString() {
        return "UnitInfoData{" +
                "lesson_list=" + lesson_list +
                '}';
    }
}
