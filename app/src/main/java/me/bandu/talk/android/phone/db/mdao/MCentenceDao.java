package me.bandu.talk.android.phone.db.mdao;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.DaoHelperInterface;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;
import me.bandu.talk.android.phone.db.dao.CentenceBeanDao;
import me.bandu.talk.android.phone.db.dao.DaoSession;
import me.bandu.talk.android.phone.db.dao.LessonBeanDao;
import me.bandu.talk.android.phone.db.dao.PartBeanDao;
import me.bandu.talk.android.phone.db.dao.UnitBeanDao;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  17:06
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MCentenceDao implements DaoHelperInterface {
    private Context context;
    private CentenceBeanDao centenceDao;
    private UnitBeanDao unitDao;
    private PartBeanDao partDao;
    private LessonBeanDao lessonDao;
    public MCentenceDao(Context context) {
        this.context = context;
        this.centenceDao = (CentenceBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.centence);
        this.unitDao = (UnitBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.unit);
        this.partDao = (PartBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.part);
        this.lessonDao = (LessonBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.lesson);
    }

    @Override
    public <T> void addData(final T bean) {
        DaoSession daoSession = DaoFactory.getInstence(context).getSesson();
        daoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                final CentenceBean centenceBean = (CentenceBean) bean;
                final PartBean part = centenceBean.getPartBean();
                final LessonBean lesson = part.getLessonBean();
                final UnitBean unit = lesson.getUnitBean();
                unitDao.insertOrReplace(unit);
                lessonDao.insertOrReplace(lesson);
                partDao.insertOrReplace(part);
                centenceDao.insertOrReplace(centenceBean);
            }
        });
    }

    public <T> void addListData(List<T> list){
        final List<CentenceBean> centences = (List<CentenceBean>) list;
        for (int i = 0;i<centences.size();i++){
            final CentenceBean centenceBean = centences.get(i);
            centenceBean.getPartBean().getLessonBean().getUnitBean().setInsert_time(System.currentTimeMillis());
            addData(centenceBean);
        }
    }

    @Override
    public void deleteData(long id) {
        centenceDao.deleteByKey(id);
    }


    @Override
    public <T> void deleteList(final List<T> beans) {
                for (int i = 0;i< beans.size();i++){
                    if (beans.size() > i){
                        CentenceBean bean = (CentenceBean) beans.get(i);
                        centenceDao.delete(bean);
                    }
                }
    }

    @Override
    public <T> T getDataById(long id) {
        return (T) centenceDao.load(id);
    }

    @Override
    public List getAllData() {
        return centenceDao.loadAll();
    }

    @Override
    public boolean hasKey(long id) {
        if(centenceDao == null) {
            return false;
        }
        QueryBuilder<CentenceBean> qb = centenceDao.queryBuilder();
        qb.where(CentenceBeanDao.Properties.Centence_id.eq("" + id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        QueryBuilder<CentenceBean> qb = centenceDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        centenceDao.getDatabase().beginTransaction();;
        centenceDao.deleteAll();
    }

    public List<CentenceBean> getCentencesByPartId(long partid){
        QueryBuilder<CentenceBean> qb = centenceDao.queryBuilder();
        qb.where(CentenceBeanDao.Properties.Part_id.eq("" + partid));
        Query<CentenceBean> query = qb.build();
        List<CentenceBean> centences = query.list();
        return centences;
    }

    public boolean isDone(long centenceid){
        CentenceBean centenceBean = getDataById(centenceid);
        return centenceBean.getDone();
    }
}
