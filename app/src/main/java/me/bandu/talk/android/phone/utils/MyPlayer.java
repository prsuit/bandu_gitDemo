package me.bandu.talk.android.phone.utils;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.DFHT.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import me.bandu.talk.android.phone.impl.OnCompletionAndErrorListener;
/**
 * Author Gaonan
 * 播放器
 */
public class MyPlayer implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback, OnErrorListener, MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnVideoSizeChangedListener {

	public static final String TAG = "Player";
	private TextView current_time;
	private TextView total_time;
	public MediaPlayer mediaPlayer;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private ProgressBar progressBar;
	private Timer mTimer = new Timer();
	private String url;
	private int duration;
	private boolean isprepared = false;
	private int mVideoWidth, mVideoHeight;

	public MyPlayer(SurfaceView surfaceView, ProgressBar progressBar, TextView tv_current, TextView tv_total, String url, boolean isViado, OnCompletionAndErrorListener completionAndErrorListener) {
		this.surfaceView = surfaceView;
		this.progressBar = progressBar;
		this.current_time = tv_current;
		this.total_time = tv_total;
		this.completionAndErrorListener = completionAndErrorListener;
		this.url = url;
		if (isViado){
			surfaceHolder = this.surfaceView.getHolder();
			surfaceHolder.addCallback(this);
			surfaceView.setZOrderOnTop(false);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}else {
			try {
				mediaPlayer = new MediaPlayer();
				Log.i(TAG, "-----url--------" + url);
				mediaPlayer.reset();
				File file = new File(url);
				FileInputStream fileInputStream = new FileInputStream(file);
				mediaPlayer.setDataSource(fileInputStream.getFD());
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setOnBufferingUpdateListener(this);
				mediaPlayer.setOnPreparedListener(this);
				mediaPlayer.setOnErrorListener(this);
				mediaPlayer.setOnCompletionListener(this);
				mediaPlayer.setOnSeekCompleteListener(this);
				mediaPlayer.setOnVideoSizeChangedListener(this);
				//mediaPlayer.prepareAsync();// prepare之后自动播放
				mediaPlayer.prepare();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mTimer.schedule(mTimerTask, 0, 1);
	}

	public void setUrl(String url){
		if (mediaPlayer == null)
			mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		try {
			File file = new File(url);
			FileInputStream fileInputStream = new FileInputStream(file);
			mediaPlayer.setDataSource(fileInputStream.getFD());
			mediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public MyPlayer(SurfaceView surfaceView, ProgressBar progressBar, TextView current_time, TextView total_time, String url, OnCompletionAndErrorListener completionAndErrorListener) {
//		this.surfaceView = surfaceView;
//		this.progressBar = progressBar;
//		this.current_time = current_time;
//		this.total_time = total_time;
//		this.completionAndErrorListener = completionAndErrorListener;
//		this.url = url;
//		surfaceHolder = this.surfaceView.getHolder();
//		surfaceHolder.addCallback(this);
//		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		mTimer.schedule(mTimerTask, 0, 10);
//	}

	private TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			boolean flag = false;
			try {
				flag = mediaPlayer.isPlaying();
			}catch (Exception e){
				e.printStackTrace();
				LogUtils.e("您的播放器处于异常状态");
			}
			if (mediaPlayer != null && flag && isprepared) {
				progressHandler.sendEmptyMessage(0);
			}
		}
	};
	@SuppressLint("HandlerLeak")
	private Handler progressHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mediaPlayer!=null){
				int position = mediaPlayer.getCurrentPosition();
				if (completionAndErrorListener!=null){
					completionAndErrorListener.onCurrentPosition(position);
				}
				if (current_time!=null){
					current_time.setText(secondToTime(position));
				}
				if (duration>0){
					progressBar.setProgress(position);
				}
			}
		}
	};
	public void play() {
		if (mediaPlayer != null) {
			mediaPlayer.start();
			if (completionAndErrorListener != null) {
				completionAndErrorListener.onStartPlay();
			}
		}
	}

	public void prepare(){
		if (mediaPlayer != null){
			try {
				mediaPlayer.prepare();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void pause() {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}
	}

	public void seekPositionPlay(int position){
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()){
				mediaPlayer.pause();
			}
			mediaPlayer.seekTo(position);
		}
	}
	public boolean isPlaying() {
		if (mediaPlayer != null) {
			return mediaPlayer.isPlaying();
		}
		return false;
	}

	public void desdroy() {
		if (mTimer != null) {
			mTimer.cancel();
		}
		if (progressHandler != null) {
			progressHandler.removeCallbacksAndMessages(null);
		}
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		Log.i(TAG, "surface changed");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(TAG, "surface created");
		try {
			if (mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setDisplay(holder);
				mediaPlayer.reset();
				mediaPlayer.setDataSource(url);
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setOnBufferingUpdateListener(this);
				mediaPlayer.setOnPreparedListener(this);
				mediaPlayer.setOnErrorListener(this);
				mediaPlayer.setOnCompletionListener(this);
				mediaPlayer.setOnSeekCompleteListener(this);
				Log.i(TAG, "-----url--------" + url);
				mediaPlayer.prepareAsync();// prepare之后自动播放
			} else {
				mediaPlayer.setDisplay(holder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i(TAG, "surface destroyed");
	}
	public boolean isPrepared(){
		return isprepared;
	}
	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.i(TAG, "onPrepared");
		duration = mp.getDuration();
		if (progressBar!=null)
			progressBar.setMax(duration);
		isprepared = true;
		if (total_time!=null){
			total_time.setText(secondToTime(duration));
		}
		if (completionAndErrorListener != null) {
			completionAndErrorListener.onPrePared();
		}
	}
	public String secondToTime(int duration){
		duration = duration/1000;
		int second = duration%60;
		int minut = duration/60;
		return (minut==0?"00":(minut<10?"0"+minut:minut+""))+":"+(second==0?"00":(second<10?"0"+second:second+""));
	}
	@Override
	public void onSeekComplete(MediaPlayer mp) {
		mp.start();
		if (completionAndErrorListener != null) {
			completionAndErrorListener.onSeekPrepared();
		}
	}
	private OnCompletionAndErrorListener completionAndErrorListener;
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		progressBar.setProgress(0);
		current_time.setText("00:00");
		if (completionAndErrorListener != null) {
			completionAndErrorListener.onCompletion(mp);
		}
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.i(TAG, "onError---what : " + what + " extra : " + extra);
		if (completionAndErrorListener != null) {
			completionAndErrorListener.onError(mp, what, extra);
		}
		return false;
	}
	public int getDuration(){
		return duration;
	}

	public long getCurrentTime(){
		if (mediaPlayer != null)
			return mediaPlayer.getCurrentPosition();
		else
			return 0;
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if (width == 0 || height == 0) {
			return;
		}
		mVideoWidth = width;
		mVideoHeight = height;
	}
}
