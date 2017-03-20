package me.bandu.talk.android.phone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DFHT.utils.UIUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.bandu.talk.android.phone.R;
import me.bandu.talk.android.phone.impl.OnCompletionAndErrorListener;
import me.bandu.talk.android.phone.impl.OnViedoViewPositionListener;
import me.bandu.talk.android.phone.utils.MyPlayer;

/**
 * 视频 （自定义View）
 *
 * @author 高楠
 */
public class VedioView extends RelativeLayout implements OnClickListener, OnCompletionAndErrorListener {
    private Context mContext;
    private SurfaceView surfaceView;
    private ProgressBar progressBar;
    private ProgressBar reg_req_code_gif_view;
    private MyPlayer player;
    private String soururl = "";
    private ImageView iv_pause;
    //private ImageView iv_play;
    private ImageView iv_background;
    private ImageView iv_play;
    private TextView tv_current, tv_total, replay;
    private LinearLayout linear_container;
    private boolean isViado;
    private OnViedoViewPositionListener listener;
    private long pausetime;
    private int endTime;
    private int startTime;

    public VedioView(Context context) {
        this(context, null);
    }

    public VedioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VedioView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        createView();
    }

    public void createView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vedio_layout, null);
        addView(view);
        initView(view);
    }

    private void initView(View view) {
        tv_current = (TextView) view.findViewById(R.id.current_time);
        tv_total = (TextView) view.findViewById(R.id.total_time);
        replay = (TextView) view.findViewById(R.id.replay);
        replay.setOnClickListener(this);
        iv_background = (ImageView) view.findViewById(R.id.iv_background);
        iv_pause = (ImageView) view.findViewById(R.id.iv_pause);
        iv_play = (ImageView) view.findViewById(R.id.iv_start);
        iv_pause.setOnClickListener(this);
        linear_container = (LinearLayout) view.findViewById(R.id.linear_container);
        surfaceView = (SurfaceView) view.findViewById(R.id.surfaceview);
        surfaceView.setOnClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        reg_req_code_gif_view = (ProgressBar) view.findViewById(R.id.reg_req_code_gif_view);
        iv_play.setOnClickListener(this);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            if (player != null && player.isPlaying()) {
                playerPause();
            }
        }
    }

    public void setViewURL(String url, boolean isViado, OnViedoViewPositionListener listener) {
        this.soururl = url;
        this.isViado = isViado;
        this.listener = listener;

        if (player == null)
            player = new MyPlayer(surfaceView, progressBar, tv_current, tv_total, soururl, isViado, this);
        else
            player.setUrl(url);
        setFrame();
    }

    public void setFrame() {
        if (isViado) {
            Bitmap bitmap = getFrame(soururl);
            if (bitmap != null) {
                iv_background.setVisibility(VISIBLE);
                iv_background.setImageBitmap(bitmap);
            } else {
                bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.no_voice);
                iv_background.setVisibility(VISIBLE);
                iv_background.setImageBitmap(bitmap);
            }
        } else {
            iv_background.setImageResource(R.mipmap.no_voice);
        }
    }


    public Bitmap getFrame(String url) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            File file = new File(url);
            FileInputStream fileInputStream = new FileInputStream(file);
            retriever.setDataSource(fileInputStream.getFD());
            Bitmap bitmap = retriever.getFrameAtTime(pausetime * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
            retriever.release();
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_start:
                if (endTime != 0) {
                    playerSeekToPosition(startTime, endTime);
                } else {
                    playerPlay();
                }

                break;
            case R.id.iv_pause:
                if (isPlaying()) {
                    playerPause();
                } else {
                    if (endTime != 0) {
                        playerSeekToPosition(startTime, endTime);
                    } else {
                        playerPlay();
                    }
                }
                break;
            case R.id.surfaceview:
                if (!isPause) {
                    if (isHiding) {
                        setViedoPlay();
                    } else {
                        setContainerHide();
                    }
                }
                break;
            case R.id.replay:
                if (listener != null)
                    listener.onReplay();
                break;
        }
    }

    public void playerPlay() {
        setViedoPlay();
        if (player != null)
            player.play();
    }

    public void rePlay() {
        setViedoPlay();
        if (player != null)
            player.seekPositionPlay(0);
    }

    public void playerPause() {
        setViedoPause();
        pausetime = player.getCurrentTime();
        if (player != null)
            player.pause();
    }

    public void playerSeekToPosition(int startPosition, int endPosition) {
        if (player != null && player.isPrepared()) {
            setWaiting();
            player.seekPositionPlay(startPosition);
            this.endTime = endPosition;
            this.startTime = startPosition;
        }
    }

    public void setViedoPlay() {
        isHiding = false;
        isPause = false;
        iv_pause.setImageResource(R.drawable.pause_video);
        iv_play.setVisibility(View.GONE);
        linear_container.setVisibility(VISIBLE);
        postTimeHideContainer();
    }

    private boolean isHiding = true;
    private boolean isPause = true;

    private void setViedoPause() {
        isPause = true;
        iv_play.setVisibility(View.VISIBLE);
        iv_pause.setImageResource(R.drawable.pause_middle);
        //linear_container.setVisibility(VISIBLE);
        //postTimeHideContainer();
    }

    private void setContainerHide() {
        iv_play.setVisibility(View.GONE);
        iv_pause.setImageResource(R.drawable.pause_video);
        linear_container.setVisibility(GONE);
        isHiding = true;
    }

    private void setWaiting() {
        reg_req_code_gif_view.setVisibility(VISIBLE);
        iv_play.setVisibility(View.GONE);
        iv_pause.setImageResource(R.drawable.pause_video);
    }

    private void setPrepared() {
        reg_req_code_gif_view.setVisibility(GONE);
        iv_pause.setImageResource(R.drawable.pause_middle);
        iv_play.setVisibility(View.VISIBLE);
    }

    private void setSeekOver() {
        reg_req_code_gif_view.setVisibility(GONE);
        setViedoPlay();
    }

    @Override
    public void onStartPlay() {
        if (isViado) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_background.setVisibility(GONE);
                }
            }, 200);
        } else {
            iv_background.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        iv_background.setVisibility(VISIBLE);
        setViedoPause();
        if (listener != null) {
            listener.onCompletion();
        }
    }

    @Override
    public void onPrePared() {
        setPrepared();
        if (listener != null) {
            listener.prepared();
        }
        pausetime = 0;
        playerPlay();
    }

    @Override
    public void onSeekPrepared() {
        if (isViado) {
            iv_background.setVisibility(GONE);
        } else {
            iv_background.setVisibility(VISIBLE);
        }
        setSeekOver();
    }

    @Override
    public void onCurrentPosition(int position) {
        if (endTime != 0) {
            if (position >= endTime) {
                if (isPlaying()) {
                    playerPause();
                }
            } else {
                if (listener != null) {
                    listener.currentPosition(position);
                }
            }
        } else {
            if (listener != null) {
                listener.currentPosition(position);
            }
        }
    }

    public void clearTime() {
        endTime = 0;
        startTime = 0;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        setViedoPause();
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN && extra == -1004) {
            Toast.makeText(mContext, "播放出错，视频源有误！", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //判断是否在播放
    public boolean isPlaying() {
        if (player != null)
            return player.isPlaying();
        return false;
    }

    public void onPause() {
        playerPause();
    }

    public void onDestory() {
        if (player != null)
            player.desdroy();
    }

    public void onResume() {
        if (player != null) {
            setFrame();
            if (!isPlaying()) {
                /*if (endTime != 0){
					playerSeekToPosition(startTime,endTime);
				}else {
					playerPlay();
				}*/
                if (listener != null)
                    listener.onReplay();
            }
        }
    }

    private void postTimeHideContainer() {
        if (isViado) {
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    linear_container.setVisibility(GONE);
                    isHiding = true;
                }
            }, 3000);
        }
    }

}
