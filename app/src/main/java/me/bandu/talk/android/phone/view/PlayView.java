package me.bandu.talk.android.phone.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
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

import com.DFHT.utils.LogUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import me.bandu.talk.android.phone.R;

/**
 * 视频 （自定义View）
 * @author gaoye
 *已不再使用
 */
@SuppressLint({ "HandlerLeak", "InflateParams" })
public class PlayView extends RelativeLayout implements OnClickListener,
		SurfaceHolder.Callback,OnCompletionListener,
		OnPreparedListener,OnVideoSizeChangedListener{
	private final String TAG = "com.fy.playertest.PlayView";
	private static final int FADE_OUT = 1;
	private static final int SHOW_PROGRESS = 2;
	private int mVideoWidth, mVideoHeight;
	private SurfaceHolder holder;
	private SurfaceView sv_screen;
	private RelativeLayout rl_control;
	private ImageView iv_pause,iv_screen;
	private TextView tv_nowtime, tv_replay, tv_alltime;
	private ProgressBar pb_progress;
	private MediaPlayer mMediaPlayer;
	private String path;

	private boolean mIsVideoSizeKnown,mIsVideoReadyToBePlayed,mIsVideoInit;
	private int mPosition;
	private long duration;
	private long endTime;
	private long startTme;
	private boolean isStart;

	private PlayListener mListener;
	
	public PlayView(Context context) {
		this(context, null);
	}

	public PlayView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		addView(LayoutInflater.from(context).inflate(R.layout.layout_vedio,
				null));
		initView();
		initPlay();
		setListener();
	}
	
	public void setListener(PlayListener mListener){
		this.mListener = mListener;
	}

	private void initPlay() {
		holder = sv_screen.getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		mMediaPlayer = new MediaPlayer();
	}
	
	private void setListener() {
		iv_pause.setOnClickListener(this);
		tv_replay.setOnClickListener(this);
		holder.addCallback(this);
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnVideoSizeChangedListener(this);
	}

	private void initView() {
		sv_screen = (SurfaceView) findViewById(R.id.sv_screen);
		rl_control = (RelativeLayout) findViewById(R.id.rl_control);
		iv_pause = (ImageView) findViewById(R.id.iv_pause);
		iv_screen = (ImageView) findViewById(R.id.iv_screen);
		tv_nowtime = (TextView) findViewById(R.id.tv_nowtime);
		tv_replay = (TextView) findViewById(R.id.tv_replay);
		tv_alltime = (TextView) findViewById(R.id.tv_alltime);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
	}
	
	//播放进度
	@SuppressLint("HandlerLeak")
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
				if (pos != -1){
					msg = obtainMessage(SHOW_PROGRESS);
					sendMessageDelayed(msg, 500 - (pos % 500));
				}
	          break;
	      }
	    }
	  };

	public void setUrl(String path) {
		this.path = path;
		this.mIsVideoReadyToBePlayed = false;
		this.endTime = 0;
		this.startTme = 0;
	}


	
	//重新播放
	public void rePlay(){
		//pause();
		//setUrl(path);
		prePlay();
	}
	
	//暂停
	public void pause() {
		LogUtils.i("pause");
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
			iv_pause.setImageResource(R.drawable.pause_middle);
		}
		mHandler.removeMessages(SHOW_PROGRESS);
	}

	public void onPause(){
		pause();
	}

	public void onResume(){
		//为毛handler中正常？
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				rePlay();
			}
		});
	}

	//销毁
	public void onDestory() {
		releaseMediaPlayer();
		doCleanUp();
	}
	
	//跳转
	public void seekTo(long startTime,long endTime){
		/*Toast.makeText(getContext(), "seekTo time:" + startTime + " duration:" + duration, Toast.LENGTH_SHORT)
		.show();*/
		if (startTime >= 0 && startTime <= duration) {
			mMediaPlayer.seekTo((int) startTime);
			if (!mMediaPlayer.isPlaying())
				startVideoPlayback();
		}
		this.endTime = endTime;
		this.startTme = startTime;
	}


	//播放或者暂停
	private void playOrPause() {
		if (mMediaPlayer.isPlaying()) {
			pause();
		}else {
			startVideoPlayback();
		}
	}
	
	/*//设置数据源
	private void startPlay(){
		switch (type) {
		case LOCAL_AUDIO:
		case LOCAL_VIDEO:
			prePlay(path);
			break;
		case LOCAL_AUDIO_LIST:
			if (paths != null && paths.size() > mPosition) {
				String urlString = paths.get(mPosition);
				setPath(urlString);
			}
			break;
		}
	}*/
	//设置数据源并准备播放
	private void prePlay() {
		if (path != null) {
			try {
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.prepareAsync();
				isStart = true;
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
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_pause:
			if (endTime > 0){
				seekTo(startTme,endTime);
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
	public void surfaceCreated(SurfaceHolder holder) {
		Toast.makeText(getContext(), "surfaceCreated", Toast.LENGTH_SHORT)
				.show();
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		if (mIsVideoInit) {
			startVideoPlayback();
		}else {
			mIsVideoInit = true;
			prePlay();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		/*Toast.makeText(getContext(), "surfaceDestroyed", Toast.LENGTH_SHORT)
				.show();*/
		mHandler.removeMessages(SHOW_PROGRESS);
		pause();
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if (width == 0 || height == 0) {
			return;
		}
		mVideoWidth = width;
		mVideoHeight = height;
		mIsVideoSizeKnown = true;
		startVideoPlayback();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mIsVideoReadyToBePlayed = true;
		duration = mp.getDuration();
		tv_alltime.setText(millisToString(duration));
		startVideoPlayback();
	}
	
	private void startVideoPlayback() {
		boolean play = mIsVideoReadyToBePlayed && mIsVideoSizeKnown && mIsVideoInit;
		if (play) {
			if (iv_screen.getVisibility() != GONE){
				iv_screen.setVisibility(GONE);
			}
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.start();
			iv_pause.setImageResource(R.drawable.pause_video);
			if (mListener != null && isStart) {
				mListener.onStartPlay();
			}
			isStart = false;
			mHandler.sendEmptyMessage(SHOW_PROGRESS);
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		/*Toast.makeText(getContext(), "onCompletion " + ((mPosition + 1) * 100 / paths.size()), Toast.LENGTH_SHORT)
		.show();*/
		iv_pause.setImageResource(R.drawable.pause_middle);
		if (mListener != null) {
			mListener.onCompletion();
		}
	}

	
	//设置进度
	private long setProgress() {
	    if (mMediaPlayer == null)
	      return 0;
			long position = mMediaPlayer.getCurrentPosition();
		    long duration = mMediaPlayer.getDuration();
		    if (pb_progress != null) {
		      if (duration > 0) {
		        long pos = 100L * position / duration;
		        pb_progress.setProgress((int) pos);
		      }
		    }
		    tv_nowtime.setText(millisToString(position));

			LogUtils.i("position:" + position + " endTime" + endTime);
			if (endTime > 0 && position >= endTime){
					pause();
					return -1;
			}else {
				if (endTime == 0 && mListener != null) {
					mListener.onProgressChange(position);
				}
			}
		    return position;
	  }
	
	private String millisToString(long millis) {
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
	}
	
	public interface PlayListener{
		public void onProgressChange(long position);
		public void onCompletion();
		public void onStartPlay();
	}

}
