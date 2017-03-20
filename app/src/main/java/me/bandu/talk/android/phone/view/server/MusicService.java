package me.bandu.talk.android.phone.view.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.SeekBar;


import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import java.io.IOException;
import java.util.ArrayList;

import me.bandu.talk.android.phone.view.PlayMp3View;
import me.bandu.talk.android.phone.view.impl.MMusic;
import me.bandu.talk.android.phone.view.impl.onMusicOver;

/**
 * Created by kiera1 on 16/6/7.
 */
public class MusicService extends Service {

    private volatile MediaPlayer mp;
    private String path;
    //是否开始唱了
    private boolean f = false;
    private ArrayList<String> playTime ;
    private int currPosition;
    //是否需要调节进度
    private boolean isNeedProgress = false;
    private int progress = 0;

    private SeekBarListener listener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //开启播放服务后, 需要给一个  播放的路径
        path = intent.getStringExtra("path");
        playTime = intent.getStringArrayListExtra("playTime");
        currPosition = intent.getIntExtra("currPosition",0);
        isNeedProgress = intent.getBooleanExtra("isNeedProgress", false);
        progress = intent.getIntExtra("progress",0);
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = new MediaPlayer();
    }

    /**
     * 对外提供一个暂停的方法
     */
    public void pause() {
        mp.pause();
    }

    /**
     * 对外提供一个继续的方法
     */
    public void resume() {
        mp.start();
    }

    class MyBinder extends Binder implements MMusic {

        private SeekBar seekBar;

        Handler handler = new Handler();

        //更新进度条
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mp.isPlaying()) {
                    //设置seekbar的进度
                    seekBar.setProgress(getSeekBarProgress() + mp.getCurrentPosition());
                }
                handler.postDelayed(runnable, 500);
            }
        };

        @Override
        public void dopause() {
            pause();
        }

        @Override
        public void doresume() {
            resume();
        }

        @Override
        public void dostart() {
            mp.start();
        }

        @Override
        public void dostop() {
            if(mp != null && mp.isPlaying()){
                mp.stop();
                mp.release();
                mp = null;
            }
        }



        @Override
        public void init(SeekBar seekBar, final onMusicOver over) {
            if(this.seekBar == null)
                this.seekBar = seekBar;
            if(mp != null){
                mp = null;
                mp = new MediaPlayer();
            }
            //重置一下mp
            try {
                mp.reset();
                mp.setDataSource(path);
                mp.prepare();
                if(isNeedProgress){
                    mp.seekTo(progress);
                    isNeedProgress = !isNeedProgress;
                }

//                seekBar.setMax(mp.getDuration());//给seekbar设置长度
                if(listener == null) {
                    listener = new SeekBarListener();
                    seekBar.setOnSeekBarChangeListener(listener);
                }


                //开始唱~~~
                mp.start();
                f = true;
                //开始更新进度条
                handler.post(runnable);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        over.onMusicOver();
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
                Intent intent = new Intent();  //Itent就是我们要发送的内容
                intent.putExtra("MSG",PlayMp3View.Mp3Broad.ERR_MSG);
                intent.setAction(PlayMp3View.Mp3Broad.ERR_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                sendBroadcast(intent);   //发送广播
            }

        }

        @Override
        public void doRset() {
            try {
                mp.release();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void doRevmo() {
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * 检查
     * @param progress 当前用户调节的进度条
     * @return true 需要发送广播重新开始  false 不需要发送广播
     */
    private boolean checkProgress(int progress) {
        long allTime = 0;
        for(int i = 0; i<playTime.size(); i++ ){
            allTime += Long.valueOf(playTime.get(i));
            if(allTime > progress){
                if(currPosition == i)
                    return false;
                currPosition = i;
                allTime = allTime - Long.valueOf(playTime.get(i));
                this.progress =(int)(progress - allTime);
                return true;
            }
        }
        return false;
    }

    private int getSeekBarProgress() {
        int time = 0;
        for(int i =0; i< playTime.size(); i++){
            if(currPosition == i)
                break;
            time += Integer.valueOf( playTime.get(i));
        }
        return time;
    }

    public class SeekBarListener implements SeekBar.OnSeekBarChangeListener{
        private int progress;
        private boolean fromUser = false;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            LogUtils.e("被调用:"+progress + " :::  fromUser:"+fromUser);
            //用户手动调节
            if(fromUser) {
                if(mp != null && mp.isPlaying())
                    mp.pause();
                this.fromUser = fromUser;
                this.progress = progress;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if(mp != null && mp.isPlaying())
                mp.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (fromUser) {
                fromUser = false;
                //计算当前进度  是否需要发送广播
                boolean needBroad = checkProgress(progress);
                if (!needBroad) {
                    try {
                        mp.seekTo(getSeekBarProgress() + progress);
                        mp.start();
                    } catch (Exception e) {
                        LogUtils.i("mp === " + mp);
                        LogUtils.i("getSeekBarProgress() === " + getSeekBarProgress());
                        LogUtils.i("progress === " + progress);

                        Intent intent = new Intent();  //Itent就是我们要发送的内容
                        intent.putExtra("MSG", PlayMp3View.Mp3Broad.ERR_MSG);
                        intent.setAction(PlayMp3View.Mp3Broad.ERR_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                        sendBroadcast(intent);   //发送广播
                    }
                } else {
                    if(mp != null){
                        if(mp.isPlaying())
                            mp.stop();
                        mp.release();
                        mp = null;
                    }
                    Intent intent = new Intent();
                    intent.setAction(PlayMp3View.Mp3Broad.TO_PROGRESS_ACTION);
                    intent.putExtra("progress", MusicService.this.progress);
                    intent.putExtra("currPosition", currPosition);//计算后的位置
                    sendBroadcast(intent);
                }
            }
        }
    }
}
