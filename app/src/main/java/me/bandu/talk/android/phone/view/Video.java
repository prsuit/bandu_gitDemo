package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.bandu.talk.android.phone.R;

/**
 * 创建者：wanglei
 * <p>时间：16/8/4  20:34
 * <p>类描述：http://blog.csdn.net/xxbs2003/article/details/8895177
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class Video extends RelativeLayout implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, MediaPlayer.OnVideoSizeChangedListener {
    private Context mContext;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private TimerTask mTimerTask;
    private Handler handleProgress;
    private TextView total_time;
    private TextView current_time;
    private SeekBar seekbar;
    private ImageView start;
    private ImageView pause;
    private ProgressBar wait;
    private ImageView background;
    private LinearLayout seekbarLayout;
    private String path;
    private int position;
    private int myProgress;
    private int bufferingPosition;
    private int num = 0;
    private boolean teadyToFinish;
    private boolean isClikPlay;
    private boolean isPlaying;
    private boolean isInitTimer;
    private boolean isSetBackground;
    public boolean isWIFI;
    private Timer mTimer;
    List<Integer> end_times;
    private Bitmap bitmap;
    private SurfaceView videoView;

    public Video(Context context) {
        super(context);
        this.mContext = context;
//        init();
    }

    public Video(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
//        init();
    }

    public Video(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        init();
    }

    public void init(PlayItem playItem) {
        this.playItem = playItem;
        View view = LayoutInflater.from(mContext).inflate(R.layout.new_vedio_layout, null);
        addView(view);
        videoView = (SurfaceView) view.findViewById(R.id.task_video_surfaceview);
//        videoView.setOnDragListener();//为此视图注册一个拖动事件侦听器回调对象
        background = (ImageView) view.findViewById(R.id.task_video_background);
        wait = (ProgressBar) view.findViewById(R.id.task_video_wait);
        //中间的开始按钮
        start = (ImageView) view.findViewById(R.id.task_video_start);
        //左下角的开始按钮
        pause = (ImageView) view.findViewById(R.id.task_video_pause);
        //进度条
        seekbar = (SeekBar) view.findViewById(R.id.task_video_seekbar);
        current_time = (TextView) view.findViewById(R.id.task_video_current_time);
        total_time = (TextView) view.findViewById(R.id.task_video_total_time);
        TextView replay = (TextView) view.findViewById(R.id.task_video_replay);//重播
        seekbarLayout = (LinearLayout) view.findViewById(R.id.task_video_container);
        surfaceHolder = videoView.getHolder();
//        bitmap = getBitmap(R.mipmap.no_voice);
//        background.setImageBitmap(bitmap);
        seekbar.setOnSeekBarChangeListener(this);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        videoView.setOnClickListener(this);
    }

    public void showackground() {
        if (background != null)
            background.setVisibility(View.VISIBLE);
    }

    public Bitmap getBitmap(int id) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true; // 设置只加载图片大小
        BitmapFactory.decodeResource(getContext().getResources(), id, opts);
        int xScale = opts.outWidth / (600 / 2);// todo 500应该是屏幕的宽
        opts.inJustDecodeBounds = false; // 设置不只加载图片大小
        opts.inSampleSize = xScale; // 设置缩放比例
        return BitmapFactory.decodeResource(getContext().getResources(), id, opts); // 按照设置加载图片(缩放)
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_video_start://中间的开始按钮
                play(0);
                break;
            case R.id.task_video_pause://左下角的开始按钮
                play(0);
                break;
            case R.id.task_video_replay://重播
                reset();
                break;
            case R.id.task_video_surfaceview://判断进度条显示
                if (seekbarLayout.isShown()) {
                    seekbarLayout.setVisibility(View.INVISIBLE);
                    setStartButShow(false);
                } else {
                    setStartButShow(true);
                    seekbarLayout.setVisibility(View.VISIBLE);
                    num = 0;
                }
                break;
        }
    }

    private void initMediaPlayer() {
//        initTimerAndHandler();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        isClikPlay = false;
    }

    /**
     * 播放
     */
    public void play(int positio) {
        ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (!isWIFI && info != null && info.getType() != ConnectivityManager.TYPE_WIFI && playItem != null) {
            playItem.showDialog();
//            isWIFI = true;
            return;
        }
        if (path != null && !TextUtils.isEmpty(path)) {
            addMyCallback();
            if (mediaPlayer == null)
                initMediaPlayer();
            if (!isClikPlay) {
                num = 0;
//                if (!mediaPlayer.isPlaying())
                initTimerAndHandler();
                mTimer = new Timer();
                //开始一个定时任务
                mTimer.schedule(mTimerTask, 0, 500);
                myPlay(positio);
                isClikPlay = true;
                isPlaying = true;
                setWaitShow(true);
                start.setVisibility(View.GONE);
                start.setImageResource(R.drawable.pause_video);
                pause.setImageResource(R.drawable.pause_video);
            } else {
                pause();
            }
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (teadyToFinish) {
            if (isPlaying) {
                mediaPlayer.pause();
                start.setImageResource(R.drawable.pause_middle);
                pause.setImageResource(R.drawable.pause_middle);
                isPlaying = false;
                setWaitShow(false);
            } else {
                mediaPlayer.start();
                start.setImageResource(R.drawable.pause_video);
                pause.setImageResource(R.drawable.pause_video);
                isPlaying = true;
                setWaitShow(true);
            }
        }
    }

    public void publicPause() {
        if (teadyToFinish) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                start.setImageResource(R.drawable.pause_middle);
                pause.setImageResource(R.drawable.pause_middle);
                isPlaying = false;
                setWaitShow(false);
            }
        }
    }

    /**
     * 复位
     */
    private void reset() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
        } else {
            if (path != null) {
                play(0);
            }
        }
        if (playItem != null) {
            index = 0;
            playItem.item(index);
        }
        num = 0;
    }

    /**
     * 停止
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            seekbar.setProgress(0);
            seekbar.setSecondaryProgress(0);
            mediaPlayer.setOnBufferingUpdateListener(null);
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer = null;
        }
        if (mTimer != null) {
            if (mTimerTask != null) {
                mTimerTask.cancel();  //将原任务从队列中移除
                mTimerTask = null;  //将原任务从队列中移除
            }
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
//        mTimerTask = null;
//        handleProgress = null;
        isClikPlay = false;
        isPlaying = false;
        teadyToFinish = false;
        isInitTimer = false;
        isSetBackground = false;
        isWIFI = false;
        start.setImageResource(R.drawable.pause_middle);
        pause.setImageResource(R.drawable.pause_middle);
        wait.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
    }

    private void myPlay(final int position) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
//			mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.setDisplay(surfaceHolder);//TODO
            mediaPlayer.prepareAsync();//缓冲
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//todo 播放完毕回调
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isPlaying = false;
                    seekbar.setProgress(0);
                    start.setImageResource(R.drawable.pause_middle);
                    pause.setImageResource(R.drawable.pause_middle);
                    index = 0;
                    playItem.item(index);
                    num = 0;
                    seekbarLayout.setVisibility(View.VISIBLE);
                    setStartButShow(true);
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//todo 准备完毕回调
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mediaPlayer.getVideoHeight() != 0 && mediaPlayer.getVideoWidth() != 0)
                        mp.start();
                    if (position > 0) mediaPlayer.seekTo(position);
                    teadyToFinish = true;
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {//todo 缓存回调
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    seekbar.setSecondaryProgress(percent);
                    bufferingPosition = percent;
//                    background.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int videoHeight, videoWidth;

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        videoHeight = videoView.getHeight();
        videoWidth = videoView.getWidth();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }
        videoWidth = width;
        videoHeight = height;
    }

    private void addMyCallback() {
        if (videoWidth != 0 && videoHeight != 0)
            surfaceHolder.setFixedSize(videoWidth, videoHeight);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mediaPlayer == null)
                    initMediaPlayer();
                if (position > 0 && path != null) {
                    myPlay(position);
                    position = 0;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (null != mediaPlayer)
                    if (mediaPlayer.isPlaying()) {
                        position = mediaPlayer.getCurrentPosition();
                        mediaPlayer.stop();
                    }
            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try {
            if (null != mediaPlayer)
                myProgress = progress * mediaPlayer.getDuration() / seekBar.getMax();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        try {
            if (null != mediaPlayer) {
                mediaPlayer.seekTo(myProgress);
                int progress;
                for (int x = 0; x < end_times.size(); x++) {
                    progress = x == 0 ? 0 : end_times.get(x - 1);
                    if (myProgress >= progress) {
                        this.index = x;
                        playItem.item(index);
                        break;
                    }
                }
                num = 0;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void itemClik(int index) {
        if (null != mediaPlayer) {
            int progress;
            this.index = index;
            playItem.item(index);
            if (index != 0)
                progress = end_times.get(index - 1);
            else
                progress = 0;
            myProgress = progress * mediaPlayer.getDuration() / seekbar.getMax();
            mediaPlayer.seekTo(progress);
        }
    }

    private synchronized void initTimerAndHandler() {
        if (!isInitTimer) {
            isInitTimer = true;
            /*******************************************************
             * 通过定时器和Handler来更新进度条
             ******************************************************/
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (mediaPlayer != null && mediaPlayer.isPlaying() && !seekbar.isPressed())
                            handleProgress.sendEmptyMessage(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            handleProgress = new Handler() {
                public void handleMessage(Message msg) {
                    int playPosition = mediaPlayer.getCurrentPosition();
                    int duration = mediaPlayer.getDuration();
                    if (playPosition > end_times.get(index >= end_times.size() ? end_times.size() - 1 : index)) {
                        if (playItem != null) {
                            index++;
                            playItem.item(index);
                        }
                    }
                    if (duration > 0) {
                        long pos = seekbar.getMax() * playPosition / duration;
                        seekbar.setProgress((int) pos);
                        current_time.setText(getSpeendTimeStr(String.valueOf(playPosition / 1000)));
                        total_time.setText(getSpeendTimeStr(String.valueOf(duration / 1000)));
                        num++;
                        if (num == 8) {//8个500毫秒之后，底部的横条小布局消失
                            seekbarLayout.setVisibility(View.INVISIBLE);
                            setStartButShow(false);
                        }
//                        if (bufferingPosition <= pos && mediaPlayer.isPlaying()) {
                        setWaitShow(bufferingPosition <= pos && mediaPlayer.isPlaying());
//                        } else {
//                            setWaitShow(false);
//                        }

                        if (!isSetBackground && bufferingPosition > 0) {
                            background.setVisibility(View.GONE);
                            if (bitmap != null) {
                                bitmap.recycle();
                                bitmap = null;
                            }
                            isSetBackground = true;
                        }
                    }
                }
            };
        }
    }

    private int index = 0;

    public void setPath(String path) {
        this.path = path;
    }

    private void setStartButShow(boolean isShow) {
        if (isShow) {
            if (!wait.isShown())
                start.setVisibility(View.VISIBLE);
        } else {
            start.setVisibility(View.GONE);
        }
    }

    private void setWaitShow(boolean isShow) {
        if (isShow) {
            if (start.isShown())
                start.setVisibility(View.GONE);
            wait.setVisibility(View.VISIBLE);
        } else {
            if (seekbarLayout.isShown())
                start.setVisibility(View.VISIBLE);
            wait.setVisibility(View.GONE);
        }
    }

    private String getSpeendTimeStr(String s) {
        if (s != null) {
            long time = Integer.parseInt(s);
            long second = (time) % 60;
            long minut = (time / 60) % 60;
            long hour = (time / 3600) % 24;
//            long day = time / 3600 / 24;
            return (hour == 0 ? "" : hour + ":") + (minut == 0 ? "00:" : minut < 10 ? "0" + minut + ":" : minut + ":") + (second == 0 ? "00" : second < 10 ? "0" + second : second);
        } else {
            return "0";
        }
    }

    public void setEnd_times(List<Integer> end_times) {
        this.end_times = end_times;
    }

    private PlayItem playItem;

    public interface PlayItem {
        void item(int index);

        void showDialog();
    }
}
