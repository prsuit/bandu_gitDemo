package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

public class UserClassListBean extends BaseBean {

    /**
     * status : 1
     * data : {"class_list":[{"cid":3929,"class_name":"三年级1班","status":1,"last_job_time":"2016-06-2909: 40: 12"}]}
     * msg : 成功
     */

    private int status;
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
        /**
         * cid : 3929
         * class_name : 三年级1班
         * status : 1
         * last_job_time : 2016-06-2909: 40: 12
         */

        private List<ClassListBean> class_list;

        public List<ClassListBean> getClass_list() {
            return class_list;
        }

        public void setClass_list(List<ClassListBean> class_list) {
            this.class_list = class_list;
        }

        public static class ClassListBean {
            private int cid;
            private String class_name;
            private int status;
            private String last_job_time;
            private boolean isShowRed = false;

            public boolean isShowRed() {
                return isShowRed;
            }

            public void setShowRed(boolean showRed) {
                isShowRed = showRed;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getLast_job_time() {
                return last_job_time;
            }

            public void setLast_job_time(String last_job_time) {
                this.last_job_time = last_job_time;
            }
        }
    }
}
