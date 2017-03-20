package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * 创建者：高楠
 * 时间：on 2015/12/9
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HomeWorkListBean extends BaseBean {
    private int quiz_id;
    private int work_id;
    private String quiz_title;
    private int read_count;
    private int read_total;
    private int repead_count;
    private int repead_total;
    private int recite_count;
    private int recite_total;

    public int getRecite_count() {
        return recite_count;
    }

    public void setRecite_count(int recite_count) {
        this.recite_count = recite_count;
    }

    public int getRecite_total() {
        return recite_total;
    }

    public void setRecite_total(int recite_total) {
        this.recite_total = recite_total;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public String getQuiz_title() {
        return quiz_title;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public int getRead_total() {
        return read_total;
    }

    public void setRead_total(int read_total) {
        this.read_total = read_total;
    }

    public int getRepead_count() {
        return repead_count;
    }

    public void setRepead_count(int repead_count) {
        this.repead_count = repead_count;
    }

    public int getRepead_total() {
        return repead_total;
    }

    public void setRepead_total(int repead_total) {
        this.repead_total = repead_total;
    }

}
