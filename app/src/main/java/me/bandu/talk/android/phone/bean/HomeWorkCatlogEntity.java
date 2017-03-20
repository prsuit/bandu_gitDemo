package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：高楠
 * 时间：on 2015/12/15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HomeWorkCatlogEntity {
    private String title;
    private String description;
    private int level;
    private String commit_time;
    private String spend_time;
    private String comment;
    private int score;
    private int total_time;
    private boolean enabled;
    private List<HomeWorkCatlogQuiz> quizs;

    public String getComment() {
        return comment;
    }
    public int getTotal_time() {
        return total_time;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCommit_time(String commit_time) {
        this.commit_time = commit_time;
    }

    public void setSpend_time(String spend_time) {
        this.spend_time = spend_time;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setQuizs(List<HomeWorkCatlogQuiz> quizs) {
        this.quizs = quizs;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public String getCommit_time() {
        return commit_time;
    }

    public String getSpend_time() {
        return spend_time==null?"0":(spend_time.equals("")?"0":spend_time);
    }

    public int getScore() {
        return score;
    }

    public List<HomeWorkCatlogQuiz> getQuizs() {
        return quizs;
    }
}
