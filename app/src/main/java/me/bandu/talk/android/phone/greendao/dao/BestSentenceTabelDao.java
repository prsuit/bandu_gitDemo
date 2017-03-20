package me.bandu.talk.android.phone.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import me.bandu.talk.android.phone.greendao.bean.BestSentenceBean;

/**
 * 创建者：wanglei
 * <p>时间：16/6/29  10:21
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class BestSentenceTabelDao extends AbstractDao<BestSentenceBean, Long> {

//    //    public static final String TABLENAME = "BEST_SENTENCE";
//    public static final String TABLENAME = "bestSentence";//数据库的表命
//
//
//    /**
//     * Properties of entity bestSentence.<br/>
//     * Can be used for QueryBuilder and for referencing column names.
//     */
//    public static class Properties {
//        public final static Property _id = new Property(0, long.class, "_id", true, "_ID");
//        public final static Property userId = new Property(1, Long.class, "userId", false, "USER_ID");
//        public final static Property classID = new Property(2, Long.class, "classID", false, "CLASS_ID");
//        public final static Property taskID = new Property(3, Long.class, "taskID", false, "TASK_ID");
//        public final static Property partID = new Property(4, Long.class, "partID", false, "PART_ID");
//        public final static Property sentenceID = new Property(5, Long.class, "sentenceID", false, "SENTENCE_ID");
//        public final static Property type = new Property(6, Long.class, "type", false, "TYPE");
//        public final static Property text = new Property(7, String.class, "text", false, "TEXT");
//        public final static Property textColor = new Property(8, String.class, "textColor", false, "TEXT_COLOR");
//        public final static Property netAddress = new Property(9, String.class, "netAddress", false, "NET_ADDRESS");
//        public final static Property myAddress = new Property(10, String.class, "myAddress", false, "MY_ADDRESS");
//        public final static Property chishengNetAddress = new Property(11, String.class, "chishengNetAddress", false, "CHISHENG_NET_ADDRESS");
//        public final static Property myVoiceTime = new Property(12, Long.class, "myVoiceTime", false, "MY_VOICE_TIME");
//        public final static Property myNum = new Property(13, Long.class, "myNum", false, "MY_NUM");
//        public final static Property state = new Property(14, Boolean.class, "state", false, "STATE");
//        public final static Property hw_quiz_id = new Property(15, Long.class, "hw_quiz_id", false, "HW_QUIZ_ID");
//    }
//
//    ;
//
//
//    public BestSentenceTabelDao(DaoConfig config) {
//        super(config);
//    }
//
//    public BestSentenceTabelDao(DaoConfig config, Session daoSession) {
//        super(config, daoSession);
//    }
//
//    /**
//     * Creates the underlying database table.
//     */
//    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
//        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
//        db.execSQL("CREATE TABLE " + constraint + "\"BEST_SENTENCE\" (" + //
//                "\"_ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: _id
//                "\"USER_ID\" INTEGER," + // 1: userId
//                "\"CLASS_ID\" INTEGER," + // 2: classID
//                "\"TASK_ID\" INTEGER," + // 3: taskID
//                "\"PART_ID\" INTEGER," + // 4: partID
//                "\"SENTENCE_ID\" INTEGER," + // 5: sentenceID
//                "\"TYPE\" INTEGER," + // 6: type
//                "\"TEXT\" TEXT," + // 7: text
//                "\"TEXT_COLOR\" TEXT," + // 8: textColor
//                "\"NET_ADDRESS\" TEXT," + // 9: netAddress
//                "\"MY_ADDRESS\" TEXT," + // 10: myAddress
//                "\"CHISHENG_NET_ADDRESS\" TEXT," + // 11: chishengNetAddress
//                "\"MY_VOICE_TIME\" INTEGER," + // 12: myVoiceTime
//                "\"MY_NUM\" INTEGER," + // 13: myNum
//                "\"STATE\" INTEGER," + // 14: state
//                "\"HW_QUIZ_ID\" INTEGER);"); // 15: hw_quiz_id
//    }
//
//    /**
//     * Drops the underlying database table.
//     */
//    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
//        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BEST_SENTENCE\"";
//        db.execSQL(sql);
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    protected void bindValues(SQLiteStatement stmt, BestSentenceBean entity) {
//        stmt.clearBindings();
//        stmt.bindLong(1, entity.getID());
//
//        Long userId = entity.getUserId();
//        if (userId != null) {
//            stmt.bindLong(2, userId);
//        }
//
//        Long classID = entity.getClassID();
//        if (classID != null) {
//            stmt.bindLong(3, classID);
//        }
//
//        Long taskID = entity.getTaskID();
//        if (taskID != null) {
//            stmt.bindLong(4, taskID);
//        }
//
//        Long partID = entity.getPartID();
//        if (partID != null) {
//            stmt.bindLong(5, partID);
//        }
//
//        Long sentenceID = entity.getSentenceID();
//        if (sentenceID != null) {
//            stmt.bindLong(6, sentenceID);
//        }
//
//        Long type = entity.getType();
//        if (type != null) {
//            stmt.bindLong(7, type);
//        }
//
//        String text = entity.getText();
//        if (text != null) {
//            stmt.bindString(8, text);
//        }
//
//        String textColor = entity.getTextColor();
//        if (textColor != null) {
//            stmt.bindString(9, textColor);
//        }
//
//        String netAddress = entity.getNetAddress();
//        if (netAddress != null) {
//            stmt.bindString(10, netAddress);
//        }
//
//        String myAddress = entity.getMyAddress();
//        if (myAddress != null) {
//            stmt.bindString(11, myAddress);
//        }
//
//        String chishengNetAddress = entity.getChishengNetAddress();
//        if (chishengNetAddress != null) {
//            stmt.bindString(12, chishengNetAddress);
//        }
//
//        Long myVoiceTime = entity.getMyVoiceTime();
//        if (myVoiceTime != null) {
//            stmt.bindLong(13, myVoiceTime);
//        }
//
//        Long myNum = entity.getMyNum();
//        if (myNum != null) {
//            stmt.bindLong(14, myNum);
//        }
//
//        Boolean state = entity.isState();
//        if (state != null) {
//            stmt.bindLong(15, state ? 1L : 0L);
//        }
//
//        Long hw_quiz_id = entity.getHw_quiz_id();
//        if (hw_quiz_id != null) {
//            stmt.bindLong(16, hw_quiz_id);
//        }
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    public Long readKey(Cursor cursor, int offset) {
//        return cursor.getLong(offset + 0);
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    public BestSentenceBean readEntity(Cursor cursor, int offset) {
//        BestSentenceBean entity = new BestSentenceBean( //
//                cursor.getLong(offset + 0), // _id
//                cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // userId
//                cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // classID
//                cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // taskID
//                cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // partID
//                cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // sentenceID
//                cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // type
//                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // text
//                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // textColor
//                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // netAddress
//                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // myAddress
//                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // chishengNetAddress
//                cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12), // myVoiceTime
//                cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13), // myNum
//                cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0, // state
//                cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15) // hw_quiz_id
//        );
//        return entity;
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    public void readEntity(Cursor cursor, BestSentenceBean entity, int offset) {
//        entity.setID(cursor.getLong(offset + 0));
//        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
//        entity.setClassID(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
//        entity.setTaskID(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
//        entity.setPartID(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
//        entity.setSentenceID(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
//        entity.setType(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
//        entity.setText(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
//        entity.setTextColor(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
//        entity.setNetAddress(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
//        entity.setMyAddress(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
//        entity.setChishengNetAddress(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
//        entity.setMyVoiceTime(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
//        entity.setMyNum(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
//        entity.setState(cursor.isNull(offset + 14) ? null : cursor.getShort(offset + 14) != 0);
//        entity.setHw_quiz_id(cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15));
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    protected Long updateKeyAfterInsert(BestSentenceBean entity, long rowId) {
//        entity.setID(rowId);
//        return rowId;
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    public Long getKey(BestSentenceBean entity) {
//        if (entity != null) {
//            return entity.getID();
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * @inheritdoc
//     */
//    @Override
//    protected boolean isEntityUpdateable() {
//        return true;
//    }


    public static final String TABLENAME = "bestSentence";//数据库的表命
    public static final String _id = "_id";//ID
    public static final String USERID = "userId";//
    public static final String CLASSID = "classID";//
    public static final String TASKID = "taskID";//作业ID
    public static final String PARTID = "partID";//题ID
    public static final String SENTENCEID = "sentenceID";//句子ID
    public static final String TYPE = "type";//句子类型1：跟读！2：朗读！3：背诵
    public static final String TEXT = "text";//文本
    public static final String TEXTCOLOR = "textColor";//变色文本
    public static final String NETADDRESS = "netAddress";//泛音地址
    public static final String MYADDRESS = "myAddress";//我的声音本地地址
    public static final String CHISHENGNETADDRESS = "chishengNetAddress";//弛声的网络声音地址
    public static final String MYVOICETIME = "myVoiceTime";//我的声音时长
    public static final String MYNUM = "myNum";//我的分数
    public static final String STATE = "state";//否超过上次的分数
    public static final String HW_QUIZ_ID = "hw_quiz_id";//否超过上次的分数
    public static final String ANSWER = "answer";//否超过上次的分数

    public BestSentenceTabelDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Properties of entity Note.<br/>
     * 实体注释的属性。
     * Can be used for QueryBuilder and for referencing column names.
     * 可用于querybuilder和引用列的名称。
     * <p/>
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "id", true, _id);
        public final static Property userId = new Property(1, Long.class, "userId", false, USERID);
        public final static Property classID = new Property(2, Long.class, "classID", false, CLASSID);
        public final static Property taskID = new Property(3, Long.class, "taskID", false, TASKID);
        public final static Property partID = new Property(4, Long.class, "partID", false, PARTID);
        public final static Property sentenceID = new Property(5, Long.class, "sentenceID", false, SENTENCEID);
        public final static Property type = new Property(6, Long.class, "type", false, TYPE);
        public final static Property text = new Property(7, String.class, "text", false, TEXT);
        public final static Property textColor = new Property(8, String.class, "textColor", false, TEXTCOLOR);
        public final static Property netAddress = new Property(9, String.class, "netAddress", false, NETADDRESS);
        public final static Property myAddress = new Property(10, String.class, "myAddress", false, MYADDRESS);
        public final static Property chishengNetAddress = new Property(11, String.class, "chishengNetAddress", false, CHISHENGNETADDRESS);
        public final static Property myVoiceTime = new Property(12, Long.class, "myVoiceTime", false, MYVOICETIME);
        public final static Property myNum = new Property(13, Long.class, "myNum", false, MYNUM);
        public final static Property state = new Property(14, String.class, "state", false, STATE);
        public final static Property hw_quiz_id = new Property(15, Long.class, "hw_quiz_id", false, HW_QUIZ_ID);
        public final static Property answer = new Property(16, String.class, "answer", false, ANSWER);

    }

    @Override
    protected BestSentenceBean readEntity(Cursor cursor, int offset) {
        int i = 1;
        return new BestSentenceBean(
                cursor.isNull(offset) ? null : cursor.getLong(offset),
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getString(offset + i++),//
                cursor.getString(offset + i++),//
                cursor.getString(offset + i++),//
                cursor.getString(offset + i++),//
                cursor.getString(offset + i++),//
                cursor.getLong(offset + i++),//
                cursor.getLong(offset + i++),//
                Boolean.valueOf(cursor.getString(offset + i++)),
                cursor.getLong(offset + i++),
                cursor.getString(offset + i));
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
//        return cursor.isNull(offset) ? null : cursor.getLong(offset);
        return cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, BestSentenceBean entity, int offset) {
        int i = 1;
        entity.setID(cursor.getLong(offset));//0
        entity.setUserId(cursor.getLong(offset + i++));//
        entity.setClassID(cursor.getLong(offset + i++));//
        entity.setTaskID(cursor.getLong(offset + i++));
        entity.setPartID(cursor.getLong(offset + i++));//
        entity.setSentenceID(cursor.getLong(offset + i++));//
        entity.setType(cursor.getLong(offset + i++));//
        entity.setText(cursor.getString(offset + i++));//
        entity.setTextColor(cursor.getString(offset + i++));//
        entity.setNetAddress(cursor.getString(offset + i++));//
        entity.setMyAddress(cursor.getString(offset + i++));//
        entity.setChishengNetAddress(cursor.getString(offset + i++));//
        entity.setMyVoiceTime(cursor.getLong(offset + i++));//
        entity.setMyNum(cursor.getLong(offset + i++));//
        entity.setState(Boolean.valueOf(cursor.getString(offset + i++)));//
        entity.setHw_quiz_id(cursor.getLong(offset + i++));
        entity.setAnswer(cursor.getString(offset + i));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, BestSentenceBean entity) {
        stmt.clearBindings();
        int i = 0;
        Long id = entity.getID();
        i++;
        if (id != null)
            stmt.bindLong(i, id);

        long userId = entity.getUserId();
        i++;
        if (userId != 0)
            stmt.bindLong(i, userId);

        long classID = entity.getClassID();
        i++;
        if (classID != 0)
            stmt.bindLong(i, classID);

        long taskID = entity.getTaskID();
        i++;
        if (taskID != 0)
            stmt.bindLong(i, taskID);

        long partID = entity.getPartID();
        i++;
        if (partID != 0)
            stmt.bindLong(i, partID);

        long sentenceID = entity.getSentenceID();
        i++;
        if (sentenceID != 0)
            stmt.bindLong(i, sentenceID);

        long type = entity.getType();
        i++;
        if (type != 0)
            stmt.bindLong(i, type);

        String text = entity.getText();
        i++;
        if (text != null)
            stmt.bindString(i, text);

        String textColor = entity.getTextColor();
        i++;
        if (textColor != null)
            stmt.bindString(i, textColor);

        String netAddress = entity.getNetAddress();
        i++;
        if (netAddress != null)
            stmt.bindString(i, netAddress);

        String myAddress = entity.getMyAddress();
        i++;
        if (myAddress != null)
            stmt.bindString(i, myAddress);

        String chishengNetAddress = entity.getChishengNetAddress();
        i++;
        if (chishengNetAddress != null)
            stmt.bindString(i, chishengNetAddress);

        long myVoiceTime = entity.getMyVoiceTime();
        i++;
        if (myVoiceTime != 0)
            stmt.bindLong(i, myVoiceTime);

        long myNum = entity.getMyNum();
        i++;
        if (myNum != 0)
            stmt.bindLong(i, myNum);

        i++;
        stmt.bindString(i, String.valueOf(entity.isState()));

        long hw_quiz_id = entity.getHw_quiz_id();
        i++;
        if (hw_quiz_id != 0)
            stmt.bindLong(i, hw_quiz_id);

        String answer = entity.getAnswer();
        i++;
        if (answer != null)
            stmt.bindString(i, answer);

    }

    @Override
    protected Long updateKeyAfterInsert(BestSentenceBean entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(BestSentenceBean entity) {
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
     * private long myNum;//我的分数
     * private boolean state;//否超过上次的分数
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + TABLENAME + " (" +
                _id + " INTEGER PRIMARY KEY ," + // 0: id  唯一键
                USERID + " INTEGER," + //
                CLASSID + " INTEGER," + //
                TASKID + " INTEGER," + //
                PARTID + " INTEGER," + //
                SENTENCEID + " INTEGER," + //
                TYPE + " INTEGER," + //
                TEXT + " TEXT," + //
                TEXTCOLOR + " TEXT," + //
                NETADDRESS + " TEXT," + //
                MYADDRESS + " TEXT," + //
                CHISHENGNETADDRESS + " TEXT," + //
                MYVOICETIME + " INTEGER," + //
                MYNUM + " INTEGER," + //
                STATE + " TEXT," +
                HW_QUIZ_ID + " INTEGER," + //
                ANSWER + " TEXT);"); //

    }


}
