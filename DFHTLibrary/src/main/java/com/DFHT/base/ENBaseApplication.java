package com.DFHT.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.DFHT.ENGlobalParams;
import com.DFHT.utils.LogUtils;


/**
 * Created by kiera1 on 15/9/22.
 */
public abstract class ENBaseApplication extends Application {

    /**
     * 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了
     */
    protected static Context mInstance;
    /**
     * 主线程ID
     */
    protected static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    protected static Thread mMainThread;
    /**
     * 主线程Handler
     */
    protected static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    protected static Looper mMainLooper;

    public static ENBaseApplication getInstance() {
        return (ENBaseApplication) mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ENGlobalParams.applicationContext = this;
        //TODO Log输出级别 上线需要修改
        ENGlobalParams.Debuggable = LogUtils.LEVEL_ERROR;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        super.onCreate();
    }

    /**
     * 获取应用的上下文
     *
     * @return 上下文
     */
    public static Context getApplication() {
        return mInstance;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looperf
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
