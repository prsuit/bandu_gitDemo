package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * Created by fanYu on 2016/6/13.
 * 薄弱句子排行榜的bean
 */
public class WeakSentenceBean extends BaseBean{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 160616412358
         * stu_name : 美女
         * avatar : http://test.new.media.bandu.in/avatar/20160616/160616412358.jpg?1466056393
         * score : 1
         * stu_job_id : 243024
         * evaluated : 1
         * status : 2
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
            private String stu_name;
            private String avatar;
            private int score;
            private String stu_job_id;
            private int evaluated;
            private int status;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getStu_job_id() {
                return stu_job_id;
            }

            public void setStu_job_id(String stu_job_id) {
                this.stu_job_id = stu_job_id;
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
