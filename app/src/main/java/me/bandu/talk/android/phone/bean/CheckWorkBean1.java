package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

public class CheckWorkBean1 extends BaseBean {

    /**
     * status : 1
     * data : {"total":100,"list":[{"job_id":1,"title":"Unit2sectionB","cdate":"2016-06-03","cday":"MON","deadline":"2016-06-0223: 59: 59","done_total":32,"total":40,"is_over":1,"is_unchecked":1}]}
     * msg : 成功
     */

    private int status;
    /**
     * total : 100
     * list : [{"job_id":1,"title":"Unit2sectionB","cdate":"2016-06-03","cday":"MON","deadline":"2016-06-0223: 59: 59","done_total":32,"total":40,"is_over":1,"is_unchecked":1}]
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
        private int total;
        /**
         * job_id : 1
         * title : Unit2sectionB
         * cdate : 2016-06-03
         * cday : MON
         * deadline : 2016-06-0223: 59: 59
         * done_total : 32
         * total : 40
         * is_over : 1
         * is_unchecked : 1
         */

        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int job_id;
            private String title;
            private String cdate;
            private String cday;
            private String deadline;
            private int done_total;
            private int total;
            private int is_over;
            private int is_unchecked;

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCdate() {
                return cdate;
            }

            public void setCdate(String cdate) {
                this.cdate = cdate;
            }

            public String getCday() {
                return cday;
            }

            public void setCday(String cday) {
                this.cday = cday;
            }

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public int getDone_total() {
                return done_total;
            }

            public void setDone_total(int done_total) {
                this.done_total = done_total;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getIs_over() {
                return is_over;
            }

            public void setIs_over(int is_over) {
                this.is_over = is_over;
            }

            public int getIs_unchecked() {
                return is_unchecked;
            }

            public void setIs_unchecked(int is_unchecked) {
                this.is_unchecked = is_unchecked;
            }
        }
    }
}
