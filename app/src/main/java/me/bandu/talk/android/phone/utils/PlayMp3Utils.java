package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.ProgressBar;

import com.DFHT.utils.LogUtils;
import com.DFHT.utils.UIUtils;

import me.bandu.talk.android.phone.view.PlayMp3View;
import me.bandu.talk.android.phone.view.impl.onMusicOver;

/**
 * 创建者：Mckiera
 * <p>时间：2016-06-14  11:25
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class PlayMp3Utils {
    private MediaPlayer mp;

    private Context context;

    private ProgressBar pbPercent;

    private int currPosition;

    // private ArrayList<String> playTime ;

    private int progress = 0;

    private Handler handler;

    //  private SeekBarListener listener;

    public void clean() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                pbPercent.setProgress(0);
            }
        });
        cleanNoPro();
    }

    public void cleanNoPro() {
        //  listener = null;
        progress = 0;
//        playTime = null;
        currPosition = 0;
        try {
            if (mp != null) {
                if (mp.isPlaying())
                    mp.stop();
                mp.release();
                mp = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mp != null)
                mp.release();
            mp = null;
        }
        handler.removeCallbacks(runnable);
    }

    public PlayMp3Utils(Context context, ProgressBar pbPercent) {
        this.context = context;
        handler = UIUtils.getHandler();
        this.pbPercent = pbPercent;
    }

    /* public void setData(ArrayList<String> playTime){
         this.playTime = playTime;
     }*/
    //更新进度条
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (mp.isPlaying()) {
                    pbPercent.setProgress(mp.getCurrentPosition());
                    //设置pbPercent的进度
//                    pbPercent.setProgress(getSeekBarProgress() + mp.getCurrentPosition());
/*                    pbPercent.setProgress(pbPercent.getProgress() +  (int)(pbPercent.getMax() / 100 * 0.9));
                    LogUtils.e("总进度:" + pbPercent.getMax());
                    LogUtils.e("当前度:" + pbPercent.getProgress());*/
                }
            } catch (Exception e) {
                //
            }
            handler.postDelayed(runnable, 300);
        }
    };

    public void init(String path, int currPosition, onMusicOver over) {
        init(path, currPosition, false, 0, over);
    }

    /**
     * 初始化方法
     */
    public void init(String path, int currPosition, boolean isNeedProgress, int progress, final onMusicOver over) {
        this.currPosition = currPosition;
        try {
            if (mp != null) {
                mp.release();
                mp = null;
            }
            mp = new MediaPlayer();
            mp.setDataSource(path);
            mp.prepare();
            if (isNeedProgress)
                mp.seekTo(progress);
            pbPercent.setMax(mp.getDuration());
            pbPercent.setProgress(0);
//            if(listener == null) {
//                listener = new SeekBarListener();
//               // pbPercent.setOnSeekBarChangeListener(listener);
//            }
            mp.start();
            handler.post(runnable);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    over.onMusicOver();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

            clean();

            Intent intent = new Intent();  //Itent就是我们要发送的内容
            intent.putExtra("MSG", PlayMp3View.Mp3Broad.ERR_MSG);
            intent.setAction(PlayMp3View.Mp3Broad.ERR_ACTION);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
            context.sendBroadcast(intent);   //发送广播
            UIUtils.showToastSafe("语音解析错误");
            LogUtils.e("错误的语音：" + path);
        }

    }

    /**
     * 对外提供一个暂停的方法
     */
    public void pause() {

        try {
            mp.pause();
        } catch (Exception e) {
            //do nothing
        }
    }

    /**
     * 对外提供一个继续的方法
     */
    public void resume() {
        try {
            if (mp != null)
                mp.start();
        } catch (IllegalStateException e) {
            stop();
        }
    }

    public void stop() {
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }


}
