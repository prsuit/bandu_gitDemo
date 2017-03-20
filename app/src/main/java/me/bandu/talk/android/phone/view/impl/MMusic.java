package me.bandu.talk.android.phone.view.impl;

import android.widget.SeekBar;

/**
 * Created by kiera1 on 16/6/7.
 */
public interface MMusic {
    //暂停播放
    void dopause();
    //继续播放
    void doresume();
    //开始播放
    void dostart();
    //停止播放
    void dostop();
    //初始化
    void init(SeekBar seekBar, onMusicOver over);
    void doRset();
    void doRevmo();
}
