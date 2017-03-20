package me.bandu.talk.android.phone.db;

import java.util.List;

import me.bandu.talk.android.phone.db.bean.LessonBean;

/**
 * 创建者：gaoye
 * 时间：2015/12/11  18:08
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface DaoHelperInterface {
    public <T> void addData(T t);
    public void deleteData(long id);
    public <T> void deleteList(List<T> list);
    public <T> T getDataById(long id);
    public <T> List<T> getAllData();
    public boolean hasKey(long id);
    public long getTotalCount();
    public void deleteAll();
}
