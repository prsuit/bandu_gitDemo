package me.bandu.talk.android.phone.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 创建者：高楠
 * 时间：on 2015/12/15
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HomeWorkEntity implements Serializable,Comparable{
    private int job_id;
    private long stu_job_id;
    private String job_status;
    private String stu_job_status;
    private String title;
    private String cdate;
    private long deadline ;
    private String cday  ;
    private int percent  ;
    private int evaluated  ;
    private boolean isEmpty = false;

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
    //    private boolean isfirst = true;

    public int getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(int evaluated) {
        this.evaluated = evaluated;
    }

//    public boolean isfirst() {
//        return isfirst;
//    }
//
//    public void setIsfirst(boolean isfirst) {
//        this.isfirst = isfirst;
//    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public long getStu_job_id() {
        return stu_job_id;
    }

    public void setStu_job_id(long stu_job_id) {
        this.stu_job_id = stu_job_id;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getStu_job_status() {
        return stu_job_status;
    }

    public void setStu_job_status(String stu_job_status) {
        this.stu_job_status = stu_job_status;
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

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getCday() {
        return cday;
    }

    public void setCday(String cday) {
        this.cday = cday;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof HomeWorkEntity)){
            return -1;
        }
        long time1 = timeToMs(cdate);
        long time2 = timeToMs(((HomeWorkEntity)o).cdate);
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
