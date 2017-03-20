package me.bandu.talk.android.phone.greendao.communal;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import me.bandu.talk.android.phone.greendao.bean.BestSentenceBean;
import me.bandu.talk.android.phone.greendao.bean.ClassTabelBean;
import me.bandu.talk.android.phone.greendao.bean.CurrentSentenceBean;
import me.bandu.talk.android.phone.greendao.bean.TaskDirectoryBean;
import me.bandu.talk.android.phone.greendao.bean.TaskListBean;
import me.bandu.talk.android.phone.greendao.dao.BestSentenceTabelDao;
import me.bandu.talk.android.phone.greendao.dao.ClassTabelDao;
import me.bandu.talk.android.phone.greendao.dao.CurrentSentenceTabelDao;
import me.bandu.talk.android.phone.greendao.dao.TaskDirectoryTabelDao;
import me.bandu.talk.android.phone.greendao.dao.TaskListTabelDao;

/**
 * 创建者：wanglei
 * 时间：16/4/20  15:19
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Session extends AbstractDaoSession {

    private final DaoConfig taskListConfig;
    private final DaoConfig taskDirectoryConfig;
    private final DaoConfig bestSentenceConfig;
    private final DaoConfig currentSentenceConfig;
    private final DaoConfig classTabelConfig;

    private final TaskListTabelDao taskListTabelDao;
    private final TaskDirectoryTabelDao taskDirectoryTabelDao;
    private final BestSentenceTabelDao bestSentenceTabelDao;
    private final CurrentSentenceTabelDao currentSentenceTabelDao;
    private final ClassTabelDao classTabelDao;

    public Session(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        taskListConfig = daoConfigMap.get(TaskListTabelDao.class).clone();
        taskListTabelDao = new TaskListTabelDao(taskListConfig, this);
        taskListConfig.initIdentityScope(type);
        registerDao(TaskListBean.class, taskListTabelDao);

        taskDirectoryConfig = daoConfigMap.get(TaskDirectoryTabelDao.class).clone();
        taskDirectoryTabelDao = new TaskDirectoryTabelDao(taskDirectoryConfig, this);
        taskDirectoryConfig.initIdentityScope(type);
        registerDao(TaskDirectoryBean.class, taskDirectoryTabelDao);

        bestSentenceConfig = daoConfigMap.get(BestSentenceTabelDao.class).clone();
        bestSentenceTabelDao = new BestSentenceTabelDao(bestSentenceConfig, this);
        bestSentenceConfig.initIdentityScope(type);
        registerDao(BestSentenceBean.class, bestSentenceTabelDao);

        currentSentenceConfig = daoConfigMap.get(CurrentSentenceTabelDao.class).clone();
        currentSentenceTabelDao = new CurrentSentenceTabelDao(currentSentenceConfig, this);
        currentSentenceConfig.initIdentityScope(type);
        registerDao(CurrentSentenceBean.class, currentSentenceTabelDao);

        classTabelConfig = daoConfigMap.get(ClassTabelDao.class).clone();
        classTabelDao = new ClassTabelDao(classTabelConfig, this);
        classTabelConfig.initIdentityScope(type);
        registerDao(ClassTabelBean.class, classTabelDao);
    }

    public void clear() {
        taskListConfig.getIdentityScope().clear();
        taskDirectoryConfig.getIdentityScope().clear();
        bestSentenceConfig.getIdentityScope().clear();
        classTabelConfig.getIdentityScope().clear();
    }

    public TaskListTabelDao getTaskListTabelDao() {
        return taskListTabelDao;
    }

    public TaskDirectoryTabelDao getTaskDirectoryTabelDao() {
        return taskDirectoryTabelDao;
    }

    public BestSentenceTabelDao getBestSentenceTabelDao() {
        return bestSentenceTabelDao;
    }

    public CurrentSentenceTabelDao getCurrentSentenceTabelDao() {
        return currentSentenceTabelDao;
    }

    public ClassTabelDao getClassTabelDao() {
        return classTabelDao;
    }
}
