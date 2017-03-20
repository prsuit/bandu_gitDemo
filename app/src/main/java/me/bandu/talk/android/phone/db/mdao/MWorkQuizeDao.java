package me.bandu.talk.android.phone.db.mdao;

import android.content.Context;

import com.DFHT.utils.UIUtils;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.dao.WorkQuize;
import me.bandu.talk.android.phone.dao.WorkQuizeDao;
import me.bandu.talk.android.phone.db.DaoHelperInterface;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.dao.DownloadBeanDao;
import me.bandu.talk.android.phone.db.dao.PartBeanDao;

/**
 * Created by Administrator on 2016/5/19.
 */
public class MWorkQuizeDao  implements DaoHelperInterface {
    private WorkQuizeDao workQuizeDao;
    public MWorkQuizeDao(){
        this.workQuizeDao = DaoUtils.getInstance().getDaoSession().getWorkQuizeDao();
    }
    @Override
    public <T> void addData(T t) {
        WorkQuize workQuize = (WorkQuize) t;
        workQuizeDao.insertOrReplace(workQuize);
    }

    @Override
    public void deleteData(long id) {
        if (hasKey(id))
            workQuizeDao.deleteByKey(id);
    }

    @Override
    public <T> void deleteList(List<T> list) {
        for (int i = 0;i<list.size();i++){
            if (list.size() > i){
                WorkQuize bean = (WorkQuize) list.get(i);
                workQuizeDao.delete(bean);
            }
        }
    }

    @Override
    public <T> T getDataById(long id) {
        return (T)workQuizeDao.load(id);
    }

    @Override
    public  List<WorkQuize> getAllData() {
        return workQuizeDao.loadAll();
    }

    @Override
    public boolean hasKey(long id) {
        if(workQuizeDao == null) {
            return false;
        }

        QueryBuilder<WorkQuize> qb = workQuizeDao.queryBuilder();
        qb.where(WorkQuizeDao.Properties.Wq_id.eq("" + id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        QueryBuilder<WorkQuize> qb = workQuizeDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        workQuizeDao.deleteAll();
    }

    public WorkQuize getByWorkAndQuize(long workId,long quizeId){
        QueryBuilder<WorkQuize> qb = workQuizeDao.queryBuilder();
        qb.where(WorkQuizeDao.Properties.Stu_job_id.eq("" + workId) ,WorkQuizeDao.Properties.Quiz_id.eq("" + quizeId));
        Query<WorkQuize> query = qb.build();
        List<WorkQuize> wqs = query.list();
        if (wqs.size() == 1)
            return wqs.get(0);
        else if (wqs.size() <= 0)
            return null;
        else if (wqs.size() > 1)
            UIUtils.showToastSafe("得到好多个");
            return null;
    }

    public void saveWrokQuize(long workId,long quizeId){
        WorkQuize workQuize = getByWorkAndQuize(workId,quizeId);
        if (workQuize == null){
            workQuize = new WorkQuize();
            workQuize.setStu_job_id(workId);
            workQuize.setQuiz_id(quizeId);
            addData(workQuize);
        }
    }

    public List<WorkQuize> getByWorkId(long workId){
        QueryBuilder<WorkQuize> qb = workQuizeDao.queryBuilder();
        qb.where(WorkQuizeDao.Properties.Stu_job_id.eq("" + workId));
        Query<WorkQuize> query = qb.build();
        List<WorkQuize> wqs = query.list();
        return wqs;
    }
}
