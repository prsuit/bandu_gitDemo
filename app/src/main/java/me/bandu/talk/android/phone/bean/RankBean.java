package me.bandu.talk.android.phone.bean;

import java.util.List;

public class RankBean {

    /**
     * status : 1
     * data : {"rank":10,"score":78,"up_score":5,"spend_time":10,"total_time":120,"done_total":20,"student_list":[{"name":"蓝翼","avatar":"http://xxxx.jpg","score":89,"total_time":99},"..."]}
     * msg : 成功
     */

    private int status;
    /**
     * rank : 10
     * score : 78
     * up_score : 5
     * spend_time : 10
     * total_time : 120
     * done_total : 20
     * student_list : [{"name":"蓝翼","avatar":"http://xxxx.jpg","score":89,"total_time":99},"..."]
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
        private int rank;
        private int score;
        private int up_score;
        private int spend_time;
        private int total_time;
        private int done_total;
        /**
         * name : 蓝翼
         * avatar : http://xxxx.jpg
         * score : 89
         * total_time : 99
         */

        private List<StudentListBean> student_list;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
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

        public int getSpend_time() {
            return spend_time;
        }

        public void setSpend_time(int spend_time) {
            this.spend_time = spend_time;
        }

        public int getTotal_time() {
            return total_time;
        }

        public void setTotal_time(int total_time) {
            this.total_time = total_time;
        }

        public int getDone_total() {
            return done_total;
        }

        public void setDone_total(int done_total) {
            this.done_total = done_total;
        }

        public List<StudentListBean> getStudent_list() {
            return student_list;
        }

        public void setStudent_list(List<StudentListBean> student_list) {
            this.student_list = student_list;
        }

        public static class StudentListBean {
            private String name;
            private String avatar;
            private int score;
            private int total_time;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getTotal_time() {
                return total_time;
            }

            public void setTotal_time(int total_time) {
                this.total_time = total_time;
            }
        }
    }
}
