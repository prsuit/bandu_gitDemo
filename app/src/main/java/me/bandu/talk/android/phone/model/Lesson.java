package me.bandu.talk.android.phone.model;

/**
 * 创建者：gaoye
 * 时间：2015/11/25  09:49
 * 类描述：一些类 为了无网络数据显示的 提供接口后会修改或者删除
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Lesson {
    private String name;
    private String str;
    private String lesson;

    public String getLesson() {
        return lesson;
    }

    public String getName() {
        return name;
    }

    public String getStr() {
        return str;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStr(String str) {
        this.str = str;
    }
}