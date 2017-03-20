package me.bandu.talk.android.phone.impl;

/**
 * 创建者：高楠
 * 时间：on 2016/3/3
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface AsyncListener {
    public final static int NEVER = 1000;
    public final static int ERROR = 1100;
    public final static int FINISH = 1200;
    public final static int CANCEL = 1300;
    public void onCancel();
    public void onFinish();
    public void onError();
}
