package me.bandu.talk.android.phone.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import me.bandu.talk.android.phone.dao.Comment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMMENT".
*/
public class CommentDao extends AbstractDao<Comment, Long> {

    public static final String TABLENAME = "COMMENT";

    /**
     * Properties of entity Comment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Comment = new Property(1, String.class, "comment", false, "COMMENT");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Value1 = new Property(3, String.class, "value1", false, "VALUE1");
        public final static Property Value2 = new Property(4, Integer.class, "value2", false, "VALUE2");
    };


    public CommentDao(DaoConfig config) {
        super(config);
    }
    
    public CommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"COMMENT\" TEXT NOT NULL ," + // 1: comment
                "\"NAME\" TEXT NOT NULL ," + // 2: name
                "\"VALUE1\" TEXT," + // 3: value1
                "\"VALUE2\" INTEGER);"); // 4: value2
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMMENT\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Comment entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindString(2, entity.getComment());
        stmt.bindString(3, entity.getName());
 
        String value1 = entity.getValue1();
        if (value1 != null) {
            stmt.bindString(4, value1);
        }
 
        Integer value2 = entity.getValue2();
        if (value2 != null) {
            stmt.bindLong(5, value2);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Comment readEntity(Cursor cursor, int offset) {
        Comment entity = new Comment( //
            cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // comment
            cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // value1
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4) // value2
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Comment entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setComment(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setValue1(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setValue2(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Comment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Comment entity) {
        if(entity != null) {
            return entity.getId();
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