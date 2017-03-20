package me.bandu.talk.android.phone.impl;

import android.media.MediaPlayer;

/**
 * 创建者：高楠
 * 时间：on 2016/1/6
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface OnCompletionAndErrorListener {
    void onStartPlay();
    void onCompletion(MediaPlayer mp);
    void onPrePared();
    void onSeekPrepared();
    void onCurrentPosition(int position);
    boolean onError(MediaPlayer mp, int what, int extra);
}
