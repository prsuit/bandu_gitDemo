package me.bandu.talk.android.phone.greendao.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 创建者：Mckiera
 * <p>时间：2016-07-14  13:56
 * <p>类描述：数据库队列类
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class DBQueue {
    /**
     * 保存线程的队列.
     */
    private static BlockingQueue<Thread> mThreadQueue = new PriorityBlockingQueue<>();

    private DBQueue(){
    }

    public static DBQueue dbQueue;

    public static synchronized DBQueue getDbQueue(){
        if(dbQueue == null)
            dbQueue = new DBQueue();
        return dbQueue;
    }

    public DBQueue start(){
        try {
            mThreadQueue.take().start();
            return this;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return this;
        }
    }

    public DBQueue add(Thread... threads){
        for(Thread thread : threads)
            mThreadQueue.add(thread);
        return this;
    }

}
