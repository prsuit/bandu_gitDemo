package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * Created by wanglanxin on 2016/6/5.
 */
public class ClassStatisticsBean {

    /**
     * status : 1
     * data : {"duration":"2月1日-7月31日","list":[{"cid":123,"name":"四年级一班","total":10,"avg_score":64,"avg_spend":189},{}]}
     * msg : 成功
     */

    private int status;
    /**
     * duration : 2月1日-7月31日
     * list : [{"cid":123,"name":"四年级一班","total":10,"avg_score":64,"avg_spend":189},{}]
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
        private String duration;
        /**
         * cid : 123
         * name : 四年级一班
         * total : 10
         * avg_score : 64
         * avg_spend : 189
         */

        private List<ListBean> list;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int cid;
            private String name;
            private int total;
            private int avg_score;
            private int avg_spend;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getAvg_score() {
                return avg_score;
            }

            public void setAvg_score(int avg_score) {
                this.avg_score = avg_score;
            }

            public int getAvg_spend() {
                return avg_spend;
            }

            public void setAvg_spend(int avg_spend) {
                this.avg_spend = avg_spend;
            }
        }
    }
}
