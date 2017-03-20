package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

/**
 * Created by fanYu on 2016/6/10.
 *
 */
public class TotalDoneBean extends BaseBean {

    /**
     * commit_time : 2016-06-03 14:18:23
     * score : 78
     * up_score : 5
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String commit_time;
        private int score;
        private int up_score;

        public String getCommit_time() {
            return commit_time;
        }

        public void setCommit_time(String commit_time) {
            this.commit_time = commit_time;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getUp_score() {
            return up_score;
        }

        public void setUp_score(int up_score) {
            this.up_score = up_score;
        }
    }
}
