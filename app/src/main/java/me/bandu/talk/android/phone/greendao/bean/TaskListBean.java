package me.bandu.talk.android.phone.greendao.bean;

import java.io.Serializable;

/**
 * 创建者：wanglei
 * <p>时间：16/6/21  16:28
 * <p>类描述：作业列表
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：313  837
 */
public class TaskListBean implements Serializable {

    private Long ID;//数据库用的唯一键
    private long userId;//用户ID
    private long classID;//班级ID
    private long taskID;//作业ID stu_job_id
    private String arrangementTime;//留作业时间
    private String taskSubmitTime;//作业提交时间
    private long taskEndTime;//作业截止时间
    private long taskTime;//作业总耗时  本地也改成秒的了
    private long taskTimeCurrent;//当前作业耗时，每次提交后删除更新为0
    private boolean isHaveTask;//假、句子表没有存过，真、句子表存过，判断是否需要去网络根据这个partID拿它里面的句子
    private String taskLastSubmitTime;//上一次的作业提交时间
    private String taskName;//作业名
    private long taskNum;//作业分数
    private long taskState;//0未完成，1已完成，2已检查
    private long taskDegree;//0作业已撤销，1作业未撤销
    private boolean isEvaluate;//是否已评价
    private String week;//星期
    private String taskRequirement;//作业等级要求
    private long taskPercentage;//作业百分比
    private String taskExplain;//作业说明
    private boolean isShowRedpoint;//是否显示小红点，真，显示，假，不显示
    private long job_id;//作业job_id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskListBean that = (TaskListBean) o;

        if (userId != that.userId) return false;
        if (classID != that.classID) return false;
        return taskID == that.taskID;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (classID ^ (classID >>> 32));
        result = 31 * result + (int) (taskID ^ (taskID >>> 32));
        return result;
    }
    public TaskListBean(){}

    public TaskListBean(Long ID, long classID, long taskID, String arrangementTime, String taskSubmitTime, long taskEndTime, long taskTime, boolean isHaveTask, long taskTimeCurrent, String taskLastSubmitTime, String taskName, long taskNum, long taskState, long taskDegree, boolean isEvaluate, String week, String taskRequirement, long userId,long taskPercentage,String taskExplain,boolean isShowRedpoint,long job_id) {
        this.ID = ID;
        this.userId = userId;
        this.classID = classID;
        this.taskID = taskID;
        this.arrangementTime = arrangementTime;
        this.taskSubmitTime = taskSubmitTime;
        this.taskEndTime = taskEndTime;
        this.taskTime = taskTime;
        this.isHaveTask = isHaveTask;
        this.taskTimeCurrent = taskTimeCurrent;
        this.taskLastSubmitTime = taskLastSubmitTime;
        this.taskName = taskName;
        this.taskNum = taskNum;
        this.taskState = taskState;
        this.taskDegree = taskDegree;
        this.isEvaluate = isEvaluate;
        this.week = week;
        this.taskRequirement = taskRequirement;
        this.taskPercentage = taskPercentage;
        this.taskExplain = taskExplain;
        this.isShowRedpoint = isShowRedpoint;
        this.job_id = job_id;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getClassID() {
        return classID;
    }

    public void setClassID(long classID) {
        this.classID = classID;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public String getArrangementTime() {
        return arrangementTime;
    }

    public void setArrangementTime(String arrangementTime) {
        this.arrangementTime = arrangementTime;
    }

    public String getTaskSubmitTime() {
        return taskSubmitTime;
    }

    public void setTaskSubmitTime(String taskSubmitTime) {
        this.taskSubmitTime = taskSubmitTime;
    }

    public long getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(long taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public long getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }

    public boolean isHaveTask() {
        return isHaveTask;
    }

    public void setHaveTask(boolean haveTask) {
        isHaveTask = haveTask;
    }

    public long getTaskTimeCurrent() {
        return taskTimeCurrent;
    }

    public void setTaskTimeCurrent(long taskTimeCurrent) {
        this.taskTimeCurrent = taskTimeCurrent;
    }

    public String getTaskLastSubmitTime() {
        return taskLastSubmitTime;
    }

    public void setTaskLastSubmitTime(String taskLastSubmitTime) {
        this.taskLastSubmitTime = taskLastSubmitTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(long taskNum) {
        this.taskNum = taskNum;
    }

    public long getTaskState() {
        return taskState;
    }

    public void setTaskState(long taskState) {
        this.taskState = taskState;
    }

    public long getTaskDegree() {
        return taskDegree;
    }

    public void setTaskDegree(long taskDegree) {
        this.taskDegree = taskDegree;
    }

    public boolean isEvaluate() {
        return isEvaluate;
    }

    public void setEvaluate(boolean evaluate) {
        isEvaluate = evaluate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTaskRequirement() {
        return taskRequirement;
    }

    public void setTaskRequirement(String taskRequirement) {
        this.taskRequirement = taskRequirement;
    }

    public long getTaskPercentage() {
        return taskPercentage;
    }

    public void setTaskPercentage(long taskPercentage) {
        this.taskPercentage = taskPercentage;
    }

    public String getTaskExplain() {
        return taskExplain;
    }

    public void setTaskExplain(String taskExplain) {
        this.taskExplain = taskExplain;
    }

    public boolean isShowRedpoint() {
        return isShowRedpoint;
    }

    public void setShowRedpoint(boolean showRedpoint) {
        isShowRedpoint = showRedpoint;
    }

    public long getJob_id() {
        return job_id;
    }

    public void setJob_id(long job_id) {
        this.job_id = job_id;
    }
}
