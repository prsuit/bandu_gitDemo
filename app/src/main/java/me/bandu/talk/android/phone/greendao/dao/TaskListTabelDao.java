package me.bandu.talk.android.phone.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;

/**
 * 创建者：wanglei
 * <p>时间：16/6/21  16:26
 * <p>类描述：作业列表表
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class TaskListTabelDao extends AbstractDao<TaskListBean, Long> {

    public static final String TABLENAME = "taskList";//数据库的表命
    public static final String _id = "_id";//字段名字
    public static final String USERID = "userId";
    public static final String CLASSID = "classId";             //班级ID
    public static final String TASKID = "taskID";               //作业ID
    public static final String ARRANGEMENTTIME = "arrangementTime";//留作业的时间
    public static final String TASKSUBMITTIME = "taskSubmitTime";//作业提交时间
    public static final String TASKENDTIME = "taskEndTime";     //作业截止时间
    public static final String TASKTIME = "taskTime";           //作业总耗时
    public static final String ISHAVETASK = "isHaveTask";       //真、句子表没有存过，假、句子表存过，判断是否需要去网络根据这个partID拿它里面的句子
    public static final String TASKTIMECURRENT = "taskTimeCurrent";//当前作业耗时，每次提交后删除更新为0
    public static final String TASKLASTSUBMITTIME = "taskLastSubmitTime";//上一次的作业提交时间
    public static final String TASKNAME = "taskName";           //作业名
    public static final String TASKNUM = "taskNum";             //作业分数
    public static final String TASKSTATE = "taskState";         //已完成，未完成，已检查
    public static final String TASKDEGREE = "taskDegree";       //作业完成度
    public static final String ISEVALUATE = "isEvaluate";       //是否已评价
    public static final String WEEK = "week";                   //星期
    public static final String TASKREQUIREMENT = "taskRequirement";//作业等级要求
    public static final String TASKPERCENTAGE = "taskPercentage";//作业百分比
    public static final String TASKEXPLAIN = "taskExplain";      //作业说明
    public static final String ISSHOWREDPOINT = "isShowRedpoint"; //是否显示小红点，真，显示，假，不显示
    public static final String JOB_ID = "job_id"; //是否显示小红点，真，显示，假，不显示

    public TaskListTabelDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Properties of entity Note.<br/>
     * 实体注释的属性。
     * Can be used for QueryBuilder and for referencing column names.
     * 可用于querybuilder和引用列的名称。
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "id", true, _id);
        public final static Property classId = new Property(1, Long.class, "classId", false, CLASSID);
        public final static Property taskID = new Property(2, Long.class, "taskID", false, TASKID);
        public final static Property arrangementTime = new Property(3, String.class, "arrangementTime", false, ARRANGEMENTTIME);
        public final static Property taskSubmitTime = new Property(4, String.class, "taskSubmitTime", false, TASKSUBMITTIME);
        public final static Property taskEndTime = new Property(5, Long.class, "taskEndTime", false, TASKENDTIME);
        public final static Property taskTime = new Property(6, Long.class, "taskTime", false, TASKTIME);
        public final static Property isHaveTask = new Property(7, String.class, "isHaveTask", false, ISHAVETASK);

        public final static Property taskTimeCurrent = new Property(8, Long.class, "taskTimeCurrent", false, TASKTIMECURRENT);
        public final static Property taskLastSubmitTime = new Property(9, String.class, "taskLastSubmitTime", false, TASKLASTSUBMITTIME);
        public final static Property taskName = new Property(10, String.class, "taskName", false, TASKNAME);
        public final static Property taskNum = new Property(11, Long.class, "taskNum", false, TASKNUM);
        public final static Property taskState = new Property(12, Long.class, "taskState", false, TASKSTATE);
        public final static Property taskDegree = new Property(13, Long.class, "taskDegree", false, TASKDEGREE);
        public final static Property isEvaluate = new Property(14, String.class, "isEvaluate", false, ISEVALUATE);
        public final static Property week = new Property(15, String.class, "week", false, WEEK);
        public final static Property taskRequirement = new Property(16, String.class, "taskRequirement", false, TASKREQUIREMENT);
        public final static Property userId = new Property(17, Long.class, "userId", false, USERID);
        public final static Property taskPercentage = new Property(18, Long.class, "taskPercentage", false, TASKPERCENTAGE);
        public final static Property taskExplain = new Property(19, String.class, "taskExplain", false, TASKEXPLAIN);
        public final static Property isShowRedpoint = new Property(20, String.class, "isShowRedpoint", false, ISSHOWREDPOINT);
        public final static Property job_id = new Property(21, Long.class, "job_id", false, JOB_ID);
    }

    @Override
    protected TaskListBean readEntity(Cursor cursor, int offset) {
        int i = 1;
        return new TaskListBean(
                cursor.isNull(offset) ? null : cursor.getLong(offset),
                cursor.getLong(offset + i++),//id
                cursor.getLong(offset + i++),//id
                cursor.getString(offset + i++),//id
                cursor.getString(offset + i++),//id
                cursor.getLong(offset + i++),//id
                cursor.getLong(offset + i++),//id
                Boolean.valueOf(cursor.getString(offset + i++)),
                cursor.getLong(offset + i++),//id
                cursor.getString(offset + i++),//id
                cursor.getString(offset + i++),//id
                cursor.getLong(offset + i++),//id
                cursor.getLong(offset + i++),//id
                cursor.getLong(offset + i++),//id
                Boolean.valueOf(cursor.getString(offset + i++)),
                cursor.getString(offset + i++),//id
                cursor.getString(offset + i++),//id
                cursor.getLong(offset + i++),
                cursor.getLong(offset + i++),
                cursor.getString(offset + i++),
                Boolean.valueOf(cursor.getString(offset + i++)),
                cursor.getLong(offset + i)
                );
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset) ? null : cursor.getLong(offset);
    }

    @Override
    protected void readEntity(Cursor cursor, TaskListBean entity, int offset) {
        int i = 1;
        entity.setID(cursor.getLong(offset));//0
        entity.setClassID(cursor.getLong(offset + i++));//1
        entity.setTaskID(cursor.getLong(offset + i++));//2
        entity.setArrangementTime(cursor.getString(offset + i++));//2
        entity.setTaskSubmitTime(cursor.getString(offset + i++));
        entity.setTaskEndTime(cursor.getLong(offset + i++));
        entity.setTaskTime(cursor.getLong(offset + i++));
        entity.setTaskTimeCurrent(cursor.getLong(offset + i++));
        entity.setHaveTask(Boolean.valueOf(cursor.getString(offset + i++)));
        entity.setTaskLastSubmitTime(cursor.getString(offset + i++));
        entity.setTaskName(cursor.getString(offset + i++));
        entity.setTaskNum(cursor.getLong(offset + i++));
        entity.setTaskState(cursor.getLong(offset + i++));
        entity.setTaskDegree(cursor.getLong(offset + i++));
        entity.setEvaluate(Boolean.valueOf(cursor.getString(offset + i++)));
        entity.setWeek(cursor.getString(offset + i++));
        entity.setTaskRequirement(cursor.getString(offset + i++));
        entity.setUserId(cursor.getLong(offset + i++));
        entity.setTaskPercentage(cursor.getLong(offset + i++));
        entity.setTaskExplain(cursor.getString(offset + i++));
        entity.setShowRedpoint(Boolean.valueOf(cursor.getString(offset + i++)));
        entity.setJob_id(cursor.getLong(offset + i++));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, TaskListBean entity) {
        stmt.clearBindings();
        int i = 0;
        Long id = entity.getID();
        i++;
        if (id != null)
            stmt.bindLong(i, id);

        long classID = entity.getClassID();
        i++;
        if (classID != 0)
            stmt.bindLong(i, classID);

        long taskID = entity.getTaskID();
        i++;
        if (taskID != 0)
            stmt.bindLong(i, taskID);

        String arrangementTime = entity.getArrangementTime();
        i++;
        if (arrangementTime != null)
            stmt.bindString(i, arrangementTime);

        String taskSubmitTime = entity.getTaskSubmitTime();
        i++;
        if (taskSubmitTime != null)
            stmt.bindString(i, taskSubmitTime);

        long taskEndTime = entity.getTaskEndTime();
        i++;
        if (taskEndTime != 0)
            stmt.bindLong(i, taskEndTime);

        long taskTime = entity.getTaskTime();
        i++;
        if (taskTime != 0)
            stmt.bindLong(i, taskTime);

        i++;
        stmt.bindString(i, String.valueOf(entity.isHaveTask()));

        long taskTimeCurrent = entity.getTaskTimeCurrent();
        i++;
        if (taskTimeCurrent != 0)
            stmt.bindLong(i, taskTimeCurrent);

        String taskLastSubmitTime = entity.getTaskLastSubmitTime();
        i++;
        if (taskLastSubmitTime != null)
            stmt.bindString(i, taskLastSubmitTime);

        String taskName = entity.getTaskName();
        i++;
        if (taskName != null)
            stmt.bindString(i, taskName);

        long taskNum = entity.getTaskNum();
        i++;
        if (taskNum != 0)
            stmt.bindLong(i, taskNum);

        long taskState = entity.getTaskState();
        i++;
        if (taskState != 0)
            stmt.bindLong(i, taskState);

        long taskDegree = entity.getTaskDegree();
        i++;
        if (taskDegree != 0)
            stmt.bindLong(i, taskDegree);

        i++;
        stmt.bindString(i, String.valueOf(entity.isEvaluate()));

        String week = entity.getWeek();
        i++;
        if (week != null)
            stmt.bindString(i, week);

        String taskRequirement = entity.getTaskRequirement();
        i++;
        if (taskRequirement != null)
            stmt.bindString(i, taskRequirement);

        long userId = entity.getUserId();
        i++;
        if (userId != 0)
            stmt.bindLong(i, userId);

        long taskPercentage = entity.getTaskPercentage();
        i++;
        if (taskPercentage != 0)
            stmt.bindLong(i, taskPercentage);

        String taskExplain = entity.getTaskExplain();
        i++;
        if (taskExplain != null)
            stmt.bindString(i, taskExplain);

        i++;
        stmt.bindString(i, String.valueOf(entity.isShowRedpoint()));

        long job_id = entity.getJob_id();
        i++;
        if (job_id != 0)
            stmt.bindLong(i, job_id);
    }

    @Override
    protected Long updateKeyAfterInsert(TaskListBean entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(TaskListBean entity) {
        if (entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }


    /**
     * Drops the underlying database table.
     * 降下基础数据库表。
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + TABLENAME;
        db.execSQL(sql);
    }

    /**
     * Creates the underlying database table.
     * 创建基础数据库表
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";

        db.execSQL("CREATE TABLE " + constraint + TABLENAME + " (" +
                _id + " INTEGER PRIMARY KEY ," + // 0: id  唯一键
                CLASSID + " INTEGER," + //
                TASKID + " INTEGER," + //
                ARRANGEMENTTIME + " TEXT," + //
                TASKSUBMITTIME + " TEXT," + //
                TASKENDTIME + " INTEGER," + //
                TASKTIME + " INTEGER," + //
                ISHAVETASK + " TEXT," + //
                TASKTIMECURRENT + " INTEGER," + //
                TASKLASTSUBMITTIME + " TEXT," + //
                TASKNAME + " TEST," + //
                TASKNUM + " INTEGER," + //
                TASKSTATE + " INTEGER," + //
                TASKDEGREE + " INTEGER," + //
                ISEVALUATE + " TEXT," + //
                WEEK + " TEXT," + //
                TASKREQUIREMENT + " TEXT," +
                USERID + " INTEGER," +
                TASKPERCENTAGE + " INTEGER," + //
                TASKEXPLAIN + " TEXT," + //
                ISSHOWREDPOINT + " TEXT," + //
                JOB_ID + " INTEGER);"); //
    }
}
