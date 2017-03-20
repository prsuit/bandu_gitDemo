package me.bandu.talk.android.phone.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import me.bandu.talk.android.phone.async.NewDownloadAsyncTask;

/**
 * 创建者：高楠
 * 时间：on 2016/1/7
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NewDownLoadService extends Service {
    private String url;

    @Override
    public void onCreate(){
        super.onCreate ();
    }

    @Override
    public void onDestroy(){
        super.onDestroy ();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (intent!=null){
            Bundle bundle = intent.getExtras ();
            if (bundle != null) {
                url = bundle.getString("url");
                addTask ();
            }
        }
        return super.onStartCommand (intent, flags, startId);
    }

    private void addTask(){
        if (url!=null){
            NewDownloadAsyncTask downloadAsyncTask = new NewDownloadAsyncTask(url);
            downloadAsyncTask.execute ();
        }
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }
}
