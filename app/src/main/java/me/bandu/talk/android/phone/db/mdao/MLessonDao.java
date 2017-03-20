package me.bandu.talk.android.phone.db.mdao;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.DaoHelperInterface;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.dao.LessonBeanDao;
import me.bandu.talk.android.phone.db.dao.UnitBeanDao;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  17:06
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MLessonDao implements DaoHelperInterface {
    private Context context;
    private LessonBeanDao lessonDao;
    private UnitBeanDao unitBeanDao;
    public MLessonDao(Context context) {
        this.context = context;
        this.lessonDao = (LessonBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.lesson);
        this.unitBeanDao = (UnitBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.unit);
    }

    @Override
    public <T> void addData(T bean) {
        lessonDao.insertOrReplace((LessonBean) bean);
    }

    @Override
    public void deleteData(long lessonid) {
        lessonDao.deleteByKey(lessonid);
    }

    @Override
    public <T> void deleteList(List<T> beans) {
        for (int i = 0;i<beans.size();i++){
            if (beans.size() > i){
                LessonBean bean = (LessonBean) beans.get(i);
                lessonDao.delete(bean);
            }
        }
    }

    public boolean addExercise(long lessonid){
        if (hasKey(lessonid)){
            LessonBean lessonBean = getDataById(lessonid);
            lessonBean.setIsadd(true);
            addData(lessonBean);
            return true;
        }
        return false;
    }

    public List<LessonBean> getLessonsByUnitid(long unitid){
        QueryBuilder<LessonBean> qb = lessonDao.queryBuilder();
        qb.where(LessonBeanDao.Properties.Unit_id.eq("" + unitid));
        Query<LessonBean> query = qb.build();
        List<LessonBean> lessonBeans = query.list();
        return lessonBeans;
    }

    @Override
    public LessonBean getDataById(long id) {
        return lessonDao.load(id);
    }

    @Override
    public List getAllData() {
        return lessonDao.loadAll();
    }

    @Override
    public boolean hasKey(long id) {
        if(lessonDao == null) {
            return false;
        }
        QueryBuilder<LessonBean> qb = lessonDao.queryBuilder();
        qb.where(LessonBeanDao.Properties.Lesson_id.eq("" + id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        QueryBuilder<LessonBean> qb = lessonDao.queryBuilder();
        return qb.buildCount().count();
    }

    public boolean isAddExercise(long id){
        if (!hasKey(id))
            return false;
        QueryBuilder<LessonBean> qb = lessonDao.queryBuilder();
        qb.where(LessonBeanDao.Properties.Lesson_id.eq("" + id),LessonBeanDao.Properties.Isadd.eq(true + ""));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public void deleteAll() {
        lessonDao.getDatabase().beginTransaction();;
        lessonDao.deleteAll();
    }
}
