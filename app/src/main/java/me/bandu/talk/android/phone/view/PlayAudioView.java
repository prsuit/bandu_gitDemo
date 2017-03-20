package me.bandu.talk.android.phone.view;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import android.R.integer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.bandu.talk.android.phone.R;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class PlayAudioView extends RelativeLayout implements OnClickListener,
		OnCompletionListener,OnPreparedListener{
	private final String TAG = "com.fy.playertest.PlayView";
	private static final int FADE_OUT = 1;
	private static final int SHOW_PROGRESS = 2;
	private RelativeLayout rl_control;
	private ImageView iv_pause;
	private TextView tv_nowtime, tv_replay, tv_alltime;
	private ProgressBar pb_progress;
	private MediaPlayer mMediaPlayer;
	private String path;
	private List<String> paths;
	
	private boolean mIsVideoReadyToBePlayed;
	private int mPosition;
	private long duration;
	private int playPosition = -1;
	
	private PlayListener mListener;
	
	public PlayAudioView(Context context) {
		this(context, null);
	}

	public PlayAudioView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlayAudioView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		addView(LayoutInflater.from(context).inflate(R.layout.layout_audio,
				null));
		initView();
		initPlay();
		setListener();
	}
	
	public void setListener(PlayListener mListener){
		this.mListener = mListener;
	}

	private void initPlay() {
		mMediaPlayer = new MediaPlayer();
	}
	
	private void setListener() {
		iv_pause.setOnClickListener(this);
		tv_replay.setOnClickListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnPreparedListener(this);
	}

	private void initView() {
		rl_control = (RelativeLayout) findViewById(R.id.rl_control);
		iv_pause = (ImageView) findViewById(R.id.iv_pause);
		tv_nowtime = (TextView) findViewById(R.id.tv_nowtime);
		tv_replay = (TextView) findViewById(R.id.tv_replay);
		tv_alltime = (TextView) findViewById(R.id.tv_alltime);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
	}
	
	//播放进度
	/*@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	      long pos;
	      switch (msg.what) {
	        case FADE_OUT:
	          //hide();
	          break;
	        case SHOW_PROGRESS:
	          pos = setProgress();
	          msg = obtainMessage(SHOW_PROGRESS);
	          sendMessageDelayed(msg, 500 - (pos % 500));
	          break;
	      }
	    }
	  };*/

	public void setUrl(String path) {
		this.path = path;
		this.mIsVideoReadyToBePlayed = false;
		prePlay();
	}


	public void setUrl(List<String> paths) {
		this.paths = paths;
		this.mPosition = 0;
		this.playPosition = -1;
		this.mIsVideoReadyToBePlayed = false;
		if (paths != null) {
			tv_alltime.setText(paths.size() + "");
		}
	}
	
	//重新播放
	public void rePlay(){
		//pause();
		setUrl(paths);
		prePlay();
	}
	
	//暂停
	public void pause() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
			iv_pause.setImageResource(R.drawable.pause_middle);
		}
		//mHandler.removeMessages(SHOW_PROGRESS);
	}

	public void onPause(){
		pause();
	}

	public void onResume(){
		rePlay();
	}

	//销毁
	public void onDestory() {
		releaseMediaPlayer();
		doCleanUp();
	}
	

	/*public void seekTo(long startTime,long endTime){
		Toast.makeText(getContext(), "seekTo time:" + startTime + " duration:" + duration, Toast.LENGTH_SHORT)
		.show();
		if (startTime >= 0 && startTime <= duration) {
			pause();
			mMediaPlayer.seekTo((int) startTime);
			mMediaPlayer.start();
		}
		this.endTime = endTime;
	}*/
	//跳转
	public void seekTo(int position){
		if (paths != null && position >= 0 && position < paths.size()){
			mPosition = position;
			playPosition = position;
			prePlay();
		}
	}

	//播放或者暂停
	private void playOrPause() {
		if (mMediaPlayer.isPlaying()) {
			pause();
		}else {
			startVideoPlayback();
		}
	}
	
	//设置数据源并准备播放
	private void prePlay() {
		String urlString = null;
		if (paths != null && paths.size() > mPosition) {
			urlString = paths.get(mPosition);
		}
		if (urlString != null) {
			try {
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(urlString);
				mMediaPlayer.prepare();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	//销毁用的
	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	//销毁用的
	private void doCleanUp() {
		mIsVideoReadyToBePlayed = false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_pause:
			if (playPosition > 0){
				seekTo(playPosition);
			}else {
				playOrPause();
			}

			break;
		case R.id.tv_replay:
			rePlay();
			break;
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		/*Toast.makeText(getContext(), "onPrepared " + ((mPosition + 1) * 100 / paths.size()), Toast.LENGTH_SHORT)
		.show();*/
		mIsVideoReadyToBePlayed = true;
		duration = mp.getDuration();
		startVideoPlayback();
	}
	
	private void startVideoPlayback() {
		boolean play = mIsVideoReadyToBePlayed;
		if (play) {
			mMediaPlayer.start();
			iv_pause.setImageResource(R.drawable.pause_video);
			if (mListener != null) {
				mListener.onStartPlay();
			}
				tv_nowtime.setText(mPosition + 1 + "");
				if (paths.size() != 0) {
					pb_progress.setProgress((mPosition + 1) * 100 / paths.size());
					if (mListener != null) {
						mListener.onProgressChange(mPosition);
					}
				}
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		/*Toast.makeText(getContext(), "onCompletion " + ((mPosition + 1) * 100 / paths.size()), Toast.LENGTH_SHORT)
		.show();*/
			if (mPosition < paths.size() - 1 && playPosition == -1) {
				mPosition++;
				prePlay();
			}else {
				iv_pause.setImageResource(R.drawable.pause_middle);
				if (mListener != null) {
					mListener.onCompletion();
				}
			}
	}

	
	/*//设置进度
	private long setProgress() {
	    if (mMediaPlayer == null)
	      return 0;
	    
	    switch (type) {
		case LOCAL_AUDIO:
		case LOCAL_VIDEO:
			long position = mMediaPlayer.getCurrentPosition();
		    long duration = mMediaPlayer.getDuration();
		    if (pb_progress != null) {
		      if (duration > 0) {
		        long pos = 100L * position / duration;
		        pb_progress.setProgress((int) pos);
		      }
		    }
		    tv_nowtime.setText(millisToString(position));
		    if (mListener != null) {
				mListener.onProgressChange(type, position);
			}
		    return position;
		case LOCAL_AUDIO_LIST:
			break;
		}

	    return 0;
	  }*/
	
	/*private String millisToString(long millis) {
		boolean negative = millis < 0;
		millis = Math.abs(millis);

		millis /= 1000;
		int sec = (int) (millis % 60);
		millis /= 60;
		int min = (int) (millis % 60);
		millis /= 60;
		int hours = (int) millis;

		String time;
		DecimalFormat format = (DecimalFormat) NumberFormat
				.getInstance(Locale.US);
		format.applyPattern("00");

		if (millis > 0)
			time = (negative ? "-" : "") + hours + ":" + format.format(min)
					+ ":" + format.format(sec);
		else
			time = (negative ? "-" : "") + min + ":" + format.format(sec);
		return time;
	}*/
	
	public interface PlayListener{
		public void onProgressChange(int position);
		public void onCompletion();
		public void onStartPlay();
	}

}
