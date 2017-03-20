package me.bandu.talk.android.phone.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import me.bandu.talk.android.phone.db.bean.UnitBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "UNIT_BEAN".
*/
public class UnitBeanDao extends AbstractDao<UnitBean, Long> {

    public static final String TABLENAME = "UNIT_BEAN";

    /**
     * Properties of entity UnitBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Unit_id = new Property(0, Long.class, "unit_id", true, "UNIT_ID");
        public final static Property Unit_name = new Property(1, String.class, "unit_name", false, "UNIT_NAME");
        public final static Property Textbook_id = new Property(2, Integer.class, "textbook_id", false, "TEXTBOOK_ID");
        public final static Property Textbook_name = new Property(3, String.class, "textbook_name", false, "TEXTBOOK_NAME");
        public final static Property Textbook_subject = new Property(4, Integer.class, "textbook_subject", false, "TEXTBOOK_SUBJECT");
        public final static Property Insert_time = new Property(5, Long.class, "insert_time", false, "INSERT_TIME");
        public final static Property Category = new Property(6, Integer.class, "category", false, "CATEGORY");
    };


    public UnitBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UnitBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"UNIT_BEAN\" (" + //
                "\"UNIT_ID\" INTEGER PRIMARY KEY ," + // 0: unit_id
                "\"UNIT_NAME\" TEXT," + // 1: unit_name
                "\"TEXTBOOK_ID\" INTEGER," + // 2: textbook_id
                "\"TEXTBOOK_NAME\" TEXT," + // 3: textbook_name
                "\"TEXTBOOK_SUBJECT\" INTEGER," + // 4: textbook_subject
                "\"INSERT_TIME\" INTEGER," + // 5: insert_time
                "\"CATEGORY\" INTEGER);"); // 6: category
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"UNIT_BEAN\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UnitBean entity) {
        stmt.clearBindings();
 
        Long unit_id = entity.getUnit_id();
        if (unit_id != null) {
            stmt.bindLong(1, unit_id);
        }
 
        String unit_name = entity.getUnit_name();
        if (unit_name != null) {
            stmt.bindString(2, unit_name);
        }
 
        Integer textbook_id = entity.getTextbook_id();
        if (textbook_id != null) {
            stmt.bindLong(3, textbook_id);
        }
 
        String textbook_name = entity.getTextbook_name();
        if (textbook_name != null) {
            stmt.bindString(4, textbook_name);
        }
 
        Integer textbook_subject = entity.getTextbook_subject();
        if (textbook_subject != null) {
            stmt.bindLong(5, textbook_subject);
        }
 
        Long insert_time = entity.getInsert_time();
        if (insert_time != null) {
            stmt.bindLong(6, insert_time);
        }
 
        Integer category = entity.getCategory();
        if (category != null) {
            stmt.bindLong(7, category);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UnitBean readEntity(Cursor cursor, int offset) {
        UnitBean entity = new UnitBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // unit_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // unit_name
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // textbook_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // textbook_name
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // textbook_subject
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // insert_time
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6) // category
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UnitBean entity, int offset) {
        entity.setUnit_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUnit_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTextbook_id(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setTextbook_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTextbook_subject(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setInsert_time(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setCategory(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UnitBean entity, long rowId) {
        entity.setUnit_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UnitBean entity) {
        if(entity != null) {
            return entity.getUnit_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
