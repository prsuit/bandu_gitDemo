package me.bandu.talk.android.phone.db.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import me.bandu.talk.android.phone.db.bean.PartBean;

import me.bandu.talk.android.phone.db.bean.CentenceBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CENTENCE_BEAN".
*/
public class CentenceBeanDao extends AbstractDao<CentenceBean, Long> {

    public static final String TABLENAME = "CENTENCE_BEAN";

    /**
     * Properties of entity CentenceBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Centence_id = new Property(0, Long.class, "centence_id", true, "CENTENCE_ID");
        public final static Property Part_id = new Property(1, Long.class, "part_id", false, "PART_ID");
        public final static Property Url_exemple = new Property(2, String.class, "url_exemple", false, "URL_EXEMPLE");
        public final static Property Url_best = new Property(3, String.class, "url_best", false, "URL_BEST");
        public final static Property Url_current = new Property(4, String.class, "url_current", false, "URL_CURRENT");
        public final static Property English = new Property(5, String.class, "english", false, "ENGLISH");
        public final static Property Chines = new Property(6, String.class, "chines", false, "CHINES");
        public final static Property Details = new Property(7, String.class, "details", false, "DETAILS");
        public final static Property Seconds = new Property(8, Integer.class, "seconds", false, "SECONDS");
        public final static Property Sorce_best = new Property(9, Integer.class, "sorce_best", false, "SORCE_BEST");
        public final static Property Sorce_current = new Property(10, Integer.class, "sorce_current", false, "SORCE_CURRENT");
        public final static Property Done = new Property(11, Boolean.class, "done", false, "DONE");
        public final static Property Video_time = new Property(12, Integer.class, "video_time", false, "VIDEO_TIME");
        public final static Property Video_start = new Property(13, Integer.class, "video_start", false, "VIDEO_START");
        public final static Property Video_end = new Property(14, Integer.class, "video_end", false, "VIDEO_END");
        public final static Property Mp3_url = new Property(15, String.class, "mp3_url", false, "MP3_URL");
    };

    private DaoSession daoSession;


    public CentenceBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CentenceBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CENTENCE_BEAN\" (" + //
                "\"CENTENCE_ID\" INTEGER PRIMARY KEY ," + // 0: centence_id
                "\"PART_ID\" INTEGER," + // 1: part_id
                "\"URL_EXEMPLE\" TEXT," + // 2: url_exemple
                "\"URL_BEST\" TEXT," + // 3: url_best
                "\"URL_CURRENT\" TEXT," + // 4: url_current
                "\"ENGLISH\" TEXT," + // 5: english
                "\"CHINES\" TEXT," + // 6: chines
                "\"DETAILS\" TEXT," + // 7: details
                "\"SECONDS\" INTEGER," + // 8: seconds
                "\"SORCE_BEST\" INTEGER," + // 9: sorce_best
                "\"SORCE_CURRENT\" INTEGER," + // 10: sorce_current
                "\"DONE\" INTEGER," + // 11: done
                "\"VIDEO_TIME\" INTEGER," + // 12: video_time
                "\"VIDEO_START\" INTEGER," + // 13: video_start
                "\"VIDEO_END\" INTEGER," + // 14: video_end
                "\"MP3_URL\" TEXT);"); // 15: mp3_url
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CENTENCE_BEAN\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CentenceBean entity) {
        stmt.clearBindings();
 
        Long centence_id = entity.getCentence_id();
        if (centence_id != null) {
            stmt.bindLong(1, centence_id);
        }
 
        Long part_id = entity.getPart_id();
        if (part_id != null) {
            stmt.bindLong(2, part_id);
        }
 
        String url_exemple = entity.getUrl_exemple();
        if (url_exemple != null) {
            stmt.bindString(3, url_exemple);
        }
 
        String url_best = entity.getUrl_best();
        if (url_best != null) {
            stmt.bindString(4, url_best);
        }
 
        String url_current = entity.getUrl_current();
        if (url_current != null) {
            stmt.bindString(5, url_current);
        }
 
        String english = entity.getEnglish();
        if (english != null) {
            stmt.bindString(6, english);
        }
 
        String chines = entity.getChines();
        if (chines != null) {
            stmt.bindString(7, chines);
        }
 
        String details = entity.getDetails();
        if (details != null) {
            stmt.bindString(8, details);
        }
 
        Integer seconds = entity.getSeconds();
        if (seconds != null) {
            stmt.bindLong(9, seconds);
        }
 
        Integer sorce_best = entity.getSorce_best();
        if (sorce_best != null) {
            stmt.bindLong(10, sorce_best);
        }
 
        Integer sorce_current = entity.getSorce_current();
        if (sorce_current != null) {
            stmt.bindLong(11, sorce_current);
        }
 
        Boolean done = entity.getDone();
        if (done != null) {
            stmt.bindLong(12, done ? 1L: 0L);
        }
 
        Integer video_time = entity.getVideo_time();
        if (video_time != null) {
            stmt.bindLong(13, video_time);
        }
 
        Integer video_start = entity.getVideo_start();
        if (video_start != null) {
            stmt.bindLong(14, video_start);
        }
 
        Integer video_end = entity.getVideo_end();
        if (video_end != null) {
            stmt.bindLong(15, video_end);
        }
 
        String mp3_url = entity.getMp3_url();
        if (mp3_url != null) {
            stmt.bindString(16, mp3_url);
        }
    }

    @Override
    protected void attachEntity(CentenceBean entity) {
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
    public CentenceBean readEntity(Cursor cursor, int offset) {
        CentenceBean entity = new CentenceBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // centence_id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // part_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // url_exemple
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // url_best
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // url_current
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // english
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // chines
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // details
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // seconds
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // sorce_best
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // sorce_current
            cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0, // done
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // video_time
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // video_start
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // video_end
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // mp3_url
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CentenceBean entity, int offset) {
        entity.setCentence_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPart_id(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setUrl_exemple(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUrl_best(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUrl_current(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEnglish(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setChines(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDetails(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSeconds(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setSorce_best(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setSorce_current(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setDone(cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0);
        entity.setVideo_time(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setVideo_start(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setVideo_end(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setMp3_url(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CentenceBean entity, long rowId) {
        entity.setCentence_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CentenceBean entity) {
        if(entity != null) {
            return entity.getCentence_id();
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getPartBeanDao().getAllColumns());
            builder.append(" FROM CENTENCE_BEAN T");
            builder.append(" LEFT JOIN PART_BEAN T0 ON T.\"PART_ID\"=T0.\"PART_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected CentenceBean loadCurrentDeep(Cursor cursor, boolean lock) {
        CentenceBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        PartBean partBean = loadCurrentOther(daoSession.getPartBeanDao(), cursor, offset);
        entity.setPartBean(partBean);

        return entity;    
    }

    public CentenceBean loadDeep(Long key) {
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
    public List<CentenceBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<CentenceBean> list = new ArrayList<CentenceBean>(count);
        
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
    
    protected List<CentenceBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<CentenceBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}