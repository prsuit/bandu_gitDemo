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
public class CheckWorkBean extends BaseBean {

    /**
     * list : [{"job_id":1,"title":"作业标题","cdate":"2015-11-23","cday":"MON","deadline":"1431111111","wj_num":12,"djc_num":15},{},{}]
     * total : 20
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int total;
        /**
         * job_id : 1
         * title : 作业标题
         * cdate : 2015-11-23
         * cday : MON
         * deadline : 1431111111
         * wj_num : 12
         * djc_num : 15
         */

        private List<ListEntity> list;

        public void setTotal(int total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int job_id;
            private String title;
            private String cdate;
            private String cday;
            private String deadline;
            private int ci_count;
            private int un_ci_count;
            private int un_ck_count;
            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setCdate(String cdate) {
                this.cdate = cdate;
            }

            public void setCday(String cday) {
                this.cday = cday;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public int getCi_count() {
                return ci_count;
            }

            public void setCi_count(int ci_count) {
                this.ci_count = ci_count;
            }

            public int getUn_ci_count() {
                return un_ci_count;
            }

            public void setUn_ci_count(int un_ci_count) {
                this.un_ci_count = un_ci_count;
            }

            public int getUn_ck_count() {
                return un_ck_count;
            }

            public void setUn_ck_count(int un_ck_count) {
                this.un_ck_count = un_ck_count;
            }

            public int getJob_id() {
                return job_id;
            }

            public String getTitle() {
                return title;
            }

            public String getCdate() {
                return cdate;
            }

            public String getCday() {
                return cday;
            }

            public String getDeadline() {
                return deadline;
            }
        }
    }
}
