package com.DFHT.voiceengine;

/**
 * 创建者：Mcablylx
 * 时间：2015/12/7 17:41
 * 类描述：驰声初始化 回调接口
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface OnSpeechEngineLoaded {

    int ONLINE_OK = 1;

    int OFFLINE_OK = 2;

    int ALL_OK = 3;

    void onLoadSuccess(int state);

    void onLoadError();
}