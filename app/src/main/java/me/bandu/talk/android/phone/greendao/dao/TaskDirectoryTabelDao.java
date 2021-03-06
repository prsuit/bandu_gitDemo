package me.bandu.talk.android.phone.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import me.bandu.talk.android.phone.greendao.bean.TaskDirectoryBean;

/**
 * 创建者：wanglei
 * <p>时间：16/6/27  16:36
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class TaskDirectoryTabelDao extends AbstractDao<TaskDirectoryBean, Long> {

    public static final String TABLENAME = "taskDirectory";//数据库的表命
    public static final String _id = "_id";//ID
    public static final String CLASSID = "classID";//
    public static final String USERID = "userId";//
    public static final String TASKID = "taskID";//
    public static final String UNITID = "unitID";//
    public static final String LESENID = "lesenID";//
    public static final String PARTID = "partID";//题ID
    public static final String TYPE = "type";//句子类型1：跟读！2：朗读！3：背诵
    public static final String ISDONE = "isDone";//真句子表没有存过，假局子表存过
    public static final String PARTNAME = "partName";//条目名
    public static final String REPEATTOTAL = "repeatTotal";//跟读总数
    public static final String REPEATCURRENT = "repeatCurrent";//跟读当前数
    public static final String REPEATTIME = "repeatTime";//跟读耗时
    public static final String DESCRIPTION = "description";//题干
    public static final String QUIZ_TYPE = "quiz_type";//题型


    public TaskDirectoryTabelDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Properties of entity Note.<br/>
     * 实体注释的属性。
     * Can be used for QueryBuilder and for referencing column names.
     * 可用于querybuilder和引用列的名称。
     * <p/>
     * private long repeatTime;//跟读耗时
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "id", true, _id);
        public final static Property classID = new Property(1, Long.class, "classID", false, CLASSID);
        public final static Property userId = new Property(2, Long.class, "userId", false, USERID);
        public final static Property taskID = new Property(3, Long.class, "taskID", false, TASKID);
        public final static Property unitID = new Property(4, Long.class, "unitID", false, UNITID);
        public final static Property lesenID = new Property(5, Long.class, "lesenID", false, LESENID);
        public final static Property partID = new Property(6, Long.class, "partID", false, PARTID);
        public final static Property type = new Property(7, Long.class, "type", false, TYPE);
        public final static Property isDone = new Property(8, String.class, "isDone", false, ISDONE);
        public final static Property partName = new Property(9, String.class, "partName", false, PARTNAME);
        public final static Property repeatTotal = new Property(10, Long.class, "repeatTotal", false, REPEATTOTAL);
        public final static Property repeatCurrent = new Property(11, Long.class, "repeatCurrent", false, REPEATCURRENT);
        public final static Property repeatTime = new Property(12, Long.class, "repeatTime", false, REPEATTIME);
        public final static Property description = new Property(13, String.class, "description", false, DESCRIPTION);
        public final static Property quiz_type = new Property(14, String.class, "quiz_type", false, QUIZ_TYPE);
    }

    // long repeatCurrent, long repeatTime
    @Override
    protected TaskDirectoryBean readEntity(Cursor cursor, int offset) {
        int i = 1;
        return new TaskDirectoryBean(
                cursor.isNull(offset) ? null : cursor.getLong(offset),
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                Boolean.valueOf(cursor.getString(offset + i++)),
                cursor.getString(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getString(offset + i++),//
                cursor.getString(offset + i));
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset) ? null : cursor.getLong(offset);
    }

    /**
     * @param cursor
     * @param entity
     * @param offset
     */
    @Override
    protected void readEntity(Cursor cursor, TaskDirectoryBean entity, int offset) {
        int i = 1;
        entity.setID(cursor.getLong(offset));//0
        entity.setClassID(cursor.getLong(offset + i++));//0
        entity.setUserId(cursor.getLong(offset + i++));//0
        entity.setTaskID(cursor.getLong(offset + i++));//1
        entity.setUnitID(cursor.getLong(offset + i++));//2
        entity.setLesenID(cursor.getLong(offset + i++));//3
        entity.setPartID(cursor.getLong(offset + i++));//4
        entity.setType(cursor.getLong(offset + i++));//5
        entity.setIsDone(Boolean.valueOf(cursor.getString(offset + i++)));//6
        entity.setPartName(cursor.getString(offset + i++));//7
        entity.setRepeatTotal(cursor.getLong(offset + i++));//8
        entity.setRepeatCurrent(cursor.getLong(offset + i++));//9
        entity.setRepeatTime(cursor.getLong(offset + i++));//10
        entity.setDescription(cursor.getString(offset + i++));
        entity.setQuiz_type(cursor.getString(offset + i));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, TaskDirectoryBean entity) {
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

        long userId = entity.getUserId();
        i++;
        if (userId != 0)
            stmt.bindLong(i, userId);

        long taskID = entity.getTaskID();
        i++;
        if (taskID != 0)
            stmt.bindLong(i, taskID);

        long unitID = entity.getUnitID();
        i++;
        if (unitID != 0)
            stmt.bindLong(i, unitID);

        long lesenID = entity.getLesenID();
        i++;
        if (lesenID != 0)
            stmt.bindLong(i, lesenID);

        long partID = entity.getPartID();
        i++;
        if (partID != 0)
            stmt.bindLong(i, partID);

        long type = entity.getType();
        i++;
        if (type != 0)
            stmt.bindLong(i, type);

        i++;
        stmt.bindString(i, String.valueOf(entity.isDone()));

        String partName = entity.getPartName();
        i++;
        if (partName != null)
            stmt.bindString(i, partName);

        long repeatTotal = entity.getRepeatTotal();
        i++;
        if (repeatTotal != 0)
            stmt.bindLong(i, repeatTotal);

        long repeatCurrent = entity.getRepeatCurrent();
        i++;
        if (repeatCurrent != 0)
            stmt.bindLong(i, repeatCurrent);

        long repeatTime = entity.getRepeatTime();
        i++;
        if (repeatTime != 0)
            stmt.bindLong(i, repeatTime);

        String description = entity.getDescription();
        i++;
        if (description != null)
            stmt.bindString(i, description);

        String quiz_type = entity.getQuiz_type();
        i++;
        if (quiz_type != null)
            stmt.bindString(i, quiz_type);
    }

    @Override
    protected Long updateKeyAfterInsert(TaskDirectoryBean entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(TaskDirectoryBean entity) {
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
                USERID + " INTEGER," + //
                TASKID + " INTEGER," + //
                UNITID + " INTEGER," + //
                LESENID + " INTEGER," + //
                PARTID + " INTEGER," + //
                TYPE + " INTEGER," + //
                ISDONE + " TEXT," + //
                PARTNAME + " TEXT," + //
                REPEATTOTAL + " INTEGER," + //
                REPEATCURRENT + " INTEGER," + //
                REPEATTIME + " INTEGER," + //
                DESCRIPTION + " TEXT," + //
                QUIZ_TYPE + " TEXT);"); //
    }
}
