package me.bandu.talk.android.phone.db.mdao;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.DaoHelperInterface;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.dao.CentenceBeanDao;
import me.bandu.talk.android.phone.db.dao.LessonBeanDao;
import me.bandu.talk.android.phone.db.dao.PartBeanDao;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  17:06
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MPartDao implements DaoHelperInterface {
    private Context context;
    private PartBeanDao partDao;
    public MPartDao(Context context) {
       this.context = context;
       this.partDao = (PartBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.part);
   }

    @Override
    public <T> void addData(T bean) {
        partDao.insertOrReplace((PartBean) bean);
    }

    @Override
    public void deleteData(long partid) {
        partDao.deleteByKey(partid);
    }

    @Override
    public <T> void deleteList(List<T> beans) {
        for (int i = 0;i<beans.size();i++){
            if (beans.size() > i){
                PartBean bean = (PartBean) beans.get(i);
                partDao.delete(bean);
            }
        }
   }

   @Override
    public <T> T getDataById(long id) {
        return (T) partDao.load(id);
    }

    @Override
    public List getAllData() {
        return partDao.loadAll();
    }

    @Override
    public boolean hasKey(long id) {
       if(partDao == null) {
           return false;
        }

        QueryBuilder<PartBean> qb = partDao.queryBuilder();
        qb.where(PartBeanDao.Properties.Part_id.eq("" + id));
       long count = qb.buildCount().count();
       return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        QueryBuilder<PartBean> qb = partDao.queryBuilder();
        return qb.buildCount().count();
    }

    public List<PartBean> getPartsByLessonid(long lessonid){
        QueryBuilder<PartBean> qb = partDao.queryBuilder();
        qb.where(PartBeanDao.Properties.Lesson_id.eq("" + lessonid));
        Query<PartBean> query = qb.build();
        List<PartBean> parts = query.list();
        return parts;
    }

   @Override
    public void deleteAll() {
        partDao.getDatabase().beginTransaction();
        partDao.deleteAll();
    }
}
