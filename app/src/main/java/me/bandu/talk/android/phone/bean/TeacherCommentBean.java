package me.bandu.talk.android.phone.bean;

/**
 * 创建者：高楠
 * 时间：on 2016/1/28
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TeacherCommentBean {
    private String comment;
    private boolean isSelect = false;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
