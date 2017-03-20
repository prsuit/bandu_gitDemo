package me.bandu.talk.android.phone.db.mdao;

import android.content.Context;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.DaoHelperInterface;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;
import me.bandu.talk.android.phone.db.dao.PartBeanDao;
import me.bandu.talk.android.phone.db.dao.UnitBeanDao;
import me.bandu.talk.android.phone.utils.FileUtil;
import me.bandu.talk.android.phone.utils.StringUtil;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  17:06
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MUnitDao implements DaoHelperInterface {
    private Context context;
    private UnitBeanDao unitDao;
    public MUnitDao(Context context) {
        this.context = context;
        this.unitDao = (UnitBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.unit);
    }

    @Override
    public <T> void addData(T bean) {
        UnitBean unitBean = (UnitBean) bean;
        unitDao.insertOrReplace(unitBean);
    }

    @Override
    public void deleteData(long unitid) {
        UnitBean unitBean = getDataById(unitid);
        MLessonDao lessonDao = new MLessonDao(context);
        MPartDao partDao = new MPartDao(context);
        MCentenceDao centenceDao = new MCentenceDao(context);
        List<LessonBean> lessons = lessonDao.getLessonsByUnitid(unitid);
        for (int i = 0;i<lessons.size();i++){
            LessonBean lesson = lessons.get(i);
            List<PartBean> parts = partDao.getPartsByLessonid(lesson.getLesson_id());
            for (int j = 0;j<parts.size();j++){
                PartBean part = parts.get(j);
                List<CentenceBean> centences = centenceDao.getCentencesByPartId(part.getPart_id());
                for (int k = 0;k<centences.size();k++){
                    CentenceBean centence = centences.get(k);
                    centenceDao.deleteData(centence.getCentence_id());
                }
                partDao.deleteData(part.getPart_id());
            }
            lessonDao.deleteData(lesson.getLesson_id());
        }
        unitDao.deleteByKey(unitid);
    }

    @Override
    public <T> void deleteList(List<T> beans) {
        for (int i = 0;i<beans.size();i++){
            if (beans.size() > i){
                UnitBean bean = (UnitBean) beans.get(i);
                deleteData(bean.getUnit_id());
            }
        }
    }

    @Override
    public <T> T getDataById(long id) {
        return (T)unitDao.load(id);
    }

    @Override
    public List<UnitBean> getAllData() {
        List<UnitBean> unitBeens = unitDao.loadAll();
        Collections.sort(unitBeens, new Comparator<UnitBean>() {
            @Override
            public int compare(UnitBean lhs, UnitBean rhs) {
                if (rhs == null)
                    return -1;
                if (lhs == null)
                    return -1;
                return (int) -(StringUtil.getLongNotnull(lhs.getInsert_time()) - StringUtil.getLongNotnull(rhs.getInsert_time()));
            }
        });
        return unitBeens;
    }

    @Override
    public boolean hasKey(long id) {
        if(unitDao == null) {
            return false;
        }

        QueryBuilder<UnitBean> qb = unitDao.queryBuilder();
        qb.where(UnitBeanDao.Properties.Unit_id.eq("" + id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        QueryBuilder<UnitBean> qb = unitDao.queryBuilder();
        return qb.buildCount().count();
    }

    public Integer[] getProgress(long unitid){
        MLessonDao lessonDao = new MLessonDao(context);
        List<LessonBean> lessons = lessonDao.getLessonsByUnitid(unitid);
        int done = 0;
        int totle = 0;
        for (int i = 0;i<lessons.size();i++){
            LessonBean lesson = lessons.get(i);
            MPartDao mPartDao = new MPartDao(context);
            List<PartBean> parts = mPartDao.getPartsByLessonid(lesson.getLesson_id());
            for (int j = 0;j<parts.size();j++){
                PartBean part = parts.get(j);
                MCentenceDao centenceDao = new MCentenceDao(context);
                List<CentenceBean> centences = centenceDao.getCentencesByPartId(part.getPart_id());
                for (int k = 0;k<centences.size();k++){
                    CentenceBean centence = centences.get(k);
                    if (centence.getDone() != null && centence.getDone())
                        done++;
                    totle++;
                }
            }
        }
        return new Integer[]{done,totle};
    }

    @Override
    public void deleteAll() {
        unitDao.deleteAll();
    }
}
