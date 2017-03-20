package me.bandu.talk.android.phone.db;

import android.content.Context;


import com.DFHT.utils.LogUtils;

import de.greenrobot.dao.AbstractDao;
import me.bandu.talk.android.phone.GlobalParams;
import me.bandu.talk.android.phone.db.dao.DaoMaster;
import me.bandu.talk.android.phone.db.dao.DaoSession;
import me.bandu.talk.android.phone.utils.PreferencesUtils;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  16:56
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DaoFactory {
    public static final int centence = 0;
    public static final int lesson = 1;
    public static final int part = 2;
    public static final int unit = 3;
    public static final int download = 4;
    private DaoSession session;
    private DaoMaster.DevOpenHelper helper;
    private static DaoFactory instence;
    private Context context;

    private DaoFactory(){}
    private DaoFactory(Context context){
        this.context = context;
    }

    public static synchronized DaoFactory getInstence(Context context){
        if (instence == null){
            instence = new DaoFactory(context);
        }
        return instence;
    }

    public AbstractDao getDao(int type){
        AbstractDao dao = null;
        if (session == null){
            initSession(context);
        }
        switch (type){
            case centence:
                dao = session.getCentenceBeanDao();
                break;
            case lesson:
                dao = session.getLessonBeanDao();
                break;
            case part:
                dao = session.getPartBeanDao();
                break;
            case unit:
                dao = session.getUnitBeanDao();
                break;
            case download:
                dao = session.getDownloadBeanDao();
                break;
        }
        return dao;
    }

    private void initSession(Context context) {
        try {
            String dbname = GlobalParams.userInfo == null || GlobalParams.userInfo.getName() == null ? "default-db" : StringUtil.getShowText(PreferencesUtils.getUid()) + "-db";
            LogUtils.i("dbname-" + dbname);
            if (helper == null)
                helper = new DaoMaster.DevOpenHelper(context, dbname, null);
            DaoMaster master = new DaoMaster(helper.getWritableDatabase());
            // master.notify();
            session = master.newSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestory(){
        if (helper != null){
            helper.close();
            helper = null;
        }
        if (session != null){
            session.clear();
            session = null;
        }
        instence = null;
    }

    public void reset(Context context){
        instence = new DaoFactory(context);
    }

    public DaoSession getSesson(){
        if (session == null){
            initSession(context);
        }
        return session;
    }
}
