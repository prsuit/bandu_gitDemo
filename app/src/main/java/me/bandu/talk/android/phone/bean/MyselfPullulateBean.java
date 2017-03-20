package me.bandu.talk.android.phone.bean;

import com.umeng.message.proguard.T;

import java.util.List;

public class MyselfPullulateBean {

    /**
     * status : 1
     * data : {"user_done":23,"user_total":57,"user_avg_score":40,"class_avg_score":50,"user_avg_spend":80,"class_avg_spend":82,"duration":"2月1日-7月31日","homeworks":[{"job_id":123,"create_time":"2016-02-0213: 45: 08","score":70},{"job_id":124,"create_time":"2016-03-0110: 23: 43","score":80}]}
     * msg : 成功
     */

    private int status;
    /**
     * user_done : 23
     * user_total : 57
     * user_avg_score : 40
     * class_avg_score : 50
     * user_avg_spend : 80
     * class_avg_spend : 82
     * duration : 2月1日-7月31日
     * homeworks : [{"job_id":123,"create_time":"2016-02-0213: 45: 08","score":70},{"job_id":124,"create_time":"2016-03-0110: 23: 43","score":80}]
     */

    private DataBean data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private int user_done;
        private int user_total;
        private int user_avg_score;
        private int class_avg_score;
        private int user_avg_spend;
        private int class_avg_spend;
        private String duration;
        /**
         * job_id : 123
         * create_time : 2016-02-0213: 45: 08
         * score : 70
         */

        private List<HomeworksBean> homeworks;

        public int getUser_done() {
            return user_done;
        }

        public void setUser_done(int user_done) {
            this.user_done = user_done;
        }

        public int getUser_total() {
            return user_total;
        }

        public void setUser_total(int user_total) {
            this.user_total = user_total;
        }

        public int getUser_avg_score() {
            return user_avg_score;
        }

        public void setUser_avg_score(int user_avg_score) {
            this.user_avg_score = user_avg_score;
        }

        public int getClass_avg_score() {
            return class_avg_score;
        }

        public void setClass_avg_score(int class_avg_score) {
            this.class_avg_score = class_avg_score;
        }

        public int getUser_avg_spend() {
            return user_avg_spend;
        }

        public void setUser_avg_spend(int user_avg_spend) {
            this.user_avg_spend = user_avg_spend;
        }

        public int getClass_avg_spend() {
            return class_avg_spend;
        }

        public void setClass_avg_spend(int class_avg_spend) {
            this.class_avg_spend = class_avg_spend;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public List<HomeworksBean> getHomeworks() {
            return homeworks;
        }

        public void setHomeworks(List<HomeworksBean> homeworks) {
            this.homeworks = homeworks;
        }

        public static class HomeworksBean {
            private int job_id;
            private String create_time;
            private int score;

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }
    }
}
