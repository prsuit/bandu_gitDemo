package me.bandu.talk.android.phone.db.mdao;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import me.bandu.talk.android.phone.db.DaoFactory;
import me.bandu.talk.android.phone.db.DaoHelperInterface;
import me.bandu.talk.android.phone.db.bean.CentenceBean;
import me.bandu.talk.android.phone.db.bean.DownloadBean;
import me.bandu.talk.android.phone.db.bean.LessonBean;
import me.bandu.talk.android.phone.db.bean.PartBean;
import me.bandu.talk.android.phone.db.bean.UnitBean;
import me.bandu.talk.android.phone.db.dao.DownloadBeanDao;
import me.bandu.talk.android.phone.db.dao.UnitBeanDao;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  17:06
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MDownloadDao implements DaoHelperInterface {
    private Context context;
    private DownloadBeanDao downloadBeanDao;
    public MDownloadDao(Context context) {
        this.context = context;
        this.downloadBeanDao = (DownloadBeanDao) DaoFactory.getInstence(context).getDao(DaoFactory.download);
    }

    @Override
    public <T> void addData(T bean) {
        DownloadBean unitBean = (DownloadBean) bean;
        downloadBeanDao.insertOrReplace(unitBean);
    }

    @Override
    public void deleteData(long unitid) {
        if (hasKey(unitid))
            downloadBeanDao.deleteByKey(unitid);
    }

    @Override
    public <T> void deleteList(List<T> beans) {
        for (int i = 0;i<beans.size();i++){
            if (beans.size() > i){
                DownloadBean bean = (DownloadBean) beans.get(i);
                downloadBeanDao.delete(bean);
            }
        }
    }

    @Override
    public <T> T getDataById(long id) {
        return (T)downloadBeanDao.load(id);
    }

    @Override
    public List<DownloadBean> getAllData() {
        return downloadBeanDao.loadAll();
    }

    @Override
    public boolean hasKey(long id) {
        if(downloadBeanDao == null) {
            return false;
        }

        QueryBuilder<DownloadBean> qb = downloadBeanDao.queryBuilder();
        qb.where(DownloadBeanDao.Properties.Download_id.eq("" + id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        QueryBuilder<DownloadBean> qb = downloadBeanDao.queryBuilder();
        return qb.buildCount().count();
    }


    @Override
    public void deleteAll() {
        downloadBeanDao.deleteAll();
    }
}
