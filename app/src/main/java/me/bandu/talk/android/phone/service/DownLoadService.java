//package me.bandu.talk.android.phone.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.IBinder;
//
//import me.bandu.talk.android.phone.async.DownloadAsyncTask;
//import me.bandu.talk.android.phone.utils.MediaPathUtils;
///**
// * Author Gaonan
// * 下载服务
// */
//public class DownLoadService extends Service {
//
////  private String mDownloadUrl;
//    private String[] urls;
//    private boolean justdown;
//    private int id ;
//    private String mFileName;
//
//    @Override
//    public void onCreate(){
//        super.onCreate ();
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy ();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent,int flags,int startId){
//        Bundle bundle = intent.getExtras ();
//        if (bundle != null) {
//            urls = bundle.getStringArray("list");
//            id = bundle.getInt("id");
//            justdown = bundle.getBoolean("justdown");
//            addTask (id);
//        }
//        return super.onStartCommand (intent, flags, startId);
//    }
//
//    private void addTask(int startId){
//        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask(
//                MediaPathUtils.getFileName(urls));
//        downloadAsyncTask.execute (urls, startId,justdown);
//    }
//
//    @Override
//    public IBinder onBind(Intent arg0){
//        return null;
//    }
//}
