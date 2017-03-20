package me.bandu.talk.android.phone.impl;

/**
 * 创建者：高楠
 * 时间：on 2016/1/6
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface OnViedoViewPositionListener{
    void currentPosition(int position);
    void prepared();
    void onCompletion();
    void onReplay();
}
