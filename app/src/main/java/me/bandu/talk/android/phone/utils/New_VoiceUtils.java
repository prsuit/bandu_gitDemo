package me.bandu.talk.android.phone.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.text.TextUtils;

import com.DFHT.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import me.bandu.talk.android.phone.service.NewDownLoadService;

/**
 * Author Gaonan
 * 音频工具类
 */
public class New_VoiceUtils implements OnCompletionListener, OnBufferingUpdateListener {
    private static New_VoiceUtils new_voiceUtils;
    private static VoiceListener voiceListener;

    public static New_VoiceUtils getInstance() {
        if (new_voiceUtils == null) {
            new_voiceUtils = new New_VoiceUtils();
        }
        return new_voiceUtils;
    }

    public static MediaPlayer mPlayer;// 唯一播放器
    private String url = "";

    //	public static int getVoiceLength(String path,Context context){
//		Uri uri = Uri.fromFile(new File(path));
//		MediaPlayer mediaPlayer = MediaPlayer.create(context, uri);
//		try {
//			mediaPlayer.setDataSource(path);
//			return  mediaPlayer.getDuration();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}
    public interface VoiceListener {
        void startVoice(String path);

        void endVoice(String path);

        void error(Exception e);
    }

    public static int getVoiceLength(String path) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();

        if (!TextUtils.isEmpty(path)) {
            if (path.contains("http://")) {
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    int duration = mediaPlayer.getDuration();
                    LogUtils.i("网络文件时长:"+duration);
                    return duration;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File file = new File(path);
                    FileInputStream fis = new FileInputStream(file);
                    mediaPlayer.setDataSource(fis.getFD());
                    mediaPlayer.prepare();
                    return mediaPlayer.getDuration();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return -1;
    }

    // 播放本地文件
    public void startVoiceLocal(String assetspath, Context context) {
        if (assetspath == null || assetspath.equals(""))
            return;
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                relaseData();
            }
        }
        try {
            AssetManager am = context.getAssets();//获得该应用的AssetManager
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            AssetFileDescriptor afd = am.openFd(assetspath);
            mPlayer.setDataSource(afd.getFileDescriptor());
            mPlayer.setLooping(false);// 设置不循环播放
            mPlayer.prepareAsync();
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp != null) {
                        mp.start();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("音乐播放异常");
            e.printStackTrace();
        }
    }

    public MediaPlayer getMediaPlay(){
        if (mPlayer == null)
            mPlayer = new MediaPlayer();
        return  mPlayer;
    }

    // 播放本地文件
    public void startVoiceFile(File file) {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        relaseData();
//		this.url = file.getName();
        try {
            if (mPlayer == null)
                mPlayer = new MediaPlayer();
            mPlayer.reset();
            FileInputStream fis = new FileInputStream(file);
            mPlayer.setDataSource(fis.getFD());
            mPlayer.setLooping(false);// 设置不循环播放
            mPlayer.prepareAsync();
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp != null && !isClickForExer) {
                        mp.start();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("音乐播放异常");
            e.printStackTrace();
        }
    }

    // 播放网络
    public void startVoiceNet(String url) {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        relaseData();
        try {
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            mPlayer.setDataSource(url);
            mPlayer.setLooping(false);// 设置不循环播放
            mPlayer.prepareAsync();
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp != null) {
                        mp.start();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("音乐播放异常");
            e.printStackTrace();
        }
    }

    Intent mIntent;

    public void startVoiceWithCache(Context contex, String url) {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        String path = FileUtil.DOWNLOAD_DIR + File.separator + MediaPathUtils.getFileNameHash(url);
        if (!path.contains("error.mp3")) {
            File file = new File(path);
            if (!file.exists() || file.length() == 0) {
                File fileDir = new File(FileUtil.DOWNLOAD_DIR);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                mIntent = new Intent(contex, NewDownLoadService.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("url", url);
                mIntent.putExtras(mBundle);
                contex.startService(mIntent);
                startVoiceNet(url);
            } else {
                startVoiceFile(file);
            }
        }
    }

    // 播放本地文件 带监听器
    public void startVoiceFileWithListener(final File file, final VoiceListener voiceListener) {
        this.voiceListener = voiceListener;
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        relaseData();
        try {
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            FileInputStream fis = new FileInputStream(file);
            mPlayer.setDataSource(fis.getFD());
            mPlayer.setLooping(false);// 设置不循环播放
            mPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (voiceListener != null) {
                        voiceListener.endVoice(file.getPath());
                    }
                }
            });
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (voiceListener != null) {
                        voiceListener.error(new Exception(file.getPath()));
                    }
                    return false;
                }
            });
            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp != null) {
                        if (voiceListener != null&&file!=null)
                            voiceListener.startVoice(file.getPath());
                        mp.start();
                    }
                }
            });
            mPlayer.prepareAsync();
        } catch (Exception e) {
            voiceListener.error(new Exception(file.getPath()));
            System.out.println("音乐播放异常");
            e.printStackTrace();
        }
    }

    // 播放本地文件
    public void startVoiceLocalWithListener(final String assetspath, Context context, final VoiceListener voiceListener) {
        this.voiceListener = voiceListener;
        if (assetspath == null || assetspath.equals(""))
            return;
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                relaseData();
            }
        }
        try {
            AssetManager am = context.getAssets();//获得该应用的AssetManager
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            AssetFileDescriptor afd = am.openFd(assetspath);
            mPlayer.setDataSource(afd.getFileDescriptor());
            mPlayer.setLooping(false);// 设置不循环播放
            mPlayer.prepareAsync();
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (voiceListener != null) {
                        voiceListener.error(new Exception(assetspath));
                    }
                    return false;
                }
            });
            mPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (voiceListener != null) {
                        voiceListener.endVoice(assetspath);
                    }
                }
            });
            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp != null) {
                        mp.start();
                        if (voiceListener != null) {
                            voiceListener.startVoice(assetspath);
                        }
                    }
                }
            });
        } catch (Exception e) {
            if (voiceListener != null) {
                voiceListener.error(new Exception(assetspath));
            }
            System.out.println("音乐播放异常");
            e.printStackTrace();
        }
    }

    // 播放网络
    public void startVoiceNetWithListener(final String url, final VoiceListener voiceListener) {
        this.voiceListener = voiceListener;
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        relaseData();
        try {
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            mPlayer.setDataSource(url);
            mPlayer.setLooping(false);// 设置不循环播放
            mPlayer.prepareAsync();
            mPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (voiceListener != null) {
                        voiceListener.endVoice(url);
                    }
                }
            });
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (voiceListener != null) {
                        voiceListener.error(new Exception(url));
                    }
                    return false;
                }
            });
            mPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp != null) {
                        if (voiceListener != null) {
                            voiceListener.startVoice(url);
                        }
                        mp.start();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("音乐播放异常");
            if (voiceListener != null) {
                voiceListener.error(new Exception(url));
            }
            e.printStackTrace();
        }
    }

    public void startVoiceWithCacheListener(Context contex, String url, VoiceListener voiceListener) {
        this.voiceListener = voiceListener;
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
        }
        String path = FileUtil.DOWNLOAD_DIR + File.separator + MediaPathUtils.getFileNameHash(url);
        if (!path.contains("error.mp3")) {
            File file = new File(path);
            if (!file.exists() || file.length() == 0) {
                File fileDir = new File(FileUtil.DOWNLOAD_DIR);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                mIntent = new Intent(contex, NewDownLoadService.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("url", url);
                mIntent.putExtras(mBundle);
                contex.startService(mIntent);
                startVoiceNetWithListener(url, voiceListener);
            } else {
//				startVoiceLocalWithListener(path,contex,voiceListener);
                startVoiceFileWithListener(file, voiceListener);
            }
        }
    }

    // 引入布尔变量防止过快点击导致录音时还在播放原音频文件
    private boolean isClickForExer;
    public void setIsClickForEx(boolean isClickForEx) {
        this.isClickForExer = isClickForEx;
    }

    // 暂停音乐
    public void pauseVoice() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }

        }
    }
    // 继续播放
    public void continueVoice() {
        if (mPlayer != null) {
            if (!mPlayer.isPlaying()) {
                mPlayer.start();
            }
        }
    }

    // 停止播放
    public static void stopVoice() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            if (voiceListener!=null){
                voiceListener.endVoice("");
            }
        }
    }

    public void relaseData() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public int getCurrentPosition() {
        if (mPlayer != null) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }


    public void stopService(Context contex) {
        if (mIntent != null)
            contex.stopService(mIntent);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        relaseData();
    }
}