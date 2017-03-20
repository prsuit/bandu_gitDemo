package me.bandu.talk.android.phone.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WORK_QUIZE".
*/
public class WorkQuizeDao extends AbstractDao<WorkQuize, Long> {

    public static final String TABLENAME = "WORK_QUIZE";

    /**
     * Properties of entity WorkQuize.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Wq_id = new Property(0, Long.class, "wq_id", true, "WQ_ID");
        public final static Property Stu_job_id = new Property(1, Long.class, "stu_job_id", false, "STU_JOB_ID");
        public final static Property Quiz_id = new Property(2, Long.class, "quiz_id", false, "QUIZ_ID");
    };

    private DaoSession daoSession;


    public WorkQuizeDao(DaoConfig config) {
        super(config);
    }
    
    public WorkQuizeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WORK_QUIZE\" (" + //
                "\"WQ_ID\" INTEGER PRIMARY KEY ," + // 0: wq_id
                "\"STU_JOB_ID\" INTEGER," + // 1: stu_job_id
                "\"QUIZ_ID\" INTEGER);"); // 2: quiz_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WORK_QUIZE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, WorkQuize entity) {
        stmt.clearBindings();
 
        Long wq_id = entity.getWq_id();
        if (wq_id != null) {
            stmt.bindLong(1, wq_id);
        }
 
        Long stu_job_id = entity.getStu_job_id();
        if (stu_job_id != null) {
            stmt.bindLong(2, stu_job_id);
        }
 
        Long quiz_id = entity.getQuiz_id();
        if (quiz_id != null) {
            stmt.bindLong(3, quiz_id);
        }
    }

    @Override
    protected void attachEntity(WorkQuize entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public WorkQuize readEntity(Cursor cursor, int offset) {
        WorkQuize entity = new WorkQuize( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // wq_id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // stu_job_id
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // quiz_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, WorkQuize entity, int offset) {
        entity.setWq_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStu_job_id(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setQuiz_id(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(WorkQuize entity, long rowId) {
        entity.setWq_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(WorkQuize entity) {
        if(entity != null) {
            return entity.getWq_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getHomeWorkDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getQuizDao().getAllColumns());
            builder.append(" FROM WORK_QUIZE T");
            builder.append(" LEFT JOIN HOME_WORK T0 ON T.\"STU_JOB_ID\"=T0.\"STU_JOB_ID\"");
            builder.append(" LEFT JOIN QUIZ T1 ON T.\"QUIZ_ID\"=T1.\"QUIZ_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected WorkQuize loadCurrentDeep(Cursor cursor, boolean lock) {
        WorkQuize entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        HomeWork homeWork = loadCurrentOther(daoSession.getHomeWorkDao(), cursor, offset);
        entity.setHomeWork(homeWork);
        offset += daoSession.getHomeWorkDao().getAllColumns().length;

        Quiz quiz = loadCurrentOther(daoSession.getQuizDao(), cursor, offset);
        entity.setQuiz(quiz);

        return entity;    
    }

    public WorkQuize loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<WorkQuize> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<WorkQuize> list = new ArrayList<WorkQuize>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<WorkQuize> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<WorkQuize> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
