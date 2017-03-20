package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * Created by fanYu on 2016/6/7.
 */
public class NameListSortBean extends BaseBean{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 160616094069
         * stu_job_id : 242646
         * stu_name : stu005
         * avatar :
         * commit_time :
         * total_time : 0
         * score : 0
         * up_score : 0
         * evaluated : 0
         * status : 0
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String uid;
            private String stu_job_id;
            private String stu_name;
            private String avatar;
            private String commit_time;
            private int total_time;
            private int score;
            private int up_score;
            private int evaluated;
            private int status;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getStu_job_id() {
                return stu_job_id;
            }

            public void setStu_job_id(String stu_job_id) {
                this.stu_job_id = stu_job_id;
            }

            public String getStu_name() {
                return stu_name;
            }

            public void setStu_name(String stu_name) {
                this.stu_name = stu_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCommit_time() {
                return commit_time;
            }

            public void setCommit_time(String commit_time) {
                this.commit_time = commit_time;
            }

            public int getTotal_time() {
                return total_time;
            }

            public void setTotal_time(int total_time) {
                this.total_time = total_time;
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

            public int getEvaluated() {
                return evaluated;
            }

            public void setEvaluated(int evaluated) {
                this.evaluated = evaluated;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
