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
public class SentenceBean extends BaseBean {
    private int sentence_id;
    private int quiz_id;
    private String sentence_content;
    private String sample_url;
    private String best_url;
    private String current_url;
    private int best_score;
    private int current_score;
    private String score_info;

    public int getSentence_id() {
        return sentence_id;
    }

    public void setSentence_id(int sentence_id) {
        this.sentence_id = sentence_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getSentence_content() {
        return sentence_content;
    }

    public void setSentence_content(String sentence_content) {
        this.sentence_content = sentence_content;
    }

    public String getSample_url() {
        return sample_url;
    }

    public void setSample_url(String sample_url) {
        this.sample_url = sample_url;
    }

    public String getBest_url() {
        return best_url;
    }

    public void setBest_url(String best_url) {
        this.best_url = best_url;
    }

    public String getCurrent_url() {
        return current_url;
    }

    public void setCurrent_url(String current_url) {
        this.current_url = current_url;
    }

    public int getBest_score() {
        return best_score;
    }

    public void setBest_score(int best_score) {
        this.best_score = best_score;
    }

    public int getCurrent_score() {
        return current_score;
    }

    public void setCurrent_score(int current_score) {
        this.current_score = current_score;
    }

    public String getScore_info() {
        return score_info;
    }

    public void setScore_info(String score_info) {
        this.score_info = score_info;
    }
}
