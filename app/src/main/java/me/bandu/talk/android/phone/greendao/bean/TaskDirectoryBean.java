package me.bandu.talk.android.phone.greendao.bean;

import java.io.Serializable;

/**
 * 创建者：wanglei
 * <p>时间：16/6/27  16:30
 * <p>类描述：作业目录
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class TaskDirectoryBean implements Serializable {

    private Long ID;//数据库用的唯一键
    private long classID;//班级ID
    private long userId;//用户ID
    private long taskID;//作业ID
    private long unitID;//
    private long lesenID;//
    private long partID;//题ID
    private long type;//句子类型1：跟读！2：朗读！3：背诵
    private boolean isDone;//网络过来的数据是否有，如果有就存，如果么有本地数据满了之后也用这个存
    private String partName;//条目名
    private long repeatTotal;//跟读总数
    private long repeatCurrent;//跟读当前数
    private long repeatTime;//跟读耗时  每次提交作业成功后清零
    private String description;//题干
    private String quiz_type;//题型

    public TaskDirectoryBean(){}
    public TaskDirectoryBean(Long ID, long classID, long userId, long taskID, long unitID, long lesenID, long partID, long type, boolean isDone, String partName, long repeatTotal, long repeatCurrent, long repeatTime,String description,String quiz_type) {
        this.ID = ID;
        this.classID = classID;
        this.userId = userId;
        this.taskID = taskID;
        this.unitID = unitID;
        this.lesenID = lesenID;
        this.partID = partID;
        this.type = type;
        this.isDone = isDone;
        this.partName = partName;
        this.repeatTotal = repeatTotal;
        this.repeatCurrent = repeatCurrent;
        this.repeatTime = repeatTime;
        this.description = description;
        this.quiz_type = quiz_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDirectoryBean that = (TaskDirectoryBean) o;

        if (classID != that.classID) return false;
        if (userId != that.userId) return false;
        return taskID == that.taskID;

    }

    @Override
    public int hashCode() {
        int result = (int) (classID ^ (classID >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (taskID ^ (taskID >>> 32));
        return result;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public long getClassID() {
        return classID;
    }

    public void setClassID(long classID) {
        this.classID = classID;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public long getUnitID() {
        return unitID;
    }

    public void setUnitID(long unitID) {
        this.unitID = unitID;
    }

    public long getLesenID() {
        return lesenID;
    }

    public void setLesenID(long lesenID) {
        this.lesenID = lesenID;
    }

    public long getPartID() {
        return partID;
    }

    public void setPartID(long partID) {
        this.partID = partID;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        isDone = isDone;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public long getRepeatTotal() {
        return repeatTotal;
    }

    public void setRepeatTotal(long repeatTotal) {
        this.repeatTotal = repeatTotal;
    }

    public long getRepeatCurrent() {
        return repeatCurrent;
    }

    public void setRepeatCurrent(long repeatCurrent) {
        this.repeatCurrent = repeatCurrent;
    }

    public long getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(long repeatTime) {
        this.repeatTime = repeatTime;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuiz_type() {
        return quiz_type;
    }

    public void setQuiz_type(String quiz_type) {
        this.quiz_type = quiz_type;
    }

//    public long getRepeatTime_1() {
//        return repeatTime_1;
//    }
//
//    public void setRepeatTime_1(long repeatTime_1) {
//        this.repeatTime_1 = repeatTime_1;
//    }
}
