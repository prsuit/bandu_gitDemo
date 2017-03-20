package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 创建者：高楠
 * 时间：on 2015/12/9
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HomeWorkBean extends BaseBean {
    /**
     * todo_count : 2
     * total : 10
     * list : [{"job_id":1,"stu_job_id":123,"job_status":1,"stu_job_status":1,"is_done":1,"percent":"15","title":"作业标题","cdate":"2015-12-11-12-13-14","deadline":"1450022399","cday":"FRI","evaluated":1},{}]
     * time : 1467689542
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int todo_count;
        private int total;
        private int time;
        /**
         * job_id : 1
         * stu_job_id : 123
         * job_status : 1
         * stu_job_status : 1
         * is_done : 1
         * percent : 15
         * title : 作业标题
         * cdate : 2015-12-11-12-13-14
         * deadline : 1450022399
         * cday : FRI
         * evaluated : 1
         */

        private List<ListBean> list;

        public int getTodo_count() {
            return todo_count;
        }

        public void setTodo_count(int todo_count) {
            this.todo_count = todo_count;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable,Comparable {
            private int job_id;
            private int stu_job_id;
            private int job_status;
            private int stu_job_status;
            private int is_done;
            private String percent;
            private String title;
            private String cdate;
            private String deadline;
            private String cday;
            private int evaluated;
            private boolean isEmpty = false;
            private int cid;
            private boolean showRedPoint = false;

            public boolean isShowRedPoint() {
                return showRedPoint;
            }

            public void setShowRedPoint(boolean showRedPoint) {
                this.showRedPoint = showRedPoint;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getCid() {

                return cid;
            }

            public boolean isEmpty() {
                return isEmpty;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }

            public int getJob_id() {
                return job_id;
            }

            public void setJob_id(int job_id) {
                this.job_id = job_id;
            }

            public int getStu_job_id() {
                return stu_job_id;
            }

            public void setStu_job_id(int stu_job_id) {
                this.stu_job_id = stu_job_id;
            }

            public int getJob_status() {
                return job_status;
            }

            public void setJob_status(int job_status) {
                this.job_status = job_status;
            }

            public int getStu_job_status() {
                return stu_job_status;
            }

            public void setStu_job_status(int stu_job_status) {
                this.stu_job_status = stu_job_status;
            }

            public int getIs_done() {
                return is_done;
            }

            public void setIs_done(int is_done) {
                this.is_done = is_done;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
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

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public String getCday() {
                return cday;
            }

            public void setCday(String cday) {
                this.cday = cday;
            }

            public int getEvaluated() {
                return evaluated;
            }

            public void setEvaluated(int evaluated) {
                this.evaluated = evaluated;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                ListBean listBean = (ListBean) o;

                if (job_id != listBean.job_id) return false;
                if (stu_job_id != listBean.stu_job_id) return false;
                if (job_status != listBean.job_status) return false;
                if (stu_job_status != listBean.stu_job_status) return false;
                if (is_done != listBean.is_done) return false;
                if (evaluated != listBean.evaluated) return false;
                if (isEmpty != listBean.isEmpty) return false;
                if (cid != listBean.cid) return false;
                if (showRedPoint != listBean.showRedPoint) return false;
                if (percent != null ? !percent.equals(listBean.percent) : listBean.percent != null)
                    return false;
                if (title != null ? !title.equals(listBean.title) : listBean.title != null)
                    return false;
                if (cdate != null ? !cdate.equals(listBean.cdate) : listBean.cdate != null)
                    return false;
                if (deadline != null ? !deadline.equals(listBean.deadline) : listBean.deadline != null)
                    return false;
                return cday != null ? cday.equals(listBean.cday) : listBean.cday == null;

            }

            @Override
            public int hashCode() {
                int result = job_id;
                result = 31 * result + stu_job_id;
                result = 31 * result + job_status;
                result = 31 * result + stu_job_status;
                result = 31 * result + is_done;
                result = 31 * result + (percent != null ? percent.hashCode() : 0);
                result = 31 * result + (title != null ? title.hashCode() : 0);
                result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
                result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
                result = 31 * result + (cday != null ? cday.hashCode() : 0);
                result = 31 * result + evaluated;
                result = 31 * result + (isEmpty ? 1 : 0);
                result = 31 * result + cid;
                result = 31 * result + (showRedPoint ? 1 : 0);
                return result;
            }

            @Override
            public int compareTo(Object o) {
                if (!(o instanceof ListBean)){
                    return -1;
                }
                long time1 = timeToMs(cdate);
                long time2 = timeToMs(((ListBean)o).cdate);
                if (time1 > time2) return -1;
                else if(time1 < time2) return 1;
                else return 0;
            }
            public long timeToMs(String cdate){
                try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
                    Date date  = sdf.parse(cdate);
                    return date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        }
    }
   /* private HomeWorkData data;

    public HomeWorkData getData() {
        return data;
    }

    public void setData(HomeWorkData data) {
        this.data = data;
    }*/

}
