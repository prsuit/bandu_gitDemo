package me.bandu.talk.android.phone.bean;

import java.io.Serializable;

/**
 * 创建者：高楠
 * 时间：on 2016/2/19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ChoseStudentListEntity implements Serializable {
    private int stu_job_id;
    private String name;
    private String avatar;
    private boolean isSelect = false;
    private String letter;
    private boolean isGroup;

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setStu_job_id(int stu_job_id) {
        this.stu_job_id = stu_job_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStu_job_id() {
        return stu_job_id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
