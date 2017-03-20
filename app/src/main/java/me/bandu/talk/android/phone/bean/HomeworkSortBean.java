package me.bandu.talk.android.phone.bean;

/**
 * 作业排序实体类
 * Created by fanyu on 2016/6/2.
 */
public class HomeworkSortBean {
    private String date;
    private String name;
    private int img_header;
    private boolean isComment;

    public HomeworkSortBean(String date, String name, int img_header, boolean isComment){
        this.date=date;
        this.name=name;
        this.img_header=img_header;
        this.isComment=isComment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_header() {
        return img_header;
    }

    public void setImg_header(int img_header) {
        this.img_header = img_header;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }
}
