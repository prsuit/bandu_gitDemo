package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mckiera on 2015/12/14.
 */
public class TeacherHomeList extends BaseBean implements Serializable{

    /**
     * list : [{"bookid":"109","check_job_num":0,"cid":"5097","class_name":"八年级06班","ctime":"2015-08-07 17:20:29","has_wait_student":false},{"bookid":"9","check_job_num":0,"cid":"5098","class_name":"三年级02班","ctime":"2015-08-27 14:47:10","has_wait_student":false},{"bookid":0,"check_job_num":0,"cid":"5099","class_name":"二年级02班","ctime":"2015-08-27 14:47:25","has_wait_student":false},{"bookid":"170","check_job_num":0,"cid":"5100","class_name":"六年级01班","ctime":"2015-10-29 10:38:57","has_wait_student":false},{"bookid":0,"check_job_num":0,"cid":"5102","class_name":"七年级07班","ctime":"2015-12-11 11:27:43","has_wait_student":false},{"bookid":"9","check_job_num":0,"cid":"5143","class_name":"何淼","ctime":"2016-01-11 11:14:34","has_wait_student":false}]
     * total : 6
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        private String total;
        /**
         * bookid : 109
         * check_job_num : 0
         * cid : 5097
         * class_name : 八年级06班
         * ctime : 2015-08-07 17:20:29
         * has_wait_student : false
         */

        private List<ListEntity> list;

        public void setTotal(String total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity implements Serializable{
            private String bookid;
            private int check_job_num;
            private String cid;
            private String class_name;
            private String ctime;
            private boolean has_wait_student;

            public void setBookid(String bookid) {
                this.bookid = bookid;
            }

            public void setCheck_job_num(int check_job_num) {
                this.check_job_num = check_job_num;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setHas_wait_student(boolean has_wait_student) {
                this.has_wait_student = has_wait_student;
            }

            public String getBookid() {
                return bookid;
            }

            public int getCheck_job_num() {
                return check_job_num;
            }

            public String getCid() {
                return cid;
            }

            public String getClass_name() {
                return class_name;
            }

            public String getCtime() {
                return ctime;
            }

            public boolean isHas_wait_student() {
                return has_wait_student;
            }
        }
    }
}
