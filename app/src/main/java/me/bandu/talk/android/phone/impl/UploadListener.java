package me.bandu.talk.android.phone.impl;

import java.io.File;

/**
 * 创建者：高楠
 * 时间：on 2016/3/3
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface UploadListener {
    int NEVER = 1000;
    int ERROR = 1100;
    int FINISH = 1200;
    int CANCEL = 1300;
    int ZIP = 1400;
    int ONE = 1;
    int MORE = 2;
    void uploadResult(int state, int flag, File file);
}
