package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.io.Serializable;

/**
 * 创建者：高楠
 * 时间：on 2015/11/30
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Quiz extends BaseBean implements Comparable,Serializable{

    private String quiz_id;
    private String quiz_name;
    private int read_count;
    private int read_total;
    private int repeat_count = 1;
    private int repeat_total;
    private boolean recit;
    private int sentence_num;

    public int getSentence_num() {
        return sentence_num;
    }

    public void setSentence_num(int sentence_num) {
        this.sentence_num = sentence_num;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public boolean isRecit() {
        return recit;
    }

    public void setRecit(boolean recit) {
        this.recit = recit;
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


    public int getRepeat_count() {
        return repeat_count;
    }

    public void setRepeat_count(int repeat_count) {
        this.repeat_count = repeat_count;
    }

    public int getRepeat_total() {
        return repeat_total;
    }

    public void setRepeat_total(int repeat_total) {
        this.repeat_total = repeat_total;
    }


    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }


    public String getQuiz_id() {
        return quiz_id;
    }

    @Override
    public int compareTo(Object another) {
        if (!(another instanceof Quiz)){
            return  -1;
        }
        if (((Quiz) another).getQuiz_id().equals(getQuiz_id())){
            return 0;
        }
        return -1;
    }
}
