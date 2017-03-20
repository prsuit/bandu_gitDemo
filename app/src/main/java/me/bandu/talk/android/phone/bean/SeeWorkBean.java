package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者:taoge
 * 时间：2015/11/30
 * 类描述：
 * 修改人:taoge
 * 修改时间：2015/11/30
 * 修改备注：
 */
public class SeeWorkBean extends BaseBean{

    /**
     * done_num : 12
     * total : 20
     * done_list : [{"stu_job_id":123,"uid":20101,"stu_name":"及永超","avatar":"http://www.bandu.cn/avatar/12.jpg","score":89},{}]
     * doing_list : ["张三","李四"]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int done_num;
        private int total;
        /**
         * stu_job_id : 123
         * uid : 20101
         * stu_name : 及永超
         * avatar : http://www.bandu.cn/avatar/12.jpg
         * score : 89
         */

        private List<DoneListEntity> done_list;
        private List<String> doing_list;

        public void setDone_num(int done_num) {
            this.done_num = done_num;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setDone_list(List<DoneListEntity> done_list) {
            this.done_list = done_list;
        }

        public void setDoing_list(List<String> doing_list) {
            this.doing_list = doing_list;
        }

        public int getDone_num() {
            return done_num;
        }

        public int getTotal() {
            return total;
        }

        public List<DoneListEntity> getDone_list() {
            return done_list;
        }

        public List<String> getDoing_list() {
            return doing_list;
        }

        public static class DoneListEntity {
            private String stu_job_id;
            private String uid;
            private String stu_name;
            private String avatar;
            private int score;
            private int evaluated;

            public void setStu_job_id(String stu_job_id) {
                this.stu_job_id = stu_job_id;
            }

            public int getEvaluated() {
                return evaluated;
            }

            public void setEvaluated(int evaluated) {
                this.evaluated = evaluated;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setStu_name(String stu_name) {
                this.stu_name = stu_name;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getStu_job_id() {
                return stu_job_id;
            }

            public String getUid() {
                return uid;
            }

            public String getStu_name() {
                return stu_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public int getScore() {
                return score;
            }
        }
    }
}
