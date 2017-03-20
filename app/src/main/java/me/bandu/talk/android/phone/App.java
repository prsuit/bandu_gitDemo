package me.bandu.talk.android.phone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.DFHT.ENGlobalParams;
import com.DFHT.base.ENBaseApplication;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.smtt.sdk.TbsDownloader;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;

import de.greenrobot.event.EventBus;
import me.bandu.talk.android.phone.greendao.communal.DaoMaster;
import me.bandu.talk.android.phone.greendao.communal.Session;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/17 15:18
 * 类描述：Application 类.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class App extends ENBaseApplication {

    public static Bundle ComRecData = new Bundle(); // 组合录音数据
    public static Session daoSession;
    public static boolean isMigrationCompletionDB = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        x.Ext.init(this);
//        x.Ext.setDebug(false);
//        PushAgent mPushAgent = PushAgent.getInstance(this);
        //       mPushAgent.enable();
        //       mPushAgent.setDebugMode(true);
        //创建腾讯x5
        TbsDownloader.needDownload(this.getApplicationContext(), false);
        if(GlobalParams.debug || "http://test.new.api.bandu.in/".equals(ConstantValue.BASE_URL)){
            ENGlobalParams.showToast = true;
        }
        initImageLoader(getApplicationContext());
        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public void dealWithNotificationMessage(final Context context, final UMessage msg) {
                super.dealWithNotificationMessage(context,msg);
                EventBus.getDefault().post("msg");
            }
        };
        PushAgent.getInstance(this).setMessageHandler(messageHandler);
        getDaoSession();
    }

    /**
     * 初始化数据库
     */
    public void getDaoSession() {
        SQLiteDatabase db = new DaoMaster.DevOpenHelper(this.getApplicationContext(), ConstantValue.DBNAME, null).getWritableDatabase();//获取可写的数据库
        DaoMaster daoMaster = new DaoMaster(db);//在数据库中建表
        if(daoSession == null)
            daoSession = daoMaster.newSession();
    }

    public void initImageLoader(Context context) {
        MemoryCache memoryCache = getMemoryCache();
        ImageLoaderConfiguration config = initConfig(context, memoryCache);
        ImageLoader.getInstance().init(config);
    }


    private ImageLoaderConfiguration initConfig(Context context, MemoryCache memoryCache) {
        ImageLoaderConfiguration config = null;
        // 创建存放图片的磁盘目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "bandu/tt/cache");
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().build();
        try {
            config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(defaultOptions).threadPriority(Thread.NORM_PRIORITY - 2)
                    // 线程的优先级
                    .memoryCache(memoryCache)
                            // 设置缓存的大小
                            // 文件高速缓存到本地，参数1：磁盘地址。 参数2：加密算法。 参数3：高速缓存的最大大小 TODO
                    .diskCache(new LruDiskCache(cacheDir, new Md5FileNameGenerator(), 10 * 1024 * 1024)).denyCacheImageMultipleSizesInMemory().threadPoolSize(5)// 线程池线程个数，避免线程过多找出oom
                    .tasksProcessingOrder(QueueProcessingType.LIFO)// 任务的处理顺序
                            // 后进先出法
                    .writeDebugLogs()// 写调试日志
                    .build();
        }
        catch (Exception e) {
            config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(defaultOptions).threadPriority(Thread.NORM_PRIORITY - 2)// 线程的优先级
                    .memoryCache(memoryCache)// 设置缓存的大小
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())// TODO
                            // 不同的地方
                    .denyCacheImageMultipleSizesInMemory().threadPoolSize(5)// 线程池线程个数，避免线程过多找出oom
                    .tasksProcessingOrder(QueueProcessingType.LIFO)// 任务的处理顺序
                            // 后进先出法
                    .writeDebugLogs()// 写调试日志
                    .build();
            e.printStackTrace();
        }
        return config;
    }

    /**
     * 获取内存，取得内存的十分之一当做ImageLoader的内存使用
     *
     * @return MemoryCache
     */
    private MemoryCache getMemoryCache() {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 10);// 获取内存的十分之一
        MemoryCache memoryCache = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {// 在android
            // api
            // 9之前
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }
        return memoryCache;
    }

}
